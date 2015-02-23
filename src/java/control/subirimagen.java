
package control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "subirimagen", urlPatterns = {"/subirimagen"})
@MultipartConfig
public class subirimagen extends HttpServlet {
    
    boolean error;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        error = false;
        String description = request.getParameter("descripcion");               // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("archivo");                             // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        response.setContentType("application/json;charset=UTF-8");
        InputStream fileContent = filePart.getInputStream();
        String destino = getServletContext().getRealPath("/") + "img/" + fileName;

        try {
            OutputStream os = new FileOutputStream(destino);
            byte[] b = new byte[2048];
            int length;
            try {
                while ((length = fileContent.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                fileContent.close();
            } catch (Exception e) {
                error = true;
            } finally {
                os.close();
            }
        } catch (Exception e) {
            error = true;
        }

        try (PrintWriter out = response.getWriter()) {
            if (error) {
                out.println("{\"r\":0)}");
            } else {
                out.println("{\"r\":1)}");
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
