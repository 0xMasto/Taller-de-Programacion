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
import publicar.ws.client.AsistenteDTO;
import publicar.ws.client.OrganizadorDTO;
import model.*;

/**
 * UC-S1: Inicio de Sesión
 * Servlet que maneja autenticación usando Web Services
 */
@WebServlet("/iniciar-sesion")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;

	public Login() {
		super();
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.port = WSClientHelper.getInstance().getPort();
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		// Manejar logout
		if ("logout".equals(action)) {
			handleLogout(request, response);
			return;
		}
		
		// Manejar login
		HttpSession objSesion = request.getSession();
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		if (nickname == null || password == null) {
			// GET: mostrar formulario de login
			request.getRequestDispatcher("/WEB-INF/web/home/iniciar.jsp")
				.forward(request, response);
			return;
		}

	// POST: procesar login
	try {
		// First verify credentials
		boolean authenticated = port.verificarLogin(nickname, password);
		
		if (!authenticated) {
			request.setAttribute("error", "Usuario o contraseña incorrectos");
			request.getRequestDispatcher("/WEB-INF/web/home/iniciar.jsp")
				.forward(request, response);
			return;
		}
		
		// Find the user by nickname or email
		AsistenteDTO asistente = null;
		OrganizadorDTO organizador = null;
		String actualNickname = null;
		
		// Check in asistentes
		try {
			AsistenteDTO[] asistentes = WSTypeConverter.toAsistenteDTOArray(port.listarAsistentesDTO());
			for (AsistenteDTO a : asistentes) {
				if (a.getNickname().equals(nickname) || a.getCorreo().equals(nickname)) {
					asistente = a;
					actualNickname = a.getNickname();
					break;
				}
			}
		} catch (Exception ex) {
			System.err.println("Error fetching asistentes: " + ex.getMessage());
		}
		
		// If not found in asistentes, check in organizadores
		if (asistente == null) {
			try {
				OrganizadorDTO[] organizadores = WSTypeConverter.toOrganizadorDTOArray(port.listarOrganizadoresDTO());
				for (OrganizadorDTO o : organizadores) {
					if (o.getNickname().equals(nickname) || o.getCorreo().equals(nickname)) {
						organizador = o;
						actualNickname = o.getNickname();
						break;
					}
				}
			} catch (Exception ex) {
				System.err.println("Error fetching organizadores: " + ex.getMessage());
			}
		}
		
		if (asistente == null && organizador == null) {
			// Usuario no encontrado
			request.setAttribute("error", "Usuario o contraseña incorrectos");
			request.getRequestDispatcher("/WEB-INF/web/home/iniciar.jsp")
				.forward(request, response);
			return;
		}
		
		// Login exitoso: crear sesión
		if (asistente != null) {
			objSesion.setAttribute("usuario_logueado", asistente);
			objSesion.setAttribute("tipo_usuario", "Asistente");
			objSesion.setAttribute("nickname", actualNickname);
		} else if (organizador != null) {
			objSesion.setAttribute("usuario_logueado", organizador);
			objSesion.setAttribute("tipo_usuario", "Organizador");
			objSesion.setAttribute("nickname", actualNickname);
		}
		
		objSesion.setAttribute("estado_sesion", EstadoSesion.LOGIN_CORRECTO);
		
		// Redirigir a home
		response.sendRedirect(request.getContextPath() + "/");
		
	} catch (Exception e) {
		e.printStackTrace();
		request.setAttribute("error", "Error en el sistema: " + e.getMessage());
		try {
			request.getRequestDispatcher("/WEB-INF/web/home/inicioErroneo.jsp")
				.forward(request, response);
		} catch (Exception ex) {
			// If forward fails, just write error to response
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
				"Error en el sistema: " + e.getMessage());
		}
	}
	}
	
	/**
	 * UC-S2: Cierre de Sesión
	 * Invalida la sesión y redirige al home
	 */
	private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
