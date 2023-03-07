<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		, success: function(result){
			console.log(result);
			//$("#frmReply")[0].reset();
			frmReply.reset();
			if(result == "ok"){
				alert("작성 완료")
			}else{
				alert("작성 실패")
			}
		}
		, error: function(){
			
		}
	});

}
</script>

</body>
</html>