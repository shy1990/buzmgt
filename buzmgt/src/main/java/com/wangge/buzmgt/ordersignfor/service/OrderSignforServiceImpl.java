package com.wangge.buzmgt.ordersignfor.service;

import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;
import com.wangge.buzmgt.cash.service.CashService;
import com.wangge.buzmgt.cash.service.WaterOrderCashService;
import com.wangge.buzmgt.cash.service.WaterOrderDetialService;
import com.wangge.buzmgt.ordersignfor.bean.OrderSignforAfterSale;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.OrderPayType;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.OrderStatus;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.AgentPayType;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.receipt.service.OrderReceiptService;
import com.wangge.buzmgt.receipt.service.ReceiptService;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.rejection.entity.Rejection;
import com.wangge.buzmgt.rejection.service.RejectionServive;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class OrderSignforServiceImpl implements OrderSignforService {
  private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";
  private final static String TIME_MIN = " 00:00:00 000";
  private final static String TIME_MAX = " 23:59:59 999";

 private static final Logger logger = Logger.getLogger(OrderSignforServiceImpl.class);

  @PersistenceContext
  private EntityManager em;
  @Autowired
  private OrderSignforRepository orderSignforRepository;

  @Autowired
  private SalesManService salesManService;

  @Autowired
  private WaterOrderCashService waterOrderCashService;

  @Autowired
  private WaterOrderDetialService detialService;

  @Autowired
  private RegionService regionService;

  @Autowired
  private RejectionServive rejectionServive;

  @Autowired
  private OrderReceiptService orderReceiptService;

  @Autowired
  private CashService cashService;

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
  public Page<OrderSignforAfterSale> findAllByCreateTime(String createTime,Integer page,Integer size) {
    StringBuilder sql = new StringBuilder();
    sql.append("select os.ORDER_NO,os.FASTMAIL_NO,usr.NICKNAME,region.NAMEPATH,os.OVER_TIME,os.ORDER_STATUS from biz_order_signfor os\n" +
            "left join sys_user usr\n" +
            "on usr.USER_ID = os.USER_ID\n" +
            "left join sys_salesman saleman\n" +
            "on usr.USER_ID = saleman.USER_ID\n" +
            "left join sys_region region\n" +
            "on region.REGION_ID = saleman.REGION_ID\n" +
            "where order_no like '%SH%' ");
    if(createTime != null && !"".equals(createTime)){
      sql.append(" and to_char(os.creat_time,'yyyy-MM-dd') like '%" + createTime + "%'");
    }
    sql.append(" order by os.OVER_TIME ");
    SQLQuery sqlQuery = sqlQuery = (em.createNativeQuery(sql.toString())).unwrap(SQLQuery.class);
    Page<OrderSignforAfterSale> PageResult = null;
    int count = sqlQuery.list().size();//总条数
    sqlQuery.setFirstResult(page * size);//设置开始的位置
    sqlQuery.setMaxResults(size);//每页显示的条数
    List<OrderSignforAfterSale> list = new ArrayList<>();
    List<Object[]> ret = sqlQuery.list();
    if(CollectionUtils.isNotEmpty(ret)){
      ret.forEach(object -> {
        OrderSignforAfterSale s = new OrderSignforAfterSale();
        s.setOrderNo((String) object[0]);
        s.setFastMallNo((String) object[1]);
        s.setNickName((String) object[2]);
        s.setNamePath((String) object[3]);
        s.setOverTime(DateUtil.timestamp2String((Timestamp) object[4],"yyyy-MM-dd"));
        s.setStatus(((BigDecimal) object[5]).toString());
        list.add(s);
      });
    }
    return new PageImpl<OrderSignforAfterSale>(list,new PageRequest(page,size),count);
  }

  @Override
  public List<OrderSignfor> findAll(Map<String, Object> searchParams) {
    regionService.disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OrderSignfor> spec = orderSignforSearchFilter(filters.values(), OrderSignfor.class);
    List<OrderSignfor> orderSignfors = orderSignforRepository.findAll(spec);
    return getOrderSignfors(orderSignfors);
  }

  @Override
  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest) {
    regionService.disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OrderSignfor> spec = orderSignforSearchFilter(filters.values(), OrderSignfor.class);
    Page<OrderSignfor> signforPage = orderSignforRepository.findAll(spec,pageRequest);
    getOrderSignfors(signforPage.getContent());
    return signforPage;
  }

  /**
   * 获取状态判断后的订单
   * @param list
   * @return
   */
  private List<OrderSignfor> getOrderSignfors(List<OrderSignfor> list){
    if (CollectionUtils.isNotEmpty(list)) {
      list.forEach(os -> {
        //订单成功
        if (OrderStatus.SUCCESS.getName().equals(os.getOrderStatus())){
          os.setOrderRoamStatus(OrderStatus.SUCCESS.getName());
        }
        //看是否已发货
        if (StringUtils.isNotEmpty(os.getFastmailNo())){
          os.setOrderRoamStatus(OrderStatus.SHIPPED.getName());
          os.setRoamTime(os.getFastmailTime());//流转时间为发货时间
        }
        //线上支付
        if (OrderPayType.ONLINE.getName().equals(os.getOrderPayType())){
          os.setOverTime(os.getOnlinePayTime());//客户付款时间
          os.setAgentPayTime(os.getOnlinePayTime());//业务付款时间
        }
        //业务签收
        if (OrderStatus.YWSIGNFOR.getName().equals(os.getOrderStatus())){
          os.setOrderRoamStatus(OrderStatus.YWSIGNFOR.getName());
          os.setRoamTime(os.getYewuSignforTime());
        }
        //看是否拒收
        if (OrderStatus.MEMBERREJECT.getName().equals(os.getOrderStatus())){
          Rejection rejection = rejectionServive.findByOrderno(os.getOrderNo());
          os.setRoamTime(rejection.getCreateTime());//设置为拒收时间
          os.setOrderRoamStatus(OrderStatus.MEMBERREJECT.getName());
        }
        //业务报备
        ReceiptRemark receiptRemark = orderReceiptService.findByOrderno(os.getOrderNo());
        if (ObjectUtils.notEqual(receiptRemark,null)){
          os.setOrderRoamStatus(OrderStatus.YWREPORTED.getName());//设置业务报备
          os.setRoamTime(receiptRemark.getCreateTime());//设置报备时间
        }
        //取消订单
        if (OrderStatus.UNDO.getName().equals(os.getOrderStatus())){
          os.setRoamTime(os.getAbrogateOrderTime());
          os.setOrderRoamStatus(OrderStatus.UNDO.getName());
        }
        //pos付款
        if (OrderPayType.POS.getName().equals(os.getOrderPayType())){
          os.setRoamTime(os.getBushPoseTime());//流转时间
          os.setOverTime(os.getCustomSignforTime());//客户付款时间
          os.setAgentPayTime(os.getBushPoseTime());//代理商付款时间
        }
        //客户签收
        if (OrderStatus.MEMBERSIGNFO.getName().equals(os.getOrderStatus())){
          os.setOrderRoamStatus(OrderStatus.MEMBERSIGNFO.getName());//状态为客户签收
          os.setRoamTime(os.getCustomSignforTime());//流转时间
        }
        Cash cash = null;
        if (OrderPayType.CASH.getName().equals(os.getOrderPayType())){
          cash = cashService.findById(os.getId());
        }
        //代理商付款状态(收现金有未付款和已付款两种情况)
        if (OrderPayType.NUPANTEBT.getName().equals(os.getOrderPayType())){
          os.setAgentPayStatus(AgentPayType.NUPANTEBT.getName());
        }else if (ObjectUtils.notEqual(cash,null)){
          if (Cash.CashStatusEnum.UnPay.getName().equals(cash.getStatus())){
            os.setAgentPayStatus(AgentPayType.NUPANTEBT.getName());//代理商未付款
            os.setOverTime(os.getCustomSignforTime());//客户付款时间
          }else {
            os.setAgentPayStatus(AgentPayType.PAID.getName());//代理商已付款
            os.setOverTime(os.getCustomSignforTime());//客户付款时间
            os.setAgentPayTime(cash.getPayDate());//代理商付款时间
          }
        }else {
          os.setAgentPayStatus(AgentPayType.PAID.getName());
        }
      });
    }
    return list;
  }


  private static <T> Specification<OrderSignfor> orderSignforSearchFilter(final Collection<SearchFilter> filters,
      final Class<OrderSignfor> entityClazz) {

    return new Specification<OrderSignfor>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = ":00 000";

      private final static String TIME_MAX = ":59 999";

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

                predicates.add(cb.isNull(expression));

              break;
            case NOTNULL:

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
  public List<OrderSignfor> getReceiptNotRemarkList(String status, String startTime, String endTime, String orderNo,String regionId) {
    // TODO Auto-generated method stub
    List<OrderSignfor> list= orderSignforRepository.getReceiptNotRemarkList(status, startTime, endTime, orderNo,regionId);
    try {
      list.forEach(notRemarklist->{
        notRemarklist.setSalesMan(salesManService.findByUserId(notRemarklist.getUserId()));
        if(notRemarklist.getSalesMan()!=null){
          notRemarklist.getSalesMan().setUser(null);
//          notRemarklist.getSalesMan().setRegion(null);
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
     WaterOrderDetail detail= detialService.findByOrderId(cash.getId());
     if(detail!=null){
       WaterOrderCash waterOrder=waterOrderCashService.findBySerialNo(detail.getSerialNo());
       cash.setPayDate(waterOrder.getPayDate());
     }
    });
    return cashList;
  }
  @Override
  public void save(List<OrderSignfor> list) {
    orderSignforRepository.save(list);
  }
  @Override
  public List<OrderSignfor> findListByOrderNo(String orderNo) {
    // TODO Auto-generated method stub
    return orderSignforRepository.findAllByOrderNo(orderNo);
  }
  @Override
  public void deleteById(Long id) {
    orderSignforRepository.delete(id);

  }

  @Override
  public OrderSignfor save(OrderSignfor orderSignfor) {
    return orderSignforRepository.save(orderSignfor);
  }

  @Override
  public OrderSignfor findByFastmailNo(String fastMailNo) {
    return orderSignforRepository.findByFastmailNo(fastMailNo);
  }
}
