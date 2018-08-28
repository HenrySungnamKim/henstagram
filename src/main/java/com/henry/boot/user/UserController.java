package com.henry.boot.user;

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

		if (user == null) {
			System.out.println("아이디가 없음.");
			return "redirect:/user/loginForm";
		}
		if (!user.getUserPassword().equals(userPassword)) {
			System.out.println("Password 불일치");
			return "redirect:/user/loginForm";
		}
		session.setAttribute("sessionedUser", user);
		System.out.println("로그인 성공!");
		return "redirect:/";
	}

	@GetMapping("/joinForm")
	public String joinForm(HttpSession session) {
		Object sessionedUser = session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			return "/user/joinForm";
		}
		return "redirect:/user/loginForm";
	}

	@PostMapping("/join")
	public String joinUser(User user) {
		userRepository.save(user);
		return "redirect:/user/loginForm";
	}

	@GetMapping("/updateForm/{userId}")
	public String updateForm(HttpSession session, @PathVariable String userId) {
		Object sessionedUser = session.getAttribute("sessionedUser");
		if (sessionedUser == null) {
			System.out.println("권한이 없습니다.");
			return "/";
		}
		return "/user/updateUserForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id, HttpSession session, User updatedUser) {

		User sessionedUser = (User) session.getAttribute("sessionedUser");
		if (!updatedUser.getId().equals(sessionedUser.getId())) {
			throw new IllegalStateException("부적절한 접근입니다.");
		}

		User user = userRepository.getOne(id);

		user.update(updatedUser);
		userRepository.save(user);
		
		session.removeAttribute("sessionedUser");
		session.setAttribute("sessionedUser", user);
		
		return "redirect:/";
	}

}
