package com.study.springmvc.case03.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import com.study.springmvc.case03.entity.Exam;
import com.study.springmvc.case03.entity.ExamSubject;
import com.study.springmvc.case03.entity.ExamPeriod;

@Service
public class ExamService {

	// CopyOnWriteArrayList 是執行緒安全的集合-適合多執行緒操作
	// ArrayList 是非執行緒安全的集合-適合單緒操作
	// 因為預設的情況下 Spring 會將 ExamController 定義為 Singleton(單一實體)
	// 所以可以使用 CopyOnWriteArrayList 來作為多執行者資料操作的集合類
	
	private List<Exam> exams = new CopyOnWriteArrayList<Exam>(); // 註冊考試的紀錄集合
	private List<ExamSubject> examSubjects = new CopyOnWriteArrayList<>(); // 預設科目選項集合
	private List<ExamPeriod> examPeriods = new CopyOnWriteArrayList<>(); // 預設科目選項集合
	private Map<String,String> examPayStatus = new HashMap<String, String>();
	
	// 以下為 ExamSubject 建構子注入
	{
		examSubjects.add(new ExamSubject("808", "JavaSE 8 OCP I"));
		examSubjects.add(new ExamSubject("809", "JavaSE 8 OCP II"));
		examSubjects.add(new ExamSubject("819", "JavaSE 11 OCP "));
		examSubjects.add(new ExamSubject("900", "JavaEE 7 OCP"));
	}
	
	// 以下為 ExamPeriod 建構子注入
	{
		examPeriods.add(new ExamPeriod("A", " 上午(A)  "));
		examPeriods.add(new ExamPeriod("B", " 下午(B)  "));
		examPeriods.add(new ExamPeriod("C", " 晚上(C)  "));
	}
	
	// 首頁(可查詢所有Exam Subject)
	public List<ExamSubject> queryExamSubjectsList(){
		return examSubjects;
	}
	
	// 首頁(可查詢所有Exam Period)
	public List<ExamPeriod> queryExamPeriodsList(){
		return examPeriods;
	}
	
	// 首頁(設定所有Exam Pay Status)
	public Map<String,String> queryExamPayStatusMap(){
		examPayStatus.clear();
		examPayStatus.put("true", " 已繳 ");
		examPayStatus.put("false", " 未繳 ");
		return examPayStatus;
	}
	
	// 首頁(包含查詢多筆)
	public List<Exam> query(){
		return exams;
	}
	
	// 查詢單筆
	public Optional<Exam> get(int index) {
		if(index < 0 || exams.size() == 0 || index >= exams.size()) {
			return Optional.ofNullable(null);
		}
		return Optional.ofNullable(exams.get(index));
//		return exams.get(index);
	}
	
	// 新增
	public Boolean add(Exam exam) {
		int previousSize =  exams.size();
		exams.add(exam);
		int nextSize =  exams.size();
		return nextSize > previousSize;
	}
	
	// 修改
	public synchronized Boolean update(int index, Exam exam) {
		if(index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		exams.set(index, exam);
		return true;
	}

	// 修改 ExamNote 單一欄位資料
	public synchronized Boolean updateExamNote(int index, String examNote) {
		if(index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		Exam exam = exams.get(index);
		exam.setExamNote(examNote);
//		exams.set(index, exam);
		return true;
	}
	// 刪除
	public synchronized Boolean delete(int index) {
		if(index < 0 || exams.size() == 0 || index >= exams.size()) {
			return false;
		}
		exams.remove(index);
		return true;
		
	} 
}
