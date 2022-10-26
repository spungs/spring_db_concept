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

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	function daumPost() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
				// 예제를 참고하여 다양한 활용법을 확인해 보세요.
	            
	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }
	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('postcode').value = data.zonecode;
	            document.getElementById("address").value = addr;
	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById("detailAddress").focus();
	        }
	    }).open();
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
		
		<input type="text" placeholder="이메일 " name="email" id="email">
		<input type="button" value="인증 번호 전송" onclick="sendAuth()"><br>
		
		<input type="text" name="authNumber" id="authNumber" placeholder="인증 번호">
		<input type="button" value="인증 번호 확인" onclick="checkAuth()"><br>
		
		<input type="text" id="postcode" placeholder="우편번호" readonly="readonly">
		<input type="button" onclick="daumPost()" value="우편번호 찾기"><br>
		<input type="text" id="address" placeholder="주소" readonly="readonly"><br>
		<input type="text" id="detailAddress" placeholder="상세주소"><br>
		
		<input type="submit" value="회원 가입">
		<input type="button" onclick="location.href='index'" value="취소">
	</form>
</body>
</html>