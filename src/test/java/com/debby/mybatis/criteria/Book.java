package com.debby.mybatis.criteria;

import java.util.Date;

/**
 * @author rocky.hu
 * @date Jan 26, 2018 5:15:09 PM
 */
public class Book {

	private Integer id;
	private String title;
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
