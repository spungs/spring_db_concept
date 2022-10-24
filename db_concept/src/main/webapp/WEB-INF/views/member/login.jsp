<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
	<c:if test="${not empty msg }">
		<script>alert('${msg}');</script>
	</c:if>
</head>
<body>
	<h3>로그인 페이지</h3>
	<c:choose>
		<c:when test="${not empty sessionScope.id }">
			<h4>이미 로그인 중입니다.</h4>
			<a href="index">인덱스 페이지로 이동</a>
		</c:when>
		<c:otherwise>
			<form action="login" method="post">
				<input type="text" name="id" placeholder="아이디"> <br>
				<input type="password" name="pw" placeholder="비밀번호"><br> 
				<input type="submit" value="로그인">
				<input type="button" value="취소" onclick="location.href='index'">
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>