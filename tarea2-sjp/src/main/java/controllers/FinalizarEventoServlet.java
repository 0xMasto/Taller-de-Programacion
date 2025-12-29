package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.BusinessException_Exception;

/**
 * UC: Finalizar Evento
 * Servlet for finalizing events (Organizador only)
 */
@WebServlet("/eventos/finalizar")
public class FinalizarEventoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Check if user is logged in and is an Organizador
        if (session == null || session.getAttribute("usuario_logueado") == null) {
            response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
            return;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipo_usuario");
        if (!"Organizador".equals(tipoUsuario)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        try {
            // Get list of non-finalized events (listarEventos already filters them)
            String[] eventos = WSTypeConverter.toStringArray(port.listarEventos());
            
            request.setAttribute("eventos", eventos);
            request.getRequestDispatcher("/WEB-INF/web/eventos/finalizar.jsp")
                .forward(request, response);
                
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar eventos: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/web/eventos/finalizar.jsp")
                .forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Check if user is logged in and is an Organizador
        if (session == null || session.getAttribute("usuario_logueado") == null) {
            response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
            return;
        }
        
        String tipoUsuario = (String) session.getAttribute("tipo_usuario");
        if (!"Organizador".equals(tipoUsuario)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        String nombreEvento = request.getParameter("nombreEvento");
        String confirmacion = request.getParameter("confirmacion");
        
        if (nombreEvento == null || nombreEvento.trim().isEmpty()) {
            request.setAttribute("error", "Debe seleccionar un evento");
            doGet(request, response);
            return;
        }
        
        if (confirmacion == null || !confirmacion.equals("SI")) {
            request.setAttribute("error", "Debe confirmar la finalización del evento");
            doGet(request, response);
            return;
        }
        
        try {
            // Finalize the event
            port.finalizarEvento(nombreEvento);
            
            request.setAttribute("success", "El evento '" + nombreEvento + "' ha sido finalizado correctamente");
            doGet(request, response);
            
        } catch (BusinessException_Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al finalizar el evento: " + e.getMessage());
            doGet(request, response);
        }
    }
}
