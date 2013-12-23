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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Moti
 */
public class SearchServlet extends HttpServlet {
    private String theparam;
@Override
    public void init() throws ServletException {
    theparam = getServletConfig().getInitParameter("someparam");
    // The  parameters can also be retrieved using the servlet context
    //theparam = getServletContext().getInitParameter("someparam");
    
    //you can output in a log file to debug etc...
    getServletContext().log("the param is " + theparam);
    // the log file is located somewhere like:
    // NetBeans/glassfish-3.1.1/glassfish/domains/domain1/logs/server.log
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "ex2";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";

        PreparedStatement pst;
        ResultSet rs ;
        
        try {
        Class.forName(driver).newInstance();
        conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        System.out.println("Connected to the database");
        String name = request.getParameter("searchName");
        String ID = request.getParameter("searchID");
        String query;
        if(ID.isEmpty())
            query = "select * from product where name='"+name+"'";
        else if(name.isEmpty())
            query = "select * from product where id='"+ID+"'";
        else
            query = "select * from product where name='"+name+"' and id='"+ID+"'" ;
        
        pst =  conn.prepareStatement(query);
        boolean isResult = pst.execute();
        out.println("<table border='1'>");
        do {
               rs = pst.getResultSet();
               while(rs.next())
               {
                    out.println("<tr><td> Id </td><td><td><input type='text' value="+rs.getString(1)+"></td></tr>");
                    out.println("<tr><td> Name </td><td><td><input type='text' value="+rs.getString(2)+"></td></tr>");
                    out.println("<tr><td> Description </td><td><td><input type='text' value="+rs.getString(3)+"></td></tr>");
                    out.println("<tr><td> Price </td><td><td><input type='text' value="+rs.getString(4)+"></td></tr>");
                    out.println("<tr><td> Quantity </td><td><td><input type='text' value="+rs.getString(5)+"></td></tr>");
               }

                isResult = pst.getMoreResults();
            } while (isResult);
        out.println("</table>");
        conn.close();
        System.out.println("Disconnected from database");
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (SQLException e) {
        }
         finally {
            try {
                if (conn != null) { conn.close(); }
			} catch (SQLException e) {
			}

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
        processRequest(request, response);
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
        processRequest(request, response);
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
