package com.care.db.basic.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.care.db.basic.dto.MemberDTO;
import com.care.db.basic.repository.IMemberDAO;

@Service
public class MemberService {
	@Autowired private IMemberDAO memberDao;
	@Autowired private HttpSession session;

	public String register(MemberDTO member, String confirm_Pw) {
		if(member.getId().isEmpty() || member.getPw().isEmpty() || confirm_Pw.isEmpty()
				|| member.getName().isEmpty() || member.getEmail().isEmpty()) {
			return "모두 입력해주세요.";
		}
		
		if(member.getPw().equals(confirm_Pw) == false) {
			return "비밀번호가 일치하지 않습니다.";
		}
		if(memberDao.selectId(member.getId()) != null) {
			return "이미 사용중인 아이디입니다.";
		}
		
		memberDao.insert(member);
		return "회원가입 성공";
	}
	
	public String login(String id, String pw) {
		if(id.isEmpty() || pw.isEmpty()) {
			return "모두 입력해주세요.";
		}
		
		MemberDTO member = memberDao.selectId(id);
		if(member == null 
				|| pw.equals(member.getPw()) == false) {
			return "아이디 / 비밀번호를 다시 확인해주세요.";
		}
		
		session.setAttribute("id", id);
		session.setAttribute("name", member.getName());
		session.setAttribute("email", member.getEmail());
		return "로그인 성공";
	}
	
	public String update(MemberDTO member, String confirm_Pw) {
		if(member.getId().isEmpty() ||member.getPw().isEmpty() 
				|| member.getName().isEmpty() || member.getEmail().isEmpty()) {
			return "모두 입력해주세요.";
		}
		if(member.getPw().equals(confirm_Pw)==false) {
			return "입력하신 두 비밀번호가 일치하지 않습니다.";
		}
		memberDao.update(member);
		session.invalidate();
		return "회원 수정 성공";
	}
	
	public String delete(String pw, String confirm_Pw) {
		if(pw.isEmpty() || confirm_Pw.isEmpty()) {
			return "모두 입력해주세요.";
		}
		if(pw.equals(confirm_Pw) == false) {
			return "입력한 두 비밀번호가 일치하지 않습니다.";
		}
		String id = (String) session.getAttribute("id");
		if(pw.equals(memberDao.selectId(id).getPw()) == false) {
			return "비밀번호를 다시 확인해주세요.";
		}
		memberDao.delete(id);
		session.invalidate();
		return "삭제 성공";
		
	}
}
