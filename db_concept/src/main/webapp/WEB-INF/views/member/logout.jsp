<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<c:if test="${not empty msg }" >
	<script>
		alert("${msg}");
	</script>
</c:if>

<script>
	location.href='index';
</script>