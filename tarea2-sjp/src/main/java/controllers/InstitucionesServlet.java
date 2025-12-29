package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ws.WSClientHelper;
import publicar.ws.client.WebServices;
import publicar.ws.client.BusinessException_Exception;

/**
 * UC-S14: Alta de Institución
 * Servlet implementation class InstitucionesServlet
 */
@WebServlet("/instituciones")
public class InstitucionesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;
       
    public InstitucionesServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String action = request.getParameter("action");
		
		// Solo organizadores pueden crear instituciones
		if (session == null || !"Organizador".equals(session.getAttribute("tipo_usuario"))) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			return;
		}
		
		if ("new".equals(action)) {
			if ("POST".equals(request.getMethod())) {
				// Procesar creación
				handleCrearInstitucion(request, response);
			} else {
				// Mostrar formulario
				request.getRequestDispatcher("/WEB-INF/web/instituciones/form.jsp")
					.forward(request, response);
			}
		} else {
			// Redirect al home si no hay acción
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
	
	private void handleCrearInstitucion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		String url = request.getParameter("url");
		
		try {
			// Validaciones
			if (nombre == null || nombre.trim().isEmpty()) {
				throw new Exception("El nombre de la institución es obligatorio");
			}
			if (descripcion == null || descripcion.trim().isEmpty()) {
				throw new Exception("La descripción es obligatoria");
			}
			if (url == null || url.trim().isEmpty()) {
				throw new Exception("El sitio web es obligatorio");
			}
			
			// Crear institución
			port.altaInstitucion(nombre.trim(), descripcion.trim(), url.trim());
			
			// Éxito - redirigir al home con mensaje
			response.sendRedirect(request.getContextPath() + "/?success=institucion-creada");
			
		} catch (BusinessException_Exception e) {
			// Error - mostrar formulario con mensaje
			request.setAttribute("error", e.getFaultInfo().getMessage());
			request.setAttribute("nombre", nombre);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("url", url);
			
			request.getRequestDispatcher("/WEB-INF/web/instituciones/form.jsp")
				.forward(request, response);
		} catch (Exception e) {
			// Validation errors
			request.setAttribute("error", e.getMessage());
			request.setAttribute("nombre", nombre);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("url", url);
			
			request.getRequestDispatcher("/WEB-INF/web/instituciones/form.jsp")
				.forward(request, response);
		}
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

