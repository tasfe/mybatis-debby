package com.debby.mybatis.pagination;

import com.debby.mybatis.annotation.MappingId;

import java.util.Date;

/**
 * @author rocky.hu
 * @date Jan 5, 2018 2:29:14 PM
 */
public class Article {

	@MappingId
	private Integer id;
	private Date createTime;
	private String title;

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

}
