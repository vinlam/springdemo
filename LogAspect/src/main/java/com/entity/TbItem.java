package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

public class TbItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Field
    private Long id;

    @Field
    private String title;

    @Field
	private String sellPoint;

    //@Field("item_price")
	@Field
    private BigDecimal price;
	
	@Field
    private Integer stockCount;
    @Field
    private Integer num;
    @Field
    private String barcode;

    //@Field("item_image")
    @Field
    private String image;
    
    @Field
    private Long categoryid;
    
    @Field
    private String status;
    @Field
    private Date createTime;
    
    @Field
    private Date updateTime;
    
    @Field
    private String itemSn;
    
    @Field
	private BigDecimal costPirce;
    
    @Field
    private BigDecimal marketPrice;
    @Field
    private String isDefault;
    
    @Field
    private Long goodsId;
    
    @Field
    private String sellerId;
    
    @Field
    private String cartThumbnail;
    
    @Field
    private String category;
    
    @Field
    private String brand;
    
    @Field
    private String spec;

    @Field
    private String seller;

    @Dynamic
    @Field("spec_*")
    private Map<String,String> specMap;

	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;
	}

    public String getTitle() {
		return title;
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getItemSn() {
		return itemSn;
	}

	public void setItemSn(String itemSn) {
		this.itemSn = itemSn;
	}

	public BigDecimal getCostPirce() {
		return costPirce;
	}

	public void setCostPirce(BigDecimal costPirce) {
		this.costPirce = costPirce;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getCartThumbnail() {
		return cartThumbnail;
	}

	public void setCartThumbnail(String cartThumbnail) {
		this.cartThumbnail = cartThumbnail;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Map<String, String> getSpecMap() {
		return specMap;
	}

	public void setSpecMap(Map<String, String> specMap) {
		this.specMap = specMap;
	}
}