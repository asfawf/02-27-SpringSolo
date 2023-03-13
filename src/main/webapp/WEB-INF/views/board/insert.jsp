<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/insert</title>
</head>
<body>
<h1>board Insert</h1>
<!-- file upload : encType -->
여기여기
<form action="insert" method="post" enctype="multipart/form-data">
		<input type="text" name="boardTitle" placeholder="제목">
		<input type="text" name="boardContent" placeholder="내용">
		<!-- file의 경우 name 은 vo 와 다른 이름으로 해야한다. -->
		<input type="file" name="report" placeholder="첨부" >
		<button type="submit">제출</button>	
	</form>
</body>
</html>