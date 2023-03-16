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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtil")
@PropertySource("classpath:properties/kh2.properties")
public class FileUtil {
	
	//위에서 지정한 Properties 에서 local.repository의 값을 UPLOAD_FOLDER 에 넣어라
	@Value("${local.repository}")
	private String UPLOAD_FOLDER;
	
	
	/***
	 * a
	 * 
	 * @param multi
	 * @param req
	 * @return
	 */
	public List<Map<String, String>> saveFileList(MultipartHttpServletRequest multiReq, HttpServletRequest request,
			String addedPath) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		Iterator<String> iterator = multiReq.getFileNames(); // Name <input name="n1" type="file" >
		while (iterator.hasNext()) {
			String name = iterator.next(); // "n1", "n2"
			MultipartFile multiFile = multiReq.getFile(name);

			Map<String, String> map = new HashMap<String, String>();
			map = saveFile(multiFile, request, addedPath);
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
	public Map<String, String> saveFile(MultipartFile multi, HttpServletRequest request, String addedPath) throws Exception{
		Map<String, String> result = null;
		String renameFilePath = null;
		String renameByTime = null;
		if(multi != null && !multi.equals("")) {
			result = new HashMap<String, String>();

			String orginalFileName = multi.getOriginalFilename();

			// file을 server에 특정 위치(저장할 폴더)에 저장
			String webServerRoot = request.getSession().getServletContext().getRealPath("");
			String savePath = webServerRoot + UPLOAD_FOLDER;
			if (addedPath != null) {
				savePath += addedPath;
			}
			// 저장할 폴더가 안만들어져 있다면 만들어줘야함.
			File folder = new File(savePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// 파일을 savePath 위치에 저장
			// 시간을 활용한 rename
			renameByTime = System.currentTimeMillis() + "_" + orginalFileName;
			// UUID
			// String renameByUUID = UUID.randomUUID().toString() + "_"+orginalFileName;

			renameFilePath = savePath + "\\" + renameByTime;
			multi.transferTo(new File(savePath + "\\" + renameByTime));

			result.put("original", orginalFileName);
			result.put("rename", renameByTime);
		}
		return result;
	}
}
