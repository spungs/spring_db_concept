<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>register</title>
<script>
	var req;
	function doubleCheck(){		// 아이디 중복체크하는 함수
		req = new XMLHttpRequest();
		req.onreadystatechange = changeText
		req.open('post', 'doubleCheck')
		var reqData = document.getElementById('id').value
		req.send(reqData)
	}
	
	function changeText(){
		if(req.readyState == 4 && req.status == 200){
			var doubleCheckMsg = document.getElementById('doubleCheckMsg')
			doubleCheckMsg.innerHTML = req.responseText
		}
	}
	
	function sendAuth(){		// 인증번호를 전송할 함수
		req = new XMLHttpRequest();
		req.onreadystatechange = emailMsg
		req.open('post', 'sendAuth')
		var reqData = document.getElementById('email').value
		req.send(reqData);
	}
	
	function checkAuth(){		// 인증번호를 확인하는 함수
		req = new XMLHttpRequest();
		req.onreadystatechange = emailMsg
		req.open('post', 'checkAuth')
		var reqData = document.getElementById('authNumber').value
		req.send(reqData)
	}
	
	function emailMsg(){
		if(req.readyState == 4 && req.status == 200){
			var emailMsg = document.getElementById('emailMsg')
			emailMsg.innerHTML = req.responseText
		}
	}
	
</script>
</head>
<body>
	<c:if test="${not empty msg }">
		<script>alert('${msg}');</script>
	</c:if>
	<font id="doubleCheckMsg" color="red"></font>
	<font id="emailMsg" color="blue"></font>
	<!-- 
		클라이언트 : doubleCheck 자바스크립트 함수에서 서버로 사용자가 입력한 아이디를 갖고 요청
		서버 : doubleCheck 요청을 받아 doubleCheck 자바 메서드 호출
		서버 : service 객체 내 doubleCheck 자바 메서드 호출
		서버 : dao 객체 내 doubleCheck 자바 메서드 호출
		서버 : tetstMapper.xml 파일에서 <select> 태그로 중복 확인 쿼리문 동작
		서버 : 클라이언트 반환 데이터 "중복된 아이디" or "사용가능한 아이디";
		클라이언트 : id="doubleCheckMsg" 태그에 출력.
	 -->
	 
	 <!-- 
		클라이언트 : sendAuth 자바스크립트 함수에서 서버로 사용자가 입력한 이메일을 갖고 요청 
		서버 : sendAuth 요청을 받아 sendAuth 자바 메서드 호출
		서버 : sendAuth 메서드 안에서 6자리의 랜덤 값 생성 후 클라이언트에게 응답
		클라이언트 : id="emailMsg" 태그에 출력.
		
		클라이언트 : checkAuth 자바스크립트 함수에서 서버로 사용자가 입력한 인증번호를 갖고 요청 
		서버 : checkAuth 요청을 받아 checkAuth 자바 메서드 호출
		서버 : checkAuth 메서드 안에서 인증번호 검증 후 클라이언트에게 응답
		서버 : 응답 메시지 : "인증번호를 입력하세요" or "인증 실패" or "인증 성공"
		클라이언트 : id="emailMsg" 태그에 출력.
	-->
	
	<form action="register" method="post">
		<input type="text" placeholder="아이디 " name="id" id="id">
		<input type="button" value="중복 확인 " onclick="doubleCheck()"><br>
		<input type="password" placeholder="비밀번호 " name="pw"><br>
		<input type="password" placeholder="비밀번호 확인 " name="confirm_Pw"><br>
		<input type="text" placeholder="이름 " name="name"><br>
		
		<input type="text" placeholder="이메일 " name="email" id="email"><br>
		<input type="button" value="인증 번호 전송" onclick="sendAuth()"><br>
		
		<input type="text" name="authNumber" id="authNumber" placeholder="인증 번호">
		<input type="button" value="인증 번호 확인" onclick="checkAuth()"><br>
		
		<input type="submit" value="회원 가입">
		<input type="button" onclick="location.href='index'" value="취소">
	</form>
</body>
</html>