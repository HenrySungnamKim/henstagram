package com.henry.boot.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.boot.domain.Answer;
import com.henry.boot.domain.AnswerRepository;
import com.henry.boot.domain.Question;
import com.henry.boot.domain.QuestionRepository;
import com.henry.boot.domain.User;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	//댓글 생성
	@PostMapping("/answer/{id}")
	public Answer create(@PathVariable Long id, HttpSession session, String contents ) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		
		Question question = questionRepository.getOne(id);
		
		Answer answer = new Answer(HttpSessionUtils.getUserFromSession(session), question, contents);
		
		question.addAnswer();
		
		return answerRepository.save(answer);
	}
	//댓글 삭제
	@DeleteMapping("/answer/{id}")
	public boolean delete(@PathVariable Long id, HttpSession session) {
		
		if (!HttpSessionUtils.isLoginUser(session)) {
			return false;
		}
		
		Answer answer = answerRepository.getOne(id);
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		
		if(answer.getWriter().getUserId() != sessionedUser.getUserId()) {
			throw new IllegalStateException("자신의 댓글만 삭제할 수 있습니다.");
		}
		
		answerRepository.delete(answer);
		
		return true;
		 
	}
	
}
