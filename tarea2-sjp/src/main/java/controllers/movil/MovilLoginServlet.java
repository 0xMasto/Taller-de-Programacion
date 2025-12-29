package controllers.movil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.AsistenteDTO;

import java.io.IOException;

/**
 * Servlet for mobile login (only asistentes)
 * Calls Web Service for authentication
 */
@WebServlet("/login")
public class MovilLoginServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show login page
        request.getRequestDispatcher("/WEB-INF/movil/login.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor ingrese usuario y contraseña");
            request.getRequestDispatcher("/WEB-INF/movil/login.jsp").forward(request, response);
            return;
        }
        
        try {
            // Verify login credentials using Web Service
            boolean authenticated = port.verificarLogin(username, password);
            
            if (!authenticated) {
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("/WEB-INF/movil/login.jsp").forward(request, response);
                return;
            }
            
            // Find the user by nickname or email
            AsistenteDTO asistente = null;
            String asistenteNickname = null;
            
            try {
                AsistenteDTO[] asistentes = WSTypeConverter.toAsistenteDTOArray(port.listarAsistentesDTO());
                for (AsistenteDTO a : asistentes) {
                    if (a.getNickname().equals(username) || a.getCorreo().equals(username)) {
                        asistente = a;
                        asistenteNickname = a.getNickname();
                        break;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error fetching asistentes: " + ex.getMessage());
            }
            
            if (asistente == null) {
                // User is not an asistente or doesn't exist
                request.setAttribute("error", "Solo asistentes pueden acceder a la aplicación móvil");
                request.getRequestDispatcher("/WEB-INF/movil/login.jsp").forward(request, response);
                return;
            }
            
            // Create session with user data
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario_logueado", asistente);
            session.setAttribute("tipo_usuario", "Asistente");
            session.setAttribute("nickname", asistenteNickname);
            session.setAttribute("authenticated", true);
            
            // Redirect to mobile home
            response.sendRedirect(request.getContextPath() + "/home");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/movil/login.jsp").forward(request, response);
        }
    }
}

