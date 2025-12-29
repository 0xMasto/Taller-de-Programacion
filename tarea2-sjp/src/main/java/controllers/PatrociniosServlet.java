package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ws.WSClientHelper;
import publicar.ws.client.WebServices;
import publicar.ws.client.PatrocinioDTO;

/**
 * UC: Consulta de Patrocinio
 * Servlet for patrocinio detail consultation
 */
@WebServlet("/patrocinios")
public class PatrociniosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String codigoPatrocinio = request.getParameter("id");
        
        if (codigoPatrocinio == null || codigoPatrocinio.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código de patrocinio requerido");
            return;
        }
        
        try {
            // Buscar patrocinio por código
            PatrocinioDTO patrocinio = port.buscarPatrocinioDTO(codigoPatrocinio);
            
            if (patrocinio == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Patrocinio no encontrado");
                return;
            }
            
            // Pasar datos a la vista
            request.setAttribute("patrocinio", patrocinio);
            
            // Forward al JSP de detalle
            request.getRequestDispatcher("/WEB-INF/web/patrocinios/detail.jsp")
                .forward(request, response);
                
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar el patrocinio: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/shared/errorPages/500.jsp")
                .forward(request, response);
        }
    }
}

