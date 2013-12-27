/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class for Servlets in order to share protected functions and members
 * shared by all
 * @author Moti and Gil Mizrahi
 */
public class MyServlet extends HttpServlet {
    
    protected final String HEADER_HTML_FILEPATH = "htmls/header.htm";
    protected final String FOOTER_HTML_FILEPATH = "htmls/footer.htm";
    protected final String LOGOUT_HTML_FILEPATH = "htmls/logout.htm";
    protected final String BACK_HTML_FILEPATH   = "htmls/back.htm";
    
    /**
     * reads a file and returns its content
     * @param filePath to read from
     * @return content of a file
     * @throws FileNotFoundException 
     */
    protected String getFileContent(String filePath) throws FileNotFoundException
    {

        String relPath = getServletContext().getRealPath("/");
        Scanner in = new Scanner(new FileReader( relPath + filePath));
        StringBuilder buff = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");        
        while (in.hasNextLine())
            buff.append(in.nextLine()).append(lineSeparator);
        in.close();

        return buff.toString();
    }
    /**
     * redirects to login page if loggedIn attribute is not defined as true
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    protected void redirectIfNotLoggedIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	Boolean isLoggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
	if (isLoggedIn == null || isLoggedIn == false)
	    response.sendRedirect("LoginServlet");
    }
}