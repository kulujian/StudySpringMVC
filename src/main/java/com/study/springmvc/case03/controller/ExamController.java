package com.study.springmvc.case03.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.case03.entity.Exam;
import com.study.springmvc.case03.service.ExamService;

@Controller
@RequestMapping("/case03/exam")
public class ExamController {

	@Autowired
	private ExamService examServices;
	
	@GetMapping("/")
	public String index(@ModelAttribute Exam exam, Model model) {
		model.addAttribute("_method", "POST");
		model.addAttribute("exams", examServices.query());
		model.addAttribute("examSubjects", examServices.queryExamSubjectsList());
		model.addAttribute("examPeriods", examServices.queryExamPeriodsList());
		model.addAttribute("examPayStatus", examServices.queryExamPayStatusMap());
		return "case03/exam";
	}
	
	@GetMapping("/{index}")
	public String get(@PathVariable("index") int index, Model model) {
		Optional<Exam> optExam = examServices.get(index);
		if(optExam.isPresent()) {
			model.addAttribute("_method", "PUT");
			model.addAttribute("exams", examServices.query());
			model.addAttribute("examSubjects", examServices.queryExamSubjectsList());
			model.addAttribute("examPeriods", examServices.queryExamPeriodsList());
			model.addAttribute("examPayStatus", examServices.queryExamPayStatusMap());
			model.addAttribute("exam", optExam.get());
			return "case03/exam";
		}
		// 沒有找到資料，應該要引導到統一錯誤處理機制頁面進行處理...(暫時回到首頁)
		return "redirect:./";
	}
	
	// 這邊
	@PostMapping("/")
	public String add(Exam exam) {
		// 設邊需要接個 Boolean 值
		examServices.add(exam);
		return "redirect:./";
	}
	
	@PutMapping("/{index}")
	public String update(@PathVariable("index") int index, Exam exam) {
		// 設邊需要接個 Boolean 值
		examServices.update(index,exam);
		return "redirect:./";
	}

	@PutMapping("/{index}/exam_note")
	public String updateExamNote(@PathVariable("index") int index, Exam exam) {
		// 設邊需要接個 Boolean 值
		examServices.updateExamNote(index,exam.getExamNote());
		return "redirect:../";
	}
	

	@DeleteMapping("/{index}")
	public String delete(@PathVariable("index") int index) {
		// 設邊需要接個 Boolean 值
		examServices.delete(index);
		return "redirect:./";
	}

}
