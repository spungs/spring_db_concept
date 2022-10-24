package com.care.db.basic.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.db.basic.dto.MemberDTO;
import com.care.db.basic.repository.IMemberDAO;
import com.care.db.basic.service.MemberService;

@Controller
public class MemberController {
	@Autowired private IMemberDAO memberDao;
	@Autowired private MemberService service;
	@Autowired private HttpSession session;
	
	@GetMapping("index")
	public String index() {
		return "member/index";
	}
	@GetMapping("register")
	public String register() {
		return "member/register";
	}
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	@GetMapping("update")
	public String update() {
		return "member/update";
	}
	@GetMapping("delete")
	public String delete() {
		return "member/delete";
	}
	
	@PostMapping("register")
	public String register(MemberDTO member, String confirm_Pw, 
			Model model, RedirectAttributes ra) {
		
		String result = service.register(member, confirm_Pw);
		//회원가입 실패 시
		if(result.equals("회원가입 성공") == false) {
			model.addAttribute("msg", result);
			return "member/register";
		}
		//회원가입 성공 시
		ra.addFlashAttribute("msg", result);
		return "redirect:index";
	}
	
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("members", memberDao.list());
		
		return "member/list";
	}
	
	@PostMapping("login")
	public String login(String id, String pw,
			Model model, RedirectAttributes ra) {
		String result = service.login(id, pw);
		
		if(result.equals("로그인 성공") == false) {
			model.addAttribute("msg", result);
			return "member/login";
		}
		
		ra.addFlashAttribute("msg", result);
		return "redirect:index";
	}
	
	@GetMapping("logout")
	public String logout(RedirectAttributes ra) {
		if(session.getAttribute("id") == null) {
			ra.addFlashAttribute("msg","로그인 후 이용해주세요.");
			return "redirect:index"; 
		}
		session.invalidate();
		ra.addFlashAttribute("msg","로그아웃 완료");
		return "redirect:index"; 
	}
	
	@PostMapping("update")
	public String update(MemberDTO member, String confirm_Pw,
			Model model, RedirectAttributes ra) {
		
		String result = service.update(member, confirm_Pw);
		if(result.equals("회원 수정 성공") == false) {
			model.addAttribute("msg", result);
			return "member/update";
		}
		
		ra.addFlashAttribute("msg", result);
		return "redirect:index";
	}
	
	@PostMapping("delete")
	public String delete(String pw, String confirm_Pw,
			Model model, RedirectAttributes ra) {
		
		String result =	service.delete(pw, confirm_Pw);
		if(result.equals("삭제 성공") == false) {
			model.addAttribute("msg", result);
			return "member/delete";
		}
		
		ra.addFlashAttribute("msg", result);
		return "redirect:index";
	}
}
