
package control;

import hibernate.Inmueble;
import java.awt.image.BufferedImage;
import java.io.File;
import modelo.ModeloInmueble;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "ControlAndroid", urlPatterns = {"/ControlAndroid"})
@MultipartConfig
public class ControlAndroid extends HttpServlet {
        boolean error=false;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String target = request.getParameter("target");
        String op = request.getParameter("op");
        String action = request.getParameter("action");
        
        if(target.equals("prueba") && op.equals("insert") && action.equals("op")){
            response.setContentType("application/json;charset=UTF-8");
            Inmueble inm = getInmueble(request);
            error = false;
            ModeloInmueble.insert(inm);
            
            try (PrintWriter out = response.getWriter()) {
                if (error) {
                    out.println("{\"r\":0)}");
                } else {
                    out.println("{\"r\":1)}");
                }
            }
        }else if(target.equals("prueba") && op.equals("select") && action.equals("view")){
            
            List<Inmueble> lista = ModeloInmueble.get();
            response.setContentType("application/json;charset=UTF-8");
            
            try (PrintWriter out = response.getWriter()) {
                out.println("[");
                boolean primera=true;
                for(Inmueble p : lista){
                    if(!primera){
                        out.println(",{");
                    }else{
                        out.println("{");
                        primera=false;
                    }
                    out.println(" \"localidad\":\""+p.getLocalidad()+"\", ");
                    out.println(" \"calle\":\""+p.getCalle()+"\", ");
                    out.println(" \"numero\":\""+p.getNumero()+"\", ");
                    out.println(" \"tipo\":\""+p.getTipo()+"\", ");
                    out.println(" \"precio\":\""+p.getPrecio()+"\" ");
                    //p.getId()
                    out.println("}");
                }
                out.println("]");
                
                
                

            }
        }
    }
    
    private Inmueble getInmueble(HttpServletRequest request){
        Inmueble inm = new Inmueble();
        inm.setUsuario(request.getParameter("usuario"));
        inm.setLocalidad(request.getParameter("localidad"));
        inm.setCalle(request.getParameter("calle"));
        inm.setNumero(request.getParameter("numero"));
        inm.setTipo(request.getParameter("tipo"));
        inm.setPrecio(request.getParameter("precio"));
        inm.setImagenes(request.getParameter("imagen"));
        return inm;
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
