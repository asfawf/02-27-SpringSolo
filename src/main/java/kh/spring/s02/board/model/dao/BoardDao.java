package kh.spring.s02.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.spring.s02.board.model.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlsession;
	
	public int insert(BoardVo vo) {
		return sqlsession.insert("boardns.inserid", vo);
	}
	
	public int update(BoardVo vo) {
		return sqlsession.update("boardns.updateid", vo);
	}
	
	
	public int updateReadCount(int boardNum) {
		//board-update ("namespace.update 의 id")
		return sqlsession.update("boardns.updateReadCount", boardNum);
		
	}
	
	public int updateForReply(int boardNum) {
		//board-update ("namespace.update 의 id")
		return sqlsession.update("boardns.updateForReply", boardNum);
		
	}
	public int delete(BoardVo vo) {
		return sqlsession.delete("boardns.delete", vo);
	} 

	
	public BoardVo selectOne(int boardNum /* PK 사용*/) {
		//board-mapper ("namespace.selectOne 의 id")
		return sqlsession.selectOne("boardns.selectOneid", boardNum);
		
	}
	
	public List<BoardVo> selectList() {
		//board-selectList ("namespace.insert 의 id")
		return sqlsession.selectList("boardns.selectListid");
		
	}
	
	public int selectOneCount() {
		//board-selectList ("namespace.selectOneCount 의 id")
		return sqlsession.selectOne("boardns.selectOneCount");
		
	}
}
