package microblog.kitsch.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;

public class FileUploadUtil {
	public static String[] fileUpload(HttpServletRequest request, HttpServletResponse response, Member member, String fileType, String uploadDir) throws ServletException, IOException {
		ArrayList<String> fList = new ArrayList<String>();
		String[] filePaths = null;
		/*String uploadDir = KitschSystem.UPLOADED_FILES_ROOT_DIR;
		File dir = new File(uploadDir);
		if (!dir.exists()) { dir.mkdir(); }*/
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Collection<Part> parts = request.getParts();
		
		for (Part part : parts) {
			// Part가 파일인지 여부는 content-type 헤더의 유무로  확인 가능
			String contentType = part.getContentType();
			if (contentType != null) {
				
				// 파일 이름은 content-disposition 헤더로부터 추출 가능
				String fileName = null;
				String contentDispositionHeader = part.getHeader("content-disposition");
				String[] elements = contentDispositionHeader.split(";");
				for (String element : elements) {
					if (element.trim().startsWith("filename")) {
						fileName = element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
					}
				}
				
				// 파일 이름이 비었다면 파일 필드는 있지만 아무 파일도 업로드 하지 않은 경우에 해당
				if (fileName != null && !fileName.isEmpty()) {
					String fullPath = null;
					// 파일 Part를 디스크에 저장
					if (fileType.equals("profileImage") || fileType.equals("headerImage")) {
						if (contentType.startsWith("image")) {
							fullPath = uploadDir + "/images/" + member.getEmail() + "$" + fileType;
							part.write(fullPath);
							request.setAttribute("contentType", "image");
						}
					} else {
						if (contentType.startsWith("image")) {
							fullPath = uploadDir + "/images/" + member.getEmail() + "$" + fileName;
							part.write(fullPath);
							request.setAttribute("contentType", "image");
						} else if (contentType.startsWith("video")) {
							fullPath = uploadDir + "/videos/" + member.getEmail() + "$" + fileName;
							part.write(fullPath);
							request.setAttribute("contentType", "video");
						} else if (contentType.startsWith("audio")) {
							fullPath = uploadDir + "/audios/" + member.getEmail() + "$" + fileName;
							part.write(fullPath);
							request.setAttribute("contentType", "audio");
						}
					}
					fList.add(fullPath);
				}
				for (String path : fList) {
					filePaths = KitschUtil.convertToStringArray(path, Posting.PATH_DELIMITER, false);
				}
			} else {
				String partName = part.getName(); // 필드 이름
			    String fieldValue = request.getParameter(partName); // 필드 값
			    request.setAttribute(partName, fieldValue);
			}
		}
		return filePaths;
	}
}
