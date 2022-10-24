<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
		<c:if test="${not empty msg }">
			<script>alert('${msg}');</script>
		</c:if>
</head>
<body>
	<h3>회원 수정 페이지</h3>
	<c:choose>
		<c:when test="${empty sessionScope.id }">
			<script>alert('로그인 후 이용해주세요.'); location.href='index';</script>
		</c:when>
		
		<c:otherwise>
			<form action="update" method="post">
				<input type="text" name="id" value="${sessionScope.id }" readonly="readonly"><br>
				<input type="password" name="pw" placeholder="수정할 비밀번호"><br>
				<input type="password" name="confirm_Pw" placeholder="수정할 비밀번호 확인"><br>
				<input type="text" name="name" placeholder="이름"><br>
				<input type="text" name="email" placeholder="이메일"><br>
				<input type="submit" value="저장">
				<input type="button" value="취소" onclick="location.href='index'">
			</form>
		</c:otherwise>
	
	</c:choose>
</body>
</html>