package microblog.kitsch.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import microblog.kitsch.KitschSystem;
import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.business.service.MemberServiceImpl;
import microblog.kitsch.business.service.PostingService;
import microblog.kitsch.business.service.PostingServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.FileUploadUtil;
import microblog.kitsch.helper.IllegalDataException;
import microblog.kitsch.helper.KitschUtil;

/**
 * Servlet implementation class PostingController
 */
public class PostingController extends HttpServlet {
	private static final long serialVersionUID = 3186997572550148840L;

	private MemberService getMemberServiceImplement() {
		return new MemberServiceImpl();
	}
	
	private PostingService getPostingServiceImplement() {
		return new PostingServiceImpl();
	}
	
	private BlogService getBlogServiceImplement() {
		return new BlogServiceImpl();
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if (action.equals("write")) {
				this.writePosting(request, response);
			} else if (action.equals("writeForm")) {
				this.writePostingForm(request, response);
			} else if (action.equals("remove")) {
				this.removePosting(request, response);
			} else if (action.equals("update")) {
				this.updatePosting(request, response);
			} else if (action.equals("updateForm")) {
				this.updatePostingForm(request, response);
			} else if (action.equals("likes")) {
				this.likeList(request, response);
			} else if (action.equals("addLike")) {
				this.addLike(request, response);
			} else if (action.equals("cancelLike")) {
				this.cancelLike(request, response);
			} else if (action.equals("reblogRequest")) {
				this.reblogRequest(request, response);
			} else if (action.equals("reblog")) {
				this.reblog(request, response);
			} else if (action.equals("replies")) {
				this.replyList(request, response);
			} else if (action.equals("writeReply")) {
				this.writeReply(request, response);
			} else if (action.equals("updateReply")) {
				this.updateReply(request, response);
			} else if (action.equals("removeReply")) {
				this.removeReply(request, response);
			} else if (action.equals("updateReplyForm")) {
				this.updateReplyForm(request, response);
			} else if (action.equals("read")) {
				this.readPosting(request, response);
			} else if (action.equals("list")) {
				this.listUpPosting(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		}
	}
	
	/*private void writePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("member");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
    	String fileType = request.getParameter("fileType");
    	if (fileType != null) {
	    	if (fileType.equals("profile")) {
	    		fileType = "profileImage";
	    	} else if (fileType.equals("header")) {
	    		fileType = "headerImage";
	    	} else {
	    		fileType = "";
	    	}
    	} else {
    		fileType = "";
    	}
    	
    	String[] fileContents = FileUploadUtil.fileUpload(request, response, member, fileType);
    	Enumeration<String> enumeration = request.getAttributeNames();
    	while (enumeration.hasMoreElements()) {
    		System.out.print("PostingController - 121 lines.");
    		System.out.println(enumeration.nextElement());
    	}
    	
		String blogName = (String) request.getAttribute("blogName");
		String title = (String) request.getAttribute("title");
		//String writer = null;
		int exposure = (Integer) request.getAttribute("exposure");
		String tags = (String) request.getAttribute("tags");
		int postingType = (Integer) request.getAttribute("postingType");
		int reblogOption = (Integer) request.getAttribute("reblogOption");
		String textContent = (String) request.getAttribute("textContent");
		String conType = (String) request.getAttribute("contentType");
		int contentType = 0;
		if (conType != null) {
			if (conType.equals("image")) {
				if (textContent != null && textContent.trim().length() != 0) {
					contentType = PostingContent.MIXED_IMAGE_FILE_CONTENT;
				}
				contentType = PostingContent.SINGLE_IMAGE_FILE_CONTENT;
			} else if (conType.equals("video")) {
				if (textContent != null && textContent.trim().length() != 0) {
					contentType = PostingContent.MIXED_VIDEO_FILE_CONTENT;
				}
				contentType = PostingContent.SINGLE_VIDEO_FILE_CONTENT;
			} else if (conType.equals("audio")) {
				if (textContent != null && textContent.trim().length() != 0) {
					contentType = PostingContent.MIXED_AUDIO_FILE_CONTENT;
				}
				contentType = PostingContent.SINGLE_AUDIO_FILE_CONTENT;
			}
		}
		
		PostingContent pContent = new PostingContent();
		if (contentType == PostingContent.TEXT_CONTENT) {
			pContent.setTextContent(textContent);
		} else if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			pContent.setFilePaths(fileContents);
			pContent.setTextContent(textContent);
		} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
			pContent.setFilePaths(fileContents);
		}
		
		Posting newPosting = new Posting(title, member.getName(), pContent, contentType, exposure, tags,  postingType, reblogOption);
		
		this.getPostingServiceImplement().writePosting(blogName, newPosting);
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}*/
	private void writePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
    	String uploadDir = getServletContext().getRealPath(KitschSystem.UPLOADED_FILES_ROOT_DIR);
		File dir = new File(uploadDir);
		if (!dir.exists()) { dir.mkdir(); }
    	
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String[] filePaths = null;
		ArrayList<String> fList = new ArrayList<String>();
		
		Collection<Part> parts = request.getParts();
		Map<String, String> params = new HashMap<String, String>();
		for (Part part : parts) {
			System.out.println("##################### PostingController - 201 #####################");
			// Part가 파일인지 여부는 content-type 헤더의 유무로 확인 가능
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
					if (contentType.startsWith("image")) {
						fullPath = uploadDir + "/images/" + fileName;
						part.write(fullPath);
						request.setAttribute("contentType", "image");
						params.put("contentType", contentType);
					} else if (contentType.startsWith("video")) {
						fullPath = uploadDir + "/videos/" + fileName;
						part.write(fullPath);
						request.setAttribute("contentType", "video");
						params.put("contentType", contentType);
					} else if (contentType.startsWith("audio")) {
						fullPath = uploadDir + "/audios/" + fileName;
						part.write(fullPath);
						request.setAttribute("contentType", "audio");
						params.put("contentType", contentType);
					}
					fList.add(fullPath);
				}
				for (String path : fList) {
					filePaths = KitschUtil.convertToStringArray(path, Posting.PATH_DELIMITER, false);
				}
				
			// Part가 파일 필드가 아닐 경우 content-type 헤더가 존재하지 않음			
			} else {
			    String partName = part.getName(); // 필드 이름
			    String fieldValue = request.getParameter(partName); // 필드 값
			    params.put(partName, fieldValue);					
			}
		}
		
		for (String key : params.keySet()) {
			System.out.print(key);
			System.out.print(" - ");
			System.out.println(params.get(key));
		}
		
		String blogName = params.get("blogName");
		int exposure = Integer.parseInt(params.get("exposure"));
		//int postingType = Integer.parseInt(params.get("postType"));
		int reblogOption = 0;
		String title = params.get("title");
		String writer = params.get("writer");
		String contents = params.get("contents");
		String tags = params.get("tags");
		int contentType = 0;
		String conType = params.get("contentType");
		
		if (conType != null) {
			if (conType.equals("image")) {
				if (contents != null && contents.trim().length() != 0) {
					contentType = PostingContent.MIXED_IMAGE_FILE_CONTENT;
				}
				contentType = PostingContent.SINGLE_IMAGE_FILE_CONTENT;
			} else if (conType.equals("video")) {
				if (contents != null && contents.trim().length() != 0) {
					contentType = PostingContent.MIXED_VIDEO_FILE_CONTENT;
				}
				contentType = PostingContent.SINGLE_VIDEO_FILE_CONTENT;
			} else if (conType.equals("audio")) {
				if (contents != null && contents.trim().length() != 0) {
					contentType = PostingContent.MIXED_AUDIO_FILE_CONTENT;
				}
				contentType = PostingContent.SINGLE_AUDIO_FILE_CONTENT;
			} else {
				contentType = PostingContent.TEXT_CONTENT;
			}
		} else {
			contentType = PostingContent.TEXT_CONTENT;
		}
		
		PostingContent pContent = new PostingContent();
		if (contentType == PostingContent.TEXT_CONTENT) {
			pContent.setTextContent(contents);
		} else if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			if (filePaths != null) {
				pContent.setFilePaths(filePaths);
			}
			pContent.setTextContent(contents);
		} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
			if (filePaths != null) {
				pContent.setFilePaths(filePaths);
			}
		}
		
		Posting newPosting = new Posting(title, writer, pContent, contentType, exposure, tags,  contentType, reblogOption);
		
		this.getPostingServiceImplement().writePosting(blogName, newPosting);
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void writePostingForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
    	Blog[] blogs = this.getBlogServiceImplement().getMemberBlogs(member);
		request.setAttribute("memberBlogs", blogs);
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/posting/postingForm_ckeditor.jsp");
		dispatcher.forward(request, response);
	}
	
	private void removePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
		
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		Blog blog = this.getBlogServiceImplement().findBlogByName(blogName);
		if (!blog.getEmail().equals(member.getEmail())) {
			errorMsgs.add("해당 블로그에 포스팅할 수 없습니다.");
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		this.getPostingServiceImplement().removePosting(blogName, postingNum);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void updatePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
		
		String blogName = request.getParameter("blogName");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		int contentType = Integer.parseInt(request.getParameter("contentType"));
		int exposure = Integer.parseInt(request.getParameter("exposure"));
		String tags = request.getParameter("tags");
		int reblogOption = Integer.parseInt(request.getParameter("reblogOption"));
		String textContent = request.getParameter("textContent");
		String fileContents = request.getParameter("fileContents");
		
		Member author = this.getMemberServiceImplement().findMemberByName(writer);
		ArrayList<String> errorMsgs = new ArrayList<String>();
		if (!(member.getEmail().equals(author.getEmail())) || !(member.getRole() == Member.ADMINISTRATOR )|| !(member.getRole() == Member.SUPER_USER)) {
			errorMsgs.add("해당 포스팅에 대한 권한이 없습니다.");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		PostingContent pContent = new PostingContent();
		if (contentType == PostingContent.TEXT_CONTENT) {
			pContent.setTextContent(textContent);
		} else if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
			pContent.setTextContent(textContent);
		} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
			pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
		}
		
		Posting posting = new Posting(title, member.getName(), pContent, contentType, exposure, tags, reblogOption);
		
		this.getPostingServiceImplement().updatePosting(blogName, posting);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void updatePostingForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Posting posting = this.getPostingServiceImplement().findPosting(blogName, postingNum);
		
		request.setAttribute("posting", posting);
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void likeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	
		Posting[] likes = this.getPostingServiceImplement().getLikedPostings(member);
		request.setAttribute("likes", likes);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/posting/likes.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
		
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Blog blog = this.getBlogServiceImplement().findBlogByName(blogName);
		PostingService postingService = this.getPostingServiceImplement();
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		if (member.getEmail().equals(blog.getEmail())) {
			errorMsgs.add("본인의 포스팅은 좋아요할 수 없습니다.");
		}
		if (postingService.isLiked(member, blogName, postingNum)) {
			errorMsgs.add("이미 좋아요한 포스팅입니다.");
		}
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		postingService.addLikes(member, blogName, postingNum);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("followingList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void cancelLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
		
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Blog blog = this.getBlogServiceImplement().findBlogByName(blogName);
		PostingService postingService = this.getPostingServiceImplement();
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		if (member.getEmail().equals(blog.getEmail())) {
			errorMsgs.add("본인의 포스팅은 좋아요 취소할 수 없습니다.");
		}
		if (!postingService.isLiked(member, blogName, postingNum)) {
			errorMsgs.add("좋아요 추가되지 않은 포스팅입니다.");
		}
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		postingService.addLikes(member, blogName, postingNum);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void reblog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
		
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		String targetBlog = request.getParameter("targetBlog");
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		PostingService postingService = this.getPostingServiceImplement();
		BlogService blogService = this.getBlogServiceImplement();
		
		Blog blog = blogService.findBlogByName(blogName);
		Blog target = blogService.findBlogByName(targetBlog);
		
		if (blog.getEmail().equals(member.getEmail())) {
			errorMsgs.add("본인의 포스팅은 리블로그할 수 없습니다.");
		}
		if (!target.getEmail().equals(member.getEmail())) {
			errorMsgs.add("블로그에 포스팅할 수 없습니다. [" + targetBlog + "]");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		postingService.reblog(member, blogName, postingNum, targetBlog);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void reblogRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
    	if (session == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
    	Member member = (Member) session.getAttribute("logonMember");
    	if (member == null) {
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
    		return;
    	}
		Blog[] memberBlogs = this.getBlogServiceImplement().getMemberBlogs(member);
		request.setAttribute("memberBlogs", memberBlogs);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/posting/reblog.jsp");
		dispatcher.forward(request, response);
	}
	
	private void replyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Posting[] replies = this.getPostingServiceImplement().getReplies(blogName, postingNum);
		request.setAttribute("replies", replies);
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void writeReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		String writer = null;
		int contentType = Integer.parseInt(request.getParameter("contentType"));
		int exposure = Integer.parseInt(request.getParameter("exposure"));
		int postingType = Integer.parseInt(request.getParameter("postingType"));
		String textContent = request.getParameter("textContent");
		String fileContents = request.getParameter("fileContents");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("logonMember");
			if (member != null) {
				PostingContent pContent = new PostingContent();
				if (contentType == PostingContent.TEXT_CONTENT) {
					pContent.setTextContent(textContent);
				} else if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
					pContent.setTextContent(textContent);
					pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
				} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
					pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
				}
				
				Posting posting = new Posting(member.getName(), pContent, contentType, exposure, postingType);
				
				this.getPostingServiceImplement().replyPosting(blogName, posting, postingNum);
				
				/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);*/
				return;
			}
		}
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void updateReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		String writer = null;
		int contentType = Integer.parseInt(request.getParameter("contentType"));
		int exposure = Integer.parseInt(request.getParameter("exposure"));
		int postingType = Integer.parseInt(request.getParameter("postingType"));
		String textContent = request.getParameter("textContent");
		String fileContents = request.getParameter("fileContents");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("logonMember");
			if (member != null) {
				PostingService postingService = this.getPostingServiceImplement();
				Posting posting = postingService.findPosting(blogName, postingNum);
				
				PostingContent pContent = new PostingContent();
				if (contentType == PostingContent.TEXT_CONTENT) {
					pContent.setTextContent(textContent);
				} else if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
					pContent.setTextContent(textContent);
					pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
				} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
					pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
				}
				
				posting.setContents(pContent);
				posting.setContentType(contentType);
				posting.setExposure(exposure);
				posting.setPostingType(postingType);
				
				postingService.updatePosting(blogName, posting);
				
				/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);*/
				return;
			}
		}
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void removeReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member author = (Member) session.getAttribute("logonMember");
			if (author != null) {
				PostingService postingService = this.getPostingServiceImplement();
				Posting reply = postingService.findPosting(blogName, postingNum);
				if (reply.getWriter().equals(author.getName())) {
					postingService.removePosting(blogName, postingNum);
					/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);*/
					return;
				}
			}
		}
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void updateReplyForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("logonMember");
			if (member != null) {
				Posting reply = this.getPostingServiceImplement().findPosting(blogName, postingNum);
				if (reply.getWriter().equals(member.getName())) {
					request.setAttribute("reply", reply);
					/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);*/
					return;
				}
			}
		}
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void readPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		PostingService postingService = this.getPostingServiceImplement();
		Posting posting = postingService.readPosting(blogName, postingNum);
		Posting[] replies = postingService.getReplies(blogName, postingNum);
		request.setAttribute("posting", posting);
		request.setAttribute("replies", replies);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/posting/posting.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listUpPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String blogName = request.getParameter("blogName");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("blogName", blogName);
		searchInfo.put("target", "posting");
		searchInfo.put("searchType", "writer");
		searchInfo.put("searchText", "");
		/*searchInfo.put("startRow", value);
		searchInfo.put("endRow", value);*/
		
	}
	/*
	*/
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
