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
			} else if (action.equals("remove")) {
				this.removePosting(request, response);
			} else if (action.equals("follow")) {
				this.following(request, response);
			} else if (action.equals("unfollow")) {
				this.unfollowing(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		} catch (IllegalDataException ide) {
			throw new ServletException(ide);
		}
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
	
	private void writeTextPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void writeVideoPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void writeAudioPosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void writeImagePosting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void visitBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
