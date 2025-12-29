package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.BusinessException_Exception;

/**
 * Servlet for archiving editions
 */
@WebServlet("/archivar-edicion")
public class ArchivarEdicionServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario_logueado") == null ||
            !"Organizador".equals(session.getAttribute("tipo_usuario"))) {
            response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
            return;
        }
        
        String nickname = (String) session.getAttribute("nickname");
        
        try {
            String[] nombreEdicionesArray = WSTypeConverter.toStringArray(port.obtenerEdicionesOrganizadasPor(nickname));
            List<String> nombreEdiciones = Arrays.asList(nombreEdicionesArray);
            
            // Get full DTOs and filter
            List<EdicionDTO> edicionesArchivables = nombreEdiciones.stream()
                .map(nombre -> {
                    try {
                        return port.buscarEdicionDTO(nombre);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(ed -> ed != null)
                .filter(ed -> "ACEPTADA".equals(ed.getEstado()))
                .filter(ed -> ed.getFechaFin() != null && WSTypeConverter.isBeforeDateString(ed.getFechaFin(), LocalDate.now()))
                .filter(ed -> !ed.isArchivada())
                .collect(Collectors.toList());
            
            request.setAttribute("ediciones", edicionesArchivables);
            request.getRequestDispatcher("/WEB-INF/web/ediciones/archivar.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al cargar ediciones: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/errorPages/500.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario_logueado") == null ||
            !"Organizador".equals(session.getAttribute("tipo_usuario"))) {
            response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
            return;
        }
        
        String nombreEdicion = request.getParameter("edicion");
        String nickname = (String) session.getAttribute("nickname");
        
        try {
            port.archivarEdicion(nombreEdicion, nickname);
            
            request.getSession().setAttribute("success", "Edición archivada exitosamente");
            response.sendRedirect(request.getContextPath() + "/perfil");
            
        } catch (BusinessException_Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}

