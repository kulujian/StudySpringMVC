package com.study.springmvc.lab.entity;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Fund {
	private Integer fid;
	private String fname;
	// 表單有關日期很重要一定要設定
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 返回日期型態(形式) ， 前端使用FROMDATE，取字串用" - "( a hh:mm:ss E 上午下午 E星期 HH 24小時制)
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 接收表單傳來的日期類型
	private Date createtime;
	private List<Fundstock> fundstocks;
	
	public Fund() {
//		super();
	}
	
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public List<Fundstock> getFundstocks() {
		return fundstocks;
	}
	public void setFundstocks(List<Fundstock> fundstocks) {
		this.fundstocks = fundstocks;
	}
	@Override
	public String toString() {
		return "Fund [fid=" + fid + ", fname=" + fname + ", createtime=" + createtime + ", fundstocks=" + fundstocks
				+ "]";
	}
	
	
	
	
	
}
