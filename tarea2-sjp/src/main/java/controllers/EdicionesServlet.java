package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.OrganizadorDTO;
import publicar.ws.client.EventoDTO;
import publicar.ws.client.AsistenteDTO;
import publicar.ws.client.RegistroDTO;
import publicar.ws.client.TipoRegistroDTO;
import publicar.ws.client.PatrocinioDTO;
import publicar.ws.client.BusinessException_Exception;
import publicar.ws.client.NombreEdicionException_Exception;

/**
 * UC-S9: Consulta de Edición
 * Servlet implementation class EdicionesServlet
 */
@WebServlet("/ediciones")
@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 5,       // 5 MB
	maxRequestSize = 1024 * 1024 * 10    // 10 MB
)
public class EdicionesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EdicionesServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String edicionNombre = request.getParameter("id");
		String eventoNombre = request.getParameter("evento");
		
		// UC-S8: Alta de Edición
		if ("new".equals(action)) {
			HttpSession session = request.getSession(false);
			if (session == null || !"Organizador".equals(session.getAttribute("tipo_usuario"))) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
				return;
			}
			
			if ("POST".equals(request.getMethod())) {
				handleCrearEdicion(request, response);
			} else {
				// Cargar TODOS los eventos del sistema
				// Un organizador puede crear ediciones de cualquier evento
				try {
					List<EventoDTO> eventos = WSTypeConverter.toEventoDTOList(port.listarEventosDTO());
					request.setAttribute("eventos", eventos);
				} catch (Exception e) {
					// Continuar sin eventos
				}
				request.getRequestDispatcher("/WEB-INF/web/ediciones/form.jsp")
					.forward(request, response);
			}
			return;
		}
		
		if (edicionNombre == null || edicionNombre.trim().isEmpty()) {
			// Redirect to home if no edition specified
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		
		try {
			// Buscar la edición como DTO (incluye el nombre del evento)
			// Usamos DTO porque Edicion no puede ver Evento directamente
			EdicionDTO edicionDTO = port.buscarEdicionDTO(edicionNombre);
			
			if (edicionDTO == null) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/shared/errorPages/404.jsp")
					.forward(request, response);
				return;
			}
			
			// Datos de la edición
			request.setAttribute("edicion", edicionDTO);
			if (edicionDTO.getFechaInicio() != null) {
				request.setAttribute("edicionFechaInicio", WSTypeConverter.formatDateString(edicionDTO.getFechaInicio()));
			}
			if (edicionDTO.getFechaFin() != null) {
				request.setAttribute("edicionFechaFin", WSTypeConverter.formatDateString(edicionDTO.getFechaFin()));
			}
			if (edicionDTO.getFechaAlta() != null) {
				request.setAttribute("edicionFechaAlta", WSTypeConverter.formatDateString(edicionDTO.getFechaAlta()));
			}
			
			// Check if registration is still open (before or on end date)
			boolean registroAbierto = false;
			if (edicionDTO.getFechaFin() != null) {
				try {
					java.time.LocalDate fechaFin = java.time.LocalDate.parse(edicionDTO.getFechaFin());
					java.time.LocalDate hoy = java.time.LocalDate.now();
					registroAbierto = !fechaFin.isBefore(hoy);
				} catch (Exception e) {
					request.setAttribute("edicionFechaAlta","AAAAAAAAAAAAAAAAAAA");
				}
			}
			request.setAttribute("registroAbierto", registroAbierto);
			String estadoEdicion = "Sin definir";
			try {
				if (WSTypeConverter.esActiva(edicionDTO)) {
					estadoEdicion = "En curso";
				} else if (WSTypeConverter.esFutura(edicionDTO)) {
					estadoEdicion = "Próxima";
				} else if (WSTypeConverter.esPasada(edicionDTO)) {
					estadoEdicion = "Finalizada";
				} else if (WSTypeConverter.estaEnCurso(edicionDTO)) {
					estadoEdicion = "En curso";
				}
			} catch (Exception ignored) {
			}
			request.setAttribute("edicionEstado", estadoEdicion);
			
			// Try to get event from parameter first (for direct navigation)
			if (eventoNombre == null || eventoNombre.trim().isEmpty()) {
				// Fallback to getting it from edicionDTO
				eventoNombre = edicionDTO.getEvento();
			}
			
			if (eventoNombre != null) {
				EventoDTO eventoDTO = port.buscarEventoDTO(eventoNombre);
				request.setAttribute("evento", eventoDTO);
			}
			
			// Obtener el organizador
			String organizador = edicionDTO.getOrganizador();
			request.setAttribute("organizador", organizador);
			if (organizador != null) {
				try {
					OrganizadorDTO organizadorDTO = port.buscarOrganizadorDTO(organizador);
					request.setAttribute("organizadorDTO", organizadorDTO);
				} catch (Exception e) {
					// Ignorar si no se puede cargar el DTO
				}
			}
			
			// Obtener tipos de registro y patrocinios
			request.setAttribute("tiposRegistro", edicionDTO.getTiposRegistro());
			request.setAttribute("patrocinios", edicionDTO.getPatrocinios());
			
			// Verificar si el usuario está logueado y es asistente
			HttpSession session = request.getSession(false);
			if (session != null) {
				Object usuarioLogueado = session.getAttribute("usuario_logueado");
				String tipoUsuario = (String) session.getAttribute("tipo_usuario");
				
				// Si es asistente, verificar si tiene registro en esta edición
				if ("Asistente".equals(tipoUsuario) && usuarioLogueado instanceof AsistenteDTO) {
					AsistenteDTO asistente = (AsistenteDTO) usuarioLogueado;
					
				// Buscar si el asistente tiene registro en esta edición
				RegistroDTO miRegistro = null;
				TipoRegistroDTO miTipoRegistro = null;
				
				try {
					// Use WS method to get properly typed registros
					RegistroDTO[] registrosArray = WSTypeConverter.toRegistroDTOArray(
						port.getRegistrosPorAsistentes(asistente.getNickname())
					);
					
					if (registrosArray != null) {
						for (RegistroDTO registro : registrosArray) {
							// Check if this registro belongs to the current edition
							if (registro.getNombreEdicion() != null && 
							    registro.getNombreEdicion().equals(edicionNombre)) {
								miRegistro = registro;
								miTipoRegistro = registro.getTipoRegistro();
								break;
							}
						}
					}
				} catch (Exception e) {
					// No tiene registros o error al cargarlos
				}
				
				if (miRegistro != null) {
					request.setAttribute("miRegistro", miRegistro);
					if (miTipoRegistro != null) {
						request.setAttribute("miTipoRegistro", miTipoRegistro);
					}
				}
				}
				
				// Si es organizador, verificar si es el organizador de esta edición
				if ("Organizador".equals(tipoUsuario) && usuarioLogueado instanceof OrganizadorDTO) {
					OrganizadorDTO organizadorUsuario = (OrganizadorDTO) usuarioLogueado;
					
					// Si es el organizador de esta edición, cargar los registros
					if (organizador != null && organizador.equals(organizadorUsuario.getNickname())) {
						try {
							RegistroDTO[] registrosEdicion = WSTypeConverter.toRegistroDTOArray(
								port.obtenerRegistrosDeEdicionDTO(edicionNombre)
							);
							request.setAttribute("registrosEdicion", registrosEdicion);
						} catch (Exception e) {
							// Error al cargar registros, ignorar
						}
					}
				}
			}
			
			// Forward a la vista
			request.getRequestDispatcher("/WEB-INF/web/ediciones/detail.jsp")
				.forward(request, response);
				
		} catch (Exception e) {
			request.setAttribute("error", "Error al cargar edición: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/shared/errorPages/500.jsp")
				.forward(request, response);
		}
	}

	/**
	 * UC-S8: Procesar alta de edición
	 */
	private void handleCrearEdicion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String eventoNombre = request.getParameter("evento");
		String nombre = request.getParameter("nombre");
		String sigla = request.getParameter("sigla");
		String ciudad = request.getParameter("ciudad");
		String pais = request.getParameter("pais");
		String fechaInicioStr = request.getParameter("fechaInicio");
		String fechaFinStr = request.getParameter("fechaFin");
		
		try {
			// Validaciones
			if (eventoNombre == null || eventoNombre.trim().isEmpty()) {
				throw new IllegalArgumentException("Debe seleccionar un evento");
			}
			if (nombre == null || nombre.trim().isEmpty()) {
				throw new IllegalArgumentException("El nombre de la edición es obligatorio");
			}
			if (sigla == null || sigla.trim().isEmpty()) {
				throw new IllegalArgumentException("La sigla es obligatoria");
			}
			if (ciudad == null || ciudad.trim().isEmpty()) {
				throw new IllegalArgumentException("La ciudad es obligatoria");
			}
			if (pais == null || pais.trim().isEmpty()) {
				throw new IllegalArgumentException("El país es obligatorio");
			}
			if (fechaInicioStr == null || fechaInicioStr.trim().isEmpty()) {
				throw new IllegalArgumentException("La fecha de inicio es obligatoria");
			}
			if (fechaFinStr == null || fechaFinStr.trim().isEmpty()) {
				throw new IllegalArgumentException("La fecha de fin es obligatoria");
			}
			
			// Parsear fechas
			java.time.LocalDate fechaInicio = java.time.LocalDate.parse(fechaInicioStr);
			java.time.LocalDate fechaFin = java.time.LocalDate.parse(fechaFinStr);
			
			// Validar que fecha fin no sea anterior a fecha inicio
			if (fechaFin.isBefore(fechaInicio)) {
				throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
			}
			
			// Obtener organizador de la sesión
			HttpSession session = request.getSession();
			OrganizadorDTO organizador = (OrganizadorDTO) session.getAttribute("usuario_logueado");
			
		// Procesar imagen si existe
		String imagenFileName = processImageUpload(request);
		String imagen = (imagenFileName != null) ? imagenFileName : "-";
		
		// Crear edición
		port.altaEdicionEvento(nombre.trim(), sigla.trim(), WSTypeConverter.localDateToString(fechaInicio), WSTypeConverter.localDateToString(fechaFin), organizador.getNickname(), ciudad.trim(), pais.trim(), eventoNombre.trim(), imagen, "-");
			
			// Éxito - redirigir al evento con mensaje
			response.sendRedirect(request.getContextPath() + "/?id=" + eventoNombre + "&success=edicion-creada");
			
		} catch (IllegalArgumentException | NombreEdicionException_Exception e) {
			// Error - mostrar formulario con mensaje
			request.setAttribute("error", e.getMessage());
			request.setAttribute("nombre", nombre);
			request.setAttribute("sigla", sigla);
			request.setAttribute("ciudad", ciudad);
			request.setAttribute("pais", pais);
			request.setAttribute("fechaInicio", fechaInicioStr);
			request.setAttribute("fechaFin", fechaFinStr);
			request.setAttribute("eventoSeleccionado", eventoNombre);
			
			// Recargar TODOS los eventos del sistema
			try {
				List<EventoDTO> eventos = WSTypeConverter.toEventoDTOList(port.listarEventosDTO());
				request.setAttribute("eventos", eventos);
			} catch (Exception ex) {
				// Continuar
			}
			
			request.getRequestDispatcher("/WEB-INF/web/ediciones/form.jsp")
				.forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Error al crear la edición: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/shared/errorPages/500.jsp")
				.forward(request, response);
		}
	}
	
	/**
	 * Procesa la carga de imagen desde el formulario
	 */
	private String processImageUpload(HttpServletRequest request) throws ServletException, IOException {
		Part imagenPart = request.getPart("imagen");
		
		// Si no hay archivo, retornar null
		if (imagenPart == null || imagenPart.getSize() == 0) {
			return null;
		}
		
		// Obtener nombre del archivo
		String fileName = Paths.get(imagenPart.getSubmittedFileName()).getFileName().toString();
		
		// Validar extensión
		String extension = "";
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			extension = fileName.substring(lastDot).toLowerCase();
		}
		
		if (!extension.matches("\\.(jpg|jpeg|png|gif)")) {
			throw new IOException("Formato de imagen no válido. Use JPG, PNG o GIF");
		}
		
		// Generar nombre único para evitar conflictos
		String uniqueFileName = UUID.randomUUID().toString() + extension;
		
		// Obtener ruta del directorio de imágenes
		String uploadPath = getServletContext().getRealPath("") + File.separator + "media" + File.separator + "images";
		
		// Crear directorio si no existe
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		// Guardar archivo
		String filePath = uploadPath + File.separator + uniqueFileName;
		imagenPart.write(filePath);
		
		return uniqueFileName;
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}

}

