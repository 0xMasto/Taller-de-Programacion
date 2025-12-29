package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;

import ws.WSClientHelper;
import ws.WSTypeConverter;
import publicar.ws.client.WebServices;
import publicar.ws.client.EdicionDTO;
import publicar.ws.client.OrganizadorDTO;
import publicar.ws.client.RegistroDTO;
import publicar.ws.client.AsistenteDTO;
import publicar.ws.client.BusinessException_Exception;

/**
 * Servlet for user management using Web Services
 */
@WebServlet("/usuarios")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class Usuarios extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private WebServices port;
    
    @Override
    public void init() throws ServletException {
        super.init();
        this.port = WSClientHelper.getInstance().getPort();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if (action == null || action.equals("list")) {
                listarUsuarios(request, response);
            } else if (action.equals("new")) {
                mostrarFormularioAlta(request, response);
            } else if (action.equals("detail")) {
                mostrarDetalle(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/errorPages/500.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String tipoUsuario = request.getParameter("tipoUsuario");
        
        try {
            if ("asistente".equalsIgnoreCase(tipoUsuario)) {
                altaAsistente(request, response);
            } else if ("organizador".equalsIgnoreCase(tipoUsuario)) {
                altaOrganizador(request, response);
            } else {
                request.setAttribute("error", "Tipo de usuario no válido");
                mostrarFormularioAlta(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            mostrarFormularioAlta(request, response);
        }
    }
    
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<AsistenteDTO> asistentes = WSTypeConverter.toAsistenteDTOList(port.listarAsistentesDTO());
        List<OrganizadorDTO> organizadores = WSTypeConverter.toOrganizadorDTOList(port.listarOrganizadoresDTO());
        
        request.setAttribute("asistentes", asistentes);
        request.setAttribute("organizadores", organizadores);
        request.getRequestDispatcher("/WEB-INF/web/usuarios/listar.jsp").forward(request, response);
    }
    
    private void mostrarFormularioAlta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<String> instituciones = WSTypeConverter.toStringList(port.listarInstituciones());
        request.setAttribute("instituciones", instituciones);
        request.getRequestDispatcher("/WEB-INF/web/usuarios/form.jsp").forward(request, response);
    }
    
    private void mostrarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nickname = request.getParameter("nickname");
        
        if (nickname == null || nickname.trim().isEmpty()) {
            request.setAttribute("error", "Nickname no especificado");
            request.getRequestDispatcher("/WEB-INF/web/usuarios/details.jsp").forward(request, response);
            return;
        }
        
        try {
            // Check if user is asistente
            String[] asistentes = WSTypeConverter.toStringArray(port.listarAsistentes());
            boolean isAsistente = java.util.Arrays.asList(asistentes).contains(nickname);
            
            // Check if user is organizador
            String[] organizadores = WSTypeConverter.toStringArray(port.listarOrganizadores());
            boolean isOrganizador = java.util.Arrays.asList(organizadores).contains(nickname);
            
            // Only fetch DTO if we know user exists in that category
            if (isAsistente) {
                try {
                    AsistenteDTO asistente = port.buscarAsistenteDTO(nickname);
                    if (asistente != null) {
                        request.setAttribute("usuario", asistente);
                        request.setAttribute("tipoUsuario", "asistente");
                    } else {
                        request.setAttribute("error", "Usuario no encontrado");
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching AsistenteDTO: " + e.getMessage());
                    request.setAttribute("error", "Error al cargar información del usuario");
                }
            } else if (isOrganizador) {
                try {
                    OrganizadorDTO organizador = port.buscarOrganizadorDTO(nickname);
                    if (organizador != null) {
                        request.setAttribute("usuario", organizador);
                        request.setAttribute("tipoUsuario", "organizador");
                        
                        // Fetch editions organized by this organizer
                        try {
                            String[] edicionNombres = WSTypeConverter.toStringArray(
                                port.obtenerEdicionesOrganizadasPor(nickname)
                            );
                            
                            // Get logged-in user's nickname to determine filtering
                            HttpSession session = request.getSession(false);
                            String loggedInNickname = (session != null) 
                                ? (String) session.getAttribute("nickname") 
                                : null;
                            boolean isOwnProfile = nickname.equals(loggedInNickname);
                            
                            // Convert edition names to DTOs and filter based on viewer
                            List<EdicionDTO> ediciones = new ArrayList<>();
                            for (String edicionNombre : edicionNombres) {
                                try {
                                    EdicionDTO edicion = port.buscarEdicionDTO(edicionNombre);
                                    if (edicion != null) {
                                        // If viewing own profile, show all editions
                                        // Otherwise, show only ACEPTADA editions
                                        if (isOwnProfile || "ACEPTADA".equals(edicion.getEstado())) {
                                            ediciones.add(edicion);
                                        }
                                    }
                                } catch (Exception ex) {
                                    // Skip this edition if there's an error
                                    System.err.println("Error loading edition: " + edicionNombre);
                                }
                            }
                            
                            request.setAttribute("ediciones", ediciones);
                            request.setAttribute("isOwnProfile", isOwnProfile);
                        } catch (Exception e) {
                            System.err.println("Error fetching organizer editions: " + e.getMessage());
                            // Continue without editions
                        }
                    } else {
                        request.setAttribute("error", "Usuario no encontrado");
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching OrganizadorDTO: " + e.getMessage());
                    request.setAttribute("error", "Error al cargar información del usuario");
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al buscar usuario: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/web/usuarios/details.jsp").forward(request, response);
    }
    
    private void altaAsistente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, BusinessException_Exception {
        
        String nickname = request.getParameter("nickname");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");
        String apellido = request.getParameter("apellido");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String institucion = request.getParameter("institucion");
        
        // Handle image upload
        String imagenPath = "-";
        Part imagenPart = request.getPart("imagen");
        if (imagenPart != null && imagenPart.getSize() > 0) {
            imagenPath = guardarImagen(imagenPart, nickname);
        }
        
        port.altaAsistente(nickname, nombre, correo, contrasenia, imagenPath, 
                                         apellido, fechaNacimiento, institucion);
        
        request.setAttribute("success", "Asistente creado exitosamente");
        response.sendRedirect(request.getContextPath() + "/usuarios?action=list");
    }
    
    private void altaOrganizador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nickname = request.getParameter("nickname");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");
        String descripcion = request.getParameter("descripcion");
        String sitioWeb = request.getParameter("sitioWeb");
        
        // Handle image upload
        String imagenPath = "-";
        Part imagenPart = request.getPart("imagen");
        if (imagenPart != null && imagenPart.getSize() > 0) {
            imagenPath = guardarImagen(imagenPart, nickname);
        }
        
        port.altaOrganizador(nickname, nombre, correo, contrasenia, imagenPath, descripcion, sitioWeb);
        
        request.setAttribute("success", "Organizador creado exitosamente");
        response.sendRedirect(request.getContextPath() + "/usuarios?action=list");
    }
    
    private String guardarImagen(Part imagenPart, String nickname) throws IOException {
        String fileName = Paths.get(imagenPart.getSubmittedFileName()).getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = "IMG-US-" + nickname + extension;
        
        String uploadPath = getServletContext().getRealPath("/") + "media/images";
        Path uploadDir = Paths.get(uploadPath);
        
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        
        Path filePath = uploadDir.resolve(newFileName);
        Files.copy(imagenPart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return "media/images/" + newFileName;
    }
}
