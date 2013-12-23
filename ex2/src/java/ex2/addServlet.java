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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Moti
 */
public class addServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "ex2";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        
        Connection conn = null;
        try 
        {   
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
        
            // checks if ID exists
        
            String queryCheck = "SELECT * from product WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(queryCheck);
            pst.setString(1, request.getParameter("addID"));
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next())
            {
                RequestDispatcher rd=request.getRequestDispatcher("MainServlet");  
                out.println("Error: ID already exists, Please Change the ID"); 
                rd.include(request, response);
            }
            //ID does not exist add to DB  
            else
            {
                pst=conn.prepareStatement("INSERT INTO product(name,id,price,description,quantity) VALUES(?,?,?,?,?)");
        
                pst.setString(1, request.getParameter("addName"));
                pst.setString(2, request.getParameter("addID"));
                pst.setString(3, request.getParameter("addPrice"));
                pst.setString(4, request.getParameter("addDescription"));
                pst.setString(5, request.getParameter("addQuantity"));

                pst.executeUpdate();

                //Show The product with search - call Search Servlet
                RequestDispatcher rd=request.getRequestDispatcher("/SearchServlet?searchName="
                        +request.getParameter("addName")+"&searchID="+request.getParameter("addID"));
                rd.forward(request,response);
                
                conn.close();
                System.out.println("Disconnected from database");
            }
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
