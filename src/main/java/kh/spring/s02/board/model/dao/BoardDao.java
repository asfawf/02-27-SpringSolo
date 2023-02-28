package kh.spring.s02.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.spring.s02.board.model.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("boardns.inserid", vo);
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
	
	public int selectOneCount() {
		//board-selectList ("namespace.selectOneCount 의 id")
		return sqlSession.selectOne("boardns.selectOneCount");
		
	}
}
