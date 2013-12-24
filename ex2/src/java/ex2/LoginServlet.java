/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex2;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gilmi
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends MyServlet {
    private final String LOGIN_HTML_FILEPATH = "htmls/login.htm";
    private final String LOGINERR_HTML_FILEPATH = "htmls/loginerror.htm";
    private final String AUTH_USERNAME = "admin";
    private final String AUTH_PASSWORD = "password";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
        Boolean isLoggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
        if (isLoggedIn == null || isLoggedIn == false)
        {
	    
	    PrintWriter out = response.getWriter();
	    try {
		out.println(getFileContent(HEADER_HTML_FILEPATH));
		out.println(getFileContent(LOGIN_HTML_FILEPATH));
		out.println(getFileContent(FOOTER_HTML_FILEPATH));
		
	    } catch (Exception e) { 
		System.err.println(e.toString()); 
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	    }
	}
	else
	    response.sendRedirect("MainServlet");
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
        return "Short description";
    }// </editor-fold>
}
