package microblog.kitsch.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.business.service.MemberServiceImpl;
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
			} else if (action.equals("following")) {
				this.followBlog(request, response);
			} else if (action.equals("unfollowing")) {
				this.unfollowBlog(request, response);
			} else if (action.equals("visit")) {
				this.visitBlog(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		} catch (IllegalDataException ide) {
			throw new ServletException(ide);
		}
	}
	
	private void createBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataDuplicatedException, DataNotFoundException {
		String blogName = request.getParameter("newBlogName");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				if (blogName != null) {
					this.getBlogServiceImplement().createBlog(member, blogName);
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void updateBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException, IllegalDataException {
		String blogName = request.getParameter("blogName");
		String newBlogName = request.getParameter("newBlogName");
		String headerImage = request.getParameter("headerImage");
		String backgroundColor = request.getParameter("backgroundColor");
		String layout = request.getParameter("layout");
		
		BlogService blogService = this.getBlogServiceImplement();
		Blog blog = blogService.findBlogByName(blogName);
		
		if (newBlogName.trim().length() != 0 || newBlogName != null) { blog.setBlogName(newBlogName); }
		if (headerImage.trim().length() != 0 || headerImage != null) { blog.setHeaderImage(headerImage); } 
		if (backgroundColor.trim().length() != 0 || backgroundColor != null) { blog.setBackgroundColor(Integer.parseInt(backgroundColor)); }
		if (layout.trim().length() != 0 || layout != null) { blog.setBlogLayout(Integer.parseInt(layout)); }
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member owner = (Member) session.getAttribute("member");
			if (owner != null) {
				if (blog.getEmail().equals(owner.getEmail())) {
					blogService.updateBlog(owner, blog);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void updateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String blogName = request.getParameter("blogName");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				Blog blog = this.getBlogServiceImplement().findBlogByName(blogName);
				if (blog.getEmail().equals(member.getEmail())) {
					request.setAttribute("blog", blog);
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void removeBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException, IllegalDataException {
		String blogName = request.getParameter("blogName");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				BlogService blogService = this.getBlogServiceImplement();
				Blog blog = blogService.findBlogByName(blogName);
				if (blog.getEmail().equals(member.getEmail())) {
					blogService.removeBlog(member, blog);
					RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void manageBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void followBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private void unfollowBlog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
