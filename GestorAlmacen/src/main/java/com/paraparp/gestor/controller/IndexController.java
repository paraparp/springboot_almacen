package com.paraparp.gestor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	
	@GetMapping("/")
	public String showIndex(Model model) {
		
		model.addAttribute("mensaje", "hola que paisa");
		
		return "index";
	}
	

}
