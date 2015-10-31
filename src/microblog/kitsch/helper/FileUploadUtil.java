package microblog.kitsch.helper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import microblog.kitsch.KitschSystem;
import microblog.kitsch.business.domain.Member;

public class FileUploadUtil {
	public static void fileUpload(HttpServletRequest request, HttpServletResponse response, Member member, String fileType) throws ServletException, IOException {
		String uploadDir = KitschSystem.UPLOADED_FILES_ROOT_DIR;
		File dir = new File(uploadDir + member.getEmail());
		if (!dir.exists()) { dir.mkdir(); }
		
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
					// 파일 Part를 디스크에 저장
					if (fileType.equals("profileImage") || fileType.equals("headerImage")) {
						if (contentType.startsWith("image")) {
							part.write(uploadDir + "/images/" + fileType);
						}
						return;
					}
					if (contentType.startsWith("image")) {
						part.write(uploadDir + "/images/" + fileName);
					} else if (contentType.startsWith("video")) {
						part.write(uploadDir + "/videos/" + fileName);
					} else if (contentType.startsWith("audio")) {
						part.write(uploadDir + "/audios/" + fileName);
					}
				}
			}
		}
	}
}
