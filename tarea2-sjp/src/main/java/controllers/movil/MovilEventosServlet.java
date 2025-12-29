package controllers.movil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.EventoDTO;
import publicar.ws.client.EdicionDTO;

/**
 * Servlet for mobile event consultation
 * UC: Consulta de Edicion de Evento
 */
@WebServlet("/eventos")
public class MovilEventosServlet extends HttpServlet {
    
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
        
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                // List all events
                listarEventos(request, response);
                
            } else if ("detail".equals(action)) {
                // Show event details with editions
                mostrarDetalleEvento(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar eventos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/movil/error.jsp").forward(request, response);
        }
    }
    
    /**
     * List all events
     */
    private void listarEventos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            EventoDTO[] eventos = WSTypeConverter.toEventoDTOArray(port.listarEventosDTO());
            request.setAttribute("eventos", eventos);
            request.getRequestDispatcher("/WEB-INF/movil/eventos/list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al listar eventos", e);
        }
    }
    
    /**
     * Show event details with approved editions
     */
    private void mostrarDetalleEvento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreEvento = request.getParameter("nombre");
        
        if (nombreEvento == null || nombreEvento.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/eventos");
            return;
        }
        
        try {
            // Get event details
            EventoDTO evento = port.buscarEventoDTO(nombreEvento);
            
            // Get all editions for this event
            EdicionDTO[] todasEdiciones = WSTypeConverter.toEdicionDTOArray(
                port.listarEdicionesDeEventoDTO(nombreEvento)
            );
            
            // Filter only ACEPTADA (approved) editions
            List<EdicionDTO> edicionesAprobadas = java.util.Arrays.stream(todasEdiciones)
                .filter(ed -> "ACEPTADA".equalsIgnoreCase(ed.getEstado()))
                .collect(Collectors.toList());
            
            request.setAttribute("evento", evento);
            request.setAttribute("ediciones", edicionesAprobadas);
            request.getRequestDispatcher("/WEB-INF/movil/eventos/detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            throw new ServletException("Error al consultar evento", e);
        }
    }
}

