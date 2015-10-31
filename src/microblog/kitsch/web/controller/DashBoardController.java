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
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.business.service.PostingService;
import microblog.kitsch.business.service.PostingServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

/**
 * Servlet implementation class DashBoardController
 */
public class DashBoardController extends HttpServlet {
	private static final long serialVersionUID = 609940431980464421L;
	
	private BlogService getBlogServiceImplementation() {
		return new BlogServiceImpl();
	}
		
	private PostingService getPostingServiceImplementation() {
		return new PostingServiceImpl();
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if (action.equals("initialize")) {
				this.initSetting(request, response);
			} else if (action.equals("main")) {
				this.dashboard(request, response);
			} else if (action.equals("text")) {
				this.writeTextPosting(request, response);
			} else if (action.equals("video")) {
				this.writeVideoPosting(request, response);
			} else if (action.equals("audio")) {
				this.writeAudioPosting(request, response);
			} else if (action.equals("image")) {
				this.writeImagePosting(request, response);
			} else if (action.equals("blog")) {
				this.visitBlog(request, response);
			} else if (action.equals("edit")) {
				
			} else if (action.equals("like")) {
				this.like(request, response);
			} else if (action.equals("cancelLike")) {
				this.cancelLike(request, response);
			} else if (action.equals("remove")) {
				this.removePosting(request, response);
			} else if (action.equals("follow")) {
				this.following(request, response);
			} else if (action.equals("unfollow")) {
				this.unfollowing(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		}
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
    	RequestDispatcher dispatcher = request.getRequestDispatcher(".main");
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
		
		this.getPostingServiceImplementation().cancelLikes(member, blogName, postingNum);
		RequestDispatcher dispatcher = request.getRequestDispatcher(".main");
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
		
		this.getPostingServiceImplementation().removePosting(blogName, postingNum);
		RequestDispatcher dispatcher = request.getRequestDispatcher(".main");
		dispatcher.forward(request, response);
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(".main");
		dispatcher.forward(request, response);
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
		RequestDispatcher dispatcher = request.getRequestDispatcher(".main");
		dispatcher.forward(request, response);
	}

	private void initSetting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    	
    	request.setAttribute("member", member);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/createBlog.jsp");
    	dispatcher.forward(request, response);
	}

	
	private void dashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    	
    	int startRow = Integer.parseInt(request.getParameter("startRow"));
    	int endRow = Integer.parseInt(request.getParameter("endRow"));
    	
    	PostingService postingService = this. getPostingServiceImplementation();
    	Posting[] postings= postingService.getRelativePostings(member, startRow, endRow);
    	
    	request.setAttribute("postings", postings);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/main.jsp");
    	dispatcher.forward(request, response);
	}
	
	private void writeTextPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
    	Blog blog = this.getBlogServiceImplementation().findBlogByName(blogName);
    	
    	ArrayList<String> errorMsgs = new ArrayList<String>();
    	if (!blog.getEmail().equals(member.getEmail())) {
    		errorMsgs.add("해당 블로그에 대한 권한이 없습니다. [" + blogName + "]");
    	}
    	if (!errorMsgs.isEmpty()) {
    		request.setAttribute("errorMsgs", errorMsgs);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/writeTextPostingForm.jsp");
    	dispatcher.forward(request, response);
	}
	
	private void writeVideoPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
    	Blog blog = this.getBlogServiceImplementation().findBlogByName(blogName);
    	
    	ArrayList<String> errorMsgs = new ArrayList<String>();
    	if (!blog.getEmail().equals(member.getEmail())) {
    		errorMsgs.add("해당 블로그에 대한 권한이 없습니다. [" + blogName + "]");
    	}
    	if (!errorMsgs.isEmpty()) {
    		request.setAttribute("errorMsgs", errorMsgs);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/writeVideoPostingForm.jsp");
    	dispatcher.forward(request, response);
	}
	
	private void writeAudioPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
    	Blog blog = this.getBlogServiceImplementation().findBlogByName(blogName);
    	
    	ArrayList<String> errorMsgs = new ArrayList<String>();
    	if (!blog.getEmail().equals(member.getEmail())) {
    		errorMsgs.add("해당 블로그에 대한 권한이 없습니다. [" + blogName + "]");
    	}
    	if (!errorMsgs.isEmpty()) {
    		request.setAttribute("errorMsgs", errorMsgs);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/writeAudioPostingForm.jsp");
    	dispatcher.forward(request, response);
	}
	
	private void writeImagePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
    	Blog blog = this.getBlogServiceImplementation().findBlogByName(blogName);
    	
    	ArrayList<String> errorMsgs = new ArrayList<String>();
    	if (!blog.getEmail().equals(member.getEmail())) {
    		errorMsgs.add("해당 블로그에 대한 권한이 없습니다. [" + blogName + "]");
    	}
    	if (!errorMsgs.isEmpty()) {
    		request.setAttribute("errorMsgs", errorMsgs);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
    		dispatcher.forward(request, response);
    		return;
    	}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/writeImagePostingForm.jsp");
    	dispatcher.forward(request, response);
	}
	
	private void visitBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		
		BlogService blogService = this.getBlogServiceImplementation();
		Blog blog = null;
		
		HttpSession session =  request.getSession(false);
		if (session == null) {
			blog = blogService.visitBlog(blogName);
			request.setAttribute("blog", blog);
		} else {
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				blogService.visitBlog(blogName);
				request.setAttribute("blog", blog);
			} else {
				blogService.visitBlog(member, blogName);
				request.setAttribute("blog", blog);
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard/blog.jsp");
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
