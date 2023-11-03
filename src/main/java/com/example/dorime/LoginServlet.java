package com.example.dorime;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/dorime")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			userDbUtil = new UserDbUtil(dataSource);
		} catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// sending to login.jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// fetching request parameters
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			// getting user with email and password
			User user = userDbUtil.getUser(email, password);
			
			if(user!=null) {
				
				// saving user attibriutes to session
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getUserId());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("email", user.getEmail());
				session.setAttribute("userType", user.getUserType());

				// redirecting to homepage
				response.sendRedirect("homepage");
				
				
			}else {
				// redirecting to login
				response.sendRedirect("login");
				
			}
			
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		

		
	}

}
