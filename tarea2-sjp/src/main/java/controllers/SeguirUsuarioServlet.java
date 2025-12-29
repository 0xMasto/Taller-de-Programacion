package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import ws.WSClientHelper;
import publicar.ws.client.WebServices;
import publicar.ws.client.BusinessException_Exception;

/**
 * Servlet for following/unfollowing users
 */
@WebServlet("/seguir-usuario")
public class SeguirUsuarioServlet extends HttpServlet {
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario_logueado") == null) {
            response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
            return;
        }
        
        String nicknameSeguidor = (String) session.getAttribute("nickname");
        String nicknameSeguido = request.getParameter("usuarioSeguir");
        String action = request.getParameter("accion");
        
        try {
            if ("seguir".equals(action)) {
                port.seguirUsuario(nicknameSeguidor, nicknameSeguido);
                request.getSession().setAttribute("success", "Ahora sigues a " + nicknameSeguido);
            } else if ("dejarSeguir".equals(action)) {
                port.dejarDeSeguirUsuario(nicknameSeguidor, nicknameSeguido);
                request.getSession().setAttribute("success", "Has dejado de seguir a " + nicknameSeguido);
            }
            
            // Redirect back to user profile
            response.sendRedirect(request.getContextPath() + "/usuarios?action=detail&nickname=" + nicknameSeguido);
            
        } catch (BusinessException_Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getFaultInfo().getMessage());
            response.sendRedirect(request.getContextPath() + "/usuarios?action=detail&nickname=" + nicknameSeguido);
        }
    }
}

