package com.care.db.basic.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.care.db.basic.dto.MemberDTO;
import com.care.db.basic.repository.IMemberDAO;

@Service
public class MemberService {
	@Autowired
	private IMemberDAO memberDao;
	@Autowired
	private HttpSession session;

	public String register(MemberDTO member, String confirm_Pw) {
		if (member.getId().isEmpty() || member.getPw().isEmpty() || confirm_Pw.isEmpty() || member.getName().isEmpty()
				|| member.getEmail().isEmpty()) {
			return "모두 입력해주세요.";
		}

		if (member.getPw().equals(confirm_Pw) == false) {
			return "비밀번호가 일치하지 않습니다.";
		}
		if (memberDao.selectId(member.getId()) != null) {
			return "이미 사용중인 아이디입니다.";
		}
		
		/*
		 * 암호화
		 * 단방형 암호화 : 평문 -> 암호문, 암호문 -x 평문 (패스워드 암호화 시 주로 사용) 
		 * 양방형 암호화 : 평문 <-> 암호문 (문서 암호화 시 주로 사용)
		 */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String cipherPassword = encoder.encode(member.getPw());
		System.out.println("암호화 적용 전 패스워드  : " + member.getPw());
		System.out.println("암호화된 패스워드 : " + cipherPassword);
		System.out.println("암호화된 패스워드 길이 : " + cipherPassword.length());
		member.setPw(cipherPassword);
		
//		ALTER TABLE DB_BASIC MODIFY pw varchar2(60)
		
		/*
		단방향의 특징
		동일한 비밀번호를 입력해도 암호문은 다르다.
		 - salt(random 값)을 평문과 더해서 암호화 알고리즘에 적용 후 암호화 과정을 거쳐서 암호문을 생성함.
		단방향 암호화 알고리즘이 동일하다면 길이는 동일하다.
		ex) 암호화 알고리즘이 SHA-1 이라는 알고리즘이라면 길이는 모두 동일하다.
		
		암호화 적용 전 패스워드  : 12341234
		암호화된 패스워드 : $2a$10$h4EkVDg7F2.Zi1YALdp7aOKioO8XK376xcgHv3OW4bYHb1t5/hZ42
		암호화된 패스워드 길이 : 60
		
		Random : salt
		(암호문에서의 랜덤값을 만들고 암호화 알고리즘에 보내서 암호문을 만들기 때문에 똑같은 패스워드여도 다른 암호문이 생성됨)
		암호화 적용 전 패스워드  : 1234
		암호화된 패스워드 : $2a$10$DnkWt7kAq8DzVe0JjCy.GuvLkxioLmYCewXy.wfHOWiMTIqfAyIGK
		암호화된 패스워드 길이 : 60
		
		암호화 적용 전 패스워드  : 1234
		암호화된 패스워드 : $2a$10$VQKpn8HAK7U6njl0WbGVZeq2kBTOXcbMUKal5jB3peo4BehmEqRPy
		암호화된 패스워드 길이 : 60
		*/

		memberDao.insert(member);
		return "회원가입 성공";
	}

	public String login(String id, String pw) {
		if (id.isEmpty() || pw.isEmpty()) {
			return "모두 입력해주세요.";
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		MemberDTO member = memberDao.selectId(id);
		if (member == null || encoder.matches(pw, member.getPw()) == false) {
			return "아이디 / 비밀번호를 다시 확인해주세요.";
		}
		
		session.setAttribute("id", id);
		session.setAttribute("name", member.getName());
		session.setAttribute("email", member.getEmail());
		return "로그인 성공";
	}

	public String update(MemberDTO member, String confirm_Pw) {
		if (member.getId().isEmpty() || member.getPw().isEmpty() || member.getName().isEmpty()
				|| member.getEmail().isEmpty()) {
			return "모두 입력해주세요.";
		}
		
		if (member.getPw().equals(confirm_Pw) == false) {
			return "입력하신 두 비밀번호가 일치하지 않습니다.";
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String cipherPassword = encoder.encode(member.getPw());
		member.setPw(cipherPassword);
		
		memberDao.update(member);
		session.invalidate();
		return "회원 수정 성공";
	}

	public String delete(String pw, String confirm_Pw) {
		if (pw.isEmpty() || confirm_Pw.isEmpty()) {
			return "모두 입력해주세요.";
		}
		if (pw.equals(confirm_Pw) == false) {
			return "입력한 두 비밀번호가 일치하지 않습니다.";
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		
		String id = (String) session.getAttribute("id");
		if (encoder.matches(pw, memberDao.selectId(id).getPw()) == false) {
			return "비밀번호를 다시 확인해주세요.";
		}
		memberDao.delete(id);
		session.invalidate();
		return "삭제 성공";

	}

	public String doubleCheck(String id) {
		if (memberDao.selectId(id) == null) {
			return "사용가능한 아이디";
		}
		return "중복된 아이디";
	}
}
