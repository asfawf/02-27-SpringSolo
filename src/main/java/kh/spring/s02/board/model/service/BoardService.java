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
	public List<BoardVo> selectList();
	public int selectOneCount();
}
