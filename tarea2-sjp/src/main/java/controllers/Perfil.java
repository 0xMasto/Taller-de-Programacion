package controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ws.WSClientHelper;
import publicar.ws.client.WebServices;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.AsistenteDTO;
import publicar.ws.client.OrganizadorDTO;
import publicar.ws.client.RegistroDTO;
import publicar.ws.client.EventoDTO;
import model.EstadoSesion;

import static ws.WSTypeConverter.*;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/perfil")
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private WebServices port;

	public Perfil() {
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

		Object usuario = request.getSession().getAttribute("usuario_logueado");
		if (usuario == null) {
			// no existe el usuario, se trata como deslogueado
			request.getSession().setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
			request.getRequestDispatcher("/home").forward(request, response);
			return;
		}
		
		// Determinar tipo de usuario y cargar datos adicionales
		if (usuario instanceof AsistenteDTO) {
			AsistenteDTO asistente = (AsistenteDTO) usuario;
			request.setAttribute("usuario", asistente);
			request.setAttribute("tipo", "Asistente");
			
			// Obtener registros del asistente (frescos desde el controlador)
			try {
				RegistroDTO[] registrosArray = toRegistroDTOArray(port.getRegistrosPorAsistentes(asistente.getNickname()));
				java.util.Collection<RegistroDTO> registros = java.util.Arrays.asList(registrosArray);
				request.setAttribute("registros", registros);
				
				// Crear mapa de edicion -> evento para mostrar en la vista
				java.util.Map<String, String> edicionToEvento = new java.util.HashMap<>();
				for (RegistroDTO registro : registros) {
					String nombreEdicion = registro.getEdicion().getNombre();
					try {
						EdicionDTO edicionDTO = port.buscarEdicionDTO(nombreEdicion);
						if (edicionDTO != null && edicionDTO.getEvento() != null) {
							edicionToEvento.put(nombreEdicion, edicionDTO.getEvento());
						}
					} catch (Exception ex) {
						// Si no se puede obtener el evento, continuar
					}
				}
				request.setAttribute("edicionToEvento", edicionToEvento);
			} catch (Exception e) {
				// Si no hay registros, continuar sin ellos
			}
		} else if (usuario instanceof OrganizadorDTO) {
			OrganizadorDTO organizador = (OrganizadorDTO) usuario;
			request.setAttribute("usuario", organizador);
			request.setAttribute("tipo", "Organizador");
			
			// Obtener ediciones del organizador
			try {
				// Obtener nombres de ediciones organizadas por este organizador
				String[] nombresEdicionesArray = toStringArray(port.obtenerEdicionesOrganizadasPor(organizador.getNickname()));
				List<String> nombresEdiciones = java.util.Arrays.asList(nombresEdicionesArray);
				
				// Convertir nombres a DTOs
				List<EdicionDTO> ediciones = new java.util.ArrayList<>();
				java.util.Map<String, String> edicionToEvento = new java.util.HashMap<>();
				
				// Obtener todos los eventos para buscar la relación edicion->evento
				EventoDTO[] todosEventosArray = toEventoDTOArray(port.listarEventosDTO());
				List<EventoDTO> todosEventos = java.util.Arrays.asList(todosEventosArray);
				
				for (String nombreEdicion : nombresEdiciones) {
					try {
						EdicionDTO edicionDTO = port.buscarEdicionDTO(nombreEdicion);
						if (edicionDTO != null) {
							ediciones.add(edicionDTO);
							// Buscar el evento de esta edición iterando por todos los eventos
							for (EventoDTO eventoDTO : todosEventos) {
								if (eventoDTO.getEdiciones() != null) {
									for (EdicionDTO edEvento : eventoDTO.getEdiciones()) {
										if (edEvento.getNombre().equals(nombreEdicion)) {
											edicionToEvento.put(nombreEdicion, eventoDTO.getNombre());
											break;
										}
									}
								}
								if (edicionToEvento.containsKey(nombreEdicion)) {
									break;
								}
							}
						}
					} catch (Exception ex) {
						// Si no se puede obtener una edición, continuar con las demás
					}
				}
				request.setAttribute("ediciones", ediciones);
				request.setAttribute("edicionToEventoOrg", edicionToEvento);
			} catch (Exception e) {
				// Si no hay ediciones, continuar sin ellas
			}
		}
		
		request.getRequestDispatcher("/WEB-INF/web/usuarios/perfil.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

}
