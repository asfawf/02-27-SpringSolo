package kh.spring.s02.board.model.vo;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class BoardFileVo {
	private String originalFilename;
	private String renameFilename;
	
	private List<BoardFileVo> boardFileList;
	
	
}
