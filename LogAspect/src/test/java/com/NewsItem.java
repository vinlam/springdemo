package com;

import java.util.Date;

/**
 * 新闻实体类
 * 
 * @author 51093
 * @param <T>
 *
 */
public class NewsItem implements Comparable<NewsItem> {

	private String title;// 标题
	private int hits;// 点击量
	private Date pubTime;// 时间

	public NewsItem() {
		super();

	}

	public NewsItem(String title, int hits, Date pubTime) {
		super();
		this.title = title;
		this.hits = hits;
		this.pubTime = pubTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

//先按时间降序，点击量升序，标题排序降序
	@Override
	public int compareTo(NewsItem o) {
		int result = 0;
		result = -this.pubTime.compareTo(o.getPubTime());// 降序
		if (0 == result) {
			// 时间相同则按照点击量排序
			result = this.hits - o.getHits();
			if (0 == result) {
				// 相同则按照标题降序
				result = -this.title.compareTo(o.getTitle());
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("标题是：" + this.title + ",");
		sb.append("时间是：" + this.pubTime + ",");
		sb.append("点击量是：" + this.hits + "\n");

		return sb.toString();
	}
}
