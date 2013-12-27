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
 * The MainServlet displays the main page and acts as a dispatcher for search and
 * add servlets
 * @author Moti and Gil Mizrahi
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends MyServlet {
    protected final String ADDFORM_HTML_FILEPATH = "htmls/addForm.htm";
    protected final String SEARCHFORM_HTML_FILEPATH = "htmls/searchForm.htm";
    
    protected void printMain(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
	try {
	    PrintWriter out = response.getWriter();
	    response.setContentType("text/html;charset=UTF-8");

	    out.println(getFileContent(HEADER_HTML_FILEPATH));
	    out.println(getFileContent(LOGOUT_HTML_FILEPATH));
            
            String addFormContent = getFileContent(ADDFORM_HTML_FILEPATH);
            
            String idExists = null;
            try { idExists = (String)request.getAttribute("idExist"); }
            catch (Exception e) { }
            if (idExists != null && idExists.equals("true")) 
            {
                // print error
                out.println("<p style=\"color: red;\">error while adding product to database: id already exists</p>");
                // add request paramters to the form
                addFormContent = addFormContent.replace("name=\"addName\"", "name=\"addName\" value=\"" + request.getParameter("addName") +"\"");
                addFormContent = addFormContent.replace("name=\"addID\"", "name=\"addID\" value=\"" + request.getParameter("addID") +"\"");
                addFormContent = addFormContent.replace("name=\"addPrice\"", "name=\"addPrice\" value=\"" + request.getParameter("addPrice") +"\"");
                addFormContent = addFormContent.replace("</textarea>", request.getParameter("addDescription") +"</textarea>");
                addFormContent = addFormContent.replace("name=\"addQuantity\"", "name=\"addQuantity\" value=\"" + request.getParameter("addQuantity") +"\"");
            }
	    out.println(getFileContent(SEARCHFORM_HTML_FILEPATH));
	    out.println(addFormContent);
	    out.println(getFileContent(FOOTER_HTML_FILEPATH));
	} catch (FileNotFoundException e) { 
	    System.err.println(e.toString()); 
	    response.sendError(HttpServletResponse.SC_NO_CONTENT);
	}
    }
    
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
                
	{  
	    redirectIfNotLoggedIn(request, response);
	    if (request.getParameter("searchName") != null)
		 request.getRequestDispatcher("SearchServlet").forward(request, response);

	    else printMain(request, response);
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
        redirectIfNotLoggedIn(request, response);
        
        if (request.getParameter("addName") != null)
        {
            String idExists = null;
            try { idExists = (String)request.getAttribute("idExist"); }
            catch (Exception e) { }
            if (idExists != null && idExists.equals("true"))
                printMain(request, response);
            else
	        request.getRequestDispatcher("AddServlet").forward(request, response);
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
