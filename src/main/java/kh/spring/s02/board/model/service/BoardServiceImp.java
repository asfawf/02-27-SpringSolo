package kh.spring.s02.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kh.spring.s02.board.model.dao.BoardDao;
import kh.spring.s02.board.model.vo.BoardVo;

@Service
public class BoardServiceImp implements BoardService{
	private BoardDao dao;

	@Override
	public int insert(BoardVo vo) {
		return dao.insert(vo);
	}

	@Override
	public int update(BoardVo vo) {
		return dao.update(vo);
	}

	@Override
	public int delete(BoardVo vo) {
		return dao.delete(vo);
	}

	@Override
	public BoardVo selectOne(int boardNum, String writer) {
		
		BoardVo result= dao.selectOne(boardNum);
		
		if(result.getBoardWriter().equals(writer))
		{
			dao.updateReadCount(boardNum);
		}
		return result;
	}

	@Override
	public List<BoardVo> selectList() {
		
		return dao.selectList();
	}

	@Override
	public int selectOneCount() {

		return dao.selectOneCount();
	}
}
