<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test1</title>
</head>
<body>
board / list . jsp
<hr>
[${boardlist }]

<c:forEach items="${boardlist }" var="board">
	${board.boardNum}<br>
</c:forEach>

<hr>
	<c:forEach begin="${pageInfo.startPage }" end="${pageInfo.endPage }" var="page">		
		${page }
		<c:if test="${pageInfo.endPage != page }">
		,
		</c:if>
	</c:forEach>
<hr>

</body>
</html>