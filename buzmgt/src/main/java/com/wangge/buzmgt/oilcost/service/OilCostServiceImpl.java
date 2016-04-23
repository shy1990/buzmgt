package com.wangge.buzmgt.oilcost.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wangge.buzmgt.oilcost.entity.OilCost;
import com.wangge.buzmgt.oilcost.entity.OilRecord;
import com.wangge.buzmgt.oilcost.repository.OilCostRepository;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesManPart;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.JSONUtil;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class OilCostServiceImpl implements OilCostService {

  private static final Logger logger = Logger.getLogger(OilCostServiceImpl.class);

  @Autowired
  private OilCostRepository oilCostRepository;

  @Autowired
  private RegionService regionService;

  @Override
  public List<OilCost> findAll() {
    return oilCostRepository.findAll();
  }

  @Override
  public Long findCount() {
    return oilCostRepository.count();
  }

  @Override
  public List<OilCost> findAll(Map<String, Object> searchParams) {
    disposeSearchParams(searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OilCost> spec = oilCostSearchFilter(filters.values(), OilCost.class);
    List<OilCost> list=oilCostRepository.findAll(spec);
    disposeOilCostList(list);

    return list;
  }
  /**
   * 处理条件参数
   * 区域选择（油补统计）
   * @param searchParams
   */
  public void disposeSearchParams(Map<String, Object> searchParams){
    String regionId = (String) searchParams.get("regionId");
    String regionType = (String) searchParams.get("regionType");
//    COUNTRY("国"), PARGANA("大区"), PROVINCE("省"), AREA("区"), CITY("市"), COUNTY("县"), TOWN("镇"), OTHER("其他")
    //TODO 整理查询出来的regionID列表
    String regionArr="";
    switch (regionType) {
    case "COUNTRY":
      break;
    case "PARGANA":
      
    case "PROVINCE":
      regionArr = disposeRegionId(regionId);
      regionArr=regionArr.substring(0, regionArr.length()-1);
      break;
    case "AREA":
      regionArr = regionId.substring(0, 4);
      break;

    default:
      regionArr =regionId;
      break;
    }
    searchParams.put("ORMLK_userId", regionArr);
    searchParams.remove("regionId");
    searchParams.remove("regionType");
    
  }
  /**
   * 根据每一个regionType判断 regionId截取的位数
   * type-->count:国家-->all
   * 
   * 
   * @param regionList
   * @return String 格式 "3701,3702,xxxx,xxx"
   */
  public String disposeRegionId(String regionId){
    //3701,
    String regionArr="";
    List<Region> regionList=regionService.findByRegion(regionId); 
    for(int n=0;n<regionList.size();n++){
      Region region= regionList.get(n);
      String regionId1=region.getId();
      if(RegionType.AREA.equals(region.getType())){
        regionArr+=regionId1.substring(0, 4)+",";
        continue;
      }
      regionArr+=disposeRegionId(regionId1);
    }
      
    return regionArr;
  }
  /**
   * 处理salesMan信息
   */
  public void disposeSalesMan(SalesMan salesMan){
    salesMan.setRegion(null);
    salesMan.setUser(null);
  }
  /**
   * 处理oilCostlist
   * @param list
   */
  public void disposeOilCostList(List<OilCost> list){
    list.forEach(l->{
      String oilRecord=l.getOilRecord();
      l.setOilRecordList(JSONUtil.stringArrtoJsonList(oilRecord, OilRecord.class));
      l.setOilRecord("");
      String orgName="";
      String regName="";
      String turename="";
      String id ="";
      try {
        SalesMan salesman=l.getSalesMan();
        id=salesman.getId();
        orgName=salesman.getUser().getOrganization().getName();
        regName=salesman.getRegion().getName();
        turename=salesman.getTruename();
      } catch (Exception e) {
        e.getMessage();
      }
      l.setSalesManPart(new SalesManPart(id,turename,regName,orgName));
    });
  }

  @Override
  public Page<OilCost> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    disposeSearchParams(searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OilCost> spec = oilCostSearchFilter(filters.values(), OilCost.class);
    Page<OilCost> page=oilCostRepository.findAll(spec, pageRequest);
    disposeOilCostList(page.getContent());
    return page;
  }

  @Override
  public Page<OilCost> findGroupByUserId(Map<String, Object> searchParams, Pageable pageRequest) {
    //查询所有油补信息
    List<OilCost> list = findAll(searchParams);
    //存储统计数据
    Map<String, OilCost> oilCostMap = new HashMap<String, OilCost>();
    // 统计后列表
    List<OilCost> oilCostList = new ArrayList<OilCost>();
    Page<OilCost> page = null;
    if (list.size() < 1) {
      return new PageImpl<OilCost>(list,pageRequest,list.size());
    }
    int total=0;
    int number=pageRequest.getPageNumber();
    int size=pageRequest.getPageSize();
    for(OilCost action:list) {
      String parentId = action.getParentId();
      String userId = StringUtils.isNotEmpty(parentId)? parentId: action.getUserId();
      OilCost o = oilCostMap.get(userId);
      if(o==null){
        if(number*size <= total && total < (number+1)*size){
          //添加条数
          oilCostMap.put(userId, action);
        }
        total++;
      }else{
        //操作数据
        Float totalDistance = o.getTotalDistance();
        Float distance = action.getDistance();
        totalDistance += distance;
        Float oilTotalCost = o.getOilTotalCost();
        Float oilCost = action.getOilCost();
        oilTotalCost += oilCost;
        o.setTotalDistance(totalDistance);
        o.setOilTotalCost(oilTotalCost);

      }
    }
    //所有油补统计列表
    Collection<OilCost> coc = oilCostMap.values();
    //
    coc.forEach(ocl->{
      oilCostList.add(ocl);
    });
    page = new PageImpl<OilCost>(oilCostList, pageRequest, total);
    return page;
  }

  private static <T> Specification<OilCost> oilCostSearchFilter(final Collection<SearchFilter> filters,
      final Class<OilCost> entityClazz) {

    return new Specification<OilCost>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<OilCost> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (CollectionUtils.isNotEmpty(filters)) {
          List<Predicate> predicates = new ArrayList<Predicate>();
          for (SearchFilter filter : filters) {
            // nested path translate, 如Task的名为"user.name"的filedName,
            // 转换为Task.user.name属性
            String[] names = StringUtils.split(filter.fieldName, ".");
            Path expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
              expression = expression.get(names[i]);
            }

            String javaTypeName = expression.getJavaType().getName();

            // logic operator
            switch (filter.operator) {
            case EQ:
              // 日期相等,小于等于最大值,大于等于最小值
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  predicates.add(cb.greaterThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                  predicates.add(cb.lessThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.equal(expression, filter.value));
              }

              break;
            case LIKE:
              predicates.add(cb.like(expression, "%" + filter.value + "%"));

              break;
            case GT:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 大于最大值
                  predicates.add(cb.greaterThan(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.greaterThan(expression, (Comparable) filter.value));
              }

              break;
            case LT:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 小于最小值
                  predicates.add(cb.lessThan(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.lessThan(expression, (Comparable) filter.value));
              }

              break;
            case GTE:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 大于等于最小值
                  predicates.add(cb.greaterThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                } catch (ParseException e) {
                  e.printStackTrace();
                }
              } else {
                predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));
              }

              break;
            case LTE:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 小于等于最大值
                  predicates.add(cb.lessThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.value));
              }

              break;
            case NOTEQ:
              predicates.add(cb.notEqual(expression, filter.value));

              break;
            case ISNULL:

              predicates.add(cb.isNull(expression));

              break;
            case NOTNULL:

              predicates.add(cb.isNotNull(expression));

              break;
            case ORE:
              /**
               * sc_OR_userId = praentId_A37010506130
               */
              String[] parameter = ((String) filter.value).split("_");
              Path expression_ = root.get(parameter[0]);
              String value_ = parameter[1];
              Predicate p = cb.or(cb.equal(expression_, value_), cb.equal(expression, value_));
              predicates.add(p);

              break;
            case ORLK:
              /**
               * sc_ORLK_userId = praentId_A37010506130
               */
              String[] parameter_LK = ((String) filter.value).split("_");
              Path expression_LK = root.get(parameter_LK[0]);
              String value_LK= parameter_LK[1];
              Predicate p_LK = cb.or(cb.like(expression_LK, value_LK), cb.like(expression, value_LK));
              predicates.add(p_LK);
              
              break;
            case ORMLK:
              /**
               * sc_ORMLK_userId = 370105,3701050,3701051
               */
              String[] parameterValue = ((String) filter.value).split(",");
              Predicate[] pl=new Predicate[parameterValue.length];
              
              for(int n=0;n<parameterValue.length;n++){
                pl[n]=(cb.like(expression, "%"+parameterValue[n]+"%"));
              }
              
              Predicate p_ = cb.or(pl);
              predicates.add(p_);
              
              break;

            }
          }

          // 将所有条件用 and 联合起来
          if (!predicates.isEmpty()) {
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        }

        return cb.conjunction();
      }
    };
  }

}
