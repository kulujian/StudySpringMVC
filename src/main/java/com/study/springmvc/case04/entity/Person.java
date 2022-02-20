package com.study.springmvc.case04.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
/*	javax.validation.constraints.*
 * 	org.hibernate.validator.constraints.*
 *  	若同時使用要注意是否會有設定上的衝突？
 *  	例如：
 *  	javax.validation.constraints.Size
 *  	org.hibernate.validator.constraints.Range
 *  	上面兩個功能相同
 */
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Person {

	@Size(min = 2, max = 50, message = "{person.name.size}")
	private String name; // 姓名
	
	@NotNull(message = "{person.age.empty}")
	@Range(min = 0, max = 250, message = "{person.age.range}")
	private Integer age; // 年齡
	
	@NotNull(message = "{person.member.empty}")
	private Boolean member; // 是否為會員
	
	@NotNull(message = "{person.birth.empty}")
	@Past(message = "{person.birth.past}")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 返回日期型態(形式) ， 前端使用FROMDATE，取字串用" - "( a hh:mm:ss E 上午下午 E星期 HH 24小時制)
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 接收表單傳來的日期類型
	private Date birth; // 生日

	
	
	public Person() {
//		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getMember() {
		return member;
	}

	public void setMember(Boolean member) {
		this.member = member;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", member=" + member + ", birth=" + birth + "]";
	}
	
}
