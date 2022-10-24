<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
</head>
<body>
	<h3>회원 목록 페이지</h3>
	<c:choose>
		<c:when test="${members.isEmpty()}">
			등록된 회원이 없습니다.
		</c:when>
		<c:otherwise>
		 <table border="1">
		 	<tr>
		 		<td>아이디</td>
		 		<td>비밀번호</td>
		 		<td>이름</td>
		 		<td>이메일</td>
		 	</tr>
			<c:forEach var="member" items="${members }">
			<tr>
				<td>${member.id }</td>
				<td>${member.pw }</td>
				<td>${member.name }</td>
				<td>${member.email }</td>
			</tr>
			</c:forEach>
		 </table>
		</c:otherwise>
	</c:choose>
	<br>
	total member : ${members.size() }<br>
	<a href="index">인덱스 페이지로 이동</a>
</body>
</html>