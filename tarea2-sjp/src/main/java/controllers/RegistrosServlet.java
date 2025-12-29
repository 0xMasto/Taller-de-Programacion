package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.AsistenteDTO;
import publicar.ws.client.TipoRegistroDTO;
import publicar.ws.client.BusinessException_Exception;
import publicar.ws.client.RegistroDuplicadoException_Exception;

/**
 * UC-S12: Registro a Edición de Evento
 * Servlet implementation class RegistrosServlet
 */
@WebServlet("/registros")
public class RegistrosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;
       
    public RegistrosServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Set character encoding to handle special characters (e.g., ñ, ó, etc.)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		// View registro details
		if ("detail".equals(action)) {
			handleViewDetail(request, response);
			return;
		}
		
		// UC-S11: Registrarse a una edición
		if ("new".equals(action)) {
			HttpSession session = request.getSession(false);
			if (session == null || !"Asistente".equals(session.getAttribute("tipo_usuario"))) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
				return;
			}
			
			if ("POST".equals(request.getMethod())) {
				handleCrearRegistro(request, response);
			} else {
				// Mostrar formulario de registro
				String edicionParam = request.getParameter("edicion");
				
				try {
				// Cargar la edición si se especificó
				if (edicionParam != null && !edicionParam.trim().isEmpty()) {
					EdicionDTO edicion = port.buscarEdicionDTO(edicionParam);
						if (edicion != null) {
							request.setAttribute("edicion", edicion);
							request.setAttribute("tiposRegistro", edicion.getTiposRegistro());
							
							// Format dates for display
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							if (edicion.getFechaInicio() != null) {
								request.setAttribute("edicionFechaInicio", WSTypeConverter.formatDateString(edicion.getFechaInicio()));
							}
							if (edicion.getFechaFin() != null) {
								request.setAttribute("edicionFechaFin", WSTypeConverter.formatDateString(edicion.getFechaFin()));
							}
						}
					}
				} catch (Exception e) {
					// Continuar
				}
				
				request.getRequestDispatcher("/WEB-INF/web/registros/form.jsp")
					.forward(request, response);
			}
			return;
		}
		
		// Redirect al home si no hay acción
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void handleViewDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String edicionNombre = request.getParameter("edicion");
		String tipoRegistroNombre = request.getParameter("tipo");
		
		if (edicionNombre == null || edicionNombre.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/perfil");
			return;
		}
		
		// Get the logged user
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			return;
		}
		
		String nickname = (String) session.getAttribute("nickname");
		if (nickname == null) {
			response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
			return;
		}
		
		try {
			// Get all registros for this user
			publicar.ws.client.RegistroDTO[] registrosArray = WSTypeConverter.toRegistroDTOArray(port.getRegistrosPorAsistentes(nickname));
			
			// Find the specific registro
			publicar.ws.client.RegistroDTO registroEncontrado = null;
			for (publicar.ws.client.RegistroDTO registro : registrosArray) {
				if (registro.getEdicion() != null && 
					registro.getEdicion().getNombre().equals(edicionNombre)) {
					if (tipoRegistroNombre == null || 
						(registro.getTipoRegistro() != null && 
						 registro.getTipoRegistro().getNombre().equals(tipoRegistroNombre))) {
						registroEncontrado = registro;
						break;
					}
				}
			}
			
			if (registroEncontrado == null) {
				response.sendRedirect(request.getContextPath() + "/perfil");
				return;
			}
			
			// Get additional edicion details
			EdicionDTO edicion = port.buscarEdicionDTO(edicionNombre);
			if (edicion != null) {
				request.setAttribute("edicion", edicion);
				
				// Format dates
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				if (edicion.getFechaInicio() != null) {
					request.setAttribute("edicionFechaInicio", WSTypeConverter.formatDateString(edicion.getFechaInicio()));
				}
				if (edicion.getFechaFin() != null) {
					request.setAttribute("edicionFechaFin", WSTypeConverter.formatDateString(edicion.getFechaFin()));
				}
			}
			
			// Format registro date
			if (registroEncontrado.getFechaRegistro() != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				request.setAttribute("registroFecha", WSTypeConverter.formatDateString(registroEncontrado.getFechaRegistro()));
			}
			
			request.setAttribute("registro", registroEncontrado);
			request.getRequestDispatcher("/WEB-INF/web/registros/detail.jsp").forward(request, response);
			
		} catch (Exception e) {
			request.setAttribute("error", "Error al cargar el registro: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/perfil");
		}
	}
	
	private void handleCrearRegistro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String edicionNombre = request.getParameter("edicion");
		String tipoRegistroNombre = request.getParameter("tipoRegistro");
		String registrationMethod = request.getParameter("registrationMethod");
		String sponsorshipCode = request.getParameter("sponsorshipCode");
		
		try {
			// Validaciones
			if (edicionNombre == null || edicionNombre.trim().isEmpty()) {
				throw new Exception("La edición es obligatoria");
			}
			if (tipoRegistroNombre == null || tipoRegistroNombre.trim().isEmpty()) {
				throw new Exception("Debe seleccionar un tipo de registro");
			}
			
			// Validar código de patrocinio si se seleccionó ese método
			if ("sponsorship".equals(registrationMethod)) {
				if (sponsorshipCode == null || sponsorshipCode.trim().isEmpty()) {
					throw new Exception("Debe ingresar un código de patrocinio");
				}
				// TODO: Implementar validaciones del código según UC-S12:
				// 1. El código pertenece a esa edición
				// 2. El tipo de registro elegido coincide con el asociado al patrocinio
				// 3. El código es de la institución del asistente
				// 4. No se alcanzó el máximo de registros con código por institución-edición
				// 5. Existen cupos del tipo de registro
				// 6. El asistente no está ya registrado
			}
			
			// Obtener asistente de la sesión
			HttpSession session = request.getSession();
			String asistenteNickname = (String) session.getAttribute("nickname");
			
			// Crear registro
			port.registrarAsistente(
			    asistenteNickname,
			    tipoRegistroNombre,
			    edicionNombre);
			
			// Actualizar el AsistenteDTO en la sesión con los nuevos registros
			try {
				AsistenteDTO asistenteActualizado = port.buscarAsistenteDTO(asistenteNickname);
				if (asistenteActualizado != null) {
					session.setAttribute("usuario_logueado", asistenteActualizado);
				}
			} catch (Exception e) {
				// Si no se puede actualizar la sesión, continuar (el registro ya se creó)
			}
			
			// Obtener el nombre del evento para la redirección
			String eventoNombre = "";
			try {
				EdicionDTO edicion = port.buscarEdicionDTO(edicionNombre);
				if (edicion != null && edicion.getEvento() != null) {
					eventoNombre = edicion.getEvento();
				}
			} catch (Exception e) {
				// Continuar sin el nombre del evento
			}
			
			// Éxito - redirigir a la edición con mensaje
			// URL encode los parámetros para manejar caracteres especiales (ñ, ó, etc.)
			String encodedEdicion = URLEncoder.encode(edicionNombre, StandardCharsets.UTF_8);
			String encodedEvento = URLEncoder.encode(eventoNombre, StandardCharsets.UTF_8);
			response.sendRedirect(request.getContextPath() + "/ediciones?id=" + encodedEdicion + 
			                      "&evento=" + encodedEvento + "&success=registro-creado");
			
		} catch (RegistroDuplicadoException_Exception e) {
		    // Usuario ya registrado - mostrar formulario con error
		    request.setAttribute("error", "Ya estás registrado en esta edición");
		    reloadFormData(request, edicionNombre, tipoRegistroNombre);
		    request.getRequestDispatcher("/WEB-INF/web/registros/form.jsp").forward(request, response);
		    
		} catch (BusinessException_Exception e) {
			// Error de validación - mostrar formulario con mensaje
			request.setAttribute("error", e.getFaultInfo().getMessage());
			reloadFormData(request, edicionNombre, tipoRegistroNombre);
			request.getRequestDispatcher("/WEB-INF/web/registros/form.jsp").forward(request, response);
			
		} catch (Exception e) {
		    // Otros errores - mostrar formulario con error genérico
		    request.setAttribute("error", "Error en el registro: " + e.getMessage());
		    reloadFormData(request, edicionNombre, tipoRegistroNombre);
		    request.getRequestDispatcher("/WEB-INF/web/registros/form.jsp").forward(request, response);
		}
	}
	
	private void reloadFormData(HttpServletRequest request, String edicionNombre, String tipoRegistroSeleccionado) {
		request.setAttribute("tipoRegistroSeleccionado", tipoRegistroSeleccionado);
		try {
			EdicionDTO edicion = port.buscarEdicionDTO(edicionNombre);
			if (edicion != null) {
				request.setAttribute("edicion", edicion);
				request.setAttribute("tiposRegistro", edicion.getTiposRegistro());
				
				// Format dates for display
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				if (edicion.getFechaInicio() != null) {
					request.setAttribute("edicionFechaInicio", WSTypeConverter.formatDateString(edicion.getFechaInicio()));
				}
				if (edicion.getFechaFin() != null) {
					request.setAttribute("edicionFechaFin", WSTypeConverter.formatDateString(edicion.getFechaFin()));
				}
			}
		} catch (Exception ex) {
			// Continuar sin datos de edición
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

