package com.henry.boot.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.henry.boot.domain.User;
import com.henry.boot.domain.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/loginForm";
	}

	@PostMapping("/login")
	public String login(String userId, String userPassword, HttpSession session) {
		User user = userRepository.findByUserId(userId);

		if (user==null) {
			System.out.println("아이디가 없음.");
			return "redirect:/user/loginForm";
		}
		
		if (!user.matchPassword(userPassword)) {
			System.out.println("Password 불일치");
			return "redirect:/user/loginForm";
		}
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		System.out.println("로그인 성공!");
		return "redirect:/";
	}

	@GetMapping("/joinForm")
	public String joinForm(HttpSession session) {

		if (HttpSessionUtils.isLoginUser(session)) {
			return "/user/main";
		}
		return "/user/joinForm";
	}

	@PostMapping("/join")
	public String joinUser(User user, HttpSession session) {

		if(HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/";
		}
		
		userRepository.save(user);
		return "redirect:/user/loginForm";
	}

	@GetMapping("/updateForm/{userId}")
	public String updateForm(HttpSession session, @PathVariable String userId) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			System.out.println("권한이 없습니다.");
			return "/";
		}
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, HttpSession session, User updatedUser) {

		if(!HttpSessionUtils.isLoginUser(session)) {
			System.out.println("잘못된 접근입니다.");
			return "redirect:/";
		}

		User user = userRepository.getOne(id);
		
		if(user==null) {
			return "redirect:/";
		}

		if (!HttpSessionUtils.getUserFromSession(session).equals(user)) {
			throw new IllegalStateException("부적절한 접근입니다.");
		}

		user.update(updatedUser);
		userRepository.save(user);
		
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
}

