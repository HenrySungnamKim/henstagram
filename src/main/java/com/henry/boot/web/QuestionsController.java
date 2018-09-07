package com.henry.boot.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.henry.boot.domain.Question;
import com.henry.boot.domain.QuestionRepository;
import com.henry.boot.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

	@Autowired
	QuestionRepository questionRepository;

	// 질문 게시판 이동 + 페이징
	@GetMapping("")
	public String questionsForm(HttpSession session, Model model,
			@PageableDefault(sort = { "id" }, direction = Direction.DESC, size = 10) Pageable pageable) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/loginForm";
		}
		Page<Question> questionsPage = questionRepository.findAll(pageable);

		int page = questionsPage.getNumber();// 현재 페이지의 번호

		int countPage = 5; // 5페이지씩 표시(한페이지당 10개 컨텐츠 기본값)

		int totalPage = questionsPage.getTotalPages(); // 생성될 페이지 수

		int prevPage = 0;
		int nextPage = 0;
		int lastPage = 0;

		if (totalPage < page) {
			page = totalPage;
		}

		int startPage = ((page - 1) / 10) * 10 + 1;

		int endPage = startPage + countPage - 1;

		if (endPage > totalPage) {

			endPage = totalPage;
		}

		if (startPage > 1) {

			page = 0;// 처음

		}

		if (page > 1) {

			prevPage = page - 1;// 이전

		}

		if (page < totalPage) {
			nextPage = page + 1;// 다음
		}

		if (endPage < totalPage) {
			lastPage = totalPage;// 마지막
		}

		model.addAttribute("prevPage", prevPage);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("lastPage", lastPage);

		model.addAttribute("page", page);
		model.addAttribute("questionsPage", questionsPage);

		return "/questions";
	}

	// 질문 생성
	@PostMapping("/{id}")
	public String createQuestion(@PathVariable Long id, HttpSession session, String title, String contents) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/loginForm";
		}

		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionedUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/questions";

	}

	// 질문 상세보기
	@GetMapping("/show/{id}")
	public String showQuestion(@PathVariable Long id, HttpSession session, Model model) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/loginForm";
		}

		model.addAttribute("questionsDetail", questionRepository.findById(id).get());
		return "/qna/showQuestion";
	}

	// 질문 수정 페이지 이동
	@GetMapping("/updateForm/{id}")
	public String updateForm(@PathVariable Long id, HttpSession session, Model model) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "/user/loginForm";
		}

		User loginUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
		Question temp = questionRepository.getOne(id);

		if (temp.getWriter().getUserId() != loginUser.getUserId()) {
			throw new IllegalStateException("부적절한 접근입니다.");

		}
		model.addAttribute("questionsDetail", questionRepository.findById(id).get());
		return "/qna/updateForm";

	}

	// 질문 수정
	@PutMapping("/{id}")
	public String updateQuestion(@PathVariable Long id, HttpSession session, String title, String contents) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/loginForm";
		}
		User loginUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);

		Question temp = questionRepository.getOne(id);

		if (temp.getWriter().getUserId() != loginUser.getUserId()) {
			throw new IllegalStateException("부적절한 접근입니다.");

		}

		temp.update(title, contents);
		questionRepository.save(temp);

		return "redirect:/questions";

	}

	// 질문 삭제
	@DeleteMapping("/{id}")
	public String dleteQuestion(@PathVariable Long id, HttpSession session) {

		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/loginForm";
		}

		Question temp = questionRepository.getOne(id);
		User loginUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);

		if (temp.getWriter().getUserId() != loginUser.getUserId()) {
			throw new IllegalStateException("부적절한 접근입니다.");

		}

		questionRepository.delete(temp);

		return "redirect:/questions";

	}
}
