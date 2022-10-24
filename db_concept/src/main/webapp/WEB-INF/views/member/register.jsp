<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>register</title>
</head>
<body>
	<c:if test="${not empty msg }">
		<script>alert('${msg}');</script>
	</c:if>
	<h3>회원가입 페이지</h3>
	<form action="register" method="post">
		<input type="text" placeholder="아이디 " name="id"><br>
		<input type="password" placeholder="비밀번호 " name="pw"><br>
		<input type="password" placeholder="비밀번호 확인 " name="confirm_Pw"><br>
		<input type="text" placeholder="이름 " name="name"><br>
		<input type="text" placeholder="이메일 " name="email"><br>
		<input type="submit" value="가입">
		<input type="button" onclick="location.href='index'" value="취소">
	</form>
</body>
</html>