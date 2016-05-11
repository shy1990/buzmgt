package com.wangge.buzmgt.ordersignfor.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.OrderPayType;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.receipt.entity.RemarkStatusEnum;
import com.wangge.buzmgt.receipt.service.OrderReceiptServiceImpl;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class OrderSignforServiceImpl implements OrderSignforService {

 private static final Logger logger = Logger.getLogger(OrderSignforServiceImpl.class);

  @PersistenceContext  
  private EntityManager em; 
  @Autowired
  private OrderSignforRepository orderSignforRepository;
  
  @Autowired
  private SalesManService salesManService;
  @Autowired
  private RegionService regionService;
  
  @Override
  public void updateOrderSignfor(OrderSignfor xlsOrder) {
    orderSignforRepository.save(xlsOrder);
    
  }
  @Override
  public OrderSignfor findByOrderNo(String orderNo) {
    return  orderSignforRepository.findByOrderNo(orderNo);
  }
  
  @Override
  public List<OrderSignfor> findAll(){
    return orderSignforRepository.findAll(); 
  }


  @Override
  public Long findCount() {
    return orderSignforRepository.count();
  }

  @Override
  public List<OrderSignfor> findAll(Map<String, Object> searchParams) {
    disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OrderSignfor> spec = orderSignforSearchFilter(filters.values(), OrderSignfor.class);
    return orderSignforRepository.findAll(spec);
  }

  @Override
  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest) {
    disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OrderSignfor> spec = orderSignforSearchFilter(filters.values(), OrderSignfor.class);

    return orderSignforRepository.findAll(spec,pageRequest);
  }

  /**
   * 处理条件参数
   * 区域选择（油补统计）
   * @param searchParams
   */
  public void disposeSearchParams(String userId,Map<String, Object> searchParams){
    String regionId = (String) searchParams.get("regionId");
    String regionType = (String) searchParams.get("regionType");
//    COUNTRY("国"), PARGANA("大区"), PROVINCE("省"), AREA("区"), CITY("市"), COUNTY("县"), TOWN("镇"), OTHER("其他")
    //TODO 整理查询出来的regionID列表
    String regionArr="";
    if(StringUtils.isNotEmpty(regionType)){
      
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
    }
    searchParams.put("ORMLK_"+userId, regionArr);
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
  
  private static <T> Specification<OrderSignfor> orderSignforSearchFilter(final Collection<SearchFilter> filters,
      final Class<OrderSignfor> entityClazz) {

    return new Specification<OrderSignfor>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDER_PAY_TYPE = "com.wangge.buzmgt.ordersignfor.entity.OrderSignfor$OrderPayType";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<OrderSignfor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
              } else if (javaTypeName.equals(TYPE_ORDER_PAY_TYPE)) {
                String type = filter.value.toString();
                if (OrderPayType.ONLINE.toString().equals(type)) {
                  filter.value = OrderPayType.ONLINE;
                }else if (OrderPayType.POS.toString().equals(type)) {
                  filter.value = OrderPayType.POS;
                }else if (OrderPayType.CASH.toString().equals(type)) {
                  filter.value = OrderPayType.CASH;
                }else if (OrderPayType.NUPANTEBT.toString().equals(type)) {
                  filter.value = OrderPayType.NUPANTEBT;
                }
                predicates.add(cb.equal(expression, filter.value));
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
  @Override
  public List<OrderSignfor> getReceiptNotRemarkList(String status, String startTime, String endTime, String orderNo,String regionId) {
    // TODO Auto-generated method stub
    List<OrderSignfor> list= orderSignforRepository.getReceiptNotRemarkList(status, startTime, endTime, orderNo,regionId);
    try {
      list.forEach(notRemarklist->{
        notRemarklist.setSalesMan(salesManService.findByUserId(notRemarklist.getUserId()));
        if(notRemarklist.getSalesMan()!=null){
          notRemarklist.getSalesMan().setUser(null);
          notRemarklist.getSalesMan().setRegion(null);
        }else{
          notRemarklist.setSalesMan(new SalesMan());
        }
      });
    } catch (Exception e) {
      logger.error(e.getMessage());
      return list;
    }
    return list;
  }
  @Override
  public List<OrderSignfor> getReceiptCashList(Map<String, Object> searchParams) {
    List<OrderSignfor> cashList=findAll(searchParams);
    //TODO 查询收现金打款时间
    cashList.forEach(cash->{
      
    });
    return cashList;
  }
  

 }
