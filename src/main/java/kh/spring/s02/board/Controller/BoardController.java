package kh.spring.s02.board.Controller;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import kh.spring.s02.board.model.service.BoardService;
import kh.spring.s02.board.model.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
		
		@Autowired
		private BoardService service;
		
		public final static int BOARD_LIMIT = 5;  // 현재 페이지에서 보이게 하고 싶은 글의 개수
		public final static int page_LIMIT = 3; // 화면 하단에 몇개의 페이지 번호를 나타낼건지 ex) << 1 2 3 >> 
		
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView viewListBoard( ModelAndView mv, HttpServletRequest req) {
			
			
			// current , limit / 2 ==> A , current - A = start,  current + A = end
			// 현재 페이지 번호
			int currentPage = 3;
			int totalCnt = service.selectOneCount();
			int totalPage = (totalCnt%BOARD_LIMIT==0)?
					(totalCnt/BOARD_LIMIT) : 
					(totalCnt/BOARD_LIMIT) + 1;
			int startPage = (currentPage%page_LIMIT==0) ?
					(currentPage/page_LIMIT -1)*page_LIMIT + 1 :
					(currentPage/page_LIMIT   )*page_LIMIT + 1;
			int endPage = (startPage + page_LIMIT > totalPage) ?
					totalPage : 
					(startPage + page_LIMIT);
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("totalPage", totalPage);
			map.put("startPage", startPage);
			map.put("endPage", endPage);
			map.put("currentPage", currentPage);
			
			// 이걸 사용할 경우 jsp 에서 ${}로 지칭 가능
			mv.addObject("pageInfo", map); // setAttribute
						
			/*
			 * mv.addObject("totalPage", totalPage); 
			 * mv.addObject("currentPage",currentPage); 
			 * mv.addObject("startPage", startPage); 
			 * mv.addObject("endPage", endPage);
			 */
			
			/*
			 * req.setAttribute("totalPage", totalPage); req.setAttribute("startPage",
			 * startPage); req.setAttribute("endPage", endPage);
			 * req.setAttribute("currentPage", currentPage);
			 * 
			 */
			// 이걸 사용할 경우 jsp 에서 ${}로 지칭 가능
			mv.addObject("boardlist", service.selectList(currentPage, BOARD_LIMIT) );
			
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
			String boardTitle = "수정내용" ;
			String boardContent = "수정제목" ;
			String boardOriginalFilename = ""; // "" ==> 저 상태면(아무것도 없으면) 파일 삭제  혹은 파일없음
			String boardRenameFilename = ""; // "" ==> 저 상태면(아무것도 없으면) 파일 삭제  혹은 파일없음
			
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
			int boardNum= 10;
			int result = service.delete(boardNum);
			
			return;
		}
		
		@GetMapping("/read")
		public void viewReadBoard() {
			//TODO
			int boardNum = 1 ;
			String writer = "user22";
			BoardVo vo = service.selectOne(boardNum, writer);
			 
		}

		// 원글 작성 페이지 이동
		@GetMapping("/insert")
		public ModelAndView viewInsertBoard(
				ModelAndView mv
				, HttpServletRequest req
				, HttpSession session
				, BoardVo vo
				) {
			mv.setViewName("board/insert");
			return mv;
		}
		
		// 원글 작성
		@GetMapping("/insertPostTest")
		public ModelAndView doInsertBoard(
				ModelAndView mv
				, BoardVo vo 
				) {
			vo.setBoardContent("임시내용");
			vo.setBoardTitle("임시제목");
			vo.setBoardWriter("user22");
			
			int result = service.insert(vo);
			
			return mv;
		}
		
		//답글 작성 페이지 이동
		@GetMapping("/insertReply")
		public ModelAndView insertReply(
				ModelAndView mv
				, int boardNum // 몇 번 글의 답글인지
				) {
			mv.setViewName("insertReply");
			
			return mv;
		}
		
		//답글 작성
		//@PostMapping("/insertReply")
		@GetMapping("/insertReplyTestPost")
		public ModelAndView viewInsertReply(
				ModelAndView mv
				, BoardVo vo
				) {
			int boardNum= 4; // 원본글의 번호
			
			vo.setBoardContent("임시답내용");
			vo.setBoardTitle("임시답제목");
			vo.setBoardWriter("user11");
			vo.setBoardNum(boardNum);
			//mv.setViewName("board/insert"); 이게 return 값의 주소를 정하게 되는 구문 
			
			service.insert(vo);
			
			return mv;		
		}
		
//		@RequestMapping(value = "/boardinsert")
		@RequestMapping("/test")
		public ModelAndView test(ModelAndView mv) {

			return mv;
		}
}
