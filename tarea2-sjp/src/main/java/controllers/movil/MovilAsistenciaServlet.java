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
 * Servlet for registering attendance to an edition
 * UC: Registrar Asistencia - Mobile
 */
@WebServlet("/asistencia")
public class MovilAsistenciaServlet extends HttpServlet {
    
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
        
        // Default action is "list"
        if (action == null || action.isEmpty()) {
            action = "list";
        }
        
        try {
            if ("list".equals(action)) {
                // Get all registrations for this asistente
                RegistroDTO[] registros = WSTypeConverter.toRegistroDTOArray(
                    port.getRegistrosPorAsistentes(nickname)
                );
                
                request.setAttribute("registros", registros);
                request.getRequestDispatcher("/WEB-INF/movil/asistencia/list.jsp").forward(request, response);
                
            } else if ("detail".equals(action)) {
                // Get specific registration detail
                String nombreEdicion = request.getParameter("nombreEdicion");
                
                if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/asistencia");
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
                    response.sendRedirect(request.getContextPath() + "/asistencia");
                    return;
                }
                
                // Check if attendance can be confirmed (edition has started)
                boolean puedeConfirmarAsistencia = false;
                if (registro.getEdicion() != null && registro.getEdicion().getFechaInicio() != null) {
                    try {
                        java.time.LocalDate fechaInicio = java.time.LocalDate.parse(registro.getEdicion().getFechaInicio());
                        java.time.LocalDate hoy = java.time.LocalDate.now();
                        puedeConfirmarAsistencia = !fechaInicio.isAfter(hoy);
                    } catch (Exception e) {
                        // If parsing fails, default to false
                    }
                }
                
                request.setAttribute("registro", registro);
                request.setAttribute("puedeConfirmarAsistencia", puedeConfirmarAsistencia);
                request.getRequestDispatcher("/WEB-INF/movil/asistencia/detail.jsp").forward(request, response);
                
            } else {
                response.sendRedirect(request.getContextPath() + "/asistencia");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar registros: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/movil/error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("authenticated") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String nickname = (String) session.getAttribute("nickname");
        String nombreEdicion = request.getParameter("nombreEdicion");
        
        if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
            request.setAttribute("error", "Debe seleccionar una edición");
            response.sendRedirect(request.getContextPath() + "/asistencia");
            return;
        }
        
        try {
            // Call WS to register attendance
            port.registrarAsistencia(nickname, nombreEdicion);
            
            // Redirect back to detail view with success message
            response.sendRedirect(request.getContextPath() + "/asistencia?action=detail&nombreEdicion=" 
                + java.net.URLEncoder.encode(nombreEdicion, "UTF-8") + "&success=true");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar asistencia: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/asistencia?action=detail&nombreEdicion=" 
                + java.net.URLEncoder.encode(nombreEdicion, "UTF-8") + "&error=" 
                + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}

