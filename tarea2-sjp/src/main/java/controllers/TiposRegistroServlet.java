package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.OrganizadorDTO;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.TipoRegistroDTO;
import publicar.ws.client.TipoRegistroDuplicadoException_Exception;

/**
 * UC-S10: Consulta de Tipo Registro
 * UC-S12: Alta de Tipo Registro
 * Servlet implementation class TiposRegistroServlet
 */
@WebServlet("/tipos-registro")
public class TiposRegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;
       
    public TiposRegistroServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String evento = request.getParameter("evento");
		String edicion = request.getParameter("edicion");
		String nombre = request.getParameter("nombre");
		
		// UC-S12: Alta de Tipo Registro
		if ("new".equals(action)) {
			HttpSession session = request.getSession(false);
			if (session == null || !"Organizador".equals(session.getAttribute("tipo_usuario"))) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
				return;
			}
			
			if ("POST".equals(request.getMethod())) {
				handleCrearTipoRegistro(request, response);
			} else {
				// Mostrar formulario
				String edicionParam = request.getParameter("edicion");
				
				// Cargar ediciones del organizador
				try {
					OrganizadorDTO org = (OrganizadorDTO) session.getAttribute("usuario_logueado");
					
					// Obtener ediciones organizadas por este organizador
					String[] nombresEdicionesArray = WSTypeConverter.toStringArray(port.obtenerEdicionesOrganizadasPor(org.getNickname()));
					java.util.List<EdicionDTO> ediciones = new java.util.ArrayList<>();
					
					for (String nombreEdicion : nombresEdicionesArray) {						
						ediciones.add(port.buscarEdicionDTO(nombreEdicion));
					}
					
					request.setAttribute("ediciones", ediciones);
					request.setAttribute("edicionSeleccionada", edicionParam);
				} catch (Exception e) {
					// Continuar
				}
				
				request.getRequestDispatcher("/WEB-INF/web/tipos-registro/form.jsp")
					.forward(request, response);
			}
			return;
		}
		
		// UC-S10: Consulta de Tipo Registro
		if (evento != null && !evento.trim().isEmpty() &&
		    edicion != null && !edicion.trim().isEmpty() && 
		    nombre != null && !nombre.trim().isEmpty()) {
			consultarTipoRegistro(request, response, evento, edicion, nombre);
			return;
		}
		
		// Redirect al home si no hay parámetros
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void handleCrearTipoRegistro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String edicionNombre = request.getParameter("edicion");
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		String costoStr = request.getParameter("costo");
		String cupoStr = request.getParameter("cupo");
		
		try {
		// Validaciones
		if (edicionNombre == null || edicionNombre.trim().isEmpty()) {
			throw new IllegalArgumentException("Debe seleccionar una edición");
		}
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del tipo de registro es obligatorio");
		}
		if (descripcion == null || descripcion.trim().isEmpty()) {
			throw new IllegalArgumentException("La descripción es obligatoria");
		}
		
		// Verificar que el organizador sea el dueño de la edición
		HttpSession session = request.getSession(false);
		OrganizadorDTO organizador = (OrganizadorDTO) session.getAttribute("usuario_logueado");
		if (organizador == null) {
			throw new IllegalArgumentException("Debe iniciar sesión como organizador");
		}
		
	// Obtener la edición y verificar permisos
	EdicionDTO edicionDTO;
	try {
		edicionDTO = port.buscarEdicionDTO(edicionNombre);
	} catch (Exception e) {
		throw new IllegalArgumentException("Error al buscar la edición: " + e.getMessage());
	}
	if (edicionDTO == null) {
		throw new IllegalArgumentException("Edición no encontrada");
	}
	
	// Validar que la edición pertenece al organizador logueado
	if (!organizador.getNickname().equals(edicionDTO.getOrganizador())) {
		throw new IllegalArgumentException("No tienes permisos para crear tipos de registro en esta edición");
	}
	
	BigDecimal costo = BigDecimal.ZERO;
	int cupo = 0;
	
	try {
		costo = new BigDecimal(costoStr);
		if (costo.compareTo(BigDecimal.ZERO) < 0) throw new NumberFormatException();
	} catch (NumberFormatException e) {
		throw new IllegalArgumentException("El costo debe ser un número válido mayor o igual a 0");
	}
	try {
		cupo = Integer.parseInt(cupoStr);
		if (cupo < 1) throw new NumberFormatException();
	} catch (NumberFormatException e) {
		throw new IllegalArgumentException("El cupo debe ser un número entero mayor a 0");
	}
		
		// Obtener nombre del evento desde la edición
		String eventoNombre = edicionDTO.getEvento();
		
	// Crear tipo de registro
	port.altaTipoRegistro(eventoNombre, edicionNombre, nombre.trim(), descripcion.trim(), WSTypeConverter.bigDecimalToString(costo), cupo);
		
		// Éxito - redirigir a la edición (URL-encode parameters to handle special characters)
		String encodedEdicion = URLEncoder.encode(edicionNombre, StandardCharsets.UTF_8);
		response.sendRedirect(request.getContextPath() + "/ediciones?id=" + encodedEdicion + "&success=tipo-creado");
			
		} catch (TipoRegistroDuplicadoException_Exception e) {
			// Error - tipo de registro duplicado
			request.setAttribute("error", "Ya existe un tipo de registro con ese nombre en esta edición");
			request.setAttribute("nombre", nombre);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("costo", costoStr);
			request.setAttribute("cupo", cupoStr);
			request.setAttribute("edicionSeleccionada", edicionNombre);
			
			// Recargar ediciones
			reloadEdiciones(request);
			
			request.getRequestDispatcher("/WEB-INF/web/tipos-registro/form.jsp")
				.forward(request, response);
		} catch (IllegalArgumentException e) {
			// Error - argumento inválido
			request.setAttribute("error", e.getMessage());
			request.setAttribute("nombre", nombre);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("costo", costoStr);
			request.setAttribute("cupo", cupoStr);
			request.setAttribute("edicionSeleccionada", edicionNombre);
			
			// Recargar ediciones
			reloadEdiciones(request);
			
			request.getRequestDispatcher("/WEB-INF/web/tipos-registro/form.jsp")
				.forward(request, response);
		}
	}
	
	/**
	 * Helper method to reload ediciones for the logged-in organizador
	 */
	private void reloadEdiciones(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			OrganizadorDTO org = (OrganizadorDTO) session.getAttribute("usuario_logueado");
			
			if (org != null) {
				// Obtener ediciones organizadas por este organizador
				String[] nombresEdicionesArray = WSTypeConverter.toStringArray(port.obtenerEdicionesOrganizadasPor(org.getNickname()));
				java.util.List<EdicionDTO> ediciones = new java.util.ArrayList<>();
				
				for (String nombreEdicion : nombresEdicionesArray) {
					try {
						EdicionDTO edicionDTO = port.buscarEdicionDTO(nombreEdicion);
						if (edicionDTO != null) {
							ediciones.add(edicionDTO);
						}
					} catch (Exception ex2) {
						// Si no se puede obtener una edición, continuar con las demás
					}
				}
				
				request.setAttribute("ediciones", ediciones);
			}
		} catch (Exception ex) {
			// Continuar sin ediciones
		}
	}
	
	private void consultarTipoRegistro(HttpServletRequest request, HttpServletResponse response, 
			String nombreEvento, String nombreEdicion, String nombreTipo)
			throws ServletException, IOException {
		
		try {
			// Buscar tipo de registro con cupo calculado correctamente
			TipoRegistroDTO tipoRegistro = port.buscarTipoRegistroDTO(nombreEdicion, nombreTipo);
			
			if (tipoRegistro == null) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/shared/errorPages/404.jsp")
					.include(request, response);
				return;
			}
			
			// Buscar la edición para obtener información adicional
			EdicionDTO edicion = port.buscarEdicionDTO(nombreEdicion);
			
			if (edicion == null) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/shared/errorPages/404.jsp")
					.include(request, response);
				return;
			}
			
			// Buscar el evento usando el parámetro proporcionado
			publicar.ws.client.EventoDTO evento = port.buscarEventoDTO(nombreEvento);
			
			if (evento == null) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/shared/errorPages/404.jsp")
					.include(request, response);
				return;
			}
			
			// Pasar datos a la vista
			request.setAttribute("tipoRegistro", tipoRegistro);
			request.setAttribute("edicion", edicion);
			request.setAttribute("evento", evento);
			
			request.getRequestDispatcher("/WEB-INF/web/tipos-registro/detail.jsp")
				.forward(request, response);
				
		} catch (Exception e) {
			request.setAttribute("error", "Error al cargar tipo de registro: " + e.getMessage());
			request.setAttribute("jakarta.servlet.error.exception", e);
			request.getRequestDispatcher("/WEB-INF/shared/errorPages/500.jsp")
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

