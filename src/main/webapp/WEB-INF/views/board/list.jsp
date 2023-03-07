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
	<tr>
		<td>${board.boardNum} </td>
		<td><a href="<%=request.getContextPath()%>/board/read?boardNum=${board.boardNum}"/> ${board.boardTitle} </td>
		<td>${board.boardWriter}  </td>
		<td>${board.boardDate}  </td>
		<td>${board.boardReadcount}  </td>
	</tr>

</c:forEach>

<hr>
	<c:forEach begin="${pageInfo.startPage }" end="${pageInfo.endPage }" var="page">		
		${page }
		<c:if test="${pageInfo.endPage != page }">
		,
		</c:if>
	</c:forEach>
<hr>

<%-- 


<hr>
	<c:forEach begin="${startPage }" end="${endPage }" var="page">		
		${page }
		<c:if test="${endPage != page }">
		,
		</c:if>
	</c:forEach>
<hr> --%>

</body>
</html>