/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Conexion;

/**
 *
 * @author Gustavo Urbina
 */
@WebServlet(name = "Publicidad", urlPatterns = {"/Publicidad"})
public class Publicidad extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession();
            String ip = (String) sesion.getAttribute("usuarioAnonimo");
            if (ip == null) {
                response.sendRedirect("index.jsp");
            } else {

                Random rn = new Random();
                String nombre = "";
                String direccion = "";
                int aleatorio = 1 + rn.nextInt(8);
                Conexion c = new Conexion();
                Connection con = c.conecta();
                PreparedStatement consulta = con.prepareStatement("SELECT * FROM PUBLICIDAD where pu_id "
                        + "=?");
                consulta.setInt(1, aleatorio);

                ResultSet rs = c.consultaPS(consulta);
                while (rs.next()) {
                    nombre = rs.getString("PU_nombre");
                    direccion = rs.getString("PU_Link");
                }
                request.setAttribute("liga", direccion);
                request.setAttribute("nombre", nombre);
                request.getRequestDispatcher("subirArchivo.jsp").forward(request, response);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Publicidad.class.getName()).log(Level.SEVERE, null, ex);
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
