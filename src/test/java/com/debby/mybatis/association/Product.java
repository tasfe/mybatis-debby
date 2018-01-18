package com.debby.mybatis.association;

import com.debby.mybatis.annotation.MappingId;
import com.debby.mybatis.annotation.MappingResult;
import com.debby.mybatis.annotation.MappingTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rocky.hu
 * @date Nov 20, 2017 4:30:52 PM
 */
public class Product {

    public enum PM {
        SMALL, MIDDLE, BIG
    }

    @MappingId
    private Integer id;
    private Date createTime;
    private String title;
    private int quantity;
    private BigDecimal price;
    private boolean soldOut;
    private double weight;
    @MappingTypeHandler(value = EnumOrdinalTypeHandler.class)
    private PM pm;

    @MappingResult(association = true)
    private ProductCategory productCategory;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public PM getPm() {
        return pm;
    }

    public void setPm(PM pm) {
        this.pm = pm;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
    
}
