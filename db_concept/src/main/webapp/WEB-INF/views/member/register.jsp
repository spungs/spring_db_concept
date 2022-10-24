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
	function doubleCheck(){
		req = new XMLHttpRequest();
		req.onreadystatechange = changeText
		req.open('post', 'doubleCheck')
		var reqData = document.getElementById('id').value
		console.log(reqData)
		req.send(reqData)
	}
	
	function changeText(){
		if(req.readyState == 4 && req.status == 200){
			var doubleCheckMsg = document.getElementById('doubleCheckMsg')
			console.log(req.responseText)
			doubleCheckMsg.innerHTML = req.responseText
			
		}
	}
</script>
</head>
<body>
	<c:if test="${not empty msg }">
		<script>alert('${msg}');</script>
	</c:if>
	
	<!-- 
		클라이언트 : doubleCheck 자바스크립트 함수에서 서버로 사용자가 입력한 아이디를 갖고 요청
		서버 : doubleCheck 요청을 받아 doubleCheck 자바 메서드 호출
		서버 : service 객체 내 doubleCheck 자바 메서드 호출
		서버 : dao 객체 내 doubleCheck 자바 메서드 호출
		서버 : tetstMapper.xml 파일에서 <select> 태그로 중복 확인 쿼리문 동작
		서버 : 클라이언트 반환 데이터 "중복된 아이디" or "사용가능한 아이디";
		클라이언트 : id="doubleCheckMsg" 태그에 출력.
	 -->
	 
	<h3>회원가입 페이지</h3>
	<form action="register" method="post">
		<input type="text" placeholder="아이디 " name="id" id="id">
		<input type="button" value="중복 확인 " onclick="doubleCheck()">
		<span id="doubleCheckMsg" style="color:red"></span>
		<br>
		<input type="password" placeholder="비밀번호 " name="pw"><br>
		<input type="password" placeholder="비밀번호 확인 " name="confirm_Pw"><br>
		<input type="text" placeholder="이름 " name="name"><br>
		<input type="text" placeholder="이메일 " name="email"><br>
		<input type="submit" value="회원 가입">
		<input type="button" onclick="location.href='index'" value="취소">
	</form>
</body>
</html>