package kh.spring.s02.common.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kh.spring.s02.board.model.vo.BoardVo;

public class FileUtil {
	private final static String UPLOAD_FOLDER = "\\resources\\uploadfiles";
	
	/***
	 * 
	 * @param multi
	 * @param req
	 * @return
	 */
	public List<Map<String, String>> saveFileList(MultipartHttpServletRequest multiReq, HttpServletRequest req, HttpSession session
			, BoardVo vo){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Iterator<String> iterator = multiReq.getFileNames();
		
		while(iterator.hasNext()) { // Name <input name = "n1" type="file">
			String name = iterator.next(); //  "n1" "n2"
			MultipartFile multiFile = multiReq.getFile(name); // 이게 꺼내기
			Map<String, String> map = new HashMap<String, String>();
			map.put("original", multiFile.getOriginalFilename());
			map.put("rename", saveFile.getOriginalFilename());
			result.add(map);
		}
		
		return result;
	}
	
	/***
	 * 
	 * @param multi
	 * @param req
	 * @return : map - "original : original filePath ", "rename":saved file path
	 */
	public Map<String, String> saveFile(MultipartFile multi, HttpServletRequest req, String addedPath) throws Exception {
		Map<String, String> result = null;
		
		String renameFilePath = null;
		
		if(multi != null && !multi.equals("")) {
			String originalFileName= multi.getOriginalFilename();
			
			// file 을 server 에 특정 위치(저장할 폴더)에 저장				
			String webServerRoot = req.getSession().getServletContext().getRealPath("");							
			String savePath= webServerRoot + UPLOAD_FOLDER;
			if(addedPath != null ) {
				savePath = savePath +addedPath;
			}
			
			
			// 저장할 폴더가 없다면 생성
			File folder = new File(savePath);
			if(!folder.exists()) {
				//mkdirs : 전체적으로 없는 폴더를 만들어 간다
				folder.mkdirs();
			}
			
			// 시간을 활용한 rename
			String renameByTime = System.currentTimeMillis() + "_"+ originalFileName;
			// UUID
			String renameByUUID = UUID.randomUUID().toString() + "_" + originalFileName; 
			
			renameFilePath = savePath + "\\" + renameByUUID;
			multi.transferTo(new File(savePath + "\\" + renameByUUID ));
			
			
			
		}
		return renameFilePath ;
	}
}
