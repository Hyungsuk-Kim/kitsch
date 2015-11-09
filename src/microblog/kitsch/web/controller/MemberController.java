package microblog.kitsch.web.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import microblog.kitsch.KitschSystem;
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
 * Servlet implementation class MemberController
 */
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 6656719421084379820L;
	
	private MemberService getMemberServiceImplement() {
		return new MemberServiceImpl();
	}
	
	private BlogService getBlogServiceImplement() {
		return new BlogServiceImpl();
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if (action.equals("register")) {
				this.signUp(request, response);
			} else if (action.equals("signIn")) {
				this.signIn(request, response);
			} else if (action.equals("signOut")) {
				this.signOut(request, response);
			} else if (action.equals("remove")) {
				this.removeMember(request, response);
			} else if (action.equals("profile")) {
				this.memberProfile(request, response);
			} else if (action.equals("update")) {
				this.updateMember(request, response);
			} else if (action.equals("editProfile")) {
				this.editProfile(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		}
	}
	
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataDuplicatedException, DataNotFoundException {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		if (email == null || email.trim().length() == 0) {
			errorMsgs.add("이메일을 입력해 주세요.");
		} else if (name == null || name.trim().length() == 0) {
			errorMsgs.add("이름을 입력해 주세요.");
		} else if (name.trim().length() > 12 || name.trim().length() < 2) {
			errorMsgs.add("이름은 2 ~ 12 글자 사이의 값으로 입력해 주세요.");
		} else if (password == null || password.trim().length() == 0) {
			errorMsgs.add("패스워드를 입력해 주세요.");
		} else if (password.trim().length() > 16 || password.trim().length() < 8) {
			errorMsgs.add("패스워드는 8 ~ 16 글자 사이의 값으로 입력해 주세요.");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		Member member = new Member(email, name, password);
		
		MemberService memberService = this.getMemberServiceImplement();
		memberService.registerMember(member);
		Member signedMember = memberService.findMemberByEmail(member.getEmail());
		
		request.getSession(true).setAttribute("logonMember", signedMember);
		RequestDispatcher dispatcher = request.getRequestDispatcher("explore?action=trend&startRow=" + KitschSystem.DEFAULT_START_ROW + "&endRow="+KitschSystem.DEFAULT_END_ROW);
		dispatcher.forward(request, response);
	}
	
	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		
		Member member = this.getMemberServiceImplement().loginCheck(email, password);
		int check = member.getCheck();
		if (check == Member.VALID_MEMBER) {
			request.getSession(true).setAttribute("logonMember", member);
			
			Blog[] memberBlogs = this.getBlogServiceImplement().getMemberBlogs(member);
			if (memberBlogs.length == 0) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/blog/createblog.jsp");
				dispatcher.forward(request, response);
				return;
			} else {
				request.getSession(false).setAttribute("memberBlogs", memberBlogs);
				RequestDispatcher dispatcher = request.getRequestDispatcher("blog?action=main");
				dispatcher.forward(request, response);
				return;
			}
		} else if (check == Member.INVALID_EMAIL) {
			errorMsgs.add("존재하지 않는 이메일입니다.");
		} else if (check == Member.INVALID_PASSWORD) {
			errorMsgs.add("패스워드가 일치하지 않습니다.");
		}
		if (!errorMsgs.isEmpty()) {
			request.setAttribute("errorMsgs", errorMsgs);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("userError.jsp");
		dispatcher.forward(request, response);
	}
	
	private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.removeAttribute("member");
			session.invalidate();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void removeMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
    	this.getMemberServiceImplement().removeMember(member);
    	session.removeAttribute("member");
    	session.invalidate();
    	
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String profileImage = request.getParameter("profileImage");
		
		MemberService memberService = this.getMemberServiceImplement();
		member = memberService.findMemberByEmail(member.getEmail());
		if (name != null) { member.setName(name); }
		if (password != null) { member.setPassword(password); }
		if (profileImage != null) { member.setProfileImage(profileImage); }
		memberService.updateMember(member);
		session.removeAttribute("member");
		session.setAttribute("member", member);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/profile.jsp");
		dispatcher.forward(request, response);
	}
	
	private void memberProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/profile.jsp");
		dispatcher.forward(request, response);
	}
	
	private void editProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
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
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/editProfile.jsp");
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
