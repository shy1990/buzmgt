package com.wangge.buzmgt.teammember.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
  * ClassName: SalesmanLevel <br/>
  * Reason: TODO 业务等级entity. <br/>
  * date: 2016年8月23日 下午1:6:09 <br/>
  *
  * @author Administrator
  * @version
  * @since JDK 1.8
 */
@Entity
@Table(name = "SYS_SALESMAN_LEVEL")
public class SalesmanLevel implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "ID")
  private Long id; //主键id

  private String levelName;//业务等级名称

  private String salesRange;//销量区间

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getSalesRange() {
    return salesRange;
  }

  public void setSalesRange(String salesRange) {
    this.salesRange = salesRange;
  }

  @Override
  public String toString() {
    return "SalesmanLevel{" +
            "id=" + id +
            ", levelName='" + levelName + '\'' +
            ", salesRange='" + salesRange + '\'' +
            '}';
  }
}
