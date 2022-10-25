package com.care.db.basic.controller;


import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.db.basic.dto.MemberDTO;
import com.care.db.basic.repository.IMemberDAO;
import com.care.db.basic.service.KakaoService;
import com.care.db.basic.service.MailService;
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
	public String register(MemberDTO member, String confirm_Pw, Model model, RedirectAttributes ra) {
		if(session.getAttribute("check")==null) {
			model.addAttribute("msg", "인증 체크를 해주세요.");
			return "member/register";
		}
		String result = service.register(member, confirm_Pw);
		//회원가입 실패 시
		if(result.equals("회원가입 성공") == false) {
			model.addAttribute("msg", result);
			return "member/register";
		}
		//회원가입 성공 시
		ra.addFlashAttribute("msg", result);
		session.invalidate(); // 인증번호 제거를 위함
		return "redirect:index";
	}
	
	@ResponseBody
	@PostMapping(value="doubleCheck", produces="text/html; charset=UTF-8")
	public String doubleCheck(@RequestBody(required = false) String id) {
		if(id == null || id.isEmpty()) {
			return "입력후 체크해주세요.";
		}
		return service.doubleCheck(id);
	}
	
	@Autowired private MailService mailService;
	@ResponseBody
	@PostMapping(value="sendAuth", produces="text/html; charset=UTF-8")
	public String sendAuth(@RequestBody(required = false) String email) {
		if(email == null) {
			return "이메일을 입력해주세요.";
		}
		// Math.random() : 0~1사이의 실수를 생성(대략 소숫점 16자리까지)
		double n = Math.random();
		// 생성된 랜덤 실수를 substring으로 2번째 인덱스부터 8번째 인덱스 전까지 추출해서 문자열로 형변환
		String randomNum = Double.toString(n).substring(2,8);
		// 인증번호는 사용자별 정보이기에 session에 꼭 저장해야함.
		session.setAttribute("randomNum", randomNum);
		System.out.println("인증번호 : "+ randomNum);
		mailService.sendMail(email, "[인증번호]", randomNum);
		return "인증번호를 이메일로 전송했습니다.";
	}
	
	@ResponseBody
	@PostMapping(value="checkAuth", produces="text/html; charset=UTF-8")
	public String checkAuth(@RequestBody(required = false) String authNumber) {
		String randomNum = (String)session.getAttribute("randomNum");
		
		if(authNumber == null)
			return "인증번호를 입력하세요 ";
		else if(randomNum == null)
			return "이메일을 입력 후 인증번호를 생성하세요.";
		else if(authNumber.equals(randomNum)) {
			session.setAttribute("check", "y"); // 인증 체크용 session
			return "인증 성공";
		}
		else 
			return "인증 실패";
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
	
	@Autowired private KakaoService kakaoService;
	@GetMapping("kakaoLogin")
	public String kakaoLogin(String code) {
		System.out.println("인가코드 : " + code);
		String accessToken = kakaoService.getAccessToken(code);
		HashMap<String, String> userInfo = kakaoService.getUserInfo(accessToken);
		System.out.println("이메일 : " + userInfo.get("email"));
		System.out.println("이름 : " + userInfo.get("nickname"));
		
		if(userInfo.isEmpty())
			return "member/login";
		
		return "member/index";
	}
}
