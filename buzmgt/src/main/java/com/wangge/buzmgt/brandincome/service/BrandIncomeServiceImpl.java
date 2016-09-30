package com.wangge.buzmgt.brandincome.service;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.entity.BrandIncome.BrandIncomeStatus;
import com.wangge.buzmgt.brandincome.entity.BrandIncomeSub;
import com.wangge.buzmgt.brandincome.entity.BrandIncomeVo;
import com.wangge.buzmgt.brandincome.repository.BrandIncomeRepository;
import com.wangge.buzmgt.brandincome.repository.BrandIncomeSubRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by peter on 16-8-31.
 * 品牌型号收益service
 */

@Service
public class BrandIncomeServiceImpl implements BrandIncomeService {
  @Resource
  private BrandIncomeRepository brandIncomeRepository;
  @PersistenceContext
  private EntityManager entityManager;
  @Resource
  private BrandIncomeSubRepository brandIncomeSubRepository;
  @Resource
  private LogService logService;

  @Override
  public BrandIncome findById(Long id) {
    return brandIncomeRepository.findOne(id);
  }

  @Override
  public BrandIncome save(BrandIncome brandIncome) {
    return brandIncomeRepository.save(brandIncome);
  }

  @Override
  public Page<BrandIncome> findAll(Map<String, Object> searchParams, Pageable pageable) {
    Specification<BrandIncome> spec = dispose(searchParams);
    return brandIncomeRepository.findAll(spec, pageable);
  }

  /**
   * 处理条件参数
   */
  public Specification<BrandIncome> dispose(Map<String, Object> searchParams) {
    //过滤删除
    searchParams.put("EQ_flag", "NORMAL");
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<BrandIncome> spec = brandIncomeSearchFilter(filters.values(), BrandIncome.class);
    return spec;
  }

  @Override
  public List<BrandIncome> findAll(Map<String, Object> searchParams, Sort sort) {
    if (ObjectUtils.equals(sort, null)) {
      sort = new Sort(Sort.Direction.DESC, "createDate");
    }
    Specification<BrandIncome> spec = dispose(searchParams);
    return brandIncomeRepository.findAll(spec, sort);
  }

  public static Specification<BrandIncome> brandIncomeSearchFilter(final Collection<SearchFilter> filters,
                                                                   final Class<BrandIncome> entityClazz) {
    return new Specification<BrandIncome>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_FlAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";

      private final static String TYPE_BRANDINCOME_STATUS = "com.wangge.buzmgt.brandincome.entity.BrandIncome$BrandIncomeStatus";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({"unchecked", "rawtypes"})
      @Override
      public Predicate toPredicate(Root<BrandIncome> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (CollectionUtils.isNotEmpty(filters)) {
          List<Predicate> predicates = new ArrayList<>();
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
                } else if (javaTypeName.equals(TYPE_FlAG_TYPE)) {
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
                } else if (javaTypeName.equals(TYPE_BRANDINCOME_STATUS)) {
                  /**
                   * BrandIncomeStatus格式转换
                   */
                  try {
                    String status = filter.value.toString();
                    BrandIncomeStatus flagEnum = BrandIncomeStatus.valueOf(status);
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
  public Page<BrandIncomeVo> findAll(HttpServletRequest request, BrandIncome brandIncome, Pageable pageable) {
    Page<BrandIncomeVo> pageResult = null;
    String hql = executeSql(request, brandIncome);
    Query q = entityManager.createNativeQuery(hql);
    int page = pageable.getPageNumber();
    int size = pageable.getPageSize();
    int count = q.getResultList().size();
    q.setFirstResult(page * size);
    q.setMaxResults(size);
    List<BrandIncomeVo> list = findBySql(q);
    pageResult = new PageImpl<BrandIncomeVo>(list, new PageRequest(page, size), count);
    return pageResult;
  }

  @Override
  public List<BrandIncomeVo> findAll(HttpServletRequest request, BrandIncome brandIncome) {
    String hql = executeSql(request, brandIncome);
    Query q = entityManager.createNativeQuery(hql);
    List<BrandIncomeVo> list = findBySql(q);
    return list;
  }

  /**
   * 获取执行的sql
   *
   * @param request
   * @param brandIncome
   * @return
   */
  private String executeSql(HttpServletRequest request, BrandIncome brandIncome) {
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
            "   and g.goods_id = '" + brandIncome.getGoodId() + "'";

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
   */
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
  }

  @Override
  public int findCycleSales(BrandIncome brandIncome) {
    return brandIncomeRepository.findCycleSales(brandIncome.getGoodId());
  }

  @Override
  public List<Map<String,Object>> findRuleByGoods(List<String> goodIds, Long mainPlanId, String userId,Date payDate) {
    List<Map<String,Object>> list = new ArrayList<>();
    Map<String,Object> map = new HashedMap();
    BrandIncome brandIncome = null;
    if (CollectionUtils.isNotEmpty(goodIds)) {
      for (String g : goodIds){
        brandIncome = brandIncomeRepository.findByGoodIdAndPlanId(g, mainPlanId);
        if (ObjectUtils.notEqual(brandIncome,null)){
          Long payTime = payDate.getTime();//付款日期
          Long startDate = brandIncome.getStartDate().getTime();//规则开始日期
          Long endDate = brandIncome.getStartDate().getTime();//规则结束日期
          if (payTime >= startDate && payTime <= endDate){
            map.put("goodId",brandIncome.getGoodId());
            map.put("rule",brandIncome);
            list.add(map);
          }
        }
      }
    }
    return list;
  }

  @Override
  public Boolean realTimeBrandIncomePay(BrandIncome brandIncome, int num, String orderNo, String goodId, String userId, Date payDate) {
    try {
      //根据传入参数计算品牌型号收益(已付款)
      Double income = (brandIncome.getCommissions()).doubleValue() * num;
      BrandIncomeSub brandIncomeSub = new BrandIncomeSub();
      brandIncomeSub.setMainplanId(brandIncome.getPlanId());
      brandIncomeSub.setSubplanId(brandIncome.getId());
      brandIncomeSub.setIncome(income);//总收益
      brandIncomeSub.setOrderno(orderNo);
      brandIncomeSub.setUserId(userId);
      brandIncomeSub.setOrderflag(1);//订单状态:已付款
      brandIncomeSub.setCountDate(payDate);//付款时间
      brandIncomeSub = brandIncomeSubRepository.save(brandIncomeSub);
      logService.log(null,brandIncomeSub, Log.EventType.SAVE);
      return true;
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      return false;
    }
  }

  @Override
  public Boolean realTimeBrandIncomeOut(BrandIncome brandIncome, int num, String orderNo, String goodId, String userId) {
    try {
      //根据传入参数计算品牌型号收益(已出库)
      Double income = (brandIncome.getCommissions()).doubleValue() * num;
      BrandIncomeSub brandIncomeSub = new BrandIncomeSub();
      brandIncomeSub.setMainplanId(brandIncome.getPlanId());
      brandIncomeSub.setSubplanId(brandIncome.getId());
      brandIncomeSub.setIncome(income);//总收益
      brandIncomeSub.setOrderno(orderNo);
      brandIncomeSub.setUserId(userId);
      brandIncomeSub.setOrderflag(0);//订单状态:已出库
      brandIncomeSub.setCountDate(new Date());//出库
      brandIncomeSub = brandIncomeSubRepository.save(brandIncomeSub);
      logService.log(null,brandIncomeSub, Log.EventType.SAVE);
      return true;
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      return false;
    }
  }
}
