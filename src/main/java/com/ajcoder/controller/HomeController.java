package com.ajcoder.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ajcoder.entity.Employee;
import com.ajcoder.service.EmpService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	public EmpService empService;
	
	
	@GetMapping("/")
	public String index(Model m) {
		List<Employee> list = empService.getAllEmp();
		m.addAttribute("empList", list);
		return "index";
	}
	
	@GetMapping("/loadEmpSave")
	public String loadEmpSave() {
		return "emp_save";
	}
	
	@GetMapping("/EditEmp/{id}")
	public String EditEmp(@PathVariable int id, Model m) {
		//System.out.println(id);
		Employee emp= empService.getEmpById(id);
		m.addAttribute("emp",emp);
		return "edit_emp";
	}
	
	@PostMapping("/saveEmp")
	public String saveEmp(@ModelAttribute Employee emp, HttpSession session, @RequestParam MultipartFile img) {
//		System.out.println(emp);
//		System.out.println(img.getOriginalFilename());
		
		emp.setImageName(img.getOriginalFilename());
		Employee newEmp = empService.saveEmp(emp);
		
		if(emp != null) {
			try {

				
				File saveFile =new ClassPathResource("static/img").getFile();
				Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+img.getOriginalFilename());
//				System.out.println(path);
				Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
			} catch (Exception e) {
				e.printStackTrace();
			}session.setAttribute("msg", "Register Successfully");
		}
			else {
			session.setAttribute("msg", "Register Fail");
		}
	
	
		
		
		
		
		return "redirect:/";
	}
	
	@PostMapping("/updateEmpDtls")
	public String updateEmp(@ModelAttribute Employee emp, HttpSession session) {

		Employee updateEmp = empService.saveEmp(emp);
		
		if(updateEmp != null) {
			session.setAttribute("msg", "Update Successfully");
		}else {
			session.setAttribute("msg", "Update Fail");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/deleteEmp/{id}")
	public String deleteEmp(@PathVariable int id, HttpSession session) {

		boolean d = empService.deleteEmp(id);
	
		if(d ) {
			session.setAttribute("msg", "Delete Successfully");
		}else {
			session.setAttribute("msg", "Somthing Wrong");
		}
		
		return "redirect:/";
	}


}
