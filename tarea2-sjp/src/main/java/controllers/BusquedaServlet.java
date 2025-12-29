package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import ws.WSClientHelper;
import publicar.ws.client.WebServices;
import publicar.ws.client.EventoDTO;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.OrganizadorDTO;
import net.java.dev.jaxb.array.AnyTypeArray;

/**
 * Servlet for search functionality
 */
@WebServlet("/busqueda")
public class BusquedaServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String query = request.getParameter("q");
        String ordenar = request.getParameter("ordenar");  // "alfa-asc", "alfa-desc", "fecha-desc" (default)
        
        try {
            List<Object> resultados = new ArrayList<>();
            
            if (query == null || query.trim().isEmpty()) {
                AnyTypeArray allResults = port.buscarEventosYEdiciones("");
                if (allResults != null && allResults.getItem() != null) {
                    resultados.addAll(allResults.getItem());
                }
            } else {
                AnyTypeArray searchResults = port.buscarEventosYEdiciones(query);
                if (searchResults != null && searchResults.getItem() != null) {
                    resultados.addAll(searchResults.getItem());
                }
            }
            
            // Separate eventos and ediciones
            List<EventoDTO> eventos = resultados.stream()
                .filter(o -> o instanceof EventoDTO)
                .map(o -> (EventoDTO) o)
                .collect(Collectors.toList());
                
            List<EdicionDTO> ediciones = resultados.stream()
                .filter(o -> o instanceof EdicionDTO)
                .map(o -> (EdicionDTO) o)
                .collect(Collectors.toList());
            
            // Filter editions based on user role
            HttpSession session = request.getSession(false);
            Object usuarioLogueado = (session != null) ? session.getAttribute("usuario_logueado") : null;
            String tipoUsuario = (String) ((session != null) ? session.getAttribute("tipo_usuario") : null);
            
            if ("Organizador".equals(tipoUsuario) && usuarioLogueado instanceof OrganizadorDTO) {
                String nicknameOrganizador = ((OrganizadorDTO) usuarioLogueado).getNickname();
                ediciones = ediciones.stream()
                    .filter(ed -> ed != null && ed.getEstado() != null && (
                        (ed.getOrganizador() != null && nicknameOrganizador != null && 
                         nicknameOrganizador.equals(ed.getOrganizador())) ||
                        "ACEPTADA".equalsIgnoreCase(ed.getEstado())
                    ))
                    .collect(Collectors.toList());
            } else {
                ediciones = ediciones.stream()
                    .filter(ed -> ed != null && ed.getEstado() != null && 
                        "ACEPTADA".equalsIgnoreCase(ed.getEstado()))
                    .collect(Collectors.toList());
            }
            
            // Apply sorting
            if ("alfa-asc".equals(ordenar)) {
                eventos.sort(Comparator.comparing(EventoDTO::getNombre));
                ediciones.sort(Comparator.comparing(EdicionDTO::getNombre));
            } else if ("alfa-desc".equals(ordenar)) {
                eventos.sort(Comparator.comparing(EventoDTO::getNombre).reversed());
                ediciones.sort(Comparator.comparing(EdicionDTO::getNombre).reversed());
            } else {
                // Default: sort by fechaAlta descending - handle publicar.ws.client.LocalDate
                eventos.sort((e1, e2) -> {
                    if (e1.getFechaAlta() == null && e2.getFechaAlta() == null) return 0;
                    if (e1.getFechaAlta() == null) return 1;
                    if (e2.getFechaAlta() == null) return -1;
                    // Compare LocalDate objects - newer first
                    return e2.getFechaAlta().toString().compareTo(e1.getFechaAlta().toString());
                });
                ediciones.sort((e1, e2) -> {
                    if (e1.getFechaAlta() == null && e2.getFechaAlta() == null) return 0;
                    if (e1.getFechaAlta() == null) return 1;
                    if (e2.getFechaAlta() == null) return -1;
                    return e2.getFechaAlta().toString().compareTo(e1.getFechaAlta().toString());
                });
            }
            
            request.setAttribute("query", query);
            request.setAttribute("ordenar", ordenar != null ? ordenar : "fecha-desc");
            request.setAttribute("eventos", eventos);
            request.setAttribute("ediciones", ediciones);
            request.setAttribute("totalResultados", eventos.size() + ediciones.size());
            
            request.getRequestDispatcher("/WEB-INF/web/busqueda/resultados.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error al realizar búsqueda: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/errorPages/500.jsp").forward(request, response);
        }
    }
}

