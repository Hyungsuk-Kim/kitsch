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
import javax.xml.ws.RequestWrapper;

import microblog.kitsch.KitschSystem;
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
 * Servlet implementation class BlogController
 */
public class BlogController extends HttpServlet {
	private static final long serialVersionUID = -8034718212768388579L;

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
			if (action.equals("create")) {
				this.createBlog(request, response);
			} else if (action.equals("update")) {
				this.updateBlog(request, response);
			} else if (action.equals("updateForm")) {
				this.updateForm(request, response);
			} else if (action.equals("remove")) {
				this.removeBlog(request, response);
			} else if (action.equals("management")) {
				this.manageBlog(request, response);
			} else if (action.equals("follow")) {
				this.followList(request, response);
			} else if (action.equals("following")) {
				this.followBlog(request, response);
			} else if (action.equals("unfollowing")) {
				this.unfollowBlog(request, response);
			} else if (action.equals("visit")) {
				this.visitBlog(request, response);
			} else if (action.equals("main")) {
				this.mainblog(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		} catch (IllegalDataException ide) {
			throw new ServletException(ide);
		}
	}
	
	private void followList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
    	
    	BlogService blogService = this.getBlogServiceImplement();
    	Blog[] followingList= blogService.getFollowingList(member);
    	
    	request.setAttribute("followingList", followingList);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blog/followingList.jsp");
    	dispatcher.forward(request, response);
	}

	private void mainblog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataDuplicatedException, DataNotFoundException {
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
    	Posting[] postings = this.getPostingServiceImplement().getRelativePostings(member, KitschSystem.DEFAULT_START_ROW, KitschSystem.DEFAULT_END_ROW);
    	
    	request.setAttribute("blog", blogs[0]);
    	request.setAttribute("blogOwner", member);
    	request.setAttribute("postings", postings);
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blog/blogPage.jsp");
    	dispatcher.forward(request, response);
	}
	
	private void createBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataDuplicatedException, DataNotFoundException {
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
    	
		String blogName = request.getParameter("newBlogName");
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		if (blogName == null || blogName.trim().length() == 0) {
			errorMsgs.add("블로그 이름을 입력해 주세요.");
		} else if (blogName.trim().length() < 2 || blogName.trim().length() > 20) {
			errorMsgs.add("블로그 이름은 2 ~ 20 글자 사이의 값을 입력해 주세요.");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		BlogService blogService = this.getBlogServiceImplement();
		blogService.createBlog(member, blogName);
		Blog blog = blogService.findBlogByName(blogName);
		
		request.setAttribute("blogOwner", member);
		request.setAttribute("blog", blog);
		
		Blog[] memberBlogs = (Blog[]) session.getAttribute("memberBlogs");
		if (memberBlogs != null && memberBlogs.length != 0) {
			session.removeAttribute("memberBlogs");
			Blog[] additionBlogs = new Blog[memberBlogs.length+1];
			
			for (int i = 0; i < memberBlogs.length; i++) {
				additionBlogs[i] = memberBlogs[i];
			}
			additionBlogs[memberBlogs.length + 1] = blog;
		} else {
			memberBlogs = new Blog[] {blog};
			session.setAttribute("memberBlogs", memberBlogs);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blog/blogPage.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException, IllegalDataException {
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
		String newBlogName = request.getParameter("newBlogName");
		String headerImage = request.getParameter("headerImage");
		String backgroundColor = request.getParameter("backgroundColor");
		String layout = request.getParameter("layout");
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		BlogService blogService = this.getBlogServiceImplement();
		Blog blog = blogService.findBlogByName(blogName);
		
		if (blogName == null || blogName.trim().length() == 0) {
			errorMsgs.add("블로그 이름이 없습니다.");
		} else if (!blog.getEmail().equals(member.getEmail())) {
			errorMsgs.add("블로그에 대한 권한이 없습니다.");
		}
		
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (newBlogName.trim().length() != 0 || newBlogName != null) { blog.setBlogName(newBlogName); }
		if (headerImage.trim().length() != 0 || headerImage != null) { blog.setHeaderImage(headerImage); } 
		if (backgroundColor.trim().length() != 0 || backgroundColor != null) { blog.setBackgroundColor(Integer.parseInt(backgroundColor)); }
		if (layout.trim().length() != 0 || layout != null) { blog.setBlogLayout(Integer.parseInt(layout)); }
		
		blogService.updateBlog(member, blog);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard?action=main");
		//RequestDispatcher dispatcher = request.getRequestDispatcher("blog?action=visit&blogName=" + newBlogName);
		dispatcher.forward(request, response);
	}
	
	private void updateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		Blog blog = this.getBlogServiceImplement().findBlogByName(blogName);
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		if (blogName == null || blogName.trim().length() == 0) {
			errorMsgs.add("블로그 이름이 없습니다.");
		} else if (!blog.getEmail().equals(member.getEmail())) {
			errorMsgs.add("블로그에 대한 권한이 없습니다.");
		}
		
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		request.setAttribute("blog", blog);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blog/editBlog.jsp");
		dispatcher.forward(request, response);
	}
	
	private void removeBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException, IllegalDataException {
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
		BlogService blogService = this.getBlogServiceImplement();
		Blog blog = blogService.findBlogByName(blogName);
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		if (blogName == null || blogName.trim().length() == 0) {
			errorMsgs.add("블로그 이름이 없습니다.");
		} else if (!blog.getEmail().equals(member.getEmail())) {
			errorMsgs.add("블로그에 대한 권한이 없습니다.");
		}
		
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		blogService.removeBlog(member, blog);
		RequestDispatcher dispatcher = null;
		if (blogService.getMemberBlogs(member).length == 0) {
			dispatcher = request.getRequestDispatcher("dashboard?action=initialize");
		} else {
			dispatcher = request.getRequestDispatcher("dashboard?action=main");
		}
		dispatcher.forward(request, response);
	}
	
	private void manageBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session !=null) {
			Member member = (Member) session.getAttribute("logonMember");
			if (member != null) {
				
			}
		}
	}
	
	private void followBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException{
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
		
		BlogService blogService = this.getBlogServiceImplement();
		ArrayList<String> errorMsgs = new ArrayList<String>();
		if (blogName == null || blogName.trim().length() == 0) {
			errorMsgs.add("블로그 이름이 없습니다.");
		} else if (blogService.isFollowed(member, blogName)) {
			errorMsgs.add("이미 팔로우 하고있는 블로그입니다.");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		blogService.following(member, blogName);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("blog?action=visit&blogName=" + blogName);
		dispatcher.forward(request, response);
	}
	
	private void unfollowBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		BlogService blogService = this.getBlogServiceImplement();
		ArrayList<String> errorMsgs = new ArrayList<String>();
		if (blogName == null || blogName.trim().length() == 0) {
			errorMsgs.add("블로그 이름이 없습니다.");
		} else if (!blogService.isFollowed(member, blogName)) {
			errorMsgs.add("팔로우하지 않은 블로그입니다.");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		blogService.unfollow(member, blogName);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("blog?action=visit&blogName=" + blogName);
		dispatcher.forward(request, response);
	}
	
	private void visitBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String blogName = request.getParameter("blogName");
		
		BlogService blogService = this.getBlogServiceImplement();
		Blog blog = null;
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("logonMember");
			if (member != null) {
				blog = blogService.visitBlog(member, blogName);
			}
		} else {
			blog = blogService.visitBlog(blogName);
		}
		
		Member blogOwner = this.getMemberServiceImplement().findMemberByEmail(blog.getEmail());
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("startRow", KitschSystem.DEFAULT_START_ROW);
		searchInfo.put("endRow", KitschSystem.DEFAULT_END_ROW);
		searchInfo.put("target", "posting");
		searchInfo.put("searchType", "all");
		searchInfo.put("searchText", " ");
		searchInfo.put("blogName", blog.getBlogName());
		searchInfo.put("sortingOption", "recently");
		
		Posting[] postings = this.getPostingServiceImplement().getPostingList(searchInfo);
		
		request.setAttribute("blog", blog);
		request.setAttribute("blogOwner", blogOwner);
		request.setAttribute("postings", postings);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blog/blogPage.jsp");
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
