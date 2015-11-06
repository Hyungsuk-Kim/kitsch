package microblog.kitsch.web.controller;

import java.io.IOException;
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
import microblog.kitsch.business.service.PostingService;
import microblog.kitsch.business.service.PostingServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;
import microblog.kitsch.test.DomainObjectsForTest;

/**
 * Servlet implementation class ExploreController
 */
public class ExploreController extends HttpServlet {
	private static final long serialVersionUID = -8361339682239591825L;

	private PostingService getPostingServiceImplementation() {
		return new PostingServiceImpl();
	}
	
	private BlogService getBlogServiceImplementation() {
		return new BlogServiceImpl();
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if (action.equals("trend")) {
				this.trendPostings(request, response);
			} else if (action.equals("images")) {
				this.imagePostings(request, response);
			} else if (action.equals("videos")) {
				this.videoPostings(request, response);
			} else if (action.equals("audios")) {
				this.audioPostings(request, response);
			} else if (action.equals("text")) {
				this.textPostings(request, response);
			} else if (action.equals("like")) {
				this.like(request, response);
			} else if (action.equals("cancelLike")) {
				this.cancelLike(request, response);
			} else if (action.equals("follow")) {
				this.following(request, response);
			} else if (action.equals("unfollow")) {
				this.unfollowing(request, response);
			} else if (action.equals("visit")) {
				this.visitBlog(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		}
	}
	
	private void visitBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		
		BlogService blogService = this.getBlogServiceImplementation();
		Blog blog = null;
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				blog = blogService.visitBlog(member, blogName);
			}
		} else {
			blog = blogService.visitBlog(blogName);
		}
		request.setAttribute("blog", blog);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/blog.jsp");
		dispatcher.forward(request, response);
	}
	
	private void like(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
    	this.getPostingServiceImplementation().addLikes(member, blogName, postingNum);
    	/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
    	dispatcher.forward(request, response);*/
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
		
		this.getPostingServiceImplementation().cancelLikes(member, blogName, postingNum);
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void following(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		this.getBlogServiceImplementation().following(member, blogName);
		
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void unfollowing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		this.getBlogServiceImplementation().unfollow(member, blogName);
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);*/
	}
	
	private void trendPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "posting");
		searchInfo.put("sortingOption", "popularity");
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		
		Posting[] postings = this.getPostingServiceImplementation().getPostingList(searchInfo);
		request.setAttribute("postings", DomainObjectsForTest.postings);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void imagePostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "posting");
		searchInfo.put("sortingOption", "popularity");
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		searchInfo.put("contentType", PostingContent.COMMON_IMAGE_CONTENT);
		
		Posting[] postings = this.getPostingServiceImplementation().getPostingList(searchInfo);
		request.setAttribute("postings", DomainObjectsForTest.images);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void videoPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "posting");
		searchInfo.put("sortingOption", "popularity");
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		searchInfo.put("contentType", PostingContent.COMMON_VIDEO_CONTENT);
		
		Posting[] postings = this.getPostingServiceImplementation().getPostingList(searchInfo);
		request.setAttribute("postings", DomainObjectsForTest.videos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void audioPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "posting");
		searchInfo.put("sortingOption", "popularity");
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		searchInfo.put("contentType", PostingContent.COMMON_AUDIO_CONTENT);
		
		Posting[] postings = this.getPostingServiceImplementation().getPostingList(searchInfo);
		request.setAttribute("postings", DomainObjectsForTest.audios);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void textPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "posting");
		searchInfo.put("sortingOption", "popularity");
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		searchInfo.put("contentType", PostingContent.TEXT_CONTENT);
		
		Posting[] postings = this.getPostingServiceImplementation().getPostingList(searchInfo);
		request.setAttribute("postings", DomainObjectsForTest.texts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
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
