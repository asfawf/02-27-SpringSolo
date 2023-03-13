package kh.spring.s02.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import kh.spring.s02.board.model.service.BoardService;
import kh.spring.s02.board.model.vo.BoardVo;
import kh.spring.s02.common.file.FileUtil;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;

	private final static int BOARD_LIMIT = 5;
	private final static int PAGE_LIMIT = 3;
	private final static String UPLOAD_FOLDER = "\\resources\\fileupload";

	@RequestMapping(value = "/list")
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView viewListBoard(ModelAndView mv, HttpServletRequest req) {
		// TODO
		// 검색단어는 제목,내용,작성자에서 포함되어있으면 찾기
		// null 또는 "" 은 검색하지 않음.
//		String searchWord = null;  
//		String searchWord = "";  
		String searchWord = "답";

		try {
			req.setCharacterEncoding("UTF-8");
			searchWord = req.getParameter("searchWord");
			System.out.println("한글 확인: " + searchWord);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// TODO
		int currentPage = 1;
		int totalCnt = service.selectOneCount(searchWord);
		int totalPage = (totalCnt % BOARD_LIMIT == 0) ? (totalCnt / BOARD_LIMIT) : (totalCnt / BOARD_LIMIT) + 1;
		int startPage = (currentPage % PAGE_LIMIT == 0) ? (currentPage / PAGE_LIMIT - 1) * PAGE_LIMIT + 1
				: (currentPage / PAGE_LIMIT) * PAGE_LIMIT + 1;
		int endPage = (startPage + PAGE_LIMIT > totalPage) ? totalPage : (startPage + PAGE_LIMIT);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("totalPage", totalPage);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("currentPage", currentPage);
		mv.addObject("pageInfo", map);

		mv.addObject("boardlist", service.selectList(currentPage, BOARD_LIMIT, searchWord));
		mv.setViewName("board/list");
		return mv;
	}

//		@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView viewUpdateBoard(ModelAndView mv) {
		System.out.println("board list controller!!!!");
		mv.addObject("boardlist", service.selectList());
		mv.setViewName("board/list");
		return mv;

	}

	@GetMapping("/updatePostTest")
	public void updateBoard() {
		int result = -1;

		int boardNum = 1;
		String boardTitle = "수정내용";
		String boardContent = "수정제목";
		String boardOriginalFilename = ""; // "" ==> 저 상태면(아무것도 없으면) 파일 삭제 혹은 파일없음
		String boardRenameFilename = ""; // "" ==> 저 상태면(아무것도 없으면) 파일 삭제 혹은 파일없음

		BoardVo vo = new BoardVo();

		vo.setBoardContent(boardContent);
		vo.setBoardTitle(boardTitle);
		vo.setBoardNum(boardNum);
		vo.setBoardOriginalFilename(boardOriginalFilename);
		vo.setBoardRenameFilename(boardRenameFilename);

		result = service.update(vo);
	}

	@GetMapping("/delete")
	public void viewDeleteBoard() {
		int boardNum = 10;
		int result = service.delete(boardNum);

		return;
	}

	@GetMapping("/read")
	public ModelAndView viewReadBoard(ModelAndView mv, @RequestParam("boardNum") int boardNum) {
		// TODO
		String writer = "user22";

		BoardVo vo = service.selectOne(boardNum, writer);
		mv.addObject("board", vo);

		List<BoardVo> replyList = service.selectReplyList(boardNum);
		mv.addObject("replyList", replyList);

		mv.setViewName("board/read");
		return mv;
	}

	// 원글 작성 페이지 이동
	@GetMapping("/insert")
	public ModelAndView viewInsertBoard(
			ModelAndView mv
			, HttpServletRequest req
			, HttpSession session
			, BoardVo vo) {
		mv.setViewName("board/insert");
		return mv;
	}

	// 원글 작성
	@PostMapping("/insert")
	public ModelAndView doInsertBoard(
			MultipartHttpServletRequest multiReq
			, @RequestParam(name = "report", required = false) MultipartFile multi // report 는
			, HttpServletRequest req
			, ModelAndView mv
			, BoardVo vo) {
		// common 의 FileUtil.jsp 와 연동
		System.out.println("#####################여기여기");
		
		int result = service.insert(vo);
		return mv;

		// @ExptionHandler
	}

	// 답글 작성 페이지 이동
	@GetMapping("/insertReply")
	public ModelAndView insertReply(ModelAndView mv, int boardNum // 몇 번 글의 답글인지
	) {
		mv.setViewName("insertReply");

		return mv;
	}

	// 답글 작성
	// @PostMapping("/insertReply")
	@GetMapping("/insertReplyTestPost")
	public ModelAndView viewInsertReply(ModelAndView mv, BoardVo vo) {
		int boardNum = 4; // 원본글의 번호

		vo.setBoardContent("임시답내용");
		vo.setBoardTitle("임시답제목");
		vo.setBoardWriter("user11");
		vo.setBoardNum(boardNum);
		// mv.setViewName("board/insert"); 이게 return 값의 주소를 정하게 되는 구문

		service.insert(vo);

		return mv;
	}

	@PostMapping("/insertReplyAjax")
	@ResponseBody
	public String insertReplyAjax(BoardVo vo) {

		/*
		 * int boardNum= 4; // 원본글의 번호
		 * 
		 * vo.setBoardContent("임시답내용"); vo.setBoardTitle("임시답제목");
		 * vo.setBoardWriter("user11");
		 */
		vo.setBoardWriter("user11");
		// mv.setViewName("board/insert"); 이게 return 값의 주소를 정하게 되는 구문

		// 답글 작성
		service.insert(vo);

		// 연관된 답글들 조회해서 ajax 로 return 하지만 mv 로 싣는건 불가
		List<BoardVo> replyList = service.selectReplyList(vo.getBoardNum());
		// mv.addObject("replyList", replyList);

		return new Gson().toJson(replyList);
	}

//		@RequestMapping(value = "/boardinsert")
	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mv) {

		return mv;
	}
}
