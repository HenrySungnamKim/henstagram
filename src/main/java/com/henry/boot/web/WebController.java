package com.henry.boot.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.henry.boot.domain.FollowingRepository;
import com.henry.boot.domain.User;
import com.henry.boot.domain.UserRepository;

@Controller
public class WebController {
	@Autowired
	UserRepository userRepository;	
	@Autowired
	FollowingRepository followingRepository;

	@GetMapping("/")
	public String main(HttpSession session) {
		Object sessionedUser = session.getAttribute("sessionedUser");

		if (sessionedUser == null) {
			return "/user/loginForm";
		}
		return "/main";
	}

	@GetMapping("/explore")
	public String explore(HttpSession session) {
		Object sessionedUser = session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			return "/user/loginForm";
		}
		return "/explore";
	}

	@GetMapping("/persInf/{id}")
	public String persInf(@PathVariable Long id, Model model,HttpSession session){
		Object sessionedUser = session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			return "/user/loginForm";
		}
		Integer followers = followingRepository.findCountAllByUserId(id);
		model.addAttribute("followinglist", followers);
		return "redirect:/personalInfo";
	}

	@GetMapping("/questions")
	public String about(HttpSession session) {
		Object sessionedUser = session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			return "/user/loginForm";
		}
		return "/questions";
	}
}
