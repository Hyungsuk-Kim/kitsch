package microblog.kitsch.web.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

/**
 * Servlet implementation class ManagementController
 */
public class ManagementController extends HttpServlet {
	private static final long serialVersionUID = -995326905896280594L;

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			if (action.equals("privilege")) {
				this.changeMemberRole(request, response);
			} else if (action.equals(arg0)) {
				
			} 
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		} catch (DataDuplicatedException dde) {
			throw new ServletException(dde);
		} catch (IllegalDataException ide) {
			throw new ServletException(ide);
		}
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
