package microblog.kitsch.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.business.service.MemberServiceImpl;
import microblog.kitsch.business.service.PostingService;
import microblog.kitsch.business.service.PostingServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

/**
 * Servlet implementation class SearchController
 */
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = -575139487857646921L;
	
	private PostingService getPostingServiceImplementaion() {
		return new PostingServiceImpl();
	}
	
	private BlogService getBlogServiceImplementaion() {
		return new BlogServiceImpl();
	}
	
	private MemberService getMemberServiceImplementaion() {
		return new MemberServiceImpl();
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if (action.equals("searchAll")) {
				this.searchAll(request, response);
			} else if (action.equals("searchMembers")) {
				this.searchMembers(request, response);
			} else if (action.equals("searchBlogs")) {
				this.searchBlogs(request, response);
			} else if (action.equals("searchPostings")) {
				this.searchPostings(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		}
	}
	
	private void searchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		//String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		//String sortingOption = request.getParameter("sortingOption");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "all");
		searchInfo.put("searchType", "all");
		//searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		/*if (sortingOption != null && sortingOption.trim().length() != 0) {
			searchInfo.put("sortingOption", sortingOption);
		}*/
		
		Member[] members = this.getMemberServiceImplementaion().getMemberList(searchInfo);
		Blog[] blogs = this.getBlogServiceImplementaion().getBlogList(searchInfo);
		Posting[] postings = this.getPostingServiceImplementaion().getPostingList(searchInfo);
		
		request.setAttribute("members", members);
		request.setAttribute("blogs", blogs);
		request.setAttribute("postings", postings);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void searchMembers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		//String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		//String sortingOption = request.getParameter("sortingOption");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "member");
		searchInfo.put("searchType", "all");
		//searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		/*if (sortingOption != null && sortingOption.trim().length() != 0) {
			searchInfo.put("sortingOption", sortingOption);
		}*/
		
		Member[] members = this.getMemberServiceImplementaion().getMemberList(searchInfo);
		
		request.setAttribute("members", members);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void searchBlogs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		//String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		//String sortingOption = request.getParameter("sortingOption");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "blog");
		searchInfo.put("searchType", "all");
		//searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		/*if (sortingOption != null && sortingOption.trim().length() != 0) {
			searchInfo.put("sortingOption", sortingOption);
		}*/
		
		Blog[] blogs = this.getBlogServiceImplementaion().getBlogList(searchInfo);
		
		request.setAttribute("blogs", blogs);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/explore/explore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void searchPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		//String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));
		//String sortingOption = request.getParameter("sortingOption");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "posting");
		searchInfo.put("searchType", "all");
		//searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		/*if (sortingOption != null && sortingOption.trim().length() != 0) {
			searchInfo.put("sortingOption", sortingOption);
		}*/
		
		Posting[] postings = this.getPostingServiceImplementaion().getPostingList(searchInfo);
		
		request.setAttribute("postings", postings);
		
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
