package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import ws.WSClientHelper;
import publicar.ws.client.WebServices;

/**
 * AJAX Servlet to check email availability
 */
@WebServlet("/checkEmail")
public class CheckEmailServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            if (email == null || email.trim().isEmpty()) {
                out.print("{\"available\": false, \"message\": \"El email no puede estar vacío\"}");
                return;
            }
            
            boolean exists = port.existeCorreo(email);
            
            if (exists) {
                out.print("{\"available\": false, \"message\": \"✗ No disponible\"}");
            } else {
                out.print("{\"available\": true, \"message\": \"✓ Disponible\"}");
            }
            
        } catch (Exception e) {
            out.print("{\"available\": false, \"message\": \"Error al verificar\"}");
        } finally {
            out.flush();
        }
    }
}

