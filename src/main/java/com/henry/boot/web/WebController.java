package com.henry.boot.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.henry.boot.domain.UserRepository;

@Controller
public class WebController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/")
	public String main(HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		return "/main";
	}

	@GetMapping("/explore")
	public String explore(HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		return "/explore";
	}

	@GetMapping("/persInf/{id}")
	public String persInf(@PathVariable Long id, Model model, HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		return "/personalInfo";
	}

	@GetMapping("/questions")
	public String about(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}
		return "/questions";
	}
}
