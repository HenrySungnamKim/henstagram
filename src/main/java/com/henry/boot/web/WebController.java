package com.henry.boot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
	@GetMapping("/")
	public String main() { 
		return "/main";
	}
	@GetMapping("/loginUser")
	public String login() { 
		return "/loginForm";
	}
	@GetMapping("/joinUser")
	public String join() { 
		return "/joinForm";
	}
	@GetMapping("/explore")
	public String explore() {
		return "/explore";
	}
	@GetMapping("/hearts")
	public String hearts() {
		return "/hearts";
	}
	@GetMapping("/personalInf")
	public String persInf() {
		return "/personalInf";
	}
	@GetMapping("/questions")
	public String about() {
		return "/questions";
	}
}
