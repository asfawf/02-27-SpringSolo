<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cPath" value="${pageContext.request.contextPath }"></c:set>
<c:set var="uploadPath" value="/resources/uploadfiles/"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.3.js" ></script>
<title>게시글</title>
</head>
<body>

<div>
<h1>${board.boardNum } 게시글</h1>
</div>
<div>
${board.boardTitle }
</div>
<div>
${board.boardContent}
</div>
<div>
	<img src="${cPath }${uploadPath}${board.boardRenameFilename }">
</div>
<hr>

<form id="frmReply">
<legend>답글 작성</legend>
	<fieldset> 
		<div>제목<input type="text" name="boardTitle"></div>
		<div>내용<input type="text" name="boardContent"></div>
		<input type="hidden" value="${board.boardNum }" name="boardNum">
		<button type="button" class="btn reply">답글작성</button>
		<button type="reset" class="btn reset">초기화</button>
	</fieldset>
</form>
<hr>

<table border="1">
	<thead> 
		<tr>
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${replyList }" var="reply">
			<tr>
				<td>${reply.boardNum} </td>
				<td>${reply.boardTitle} </td>
				<td>${reply.boardWriter} </td>
				<td>${reply.boardDate} </td>
				<td>${reply.boardReadcount} </td>
			</tr>
		</c:forEach>
		
	</tbody>
</table>
	
	
	
<script>

$(".btn.reply").on("click", replyClickHandler);

function replyClickHandler(){
	
	console.log(this); // 클릭이 일어난 Element == this
	console.log($(this));
	// 이게 기본모양 ==> $.ajax();
	
	$.ajax({
		
		/* url 필수 */
		url: "<%=request.getContextPath()%>"+"/board/insertReplyAjax"
		, type: "post"
		, data: $("#frmReply").serialize()
		// data: {boardTitle: $("#a").val(), boardContent: $("#b").val() , boardNum: '${board.boardNum}'}
		,dataType: "json" // success 에 들어오는 데이터가 json 모양일것이고 이것을 js object 로 변형해서 return 값을 변형해라 
		, success: function(result){
			console.log(result);
			console.log(result[0]);
			console.log(result[0].boardDate);
			//$("#frmReply")[0].reset();
			frmReply.reset();
			if(result.length > 0){
				alert("작성 완료")
			}else{
				alert("작성 실패")
			}
			
			// 답글 부분 화면 업데이트 + 호출
			dispaltRelpy(result);
		}
		, error: function(){
			
		}
	});
}

		//답글 부분 화면 업데이트 + 응답
		function dispaltRelpy(result){
			
			
			var htmlVal = '';
			for(i = 0; i< result.length; i++){
				var reply = result[i];
				htmlVal += '<tr>';
				htmlVal += '<td>'+reply.boardNum+'</td>';
				htmlVal += '<td><a href="<%=request.getContextPath()%>/board/read?boardNum='+reply.boardNum+'">'+reply.boardTitle+'</a></td>';
				htmlVal += '<td>'+reply.boardWriter+'</td>';
				htmlVal += '<td>'+reply.boardDate+'</td>';
				htmlVal += '<td>'+reply.boardReadcount+'</td>';
				htmlVal += '</tr>';
			}
			$("tbody").html(htmlVal);
			
		}


</script>

</body>
</html>