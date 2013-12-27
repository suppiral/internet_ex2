/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The LoginServlet is responsible of the login to the website
 * @author Moti and Gil Mizrahi
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends MyServlet {
    private final String LOGIN_HTML_FILEPATH = "htmls/login.htm";
    private final String LOGINERR_HTML_FILEPATH = "htmls/loginerror.htm";
    private String AUTH_USERNAME;
    private String AUTH_PASSWORD;

    /**
     * init function, configuring the members values from web.xml
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init() throws ServletException {
	AUTH_USERNAME =  getServletConfig().getInitParameter("username");
	AUTH_PASSWORD =  getServletConfig().getInitParameter("password");
    }
    
    /**
     * Handles the HTTP GET. <code>GET</code> method.<br/>
     * if the user is not logged in, show login page, else redirect to main page
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	
        response.setContentType("text/html;charset=UTF-8");
	
	
	// if the user is not logged in, show login page, else redirect to main page
        Boolean isLoggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
        if (isLoggedIn == null || isLoggedIn == false)
        {
	    
	    PrintWriter out = response.getWriter();
	    try {
		out.println(getFileContent(HEADER_HTML_FILEPATH));
		out.println(getFileContent(LOGIN_HTML_FILEPATH));
		out.println(getFileContent(FOOTER_HTML_FILEPATH));
		
	    } catch (FileNotFoundException e) { 
		System.err.println(e.toString()); // for debugging purposes
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	    }
	}
	else
	    response.sendRedirect("MainServlet");
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.<br/>
     * if the username and password are correct, redirect to mainservlet.
     * otherwise, display the same page with an error message.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();
        try {
	    String uName = request.getParameter("username");
	    String pass = request.getParameter("password");
	    if (AUTH_USERNAME.equals(uName) && AUTH_PASSWORD.equals(pass)) 
	    {
		request.getSession().setAttribute("loggedIn", true);
		response.sendRedirect("MainServlet");
	    }
		out.println(getFileContent(HEADER_HTML_FILEPATH));
		out.println(getFileContent(LOGINERR_HTML_FILEPATH));
		out.println(getFileContent(LOGIN_HTML_FILEPATH));
		out.println(getFileContent(FOOTER_HTML_FILEPATH));

	 } catch (IOException e) { 
		System.err.println(e.toString()); 
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	 }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "The LoginServlet is responsible of the login to the website";
    }// </editor-fold>
}
