<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.3.js"></script>
<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<hr>
WEB-INF -> views -> home.jsp // homeController 에서 return home; 으로 해당 jsp 지정 
<hr>
<P>  The time on the server is ${serverTime}. </P>
</body>

${hello }

<button type="button" class="gotoList"> List 로</button>

<script>
	$(".gotoList").on("click", btnGotoList )
	function btnGotoList() {
		location.href="<%=request.getContextPath()%>"+"/board/list";
	}
</script>

</html>
