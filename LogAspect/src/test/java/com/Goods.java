package com;

/**
 * 商品实体类
 * 
 * @author
 *
 */
public class Goods {
	private String name;// 商品名称
	private int fav;// 收藏量
	private int price;// 价格

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFav() {
		return fav;
	}

	public void setFav(int fav) {
		this.fav = fav;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Goods() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Goods(String name, int fav, int price) {
		super();
		this.name = name;
		this.fav = fav;
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("商品名称：" + this.name + ",");
		sb.append("收藏量：" + this.fav + ",");
		sb.append("商品价格：" + this.price + "\n");

		return sb.toString();
	}
}