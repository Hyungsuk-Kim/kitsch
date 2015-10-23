package microblog.kitsch.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			if (action.equals("signUp")) {
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
			} else if (action.equals("management")) {
				this.managementMember(request, response);
			} else if (action.equals("privilege")) {
				this.changeMemberRole(request, response);
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		} catch (IllegalDataException ide) {
			throw new ServletException(ide);
		}
	}
	
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataDuplicatedException {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		Member member = new Member(email, name, password);
		
		MemberService memberService = this.getMemberServiceImplement();
		memberService.registerMember(member);
		Member signedMember = memberService.findMemberByEmail(member.getEmail());
		
		request.getSession(true).setAttribute("member", signedMember);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Member member = this.getMemberServiceImplement().loginCheck(email, password);
		int check = member.getCheck();
		if (check == Member.VALID_MEMBER) {
			request.getSession(true).setAttribute("member", member);
			
			Blog[] memberBlogs = this.getBlogServiceImplement().getMemberBlogs(member);
			if (memberBlogs.length == 0) {
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			} else {
				request.getSession(false).setAttribute("memberBlogs", memberBlogs);
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		} else if (check == Member.INVALID_EMAIL) {
			request.setAttribute("signInErrorMsg", "등록되지 않은 이메일입니다.");
		} else if (check == Member.INVALID_PASSWORD) {
			request.setAttribute("signInErrorMsg", "비밀번호가 일치하지 않습니다.");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.removeAttribute("member");
			session.invalidate();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void removeMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
		
		Member member = null;
		if (session != null) {
			member = (Member) session.getAttribute("member");
			if (member != null) {
				this.getMemberServiceImplement().removeMember(member);
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String profileImage = request.getParameter("profileImage");
		
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				MemberService memberService = this.getMemberServiceImplement();
				member = memberService.findMemberByEmail(member.getEmail());
				if (name != null) { member.setName(name); }
				if (password != null) { member.setPassword(password); }
				if (profileImage != null) { member.setProfileImage(profileImage); }
				memberService.updateMember(member);
				session.removeAttribute("member");
				session.setAttribute("member", member);
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void changeMemberRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException, IllegalDataException {
		HttpSession session = request.getSession(false);
		
		String targetMemberName = request.getParameter("targetMemberName");
		String role = request.getParameter("privilege");
		
		if (session != null) {
			Member admin = (Member) session.getAttribute("member");
			if (admin.getRole() == Member.ADMINISTRATOR) {
				this.getMemberServiceImplement().giveRole(admin, targetMemberName, Integer.parseInt(role));
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void memberProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			Member member = (Member) session.getAttribute("member");
			if (member != null) {
				member = this.getMemberServiceImplement().findMemberByEmail(member.getEmail());
				
				request.setAttribute("member", member);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void managementMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String[] privileges = request.getParameterValues("privilege");
		int[] roles = new int[privileges.length];
		
		if (privileges == null) { return; }
		
		for (int i = 0; i < privileges.length; i++) {
			if (privileges[i].equals("administrator")) { roles[i] = Member.ADMINISTRATOR; }
			else if (privileges[i].equals("superuser")) { roles[i] = Member.SUPER_USER; }
			else if (privileges[i].equals("normaluser")) { roles[i] = Member.NORMAL_USER; }
		}
		
		if (session != null) {
			Member admin = (Member) session.getAttribute("member");
			if (admin.getRole() == Member.ADMINISTRATOR) {
				MemberService memberService = this.getMemberServiceImplement();
				Member[] administrators = null;
				Member[] superUsers = null;
				Member[] normalUsers = null;
				for (int role : roles) {
					if (role == Member.ADMINISTRATOR) { 
						administrators = memberService.getMembersAsRole(role);
						request.setAttribute("administrators", administrators);
					}
					else if (role == Member.SUPER_USER) { 
						superUsers = memberService.getMembersAsRole(role);
						request.setAttribute("superUsers", superUsers);
					}
					else if (role == Member.NORMAL_USER) { 
						normalUsers = memberService.getMembersAsRole(role);
						request.setAttribute("normalUsers", normalUsers);
					}
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
				dispatcher.forward(request, response);
				return;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
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
