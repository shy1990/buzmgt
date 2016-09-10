package com.wangge.buzmgt.superposition.entity;

import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.plan.entity.MachineType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by joe on 16-9-9.
 * 商品类型
 */
@Entity
@Table(name = "SYS_SUPER_GOODS_TYPE")
public class GoodsType {

    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "SU_GOODS_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "MACHINE_TYPE", updatable = false, insertable = false)
    private MachineType machineType;//机型类别

    @Column(name = "MACHINE_TYPE")
    private String machineTypeId;//机型类别

    @OneToOne
    @JoinColumn(name = "BRAND_ID", updatable = false, insertable = false)
    private Brand brand; // 品牌

    @Column(name = "BRAND_ID")
    private String brandId;//品牌ID

    @OneToOne
    @JoinColumn(name = "GOOD_ID", updatable = false, insertable = false)
    private Goods good; // 型号

    @Column(name = "GOOD_ID")
    private String goodId;//型号ID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(String machineTypeId) {
        this.machineTypeId = machineTypeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public MachineType getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Goods getGood() {
        return good;
    }

    public void setGood(Goods good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", machineType=" + machineType +
                ", machineTypeId='" + machineTypeId + '\'' +
                ", brand=" + brand +
                ", brandId='" + brandId + '\'' +
                ", good=" + good +
                ", goodId='" + goodId + '\'' +
                '}';
    }
}
