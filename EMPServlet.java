

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EMPServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            
            
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            Connection conn = DriverManager.getConnection("jdbc:db2://172.17.0.142:50001/itgdb","ITGUSR11","miracle11");
	    Statement stm = conn.createStatement();
            PreparedStatement ps;
            ResultSet rs;
                 
            String rbut = request.getParameter("Submit");
            String lbut = request.getParameter("lsubmit");
            
            if(rbut != null)
            {
            int id = Integer.parseInt(request.getParameter("id"));
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String username = request.getParameter("uname");
            String password = request.getParameter("password");
            String designation = request.getParameter("designation");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            
            ps = conn.prepareStatement("insert into empservlettable values(?,?,?,?,?,?,?,?)");
            
            ps.setInt(1, id);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.setString(6, designation);
            ps.setString(7, phone);
            ps.setString(8, email);
            
            ps.executeUpdate();
            
            out.println("Your Registared Successfullyy....!");
            }
            else if(lbut != null)
            {
                String luname = request.getParameter("luname");
                String lpassword = request.getParameter("lpassword");
                String str="";
                ps = conn.prepareStatement("select designation from empservlettable where u_name=? and password=?");
                ps.setString(1, luname);
                ps.setString(2, lpassword);
                
                rs = ps.executeQuery();
                
                while(rs.next())
                {
                   str = rs.getString(1);
                }
                if(str.equals("HR") || str.equals("Manager") || str.equals("Employee"))
                {
                    //out.println("Welcom to HR");
                    
                    RequestDispatcher rd = request.getRequestDispatcher("NewServlet");
                    rd.forward(request, response);
                }
                else
                {
                    RequestDispatcher rd = request.getRequestDispatcher("EMPLogin.html");
                    //out.println("Please enter Valid Credantials");
                    rd.forward(request, response);
                }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EMPServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EMPServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EMPServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EMPServlet.class.getName()).log(Level.SEVERE, null, ex);
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
