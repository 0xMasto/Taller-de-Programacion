package controllers.movil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.RegistroDTO;

/**
 * Servlet for mobile registration consultation
 * UC: Consulta de Registro - Mobile
 */
@WebServlet("/registros")
public class MovilRegistrosServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authenticated") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String nickname = (String) session.getAttribute("nickname");
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                // List attendee's registrations
                RegistroDTO[] registros = WSTypeConverter.toRegistroDTOArray(
                    port.getRegistrosPorAsistentes(nickname)
                );
                
                request.setAttribute("registros", registros);
                request.getRequestDispatcher("/WEB-INF/movil/registros/list.jsp").forward(request, response);
                
            } else if ("detail".equals(action)) {
                String nombreEdicion = request.getParameter("edicion");
                
                if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/registros");
                    return;
                }
                
                // Get all registrations and find the one for this edition
                RegistroDTO[] registros = WSTypeConverter.toRegistroDTOArray(
                    port.getRegistrosPorAsistentes(nickname)
                );
                
                RegistroDTO registro = Arrays.stream(registros)
                    .filter(r -> nombreEdicion.equals(r.getNombreEdicion()))
                    .findFirst()
                    .orElse(null);
                
                if (registro == null) {
                    request.setAttribute("error", "No se encontró el registro");
                    response.sendRedirect(request.getContextPath() + "/registros");
                    return;
                }
                
                request.setAttribute("registro", registro);
                request.getRequestDispatcher("/WEB-INF/movil/registros/detail.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar registros: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/movil/error.jsp").forward(request, response);
        }
    }
}

