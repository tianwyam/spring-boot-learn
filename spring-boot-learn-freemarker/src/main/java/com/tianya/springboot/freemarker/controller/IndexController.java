package com.tianya.springboot.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@GetMapping("/index")
	public ModelAndView index() {
		
		ModelAndView view = new ModelAndView("index");
		view.addObject("user", "Tom");
		
		return view ;
	}

}
