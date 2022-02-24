package com.study.springmvc.case03.entity;

public class ExamPeriod {

	private String id;
	private String name;
	
	
	public ExamPeriod() {
//		super();
	}
	
	public ExamPeriod(String id, String name) {
//		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		@Override
	public String toString() {
		return "examPeriod [id=" + id + ", name=" + name + "]";
	}
		
		
}
