package kh.spring.s02.board.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.spring.s02.board.controller.BoardController;
import kh.spring.s02.board.model.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("boardns.insertid", vo);
	}
	
	public int update(BoardVo vo) {
		return sqlSession.update("boardns.updateid", vo);
	}
	
	
	public int updateReadCount(int boardNum) {
		//board-update ("namespace.update 의 id")
		return sqlSession.update("boardns.updateReadCount", boardNum);
		
	}
	
	public int updateForReply(int boardNum) {
		//board-update ("namespace.update 의 id")
		return sqlSession.update("boardns.updateForReply", boardNum);
		
	}

	// 여러가지의 타입을 저장하고 싶으면 Object	
	public List<BoardVo> tempSelect() {
		//board-update ("namespace.update 의 id")
		//return sqlSession.selectList("boardns.tempSelect");
		/*
		 * List<HashMap<String , Object>> listmap=
		 * sqlSession.selectList("boardns.tempSelect"); for (HashMap<String , Object>
		 * map : listmap) { System.out.println((String)map.get("boardDate")); }
		 */
		
		List<BoardVo> volist= sqlSession.selectList("boardns.tempSelect");
		for (BoardVo vo : volist) {
			System.out.println(vo.getBoardDate());
		}
		// property = key = attribute = column =field
		return volist;
	}
	
	public int delete(int num) {
		return sqlSession.delete("boardns.deleteid", num);
	} 

	
	public BoardVo selectOne(int boardNum /* PK 사용*/) {
		//board-mapper ("namespace.selectOne 의 id")
		return sqlSession.selectOne("boardns.selectOneid", boardNum);
		
	}
	
	public List<BoardVo> selectList() {
		//board-selectList ("namespace.insert 의 id")
		return sqlSession.selectList("boardns.selectListid");
		
	}
	
	public List<BoardVo> selectList(int currentPage, int limit) {
		
		int offset= (currentPage-1)*limit ;
		
		RowBounds rb= new RowBounds(offset, limit); // 현재 페이지 , 1페이지에서 보일 리스트 개수
		
		return sqlSession.selectList("boardns.selectListid", null , rb);
		
	}
	
	
public List<BoardVo> selectList(int currentPage, int limit, String searchWord) {
		
		int offset= (currentPage-1)*limit ;
		
		RowBounds rb= new RowBounds(offset, limit); // 현재 페이지 , 1페이지에서 보일 리스트 개수
		
		return sqlSession.selectList("boardns.selectListid",searchWord, rb);
		
	}
	
	public int selectOneCount() {
		//board-selectList ("namespace.selectOneCount 의 id")
		return sqlSession.selectOne("boardns.selectOneCount");
		
	}
	
	public int selectOneCount(String searchWord) {
		//board-selectList ("namespace.selectOneCount 의 id")
		return sqlSession.selectOne("boardns.selectOneCount", searchWord);
		
	}

	public List<BoardVo> selectReplyList(int boardNum) {
		return sqlSession.selectList("boardns.selectReplyList",boardNum);
	}
}
