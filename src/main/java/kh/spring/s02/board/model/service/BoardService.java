package kh.spring.s02.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kh.spring.s02.board.model.vo.BoardVo;

public interface BoardService {
	public int insert(BoardVo vo);
	public int update(BoardVo vo);
//	public int updateReadCount(int boardNum);
//	public int updateForReply(int boardNum);
	public int delete(int num);
	public BoardVo selectOne(int boardNum /* PK 사용*/, String writer);
	
	
	public List<BoardVo> selectList(); //전체읽기
	public List<BoardVo> selectList(int currentPage , int limit); // paging 처리
	public List<BoardVo> selectList(int currentPage , int limit, String searchWord); // paging 처리+ search
	
	public int selectOneCount();
	public int selectOneCount(String searchWord);
	
	public List<BoardVo> selectReplyList(int boardNum); // 특정 글의  댓글 읽기 전체읽기
	public List<BoardVo> selectReplyList(int boardNum, int currentPage , int limit); // 특정 글의  댓글 paging 처리
	
}
