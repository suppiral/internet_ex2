/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ex2;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Moti and Gil Mizrahi
 */
public class SearchServlet extends MyServlet {
    //Members of the Servlet , refer to database login.
    private String url,dbName,driver,userName,password ;
    
    
    /**
     * init function, configuring the members values from web.xml
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init() throws ServletException {
	url =  getServletConfig().getInitParameter("url");
	dbName =  getServletConfig().getInitParameter("dbname");
	driver =  getServletConfig().getInitParameter("driver");
	userName =  getServletConfig().getInitParameter("username");
	password =  getServletConfig().getInitParameter("password");
    }
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     * This function searches for a given product in the database
     * and printed in an HTML table.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings({"UseSpecificCatch", "BroadCatchBlock", "TooBroadCatch"})
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;//the connection
        PreparedStatement pst = null;//prepared statement
        ResultSet rs = null ;//result set
        
        try {
        Class.forName(driver).newInstance();
        conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        System.out.println("Connected to the database");
        String name = request.getParameter("searchName");
        String ID = request.getParameter("searchID");
        String query;
	
	
        if(ID.isEmpty())//only Name entered
	{
            query = "select * from product where name like ?";
	    pst=conn.prepareStatement(query);
            pst.setString(1, "%" + name + "%");
	}
        else if(name.isEmpty())//only ID entered
	{
            query = "select * from product where id= ?";
	    pst=conn.prepareStatement(query);
            pst.setString(1, ID);
	}
        else //Name and ID entered
	{
            query = "select * from product where name like ? and id= ? " ;
	    pst=conn.prepareStatement(query);
            pst.setString(1, "%" + name + "%");
            pst.setString(2, ID);   
	}
        
        boolean isResult = pst.execute();
        out.println("<table border='1'>");
        do {
               out.print("<tr class=\"descTr\"><td> Id </td>");
               out.print("<td> Name </td>");
               out.print("<td> Description </td>");
               out.print("<td> Price </td>");
               out.println("<td> Quantity </td></tr>");
               rs = pst.getResultSet();
               while(rs.next())
               {
                    out.print("<tr><td>"+rs.getString(1)+"</td>");
                    out.print("<td>"+rs.getString(2)+"</td>");
                    out.print("<td>"+rs.getString(3)+"</td>");
                    out.print("<td>"+rs.getString(4)+"</td>");
                    out.println("<td>"+rs.getString(5)+"</td></tr>");
               }
               isResult = pst.getMoreResults();
            }while (isResult);
        out.println("</table>");
        System.out.println("Disconnected from database");
        }catch (SQLException e) {
	    out.println("<p>Cannot connect to database. please try again later.</p>");
        }catch(Exception e){
            out.println("<p class=\"errormsg\">Cannot connect to database. please try again later.</p>");
        }
         finally {
            //Closing connection,result sets and prepared statement
            closeEverything(rs,pst,conn);
         }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	redirectIfNotLoggedIn(request, response);
	PrintWriter out = response.getWriter();
	out.println(getFileContent(HEADER_HTML_FILEPATH));
	out.println(getFileContent(LOGOUT_HTML_FILEPATH));
        processRequest(request, response);
	out.println(getFileContent(BACK_HTML_FILEPATH));
	out.println(getFileContent(FOOTER_HTML_FILEPATH));

	
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
    
    /**
     * This function makes sure we close connection result set and p.statement
     * this function is called from finally block
     * @param rs Result Set
     * @param pst Prepared Statement
     * @param con Connection
     */
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
            } catch (SQLException e) {
            }
	}
    }
}
