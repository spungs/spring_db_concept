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
			<!-- 
				이미지 경로 
				https://developers.kakao.com/tool/demo/login/login?method=dynamic
				문서 경로 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
				
				REST API : 184b7cd1ae078e6726bfc4116b250c3c
				REDIRECT URI : http://localhost:8085/db/kakaoLogin
				인가 코드 요청 URI : 
				/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code
			
		 -->
			<a href="https://kauth.kakao.com/oauth/authorize?
			client_id=6d17181e330205aefb88af04bd65f7d2&
			redirect_uri=http://localhost:8085/db/kakaoLogin&
			response_type=code">
				<img src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
				width="170" alt="카카오 로그인 버튼" />
			</a>
		</c:otherwise>
	</c:choose>
</body>
</html>