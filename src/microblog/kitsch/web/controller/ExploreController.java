package microblog.kitsch.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.service.PostingService;
import microblog.kitsch.business.service.PostingServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

/**
 * Servlet implementation class ExploreController
 */
public class ExploreController extends HttpServlet {
	private static final long serialVersionUID = -8361339682239591825L;

	private PostingService getPostingServiceImplement() {
		return new PostingServiceImpl();
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
			}
		} catch (DataNotFoundException dne) {
			throw new ServletException(dne);
		}
	}
	
	private void trendPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataNotFoundException {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put(key, value);
		
		Posting[] postings = this.getPostingServiceImplement().getPostingList(searchInfo);
		request.setAttribute("postings", postings);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void imagePostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put(key, value);
		
		Posting[] postings = this.getPostingServiceImplement().getPostingList(searchInfo);
		request.setAttribute("postings", postings);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void videoPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put(key, value);
		
		Posting[] postings = this.getPostingServiceImplement().getPostingList(searchInfo);
		request.setAttribute("postings", postings);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void audioPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put(key, value);
		
		Posting[] postings = this.getPostingServiceImplement().getPostingList(searchInfo);
		request.setAttribute("postings", postings);
		RequestDispatcher dispatcher = request.getRequestDispatcher(arg0);
		dispatcher.forward(request, response);
	}
	
	private void textPostings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put(key, value);
		
		Posting[] postings = this.getPostingServiceImplement().getPostingList(searchInfo);
		request.setAttribute("postings", postings);
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
