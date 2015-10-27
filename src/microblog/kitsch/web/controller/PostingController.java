package microblog.kitsch.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	private void writePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
		String blogName = request.getParameter("blogName");
		String title = request.getParameter("title");
		//String writer = null;
		int contentType = Integer.parseInt(request.getParameter("contentType"));
		int exposure = Integer.parseInt(request.getParameter("exposure"));
		String tags = request.getParameter("tags");
		int postingType = Integer.parseInt(request.getParameter("postingType"));
		int reblogOption = Integer.parseInt(request.getParameter("reblogOption"));
		String textContent = request.getParameter("textContent");
		String fileContents = request.getParameter("fileContents");
		
		PostingContent pContent = new PostingContent();
		if (contentType == PostingContent.TEXT_CONTENT) {
			pContent.setTextContent(textContent);
		} else if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
			pContent.setTextContent(textContent);
		} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
			pContent.setFilePaths(KitschUtil.convertToStringArray(fileContents, Posting.PATH_DELIMITER, false));
		}
		
		Posting newPosting = new Posting(title, member.getName(), pContent, contentType, exposure, tags,  postingType, reblogOption);
		
		this.getPostingServiceImplement().writePosting(blogName, newPosting);
				
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void writePostingForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
	}
	
	private void removePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
	
	private void updatePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String blogName = request.getParameter("blogName");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		int contentType = Integer.parseInt(request.getParameter("contentType"));
		int exposure = Integer.parseInt(request.getParameter("exposure"));
		String tags = request.getParameter("tags");
		int reblogOption = Integer.parseInt(request.getParameter("reblogOption"));
		String textContent = request.getParameter("textContent");
		String fileContents = request.getParameter("fileContents");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				Member author = this.getMemberServiceImplement().findMemberByName(writer);
				if (member.getEmail().equals(author.getEmail())) {
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
					
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void updatePostingForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Posting posting = this.getPostingServiceImplement().findPosting(blogName, postingNum);
		
		request.setAttribute("posting", posting);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void likeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
		Posting[] likes = this.getPostingServiceImplement().getLikedPostings(member);
		request.setAttribute("likes", likes);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void addLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(".list");
		dispatcher.forward(request, response);
	}
	
	private void cancelLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	Member member = (Member) session.getAttribute("member");
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
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				Blog[] memberBlogs = this.getBlogServiceImplement().getMemberBlogs(member);
				request.setAttribute("memberBlogs", memberBlogs);
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void replyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Posting[] replies = this.getPostingServiceImplement().getReplies(blogName, postingNum);
		request.setAttribute("replies", replies);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
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
			Member member = (Member) session.getAttribute("member");
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
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void updateReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			Member member = (Member) session.getAttribute("member");
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
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void removeReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member author = (Member) session.getAttribute("member");
			if (author != null) {
				PostingService postingService = this.getPostingServiceImplement();
				Posting reply = postingService.findPosting(blogName, postingNum);
				if (reply.getWriter().equals(author.getName())) {
					postingService.removePosting(blogName, postingNum);
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void updateReplyForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				Posting reply = this.getPostingServiceImplement().findPosting(blogName, postingNum);
				if (reply.getWriter().equals(member.getName())) {
					request.setAttribute("reply", reply);
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void readPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		int postingNum = Integer.parseInt(request.getParameter("postingNum"));
		
		Posting posting = this.getPostingServiceImplement().readPosting(blogName, postingNum);
		request.setAttribute("posting", posting);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void listUpPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String blogName = request.getParameter("blogName");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("blogName", blogName);
		searchInfo.put("target", "posting");
		searchInfo.put("searchType", "writer");
		searchInfo.put("searchText", "");
		searchInfo.put("startRow", value);
		searchInfo.put("endRow", value);
		
	}
	
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
