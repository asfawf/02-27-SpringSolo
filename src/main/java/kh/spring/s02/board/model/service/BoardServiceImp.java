package kh.spring.s02.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.spring.s02.board.model.dao.BoardDao;
import kh.spring.s02.board.model.vo.BoardVo;

@Service
public class BoardServiceImp implements BoardService{
	
	@Autowired
	private BoardDao dao;

	@Override
	@Transactional
	public int insert(BoardVo vo) {
		// 어떤 값도 채워지지 않을 경우 기본 값은 0 
		if(vo.getBoardNum() != 0) {
			// 답글의 경우 boardNum 이 채워져 있다 + 원글의 경우 0  
			dao.updateForReply(vo.getBoardNum()); 
		}
		
		return dao.insert(vo);
	}

	@Override
	public int update(BoardVo vo) {
		return dao.update(vo);
	}

	@Override
	public int delete(int num) {
		return dao.delete(num);
	}

	@Override
	public BoardVo selectOne(int boardNum, String writer) {
		
		BoardVo result= dao.selectOne(boardNum);
		
		if(result != null && result.getBoardWriter().equals(writer))
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

	@Override
	public int selectOneCount(String searchWord) {
		return dao.selectOneCount(searchWord);
	}
	
	@Override
	public List<BoardVo> selectList(int currentPage, int limit) {
		
		return dao.selectList(currentPage, limit);
	}

	@Override
	public List<BoardVo> selectList(int currentPage, int limit, String searchWord) {
		return dao.selectList(currentPage, limit, searchWord);	
	}

	
	@Override
	public List<BoardVo> selectReplyList(int boardNum) {
		
		return dao.selectReplyList(boardNum);
	}

	@Override
	public List<BoardVo> selectReplyList(int boardNum, int currentPage, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
