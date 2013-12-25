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
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Moti
 */
public class addServlet extends MyServlet {
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
        PreparedStatement pst=null ;
        try 
        {   
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);            
            
            pst=conn.prepareStatement("INSERT INTO product(name,id,price,description,quantity) VALUES(?,?,?,?,?)");
            pst.setString(1, request.getParameter("addName"));
            pst.setString(2, request.getParameter("addID"));
            pst.setString(3, request.getParameter("addPrice"));
            pst.setString(4, request.getParameter("addDescription"));
            pst.setString(5, request.getParameter("addQuantity"));

            pst.executeUpdate();
            
            out.println("<b>Product was added successfuly!</b><br>");
            out.println("Name : "+request.getParameter("addName"));
            out.println("<br>ID : "+request.getParameter("addID"));
            out.println("<br>Price : "+request.getParameter("addPrice"));
            out.println("<br>Description : "+request.getParameter("addDescription"));
            out.println("<br>Quantity : "+request.getParameter("addQuantity"));
            
	    
           
        } catch (SQLIntegrityConstraintViolationException e) { // key violation
                /*RequestDispatcher rd=request.getRequestDispatcher("MainServlet");  
                out.println("Error: ID already exists, Please Change the ID"); 
                rd.include(request, response);*/
                request.setAttribute("idExist","true");
                request.getRequestDispatcher("MainServlet").forward(request, response);
        } catch (SQLException e) { out.println("<p>Cannot connect to database. please try again later.</p>");
        } catch (Exception ex) {
        }
         finally {
            closeEverything(null,pst,conn);
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
        //processRequest(request, response);	
	
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
	PrintWriter out = response.getWriter();
	out.println(getFileContent(HEADER_HTML_FILEPATH));
	out.println(getFileContent(LOGOUT_HTML_FILEPATH));
        processRequest(request, response);
	out.println(getFileContent(BACK_HTML_FILEPATH));
	out.println(getFileContent(FOOTER_HTML_FILEPATH));
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
