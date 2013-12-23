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
 *
 * @author gilmi
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends MyServlet {

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
                Boolean isLoggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
        {
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    try {
		if (isLoggedIn == null || isLoggedIn == false)
		    response.sendRedirect("LoginServlet");
		else {
		    out.println(getFileContent(HEADER_HTML_FILEPATH));
		    out.println(getFileContent(HEADER_LOGOUT_FILEPATH));
		    out.println("nothing");
		    /* todo: add include to other servlets */
		    out.println(getFileContent(FOOTER_HTML_FILEPATH));
		}
	    } catch (FileNotFoundException e) { 
		System.err.println(e.toString()); 
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	    }
	    finally { try  { out.close(); } catch (Exception e) {} }
	}
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
