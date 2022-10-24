<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete</title>
	<c:if test="${not empty msg }">
		<script>alert('${msg}');</script>
	</c:if>
	<c:if test="${empty sessionScope.id}">
		<script>alert('로그인 후 이용해주세요.'); location.href='index';</script>
	</c:if>
</head>
<body>
	<h3>회원 삭제 페이지</h3>
	<form action="delete" method="post">
		<input type="password" name="pw" placeholder="비밀번호"><br>
		<input type="password" name="confirm_Pw" placeholder="비밀번호 확인"><br>
		<input type="submit" value="회원 삭제">
		<input type="button" value="취소" onclick="location.href='index'">
	</form>
</body>
</html>