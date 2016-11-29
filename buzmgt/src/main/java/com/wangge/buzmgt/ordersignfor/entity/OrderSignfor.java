package com.wangge.buzmgt.ordersignfor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.springframework.format.annotation.DateTimeFormat;

//@JsonInclude(Include.NON_EMPTY)
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
@Table(name = "BIZ_ORDER_SIGNFOR")
@NamedEntityGraph(
    name = "graph.OrderSignfor.salesMan",
    attributeNodes={
        @NamedAttributeNode(value="salesMan",subgraph = "graph.OrderSignfor.salesMan.user")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "graph.OrderSignfor.salesMan.user",
            attributeNodes = {
                @NamedAttributeNode("user")
            }
        )
    }
)
public class OrderSignfor implements Serializable {

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;

  /**
   * 所属店铺关联状态
   */
  public static enum RelatedStatus {
    NOTRELATED("未关联"), ENDRELATED("已关联");
    private String name;

    RelatedStatus(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
  }
  
  /**
   * 订单状态
   *
   */
  public static enum OrderStatus{
    SUCCESS("订单成功"),UNDO("订单取消"),YWSIGNFOR("业务签收"),MEMBERSIGNFO("客户签收"),MEMBERREJECT("客户拒收"),SHIPPED("已发货"),YWREPORTED("业务报备");
    
    private String name;
    
    OrderStatus(String name){
      this.name=name;
    }
    public String getName(){
      return name;
    }
    
  }
  
  public static enum OrderPayType{
    ONLINE("0","线上支付"),POS("1","POS"),CASH("2","现金"),NUPANTEBT("","未支付");
    
    private String name;
    private String value;
    
    OrderPayType(String value,String name){
      this.value=value;
      this.name=name;
      
    }
    public String getValue(){
      return value;
    }
    public String getName(){
      return name;
    }
  }

  public static enum AgentPayType{
    NUPANTEBT("","未付款"),PAID("","已付款");

    private String name;
    private String value;

    AgentPayType(String value,String name){
      this.value=value;
      this.name=name;

    }
    public String getValue(){
      return value;
    }
    public String getName(){
      return name;
    }
  }
  
  
  @Id
  @Column(name = "SIGNID")
  @SequenceGenerator(name = "idgen")
  @GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
  private Long id;
  private String fastmailNo;
	@Column(name = "ORDER_NO")
  private String orderNo;
  @Transient
  private String aging;//时效
  @Transient
  private Date payDate;//支付时间
  
  @Transient
  private List<OrderItem> items;
  
  //业务签收异常标记
  @Transient
  private String ywSignforTag;
  @Transient
  private String goodNum;
  
  @Column(name="user_id")
  private String userId;
//  private String truename;
//  private String ywName;//业务名称;
 
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
  @JoinColumn(name = "user_id",insertable=false,updatable=false)
  private SalesMan salesMan;
  
  private String userPhone;
  private String shopName;
  private Float orderPrice;
  private Integer phoneCount = 0;
  @Column(name="CREAT_TIME")
  private Date createTime;

  private Date yewuSignforTime; 
  private Date customSignforTime;
  @Enumerated(EnumType.ORDINAL)
  private OrderStatus orderStatus;
  
  @Enumerated(EnumType.ORDINAL)
  private OrderPayType orderPayType = OrderPayType.NUPANTEBT;
  private String yewuSignforGeopoint;
  private String customSignforGeopoint;
  private Integer customSignforException = 0;
  private int partsCount;
  private Date fastmailTime;
  private String customUnSignRemark;
  private Float actualPayNum;//实际支付金额

  @Enumerated(EnumType.STRING)
  private RelatedStatus relatedStatus;

  private String memberPhone;

  @Transient
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @Temporal(TemporalType.TIMESTAMP)
  private Date roamTime;//订单流转时间

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @Temporal(TemporalType.TIMESTAMP)
  private Date overTime;//客户付款时间

  @Column(name = "ABROGATE_TIME")
  private Date abrogateOrderTime;//取消订单时间

  @Column(name = "BUSH_TIME")
  private Date bushPoseTime;//刷pose时间

  @Transient
  private String agentPayStatus;//代理商付款状态：未付款 1-已付款

  @Transient
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @Temporal(TemporalType.TIMESTAMP)
  private Date agentPayTime;//代理商付款时间

  @Transient
  private String orderRoamStatus;//订单流转状态

  private Date onlinePayTime;//线上支付时间

  private String dealType;//支付类型

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
    this.items = items;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }
  public String getGoodNum() {
    return goodNum;
  }

  public void setGoodNum(String goodNum) {
    this.goodNum = goodNum;
  }
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getYwSignforTag() {
    return ywSignforTag;
  }

  public void setYwSignforTag(String ywSignforTag) {
    this.ywSignforTag = ywSignforTag;
  }

  
  public String getAging() {
    return aging;
  }

  public void setAging(String aging) {
    this.aging = aging;
  }

 
  public SalesMan getSalesMan() {
    return salesMan==null?new SalesMan() :salesMan;
  }

  public void setSalesMan(SalesMan salesMan) {
    this.salesMan = salesMan;
  }

  public OrderSignfor (){
    super();
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getFastmailNo() {
    return fastmailNo;
  }
  public void setFastmailNo(String fastmailNo) {
    this.fastmailNo = fastmailNo;
  }
  public String getOrderNo() {
    return orderNo;
  }
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
//  public String getUserId() {
//    return userId;
//  }
//  public void setUserId(String userId) {
//    this.userId = userId;
//  }
  public String getUserPhone() {
    return userPhone;
  }
  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }
  public String getShopName() {
    return shopName;
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
 
  public Float getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(Float orderPrice) {
    this.orderPrice = orderPrice;
  }

  public Integer getPhoneCount() {
    return phoneCount;
  }

  public void setPhoneCount(Integer phoneCount) {
    this.phoneCount = phoneCount;
  }

  public Date getYewuSignforTime() {
    return yewuSignforTime;
  }
  public void setYewuSignforTime(Date yewuSignforTime) {
    this.yewuSignforTime = yewuSignforTime;
  }
  public Date getCustomSignforTime() {
    return customSignforTime;
  }
  public void setCustomSignforTime(Date customSignforTime) {
    this.customSignforTime = customSignforTime;
  }
  public String getOrderStatus() {
    return orderStatus == null ? "" :orderStatus.getName();
  }
  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
  public String getOrderPayType() {
    return orderPayType == null ? OrderPayType.NUPANTEBT.getName():orderPayType.getName();
  }
  public void setOrderPayType(OrderPayType orderPayType) {
    this.orderPayType = orderPayType;
  }
  public String getYewuSignforGeopoint() {
    return yewuSignforGeopoint;
  }
  public void setYewuSignforGeopoint(String yewuSignforGeopoint) {
    this.yewuSignforGeopoint = yewuSignforGeopoint;
  }
  public String getCustomSignforGeopoint() {
    return customSignforGeopoint;
  }
  public void setCustomSignforGeopoint(String customSignforGeopoint) {
    this.customSignforGeopoint = customSignforGeopoint;
  }
  public Integer getCustomSignforException() {
    return customSignforException;
  }
  public void setCustomSignforException(Integer customSignforException) {
    this.customSignforException = customSignforException;
  }

  public Date getFastmailTime() {
    return fastmailTime;
  }

  public void setFastmailTime(Date fastmailTime) {
    this.fastmailTime = fastmailTime;
  }

  public String getCustomUnSignRemark() {
    return customUnSignRemark;
  }

  public void setCustomUnSignRemark(String customUnSignRemark) {
    this.customUnSignRemark = customUnSignRemark;
  }

  public int getPartsCount() {
    return partsCount;
  }

  public void setPartsCount(int partsCount) {
    this.partsCount = partsCount;
  }

  public Float getActualPayNum() {
    return actualPayNum;
  }

  public void setActualPayNum(Float actualPayNum) {
    this.actualPayNum = actualPayNum;
  }

  public RelatedStatus getRelatedStatus() {
    return relatedStatus;
  }

  public void setRelatedStatus(RelatedStatus relatedStatus) {
    this.relatedStatus = relatedStatus;
  }

  public String getMemberPhone() {
    return memberPhone;
  }

  public void setMemberPhone(String memberPhone) {
    this.memberPhone = memberPhone;
  }

  public Date getRoamTime() {
    return roamTime;
  }

  public void setRoamTime(Date roamTime) {
    this.roamTime = roamTime;
  }

  public Date getOverTime() {
    return overTime;
  }

  public void setOverTime(Date overTime) {
    this.overTime = overTime;
  }

  public Date getAbrogateOrderTime() {
    return abrogateOrderTime;
  }

  public void setAbrogateOrderTime(Date abrogateOrderTime) {
    this.abrogateOrderTime = abrogateOrderTime;
  }

  public Date getBushPoseTime() {
    return bushPoseTime;
  }

  public void setBushPoseTime(Date bushPoseTime) {
    this.bushPoseTime = bushPoseTime;
  }

  public String getAgentPayStatus() {
    return agentPayStatus;
  }

  public void setAgentPayStatus(String agentPayStatus) {
    this.agentPayStatus = agentPayStatus;
  }

  public Date getAgentPayTime() {
    return agentPayTime;
  }

  public void setAgentPayTime(Date agentPayTime) {
    this.agentPayTime = agentPayTime;
  }

  public String getOrderRoamStatus() {
    return orderRoamStatus;
  }

  public void setOrderRoamStatus(String orderRoamStatus) {
    this.orderRoamStatus = orderRoamStatus;
  }

  public Date getOnlinePayTime() {
    return onlinePayTime;
  }

  public void setOnlinePayTime(Date onlinePayTime) {
    this.onlinePayTime = onlinePayTime;
  }

  public String getDealType() {
    return dealType;
  }

  public void setDealType(String dealType) {
    this.dealType = dealType;
  }
}
