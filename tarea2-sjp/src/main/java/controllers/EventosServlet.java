package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import publicar.ws.client.EventoDTO;
import publicar.ws.client.CategoriaDTO;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.OrganizadorDTO;
import publicar.ws.client.InstitucionDTO;
import publicar.ws.client.BusinessException_Exception;

/**
 * UC-S6: Alta de Evento
 * UC-S7: Consulta de Evento
 * Servlet para gestión de eventos
 */
@WebServlet("/eventos")
@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 5,       // 5 MB
	maxRequestSize = 1024 * 1024 * 10    // 10 MB
)
public class EventosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventosServlet() {
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
		
		// Set character encoding to handle special characters (e.g., ñ, ó, etc.)
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		
		// Cargar datos desde CSV
		if ("cargar-datos".equals(action)) {
			if ("POST".equals(request.getMethod())) {
				cargarDatosDesdeCSV(request, response);
				return;
			} 
		}
		
		// UC-S6: Alta de Evento (solo organizadores)
		if ("new".equals(action)) {
			HttpSession session = request.getSession(false);
			if (session == null || !"Organizador".equals(session.getAttribute("tipo_usuario"))) {
				response.sendRedirect(request.getContextPath() + "/iniciar-sesion");
				return;
			}
			
			if ("POST".equals(request.getMethod())) {
				handleCrearEvento(request, response);
			} else {
				// Cargar categorías para el formulario
				try {
					List<CategoriaDTO> categorias = WSTypeConverter.toCategoriaDTOList(port.listarCategoriasDTO());
					request.setAttribute("categorias", categorias);
				} catch (Exception e) {
					// Continuar sin categorías
				}
				request.getRequestDispatcher("/WEB-INF/web/eventos/form.jsp")
					.forward(request, response);
			}
			return;
		}
		
		// UC-S7: Consulta de Evento específico
		if (id != null && !id.trim().isEmpty()) {
			consultarEvento(request, response, id);
			return;
		}
		
		// UC-S7: Listar todos los eventos (INDEX)
		listarEventos(request, response);
	}
	
	/**
	 * UC-S7: Listar todos los eventos (para el index)
	 */
	private void listarEventos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// Obtener todos los eventos como DTOs
			List<EventoDTO> eventos = WSTypeConverter.toEventoDTOList(port.listarEventosDTO());
			
			// Filtrar por categoría si se especifica
			String categoriaFiltro = request.getParameter("categoria");
			if (categoriaFiltro != null && !categoriaFiltro.trim().isEmpty()) {
				// Normalizar el nombre de la categoría para comparación (sin acentos)
				String categoriaNormalizada = normalizarTexto(categoriaFiltro.trim());
				
				eventos = eventos.stream()
					.filter(evento -> {
						if (evento.getCategorias() != null) {
							return evento.getCategorias().stream()
								.anyMatch(cat -> normalizarTexto(cat.getNombre()).equals(categoriaNormalizada));
						}
						return false;
					})
					.collect(java.util.stream.Collectors.toList());
				
				// Pasar el filtro aplicado a la vista
				request.setAttribute("categoriaFiltro", categoriaFiltro);
			}
			
			// Pasar los eventos a la vista
			request.setAttribute("eventos", eventos);
			
			// Forward al JSP
			request.getRequestDispatcher("/WEB-INF/web/eventos/list.jsp")
				.forward(request, response);
				
		} catch (Exception e) {
			request.setAttribute("error", "Error al cargar los eventos: " + e.getMessage());
			request.setAttribute("jakarta.servlet.error.exception", e);
			request.getRequestDispatcher("/WEB-INF/shared/errorPages/500.jsp")
				.forward(request, response);
		}
	}
	
	/**
	 * Normaliza texto para comparación sin acentos ni diferencias de mayúsculas
	 */
	private String normalizarTexto(String texto) {
		if (texto == null) return "";
		
		// Convertir a minúsculas y reemplazar caracteres acentuados
		return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			.toLowerCase()
			.trim();
	}
	
	/**
	 * UC-S7: Consultar detalle de un evento específico
	 */
	private void consultarEvento(HttpServletRequest request, HttpServletResponse response, String nombreEvento)
			throws ServletException, IOException {
		
		try {
			// Buscar el evento
			EventoDTO evento = port.buscarEventoDTO(nombreEvento);
			
			if (evento == null) {
				response.sendError(404);
				request.getRequestDispatcher("/WEB-INF/shared/errorPages/404.jsp")
					.include(request, response);
				return;
			}
			
		// Obtener ediciones del evento
		List<EdicionDTO> ediciones = WSTypeConverter.toEdicionDTOList(port.listarEdicionesDeEventoDTO(nombreEvento));
		System.out.println("Ediciones: " + ediciones.stream().map(EdicionDTO::getEstado).collect(java.util.stream.Collectors.toList()));
		// Filtrar ediciones según rol del usuario
		HttpSession session = request.getSession(false);
		Object usuarioLogueado = (session != null) ? session.getAttribute("usuario_logueado") : null;
		String tipoUsuario = (session != null) ? (String) session.getAttribute("tipo_usuario") : null;
		
		if (usuarioLogueado != null && "Organizador".equals(tipoUsuario)) {
			// Organizador ve TODAS sus ediciones (Ingresada, Aceptada, Rechazada) or "ACEPTADA"
			OrganizadorDTO organizador = (OrganizadorDTO) usuarioLogueado;
			String nicknameOrganizador = organizador.getNickname();
			ediciones = ediciones.stream()
				.filter(ed -> ed != null && ed.getEstado() != null && (
							  (ed.getOrganizador() != null && nicknameOrganizador != null && 
							   nicknameOrganizador.equals(ed.getOrganizador())) ||
							  "ACEPTADA".equalsIgnoreCase(ed.getEstado())
				))
				.collect(java.util.stream.Collectors.toList());
		} else {
			// Visitante/Asistente: solo ediciones Aceptadas
			ediciones = ediciones.stream()
				.filter(ed -> ed != null && ed.getEstado() != null && 
							  "ACEPTADA".equalsIgnoreCase(ed.getEstado()))
				.collect(java.util.stream.Collectors.toList());
		}

			
			// Pasar datos a la vista
			request.setAttribute("evento", evento);
			request.setAttribute("ediciones", ediciones);
			request.setAttribute("tipoUsuario", tipoUsuario);
			
			// Forward al JSP de detalle
			request.getRequestDispatcher("/WEB-INF/web/eventos/detail.jsp")
				.forward(request, response);
				
		} catch (Exception e) {
			request.setAttribute("error", "Error al consultar el evento: " + e.getMessage());
			request.setAttribute("jakarta.servlet.error.exception", e);
			request.getRequestDispatcher("/WEB-INF/shared/errorPages/500.jsp")
				.forward(request, response);
		}
	}

	/**
	 * UC-S6: Procesar alta de evento
	 */
	private void handleCrearEvento(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		String sigla = request.getParameter("sigla");
		String[] categoriasSeleccionadas = request.getParameterValues("categorias");
		
		try {
			// Validaciones
			if (nombre == null || nombre.trim().isEmpty()) {
				throw new BusinessException_Exception("El nombre del evento es obligatorio", new publicar.ws.client.BusinessException());
			}
			if (descripcion == null || descripcion.trim().isEmpty()) {
				throw new BusinessException_Exception("La descripción es obligatoria", new publicar.ws.client.BusinessException());
			}
			if (sigla == null || sigla.trim().isEmpty()) {
				throw new BusinessException_Exception("La sigla es obligatoria", new publicar.ws.client.BusinessException());
			}
			if (categoriasSeleccionadas == null || categoriasSeleccionadas.length == 0) {
				throw new BusinessException_Exception("Debe seleccionar al menos una categoría", new publicar.ws.client.BusinessException());
			}
			
			// Convertir array de categorías a colección
			java.util.List<CategoriaDTO> categorias = java.util.Arrays.stream(categoriasSeleccionadas)
				.map(WSTypeConverter::createCategoriaDTO)
				.collect(java.util.stream.Collectors.toList());
			
			// Procesar imagen si existe
			String imagenFileName = processImageUpload(request);
			
		// Crear evento (con o sin imagen)
		String imagen = (imagenFileName != null) ? imagenFileName : "-";
		port.altaEvento(nombre.trim(), sigla.trim(), descripcion.trim(), WSTypeConverter.toCategoriaDTOArray(categorias), imagen);
			
			// Éxito - redirigir al home con mensaje
			response.sendRedirect(request.getContextPath() + "/?success=evento-creado");
			
		} catch (BusinessException_Exception e) {
			// Error - mostrar formulario con mensaje
			request.setAttribute("error", e.getMessage());
			request.setAttribute("nombre", nombre);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("sigla", sigla);
			
			// Recargar categorías
			try {
				List<CategoriaDTO> categorias = WSTypeConverter.toCategoriaDTOList(port.listarCategoriasDTO());
				request.setAttribute("categorias", categorias);
				request.setAttribute("categoriasSeleccionadas", categoriasSeleccionadas);
			} catch (Exception ex) {
				// Continuar
			}
			
			request.getRequestDispatcher("/WEB-INF/web/eventos/form.jsp")
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
 * Cargar datos desde archivos CSV en resources
 */
private void cargarDatosDesdeCSV(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    StringBuilder resultado = new StringBuilder();
    int errores = 0;
    
    try {
        String csvFolder = "datos-prueba-tarea3-csv-v1_0";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Maps to store CSV data for efficient lookups
        Map<String, String[]> categoriasMap = new HashMap<>();
        Map<String, String[]> institucionesMap = new HashMap<>();
        Map<String, String[]> usuariosMap = new HashMap<>();
        Map<String, String[]> usuariosAsistentesMap = new HashMap<>();
        Map<String, String[]> usuariosOrganizadoresMap = new HashMap<>();
        Map<String, String[]> eventosMap = new HashMap<>();
        Map<String, String[]> edicionesMap = new HashMap<>();
        Map<String, String[]> tiposRegistroMap = new HashMap<>();
        Map<String, String[]> patrociniosMap = new HashMap<>();
        Map<String, String[]> registrosMap = new HashMap<>();
        Map<String, String[]> seguidoresMap = new HashMap<>();
        
        // Map to store eventos that need to be finalized (after all editions are loaded)
        Map<String, Integer> eventosAFinalizar = new HashMap<>();
        
        // Load all CSV files into maps for reference lookups
        try {
            categoriasMap = cargarCSVMap(csvFolder + "/2025Categorias.csv");
            institucionesMap = cargarCSVMap(csvFolder + "/2025Instituciones.csv");
            usuariosMap = cargarCSVMap(csvFolder + "/2025Usuarios.csv");
            usuariosAsistentesMap = cargarCSVMap(csvFolder + "/2025Usuarios-Asistentes.csv");
            usuariosOrganizadoresMap = cargarCSVMap(csvFolder + "/2025Usuarios-Organizadores.csv");
            eventosMap = cargarCSVMap(csvFolder + "/2025Eventos.csv");
            edicionesMap = cargarCSVMap(csvFolder + "/2025EdicionesEventos.csv");
            tiposRegistroMap = cargarCSVMap(csvFolder + "/2025TipoRegistro.csv");
            patrociniosMap = cargarCSVMap(csvFolder + "/2025Patrocinios.csv");
            registrosMap = cargarCSVMap(csvFolder + "/2025Registros.csv");
            seguidoresMap = cargarCSVMap(csvFolder + "/2025SeguidoresSeguidos.csv");
            
            resultado.append("✓ Archivos CSV cargados en memoria<br>");
            
        } catch (Exception e) {
            resultado.append("✗ Error al cargar archivos CSV: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 1. Cargar Categorías
        try {
            for (String[] cat : categoriasMap.values()) {
                
                    try {
                        port.altaCategoria(cat[1].trim());
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar categoría: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
            }
            resultado.append("✓ Categorías cargadas<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar categorías: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 2. Cargar Instituciones
        try {
            for (String[] inst : institucionesMap.values()) {
                
                    try {
                        String nombre = inst[1].trim();
                        String descripcion = inst[2].trim();
                        String sitioWeb = inst[3].trim();
                        port.altaInstitucion(nombre, descripcion, sitioWeb);
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar institución: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
                }
            resultado.append("✓ Instituciones cargadas<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar instituciones: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        // 3. Cargar Usuarios base
        try {
            for (String[] usr : usuariosMap.values()) {
                    String ref = usr[0].trim();
                    String tipo = usr[1].trim();
                    String nickname = usr[2].trim();
                    String nombre = usr[3].trim();
                    String correo = usr[4].trim();
                    String contrasenia = usr[5].trim();
                    String imagen = usr[6].trim();
             
                    try {
                        if ("A".equals(tipo)) {
                            // Es asistente - buscar datos adicionales
                            String[] datosAsistente = usuariosAsistentesMap.get(ref);
                         
           
                                String apellido = datosAsistente[1].trim();
                                LocalDate fechaNac = LocalDate.parse(datosAsistente[2].trim(), formatter);
                                String instRef = "";
                                if (datosAsistente.length >= 4) {
                                 instRef = datosAsistente[3].trim();

                                }else {
                                    instRef = "";
                                }
                            
                             
                                String inst = "-";
                                if (!instRef.isEmpty() && !"-".equals(instRef)) {
                                    String[] instData = institucionesMap.get(instRef);
                                    if (instData != null && instData.length > 1) {
                                        inst = instData[1].trim();
                                    }
                                }
                                port.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, WSTypeConverter.localDateToString(fechaNac), inst);
                            
                        } else if ("O".equals(tipo)) {
                            // Es organizador - buscar datos adicionales
                            String[] datosOrg = usuariosOrganizadoresMap.get(ref);
                            String descripcion = "";
                            String sitioWeb = "";
                          
                                descripcion = datosOrg[1].trim();
                                if (datosOrg.length >= 3) {
                                    sitioWeb = datosOrg[2].trim();
                                }else {
                                    sitioWeb = "";
                                }
                            
                            port.altaOrganizador(nickname, nombre, correo, contrasenia, imagen, descripcion, sitioWeb);
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar usuario ").append(nickname).append(": ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
                }
            resultado.append("✓ Usuarios cargados<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar usuarios: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 3.5. Cargar Seguidores/Seguidos relationships
        try {
            for (Map.Entry<String, String[]> entry : seguidoresMap.entrySet()) {
                String[] datos = entry.getValue();
                // CSV format: Ref;RefSeguidor;NickNameSeguidor;RefSeguido;NickNameSeguido
                if (datos.length >= 5) {
                    String nicknameSeguidor = datos[2].trim();
                    String nicknameSeguido = datos[4].trim();
                    try {
                        port.seguirUsuario(nicknameSeguidor, nicknameSeguido);
                    } catch (Exception e) {
                        // Ignore if already following or users don't exist
                    }
                }
            }
            resultado.append("✓ Relaciones de seguidores cargadas<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar seguidores: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 4. Cargar Eventos
        try {
            for (String[] evt : eventosMap.values()) {
                    try {
                        String nombre = evt[1].trim();
                        String descripcion = evt[2].trim();
                        String sigla = evt[3].trim();
                        String fechaAltaStr = evt[4].trim();
                        String[] categoriaRefs = evt[5].trim().split(",");
                        String imagen = evt[6].trim();
                        String finalizado = evt.length > 7 ? evt[7].trim() : "No";
                        int visitas = evt.length > 8 ? Integer.parseInt(evt[8].trim()) : 0;
                        
                        // Parse fechaAlta from CSV
                        LocalDate fechaAlta = LocalDate.parse(fechaAltaStr, formatter);
                        
                        List<CategoriaDTO> listaCategorias = new ArrayList<>();
                        for (String catRef : categoriaRefs) {
                            String[] catData = categoriasMap.get(catRef.trim());
                                
                                listaCategorias.add(WSTypeConverter.createCategoriaDTO(catData[1].trim()));
                            
                        }
                        
                        // Use the new web service method that accepts fechaAlta
                        port.altaEventoConFechaAlta(nombre, sigla, descripcion, WSTypeConverter.toCategoriaDTOArray(listaCategorias), imagen, WSTypeConverter.localDateToString(fechaAlta));
                        
                        // Almacenar eventos que necesitan ser finalizados (se hará después de cargar ediciones)
                        if ("Si".equalsIgnoreCase(finalizado)) {
                            eventosAFinalizar.put(nombre, visitas);
                        } else {
                            // Establecer visitas inmediatamente si el evento NO está finalizado
                            if (visitas > 0) {
                                port.setVisitasEvento(nombre, visitas);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar evento: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
            }
            resultado.append("✓ Eventos cargados<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar eventos: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 5. Cargar Ediciones
        try {
            for (String[] ed : edicionesMap.values()) {
                    try {
                        String eventoRef = ed[1].trim();
                        String organizadorRef = ed[2].trim();
                        String nombre = ed[3].trim();
                        String sigla = ed[4].trim();
                        String ciudad = ed[5].trim();
                        String pais = ed[6].trim();
                        LocalDate fechaInicio = LocalDate.parse(ed[7].trim(), formatter);
                        LocalDate fechaFin = LocalDate.parse(ed[8].trim(), formatter);
                        LocalDate fechaAlta = LocalDate.parse(ed[9].trim(), formatter);
                        String estado = ed[10].trim();
                        String imagen = ed[11].trim();
                        String[] eventoData = eventosMap.get(eventoRef);
                        if (eventoData != null && eventoData.length >= 2) {
                            String nombreEvento = eventoData[1].trim();
                            
                            String[] orgData = usuariosMap.get(organizadorRef);
                            if (orgData != null && orgData.length >= 3) {
                                String nicknameOrganizador = orgData[2].trim();
                    
                        
                                port.altaEdicionEventoConEstado(nombre, sigla, WSTypeConverter.localDateToString(fechaInicio), WSTypeConverter.localDateToString(fechaFin), WSTypeConverter.localDateToString(fechaAlta), nicknameOrganizador, ciudad, pais, nombreEvento, imagen, "-", estado);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar edición: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
            }
            resultado.append("✓ Ediciones cargadas<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar ediciones: ").append(e.getMessage()).append("<br>");
            errores++;
        }
    
        
        
        // 6. Cargar TipoRegistro
        try {
            for (String[] tr : tiposRegistroMap.values()) {
                    try {
                        String edicionRef = tr[1].trim();
                        String nombre = tr[2].trim();
                        String descripcion = tr[3].trim();
                        java.math.BigDecimal costo = new java.math.BigDecimal(tr[4].trim());
                        int cupo = Integer.parseInt(tr[5].trim());
                        
                        String[] edicionData = edicionesMap.get(edicionRef);
                        if (edicionData != null && edicionData.length >= 4) {
                            String nombreEdicion = edicionData[3].trim();
                            String eventoRef = edicionData[1].trim();
                            String[] eventoData = eventosMap.get(eventoRef);
                            if (eventoData != null && eventoData.length >= 2) {
                                String nombreEvento = eventoData[1].trim();
                                port.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, WSTypeConverter.bigDecimalToString(costo), cupo);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar tipo de registro: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
            }
            resultado.append("✓ Tipos de registro cargados<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar tipos de registro: ").append(e.getMessage()).append("<br>");
            errores++;
        }
      
        // 7. Cargar Patrocinios
        try {
            for (String[] pat : patrociniosMap.values()) {
                    try {
                        String edicionRef = pat[1].trim();
                        String institucionRef = pat[2].trim();
                        String nivel = pat[3].trim();
                        String tipoRegistroRef = pat[4].trim();
                        java.math.BigDecimal aporte = new java.math.BigDecimal(pat[5].trim());
                        int cantidadRegistros = Integer.parseInt(pat[7].trim());
                        String codigoPatrocinio = pat[8].trim();
                        
                        String[] edicionData = edicionesMap.get(edicionRef);
                        String[] institucionData = institucionesMap.get(institucionRef);
                        String[] tipoRegistroData = tiposRegistroMap.get(tipoRegistroRef);
                        
                        if (edicionData != null && edicionData.length >= 4 &&
                            institucionData != null && institucionData.length >= 2 &&
                            tipoRegistroData != null && tipoRegistroData.length >= 3) {
                            
                            String nombreEdicion = edicionData[3].trim();
                            String nombreInstitucion = institucionData[1].trim();
                            String nombreTipoRegistro = tipoRegistroData[2].trim();
                            
                            String eventoRef = edicionData[1].trim();
                            String[] eventoData = eventosMap.get(eventoRef);
                            if (eventoData != null && eventoData.length >= 2) {
                                String nombreEvento = eventoData[1].trim();
                                
                                port.altaPatrocinio(
                                    nombreEvento,
                                    nombreEdicion,
                                    nombreTipoRegistro,
                                    nombreInstitucion,
                                    nivel,
                                    WSTypeConverter.bigDecimalToString(aporte),
                                    cantidadRegistros,
                                    codigoPatrocinio
                                );
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar patrocinio: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
            }
            resultado.append("✓ Patrocinios cargados<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar patrocinios: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 8. Cargar Registros
        try {
            for (String[] reg : registrosMap.values()) {
                    try {
                        String usuarioRef = reg[1].trim();
                        String edicionRef = reg[2].trim();
                        String tipoRegistroRef = reg[3].trim();
                        // reg[4] = FechaRegistro (backend uses LocalDate.now())
                        // reg[5] = Costo (backend calculates based on patrocinio)
                        // reg[6] = Patrocinio (handled automatically by backend logic)
                        String asiste =  reg[7].trim();

                        
                        Boolean asistio = "S".equalsIgnoreCase(asiste);
                        
                        String[] usuarioData = usuariosMap.get(usuarioRef);
                        String[] edicionData = edicionesMap.get(edicionRef);
                        String[] tipoRegistroData = tiposRegistroMap.get(tipoRegistroRef);
                        
                        if (usuarioData != null && usuarioData.length >= 3 &&
                            edicionData != null && edicionData.length >= 4 &&
                            tipoRegistroData != null && tipoRegistroData.length >= 3) {
                            
                            String nicknameUsuario = usuarioData[2].trim();
                            String nombreEdicion = edicionData[3].trim();
                            String nombreTipoRegistro = tipoRegistroData[2].trim();
                            
                            String tipoUsuario = usuarioData[1].trim();
                            if ("A".equals(tipoUsuario)) {
                                port.registrarAsistenteConAsistente(
                                    nicknameUsuario,
                                    nombreTipoRegistro,
                                    nombreEdicion,
                                    asistio
                                );
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar registro: ").append(e.getMessage()).append("<br>");
                        errores++;
                    }
            }
            resultado.append("✓ Registros cargados<br>");
        } catch (Exception e) {
            resultado.append("✗ Error al cargar registros: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // 9. Finalizar eventos y establecer visitas (después de cargar todas las ediciones)
        try {
            for (Map.Entry<String, Integer> entry : eventosAFinalizar.entrySet()) {
                String nombreEvento = entry.getKey();
                int visitas = entry.getValue();
                
                try {
                    // Finalizar el evento
                    port.finalizarEvento(nombreEvento);
                    
                    // Establecer visitas
                    if (visitas > 0) {
                        port.setVisitasEvento(nombreEvento, visitas);
                    }
                } catch (Exception e) {
                    resultado.append("✗ Error al finalizar evento ").append(nombreEvento).append(": ").append(e.getMessage()).append("<br>");
                    errores++;
                }
            }
            if (!eventosAFinalizar.isEmpty()) {
                resultado.append("✓ Eventos finalizados<br>");
            }
        } catch (Exception e) {
            resultado.append("✗ Error al finalizar eventos: ").append(e.getMessage()).append("<br>");
            errores++;
        }
        
        // Resultado final
        if (errores == 0) {
            request.setAttribute("success", "✅ Todos los datos cargados exitosamente:<br><br>" + resultado.toString());
        } else {
            request.setAttribute("success", "⚠️ Datos cargados con " + errores + " errores:<br><br>" + resultado.toString());
        }
        
    } catch (Exception e) {
        request.setAttribute("error", "❌ Error general al cargar datos: " + e.getMessage());
    }
    
    // Redirigir a la lista de eventos
    listarEventos(request, response);
}

/**
 * Cargar archivo CSV desde resources y retornar como Map
 */
private Map<String, String[]> cargarCSVMap(String rutaArchivo) throws IOException {
    Map<String, String[]> mapa = new HashMap<>();
    
    try (InputStream inputStream = abrirStreamCSV(rutaArchivo)) {
        if (inputStream == null) {
            throw new IOException("Archivo no encontrado en resources: " + rutaArchivo);
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(";");
                if (valores.length > 0 && !valores[0].equals("Ref")) {
                    mapa.put(valores[0].trim(), valores);
                }
            }
        }
    }
    
    return mapa;
}

/**
 * Intentar abrir un recurso CSV considerando distintas ubicaciones posibles.
 */
private InputStream abrirStreamCSV(String rutaArchivo) throws IOException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream(rutaArchivo);
    
    if (stream == null) {
        stream = getClass().getClassLoader().getResourceAsStream("resources/" + rutaArchivo);
    }
    
    if (stream == null && getServletContext() != null) {
        stream = getServletContext().getResourceAsStream("/WEB-INF/classes/" + rutaArchivo);
    }
    
    if (stream == null && getServletContext() != null) {
        stream = getServletContext().getResourceAsStream("/WEB-INF/classes/resources/" + rutaArchivo);
    }
    
    if (stream == null) {
        java.nio.file.Path path = java.nio.file.Paths.get(rutaArchivo);
        if (java.nio.file.Files.exists(path)) {
            stream = java.nio.file.Files.newInputStream(path);
        }
    }
    
    return stream;
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

