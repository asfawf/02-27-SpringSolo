<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
<c:set var="uploadpath" value="/resources/uploadfiles/"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글</title>
	<script src="https://code.jquery.com/jquery-3.6.3.js" ></script>
</head>
<body>
	<h1>${board.boardNum } 게시글</h1> 
	<div>
	<p> ${board.boardTitle }</p>
	</div>
	<div>
	${board.boardContent }
	</div>
	<div>
	<img src="${cpath }${uploadpath}${board.boardRenameFilename }">
	</div>
	<form id="frmReply" enctype="multipart/form-data">
	<fieldset>
	 	<legend>답글작성</legend>
		<div>제목<input type="text" name="boardTitle"></div>
		<div>내용<input type="text" name="boardContent"></div>
		<div>파일<input type="file" name="report"></div>
		<input type="hidden" name="boardNum" value="${board.boardNum }">
		<button type="button" class="btn reply">답글작성</button>
		<!-- <button type="reset">초기화</button> -->
	</fieldset>
	</form>
	<hr>
<h4>댓글</h4>
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
		<!-- 제목을 누르면 글읽기 화면으로 이동 -->
		<tr>
			<td>${reply.boardNum }</td>
			<td><a href="<%=request.getContextPath()%>/board/read?boardNum=${reply.boardNum }">${reply.boardTitle }</a></td>
			<td>${reply.boardWriter }</td>
			<td>${reply.boardDate }</td>
			<td>${reply.boardReadcount }</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<script>
	$(".btn.reply").on("click", replyClickHandler);
	
	function replyClickHandler(){
		console.log(this);  // this (DOM)
		console.log($(this));  // this를 jquery 형태로 변형
		//$(this).parents("form")
		//console.log($("#frmReply").serialize());
		//file포함되어있는 경우 serialize()로 데이터 전달 할 수 없음. contentType:"multipart/form-data" 적용 안됨.
		let formdata = new FormData();
		formdata.append("boardTitle", $("[name=boardTitle]").val());
		formdata.append("boardContent", $("[name=boardContent]").val());
		formdata.append("report",  $("[name=report]")[0].files[0]);
		formdata.append("boardNum", $("[name=boardNum]").val());
		console.log(formdata);
		$.ajax({ 
			url: "${pageContext.request.contextPath}/board/insertReplyAjax"
			, type: "post"
			
			//, contentType:"multipart/form-data"
			, contentType: false
			, processData: false
			, data: formdata  // QueryString // js object
			
			, dataType:"json"   // success에 들어오는 데이터가 json 모양일것이고 이것을 js object 로 변형해서 result에 실어줌.

    
          
            
    

          
    
    
  
			, success: function(result){
				console.log(result);
				console.log(result[0]);
				console.log(result[0].boardDate);
				//$("#frmReply").eq(0).reset();  // 작성된 글 초기화
				frmReply.reset(); // 작성된 글 초기화
				if(result.length > 0){
					alert("댓글작성되었습니다.")
				} else {
					alert("댓글작성되지 않았습니다. 다시 작성해주세요.")
				}
				// 답글 부분 화면 업데이트
				displayReply(result);
			}
			, error: function(){
				
			}
		});
	}	
	// 답글 부분 화면 업데이트
	function displayReply(result){
		console.log(result);
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