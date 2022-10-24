<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index</title>
	<c:if test="${not empty msg }">
		<script>alert('${msg}');</script>
	</c:if>
</head>
<body>
	<h3>인덱스 페이지</h3>
	<a href="register">회원 가입</a> | 
	<a href="list">회원 목록</a> | 
	<a href="update">회원 수정</a> | 
	<a href="delete">회원 삭제</a> | 
	<a href="login">로그인</a> | 
	<a href="logout">로그아웃</a>
</body>
</html>