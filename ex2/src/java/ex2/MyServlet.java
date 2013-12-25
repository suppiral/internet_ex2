/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gil
 */
public class MyServlet extends HttpServlet {

    protected final String HEADER_HTML_FILEPATH = "htmls/header.htm";
    protected final String FOOTER_HTML_FILEPATH = "htmls/footer.htm";
    protected final String LOGOUT_HTML_FILEPATH = "htmls/logout.htm";
    protected final String BACK_HTML_FILEPATH   = "htmls/back.htm";
    String url,dbName,driver,userName,password ;
    
    @Override
    public void init() throws ServletException {
    url = getServletConfig().getInitParameter("url");
    dbName = getServletConfig().getInitParameter("dbname");
    driver = getServletConfig().getInitParameter("driver");
    userName = getServletConfig().getInitParameter("username");
    password = getServletConfig().getInitParameter("password");
    }

    /**
     * 
     * @param filePath to read from
     * @return
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
    protected void redirectIfNotLoggedIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	Boolean isLoggedIn = (Boolean)request.getSession().getAttribute("loggedIn");
	if (isLoggedIn == null || isLoggedIn == false)
	    response.sendRedirect("LoginServlet");
    }
    
    public static void closeEverything(ResultSet rs, PreparedStatement pst,
		Connection con) {
	if (rs != null) {
		try {
			rs.close();
		} catch (SQLException e) {
		}
	}
	if (pst != null) {
		try {
			pst.close();
		} catch (SQLException e) {
		}
	}
	if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
            }
	}
}
}