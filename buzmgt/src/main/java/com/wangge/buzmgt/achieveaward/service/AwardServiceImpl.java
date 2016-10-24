package com.wangge.buzmgt.achieveaward.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.wangge.buzmgt.achieveaward.entity.AwardGood;
import com.wangge.buzmgt.brandincome.entity.BrandIncomeVo;
import com.wangge.buzmgt.brandincome.service.BrandIncomeServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.achieveaward.entity.Award;
import com.wangge.buzmgt.achieveaward.entity.Award.AwardStatusEnum;
import com.wangge.buzmgt.achieveaward.repository.AwardRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.SearchFilter;
/**
 * 
* @ClassName: AwardServiceImpl
* @Description: 达量奖励收益业务层处理
* @author ChenGuop
* @date 2016年9月14日 上午11:17:53
*
 */
@Service
public class AwardServiceImpl implements AwardService {

  @Autowired
  private AwardRepository awardRepository;
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private BrandIncomeServiceImpl brandIncomeService;
  
  public List<Award> findAll(Map<String,Object> searchParams){
    return this.findAll(searchParams, new Sort(Direction.DESC, "createDate"));
  }
  /**
   * 处理条件参数
   */
  public Specification<Award> dispose(Map<String, Object> searchParams){
    //过滤删除
    searchParams.put("EQ_flag", "NORMAL");
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<Award> spec = awardSearchFilter(filters.values(), Award.class);
    return spec;
  }
  @Override
  public List<Award> findAll(Map<String, Object> searchParams, Sort sort) {
    if(ObjectUtils.equals(sort, null)){
      sort =  new Sort(Direction.DESC, "createDate");
    }
    Specification<Award> spec = dispose(searchParams);
    return awardRepository.findAll(spec, sort);
  }

  @Override
  public Page<Award> findAll(Map<String, Object> searchParams, Pageable pageable) {
    Specification<Award> spec = dispose(searchParams);
    return awardRepository.findAll(spec, pageable);
  }

  @Override
  public List<Award> findByMachineTypeAndPlanId(String machineType,String planId) {
    Map<String, Object> searchParams = new HashMap<>();
    searchParams.put("EQ_machineType", machineType);
    searchParams.put("EQ_planId", planId);
    return this.findAll(searchParams);
  }
  @Override
  @Transactional
  public void save(Award award) {
    try {
      awardRepository.save(award);
    } catch (Exception e) {
      LogUtil.error(e.getMessage(), e);
      throw e;
    }
  }
  @Override
  public Award findOne(Long id){
    return awardRepository.findByAwardId(id);
  }

  public static Specification<Award> awardSearchFilter(final Collection<SearchFilter> filters,
      final Class<Award> entityClazz){
    return new Specification<Award>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_PLAN_TYPE = "com.wangge.buzmgt.common.PlanTypeEnum";

      private final static String TYPE_FlAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";
      
      private final static String TYPE_ACHIEVE_STATUS = "com.wangge.buzmgt.achieveaward.entity.Award$AwardStatusEnum";
      
      private final static String TYPE_DATE = "java.util.Date";
      
      @SuppressWarnings({"unchecked","rawtypes"})
      @Override
      public Predicate toPredicate(Root<Award> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(CollectionUtils.isNotEmpty(filters)){
          List<Predicate> predicates = new ArrayList<>();
          for(SearchFilter filter : filters){
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
              } else if (javaTypeName.equals(TYPE_PLAN_TYPE)) {
                /**
                 * PlanTypeEnum格式转换
                 */
                try {
                  String status = filter.value.toString();
                  PlanTypeEnum planTypeEnum = PlanTypeEnum.valueOf(status);
                  filter.value = planTypeEnum;
                } catch (Exception e) {
                  LogUtil.info(e.getMessage(), e);
                  break ;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else if(javaTypeName.equals(TYPE_FlAG_TYPE)){
                /**
                 * FlagEnum格式转换
                 */
                try {
                  String status = filter.value.toString();
                  FlagEnum flagEnum = FlagEnum.valueOf(status);
                  filter.value = flagEnum;
                } catch (Exception e) {
                  LogUtil.error(e.getMessage(), e);
                  break;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else if(javaTypeName.equals(TYPE_ACHIEVE_STATUS)){
                /**
                 * FlagEnum格式转换
                 */
                try {
                  String status = filter.value.toString();
                  AwardStatusEnum flagEnum = AwardStatusEnum.valueOf(status);
                  filter.value = flagEnum;
                } catch (Exception e) {
                  LogUtil.error(e.getMessage(), e);
                  break;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else {
                predicates.add(cb.equal(expression, filter.value));
              }

              break;
            case IN:
              predicates.add(cb.in(expression).value(filter.value));
              
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
              boolean value = Boolean.parseBoolean("true");
              if (value)
                predicates.add(cb.isNull(expression));
              else
                predicates.add(cb.isNotNull(expression));

              break;
            case ORMLK:
              /**
               * sc_ORMLK_userId = 370105,3701050,3701051 用于区域选择
               */
              String[] parameterValue = ((String) filter.value).split(",");
              Predicate[] pl = new Predicate[parameterValue.length];

              for (int n = 0; n < parameterValue.length; n++) {
                pl[n] = (cb.like(expression, "%" + parameterValue[n] + "%"));
              }

              Predicate p_ = cb.or(pl);
              predicates.add(p_);
              break;

            default:
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
  public int findCycleSales(List<String> goodIds) {
    int num = awardRepository.findCycleSales(goodIds);
    return num;
  }

  @Override
  public Page<BrandIncomeVo> findAll(HttpServletRequest request, Award award, Pageable pageable) {
    Page<BrandIncomeVo> pageResult = null;
    String hql = executeSql(request, award);
    Query q = entityManager.createNativeQuery(hql);
    int page = pageable.getPageNumber();
    int size = pageable.getPageSize();
    int count = q.getResultList().size();
    q.setFirstResult(page * size);
    q.setMaxResults(size);
    List<BrandIncomeVo> list = brandIncomeService.findBySql(q);
    pageResult = new PageImpl<BrandIncomeVo>(list, new PageRequest(page, size), count);
    return pageResult;
  }

  @Override
  public List<BrandIncomeVo> findAll(HttpServletRequest request, Award award) {
    String hql = executeSql(request, award);
    Query q = entityManager.createNativeQuery(hql);
    List<BrandIncomeVo> list = brandIncomeService.findBySql(q);
    return list;
  }

  /**
   * 获取执行的sql
   *
   * @param request
   * @param award
   * @return
   */
  private String executeSql(HttpServletRequest request, Award award) {
    Set<AwardGood> awardGoods = award.getAwardGoods();
    List<String> goodIds = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(awardGoods)){
      awardGoods.forEach(awardGood -> {
        goodIds.add(awardGood.getGoodId());
      });
    }
    String hql = "select distinct g.region_id,\n" +
            "                nvl(sum(g.nums) over(partition by g.region_id),0) AS nums,\n" +
            "                g.goods_name,\n" +
            "                g.brand_name,\n" +
            "                g.TRUENAME,\n" +
            "                g.goods_id,\n" +
            "                g.stars_level,\n" +
            "                s.level_name,\n" +
            "                b.start_date,\n" +
            "                b.end_date,\n" +
            "                b.status,\n" +
            "                g.namepath\n" +
            "  from sys_goods_order g\n" +
            " inner join sys_salesman s\n" +
            "    on g.region_id = s.region_id\n" +
            " inner join sys_brand_income b\n" +
            "    on g.goods_id = b.good_id\n" +
            "where to_char(g.PAY_TIME, 'yyyy-mm-dd') between\n" +
            "       to_char(b.start_date, 'yyyy-mm-dd') and\n" +
            "       to_char(b.end_date, 'yyyy-mm-dd')\n" +
            "   and g.goods_id in (" + goodIds + ")";

    String starsLevel = request.getParameter("starsLevel");
    if (StringUtils.isNotEmpty(starsLevel)) {
      hql += "\n and g.stars_level = '" + starsLevel + "'";
    }
    String levelName = request.getParameter("levelName");
    if (StringUtils.isNotEmpty(levelName)) {
      hql += "\n and s.level_name = '" + levelName + "'";
    }
    String trueName = request.getParameter("trueName");
    if (StringUtils.isNotEmpty(trueName)) {
      hql += "\n and s.truename like '%" + trueName + "%'";
    }
    return hql;
  }

  /**
   * 遍历查询结果
   *
   * @param query
   * @return
   *//*
  private List<BrandIncomeVo> findBySql(Query query) {
    List<BrandIncomeVo> list = new ArrayList<>();
    List<Object[]> ret = query.getResultList();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(o -> {
        BrandIncomeVo bi = new BrandIncomeVo();
        bi.setRegionId((String) o[0]);
        bi.setNums(((BigDecimal) o[1]).intValue());
        bi.setGoodsName((String) o[2]);
        bi.setBrandName((String) o[3]);
        bi.setTruename((String) o[4]);
        bi.setGoodsId((String) o[5]);
        bi.setStarsLevel(((BigDecimal) o[6]).intValue());
        bi.setLevelName((String) o[7]);
        bi.setStartDate((Date) o[8]);
        bi.setEndDate((Date) o[9]);
        bi.setStatus((String) o[10]);
        bi.setNamepath((String) o[11]);
        list.add(bi);
      });
    }
    return list;
  }*/
}
