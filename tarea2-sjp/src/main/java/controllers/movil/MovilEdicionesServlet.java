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
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.TipoRegistroDTO;
import publicar.ws.client.PatrocinioDTO;
import publicar.ws.client.RegistroDTO;

/**
 * Servlet for mobile edition detail consultation
 * UC: Consulta de Edicion de Evento - Edition Details
 */
@WebServlet("/ediciones")
public class MovilEdicionesServlet extends HttpServlet {
    
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
        
        String nombreEdicion = request.getParameter("nombre");
        
        if (nombreEdicion == null || nombreEdicion.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/eventos");
            return;
        }
        
        try {
            String nickname = (String) session.getAttribute("nickname");
            
            // Get edition details
            EdicionDTO edicion = port.buscarEdicionDTO(nombreEdicion);
            
            // Get registration types for this edition
            TipoRegistroDTO[] tiposRegistro = WSTypeConverter.toTipoRegistroDTOArray(
                port.obtenerTiposDeRegistroDeEdicionDTO(nombreEdicion)
            );
            
            // Get sponsorships for this edition
            PatrocinioDTO[] patrocinios = WSTypeConverter.toPatrocinioDTOArray(
                port.obtenerPatrociniosDeEdicionDTO(nombreEdicion)
            );
            
            // Check if asistente is registered
            boolean registrado = port.asistenteEstaRegistradoEnEdicion(nickname, nombreEdicion);
            
            // If registered, get registration details
            RegistroDTO miRegistro = null;
            if (registrado) {
                RegistroDTO[] registros = WSTypeConverter.toRegistroDTOArray(
                    port.getRegistrosPorAsistentes(nickname)
                );
                // Find the registration for this edition
                miRegistro = Arrays.stream(registros)
                    .filter(r -> nombreEdicion.equals(r.getNombreEdicion()))
                    .findFirst()
                    .orElse(null);
            }
            
            request.setAttribute("edicion", edicion);
            request.setAttribute("tiposRegistro", tiposRegistro);
            request.setAttribute("patrocinios", patrocinios);
            request.setAttribute("registrado", registrado);
            request.setAttribute("miRegistro", miRegistro);
            
            request.getRequestDispatcher("/WEB-INF/movil/ediciones/edicion-detail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al consultar edición: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/movil/error.jsp").forward(request, response);
        }
    }
}

