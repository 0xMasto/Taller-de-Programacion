package publicar;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;

import logica.interfaces.Fabrica;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import logica.dto.*;
import exception.*;
import logica.model.EstadoEnum;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Servidor de Web Services SOAP/RPC que expone toda la funcionalidad de los controladores
 * siguiendo las especificaciones de la Tarea 3
 */
@WebService
@SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class WebServices {

    private Endpoint endpoint = null;
    private final iControladorUsuarios controladorUsuarios;
    private final iControladorEventos controladorEventos;
    
    // Constructor
    public WebServices() {
        Fabrica fabrica = Fabrica.getInstancia();
        this.controladorUsuarios = fabrica.getControladorUsuario();
        this.controladorEventos = fabrica.getControladorEventos();
    }

    /**
     * Publicar el servicio web en la URL configurada
     */
    @WebMethod(exclude = true)
    public void publicar() {
        String url = loadConfiguredURL();
        endpoint = Endpoint.publish(url, this);
        System.out.println("Web Services publicados en: " + url);
        System.out.println("WSDL disponible en: " + url + "?wsdl");
    }

    /**
     * Cargar URL configurada desde archivo properties externo
     */
    @WebMethod(exclude = true)
    private String loadConfiguredURL() {
        // Asegurar que el directorio de configuración existe
        String propertiesPath = "/ens/devel01/tpgr01/servidor.properties";
        
        Properties props = new Properties();
        try (java.io.FileInputStream fis = new java.io.FileInputStream(propertiesPath)) {
            props.load(fis);
            String host = props.getProperty("webservices.host" );
            String port = props.getProperty("webservices.port" );
            String path = props.getProperty("webservices.path" );
            String url = "http://" + host + ":" + port + path;
            System.out.println("Configuración cargada desde: " + propertiesPath);
            return url;
        } catch (IOException e) {
            
            return "";
        }
    }


    // ==================== MÉTODOS DE USUARIOS ====================
    
    @WebMethod
    public String[] listarUsuarios() {
        List<String> usuarios = controladorUsuarios.listarUsuarios();
        return usuarios.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] consultarUsuario(@WebParam(name = "nickname") String nickname) {
        Set<String> info = controladorUsuarios.consultarUsuario(nickname);
        return info.toArray(new String[0]);
    }
    
    @WebMethod
    public void altaAsistente(@WebParam(name = "nickname") String nickname, 
                              @WebParam(name = "nombre") String nombre, 
                              @WebParam(name = "correo") String correo, 
                              @WebParam(name = "contrasenia") String contrasenia, 
                              @WebParam(name = "imagen") String imagen,
                              @WebParam(name = "apellido") String apellido, 
                              @WebParam(name = "fechaNacimiento") String fechaNacimiento, 
                              @WebParam(name = "institucion") String institucion) throws BusinessException {
        LocalDate fecha = fechaNacimiento != null ? LocalDate.parse(fechaNacimiento) : null;
        if (institucion != null && !institucion.isEmpty()) {
            controladorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fecha, institucion);
        } else {
            controladorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fecha);
        }
    }
    
    @WebMethod
    public void altaOrganizador(@WebParam(name = "nickname") String nickname, 
                                @WebParam(name = "nombre") String nombre, 
                                @WebParam(name = "correo") String correo, 
                                @WebParam(name = "contrasenia") String contrasenia, 
                                @WebParam(name = "imagen") String imagen,
                                @WebParam(name = "descripcion") String descripcion, 
                                @WebParam(name = "sitioWeb") String sitioWeb) {
        controladorUsuarios.altaOrganizador(nickname, nombre, correo, contrasenia, imagen, descripcion, sitioWeb);
    }
    
    @WebMethod
    public String[] listarOrganizadores() {
        List<String> organizadores = controladorUsuarios.listarOrganizadores();
        return organizadores.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] listarAsistentes() {
        Set<String> asistentes = controladorUsuarios.listarAsistentes();
        return asistentes.toArray(new String[0]);
    }
    
    @WebMethod
    public boolean existeNickname(@WebParam(name = "nickname") String nickname) {
        return controladorUsuarios.existeNickname(nickname);
    }
    
    @WebMethod
    public boolean existeCorreo(@WebParam(name = "correo") String correo) {
        return controladorUsuarios.existeCorreo(correo);
    }
    
    @WebMethod
    public AsistenteDTO buscarAsistenteDTO(@WebParam(name = "nickname") String nickname) throws BusinessException {
        AsistenteDTO asistente = controladorUsuarios.buscarAsistenteDTO(nickname);
        if (asistente == null) {
            throw new BusinessException("Asistente no encontrado: " + nickname);
        }
        return asistente;
    }
    
    @WebMethod
    public OrganizadorDTO buscarOrganizadorDTO(@WebParam(name = "nickname") String nickname) throws BusinessException {
        OrganizadorDTO organizador = controladorUsuarios.buscarOrganizadorDTO(nickname);
        if (organizador == null) {
            throw new BusinessException("Organizador no encontrado: " + nickname);
        }
        return organizador;
    }
    
    @WebMethod
    public AsistenteDTO[] listarAsistentesDTO() {
        List<AsistenteDTO> asistentes = controladorUsuarios.listarAsistentesDTO();
        return asistentes.toArray(new AsistenteDTO[0]);
    }
    
    @WebMethod
    public OrganizadorDTO[] listarOrganizadoresDTO() {
        List<OrganizadorDTO> organizadores = controladorUsuarios.listarOrganizadoresDTO();
        return organizadores.toArray(new OrganizadorDTO[0]);
    }
    
    @WebMethod
    public boolean asistenteEstaRegistradoEnEdicion(@WebParam(name = "nicknameAsistente") String nicknameAsistente, 
                                                    @WebParam(name = "nombreEdicion") String nombreEdicion) {
        return controladorUsuarios.asistenteEstaRegistradoEnEdicion(nicknameAsistente, nombreEdicion);
    }
    
    @WebMethod
    public String[] obtenerEdicionesRegistradasDeAsistente(@WebParam(name = "nicknameAsistente") String nicknameAsistente) {
        List<String> ediciones = controladorUsuarios.obtenerEdicionesRegistradasDeAsistente(nicknameAsistente);
        return ediciones.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] obtenerRegistrosDeAsistente(@WebParam(name = "nicknameAsistente") String nicknameAsistente) {
        List<String> registros = controladorUsuarios.obtenerRegistrosDeAsistente(nicknameAsistente);
        return registros.toArray(new String[0]);
    }
    
    @WebMethod
    public RegistroDTO[] getRegistrosPorAsistentes(@WebParam(name = "nickname") String nickname) {
        Set<RegistroDTO> registros = controladorUsuarios.getRegistrosPorAsistentes(nickname);
        return registros.toArray(new RegistroDTO[0]);
    }
    
    @WebMethod
    public String[] listarEventosPorOrganizador(@WebParam(name = "nickname") String nickname) {
        List<String> eventos = controladorUsuarios.listarEventosPorOrganizador(nickname);
        return eventos.toArray(new String[0]);
    }
    
    // Nuevos métodos de la Tarea 3
    @WebMethod
    public void seguirUsuario(@WebParam(name = "nicknameSeguidor") String nicknameSeguidor, 
                             @WebParam(name = "nicknameSeguido") String nicknameSeguido) throws BusinessException {
        controladorUsuarios.seguirUsuario(nicknameSeguidor, nicknameSeguido);
    }
    
    @WebMethod
    public void dejarDeSeguirUsuario(@WebParam(name = "nicknameSeguidor") String nicknameSeguidor, 
                                    @WebParam(name = "nicknameSeguido") String nicknameSeguido) throws BusinessException {
        controladorUsuarios.dejarDeSeguirUsuario(nicknameSeguidor, nicknameSeguido);
    }
    
    @WebMethod
    public String[] obtenerSeguidores(@WebParam(name = "nickname") String nickname) {
        List<String> seguidores = controladorUsuarios.obtenerSeguidores(nickname);
        return seguidores.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] obtenerSeguidos(@WebParam(name = "nickname") String nickname) {
        List<String> seguidos = controladorUsuarios.obtenerSeguidos(nickname);
        return seguidos.toArray(new String[0]);
    }
    
    @WebMethod
    public boolean verificarLogin(@WebParam(name = "nicknameOEmail") String nicknameOEmail, 
                                  @WebParam(name = "contrasenia") String contrasenia) {
        return controladorUsuarios.verificarLogin(nicknameOEmail, contrasenia);
    }
    
    @WebMethod
    public boolean esAsistente(@WebParam(name = "nickname") String nickname) {
        AsistenteDTO asistente = controladorUsuarios.buscarAsistenteDTO(nickname);
        return asistente != null;
    }

    // ==================== MÉTODOS DE EVENTOS ====================
    
    @WebMethod
    public void altaEvento(@WebParam(name = "nombre") String nombre, 
                          @WebParam(name = "sigla") String sigla, 
                          @WebParam(name = "descripcion") String descripcion, 
                          @WebParam(name = "categorias") CategoriaDTO[] categorias,
                          @WebParam(name = "imagen") String imagen) throws BusinessException {
        List<CategoriaDTO> categoriasList = java.util.Arrays.asList(categorias);
        if (imagen != null && !imagen.isEmpty()) {
            controladorEventos.altaEvento(nombre, sigla, descripcion, categoriasList, imagen);
        } else {
            controladorEventos.altaEvento(nombre, sigla, descripcion, categoriasList);
        }
    }
    
    @WebMethod
    public void altaEventoConFechaAlta(@WebParam(name = "nombre") String nombre, 
                          @WebParam(name = "sigla") String sigla, 
                          @WebParam(name = "descripcion") String descripcion, 
                          @WebParam(name = "categorias") CategoriaDTO[] categorias,
                          @WebParam(name = "imagen") String imagen,
                          @WebParam(name = "fechaAlta") String fechaAlta) throws BusinessException {
        List<CategoriaDTO> categoriasList = java.util.Arrays.asList(categorias);
        LocalDate fAlta = LocalDate.parse(fechaAlta);
        controladorEventos.altaEvento(nombre, sigla, descripcion, categoriasList, imagen, fAlta);
    }
    
    @WebMethod
    public void altaEdicionEvento(@WebParam(name = "nombre") String nombre, 
                                  @WebParam(name = "sigla") String sigla, 
                                  @WebParam(name = "fechaInicio") String fechaInicio, 
                                  @WebParam(name = "fechaFin") String fechaFin,
                                  @WebParam(name = "nombreOrganizador") String nombreOrganizador, 
                                  @WebParam(name = "ciudad") String ciudad, 
                                  @WebParam(name = "pais") String pais, 
                                  @WebParam(name = "nombreEvento") String nombreEvento,
                                  @WebParam(name = "imagen") String imagen,
                                  @WebParam(name = "videoUrl") String videoUrl) throws NombreEdicionException {
        LocalDate fInicio = LocalDate.parse(fechaInicio);
        LocalDate fFin = LocalDate.parse(fechaFin);
        
        if (imagen != null && !imagen.isEmpty()) {
            controladorEventos.altaEdicionEvento(nombre, sigla, fInicio, fFin, nombreOrganizador, ciudad, pais, nombreEvento, imagen);
        } else {
            controladorEventos.altaEdicionEvento(nombre, sigla, fInicio, fFin, nombreOrganizador, ciudad, pais, nombreEvento);
        }
        
        // Agregar video URL si existe
        if (videoUrl != null && !videoUrl.isEmpty()) {
            logica.model.Edicion edicion = controladorEventos.buscarEdicion(nombre);
            if (edicion != null) {
                edicion.setVideoUrl(videoUrl);
            }
        }
    }
    
    @WebMethod
    public void altaEdicionEventoConEstado(@WebParam(name = "nombre") String nombre, 
                                          @WebParam(name = "sigla") String sigla, 
                                          @WebParam(name = "fechaInicio") String fechaInicio, 
                                          @WebParam(name = "fechaFin") String fechaFin,
                                          @WebParam(name = "fechaAlta") String fechaAlta,
                                          @WebParam(name = "nombreOrganizador") String nombreOrganizador, 
                                          @WebParam(name = "ciudad") String ciudad, 
                                          @WebParam(name = "pais") String pais, 
                                          @WebParam(name = "nombreEvento") String nombreEvento,
                                          @WebParam(name = "imagen") String imagen,
                                          @WebParam(name = "videoUrl") String videoUrl,
                                          @WebParam(name = "estado") String estado) throws NombreEdicionException {
        LocalDate fInicio = LocalDate.parse(fechaInicio);
        LocalDate fFin = LocalDate.parse(fechaFin);
        LocalDate fAlta = LocalDate.parse(fechaAlta);
        
        // Call controller method that accepts estado
        controladorEventos.altaEdicionEvento(nombre, sigla, fInicio, fFin, fAlta, nombreOrganizador, ciudad, pais, nombreEvento, imagen, estado);
        
        // Agregar video URL si existe
        if (videoUrl != null && !videoUrl.isEmpty()) {
            logica.model.Edicion edicion = controladorEventos.buscarEdicion(nombre);
            if (edicion != null) {
                edicion.setVideoUrl(videoUrl);
            }
        }
    }
    
    @WebMethod
    public void altaCategoria(@WebParam(name = "nombre") String nombre) throws BusinessException {
        controladorEventos.altaCategoria(nombre);
    }
    
    @WebMethod
    public boolean altaInstitucion(@WebParam(name = "nombre") String nombre, 
                               @WebParam(name = "descripcion") String descripcion, 
                               @WebParam(name = "sitioWeb") String sitioWeb) {
        return controladorEventos.altaInstitucion(nombre, descripcion, sitioWeb);
    }
    
    @WebMethod
    public void altaTipoRegistro(@WebParam(name = "nombreEvento") String nombreEvento, 
                                @WebParam(name = "nombreEdicion") String nombreEdicion, 
                                @WebParam(name = "nombre") String nombre,
                                @WebParam(name = "descripcion") String descripcion, 
                                @WebParam(name = "costo") String costo, 
                                @WebParam(name = "cupo") int cupo) 
            throws TipoRegistroDuplicadoException {
        BigDecimal costoDecimal = new BigDecimal(costo);
        controladorEventos.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, costoDecimal, cupo);
    }
    
    @WebMethod
    public void altaPatrocinio(@WebParam(name = "nombreEvento") String nombreEvento, 
                              @WebParam(name = "nombreEdicion") String nombreEdicion, 
                              @WebParam(name = "nombreTipoRegistro") String nombreTipoRegistro,
                              @WebParam(name = "nombreInstitucion") String nombreInstitucion, 
                              @WebParam(name = "nivelPatrocinio") String nivelPatrocinio, 
                              @WebParam(name = "aporteEconomico") String aporteEconomico,
                              @WebParam(name = "cantidadRegistros") int cantidadRegistros, 
                              @WebParam(name = "codigoPatrocinio") String codigoPatrocinio) 
            throws CostoRegistrosExcedidoException, PatrocinioDuplicadoException {
        BigDecimal aporte = new BigDecimal(aporteEconomico);
        controladorEventos.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, nombreInstitucion, 
                                         nivelPatrocinio, aporte, cantidadRegistros, codigoPatrocinio);
    }
    
    @WebMethod
    public void registrarAsistente(@WebParam(name = "nicknameAsistente") String nicknameAsistente, 
                                   @WebParam(name = "nombreEdicion") String nombreEdicion, 
                                   @WebParam(name = "nombreTipoRegistro") String nombreTipoRegistro) 
            throws CupoLlenoException, RegistroDuplicadoException {
        controladorEventos.registrarAsistente(nicknameAsistente, nombreEdicion, nombreTipoRegistro);
    }
    
    @WebMethod
    public void registrarAsistenteConAsistente(@WebParam(name = "nicknameAsistente") String nicknameAsistente, 
                                               @WebParam(name = "nombreTipoRegistro") String nombreTipoRegistro,
                                               @WebParam(name = "nombreEdicion") String nombreEdicion,
                                               @WebParam(name = "asistio") Boolean asistio) 
            throws CupoLlenoException, RegistroDuplicadoException {
        controladorEventos.registrarAsistenteConAsistente(nicknameAsistente, nombreTipoRegistro, nombreEdicion, asistio);
    }
    
    @WebMethod
    public String[] listarEventos() {
        List<String> eventos = controladorEventos.listarEventos();
        return eventos.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] listarInstituciones() {
        List<String> instituciones = controladorEventos.listarInstituciones();
        return instituciones.toArray(new String[0]);
    }
    
    @WebMethod
    public CategoriaDTO[] listarCategoriasDTO() {
        List<CategoriaDTO> categorias = controladorEventos.listarCategoriasDTO();
        return categorias.toArray(new CategoriaDTO[0]);
    }
    
    @WebMethod
    public String[] listarEdicionesDeEvento(@WebParam(name = "nombreEvento") String nombreEvento) {
        List<String> ediciones = controladorEventos.listarEdicionesDeEvento(nombreEvento);
        return ediciones.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] obtenerTiposDeRegistroDeEdicion(@WebParam(name = "nombreEdicion") String nombreEdicion) {
        Set<String> tipos = controladorEventos.obtenerTiposDeRegistroDeEdicion(nombreEdicion);
        return tipos.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] listarPatrocinios() {
        List<String> patrocinios = controladorEventos.listarPatrocinios();
        return patrocinios.toArray(new String[0]);
    }
    
    @WebMethod
    public String[] obtenerEdicionesOrganizadasPor(@WebParam(name = "nicknameOrganizador") String nicknameOrganizador) {
        List<String> ediciones = controladorEventos.obtenerEdicionesOrganizadasPor(nicknameOrganizador);
        return ediciones.toArray(new String[0]);
    }
    
    @WebMethod
    public EventoDTO buscarEventoDTO(@WebParam(name = "nombre") String nombre) throws BusinessException {
        EventoDTO evento = controladorEventos.buscarEventoDTO(nombre);
        if (evento == null) {
            throw new BusinessException("Evento no encontrado: " + nombre);
        }
        return evento;
    }
    
    @WebMethod
    public EdicionDTO buscarEdicionDTO(@WebParam(name = "nombre") String nombre) throws BusinessException {
        EdicionDTO edicion = controladorEventos.buscarEdicionDTO(nombre);
        if (edicion == null) {
            throw new BusinessException("Edición no encontrada: " + nombre);
        }
        return edicion;
    }
    
    @WebMethod
    public InstitucionDTO buscarInstitucionDTO(@WebParam(name = "nombre") String nombre) throws BusinessException {
        InstitucionDTO institucion = controladorEventos.buscarInstitucionDTO(nombre);
        if (institucion == null) {
            throw new BusinessException("Institución no encontrada: " + nombre);
        }
        return institucion;
    }
    
    @WebMethod
    public PatrocinioDTO buscarPatrocinioDTO(@WebParam(name = "codigoPatrocinio") String codigoPatrocinio) throws BusinessException {
        PatrocinioDTO patrocinio = controladorEventos.buscarPatrocinioDTO(codigoPatrocinio);
        if (patrocinio == null) {
            throw new BusinessException("Patrocinio no encontrado: " + codigoPatrocinio);
        }
        return patrocinio;
    }
    
    @WebMethod
    public TipoRegistroDTO buscarTipoRegistroDTO(@WebParam(name = "nombreEdicion") String nombreEdicion, 
                                                 @WebParam(name = "nombreTipo") String nombreTipo) {
        return controladorEventos.buscarTipoRegistroDTO(nombreEdicion, nombreTipo);
    }
    
    @WebMethod
    public EventoDTO[] listarEventosDTO() {
        List<EventoDTO> eventos = controladorEventos.listarEventosDTO();
        return eventos.toArray(new EventoDTO[0]);
    }
    
    @WebMethod
    public EdicionDTO[] listarTodasLasEdicionesDTO() {
        List<EdicionDTO> ediciones = controladorEventos.listarTodasLasEdicionesDTO();
        return ediciones.toArray(new EdicionDTO[0]);
    }
    
    @WebMethod
    public EdicionDTO[] listarEdicionesDeEventoDTO(@WebParam(name = "nombreEvento") String nombreEvento) {
        List<EdicionDTO> ediciones = controladorEventos.listarEdicionesDeEventoDTO(nombreEvento);
        return ediciones.toArray(new EdicionDTO[0]);
    }
    
    @WebMethod
    public TipoRegistroDTO[] obtenerTiposDeRegistroDeEdicionDTO(@WebParam(name = "nombreEdicion") String nombreEdicion) {
        List<TipoRegistroDTO> tipos = controladorEventos.obtenerTiposDeRegistroDeEdicionDTO(nombreEdicion);
        return tipos.toArray(new TipoRegistroDTO[0]);
    }
    
    @WebMethod
    public PatrocinioDTO[] obtenerPatrociniosDeEdicionDTO(@WebParam(name = "nombreEdicion") String nombreEdicion) {
        List<PatrocinioDTO> patrocinios = controladorEventos.obtenerPatrociniosDeEdicionDTO(nombreEdicion);
        return patrocinios.toArray(new PatrocinioDTO[0]);
    }
    
    @WebMethod
    public RegistroDTO[] obtenerRegistrosDeEdicionDTO(@WebParam(name = "nombreEdicion") String nombreEdicion) {
        List<RegistroDTO> registros = controladorEventos.obtenerRegistrosDeEdicionDTO(nombreEdicion);
        return registros.toArray(new RegistroDTO[0]);
    }
    
    @WebMethod
    public void cambiarEstadoEdicion(@WebParam(name = "nombreEdicion") String nombreEdicion, 
                                     @WebParam(name = "nuevoEstado") String nuevoEstado) {
        EstadoEnum estado = EstadoEnum.valueOf(nuevoEstado.toUpperCase());
        controladorEventos.cambiarEstadoEdicion(nombreEdicion, estado);
    }
    
    @WebMethod
    public String[] listarEdicionesIngresadas(@WebParam(name = "nombreEvento") String nombreEvento) {
        List<String> ediciones = controladorEventos.listarEdicionesIngresadas(nombreEvento);
        return ediciones.toArray(new String[0]);
    }
    
    @WebMethod
    public boolean existeInstitucion(@WebParam(name = "nombre") String nombre) {
        return controladorEventos.existeInstitucion(nombre);
    }
    
    // Nuevos métodos de la Tarea 3
    @WebMethod
    public void finalizarEvento(@WebParam(name = "nombreEvento") String nombreEvento) throws BusinessException {
        controladorEventos.finalizarEvento(nombreEvento);
    }
    
    @WebMethod
    public String[] listarEventosNoFinalizados() {
        List<String> eventos = controladorEventos.listarEventos();
        return eventos.toArray(new String[0]);
    }
    
    @WebMethod
    public void archivarEdicion(@WebParam(name = "nombreEdicion") String nombreEdicion, 
                               @WebParam(name = "nicknameOrganizador") String nicknameOrganizador) throws BusinessException {
        controladorEventos.archivarEdicion(nombreEdicion, nicknameOrganizador);
    }
    
    @WebMethod
    public void registrarAsistencia(@WebParam(name = "nicknameAsistente") String nicknameAsistente, 
                                    @WebParam(name = "nombreEdicion") String nombreEdicion) throws BusinessException {
        controladorEventos.registrarAsistencia(nicknameAsistente, nombreEdicion);
    }
    
    @WebMethod
    public void registrarVisita(@WebParam(name = "nombreEvento") String nombreEvento) {
        controladorEventos.registrarVisita(nombreEvento);
    }
    
    @WebMethod
    public void setVisitasEvento(@WebParam(name = "nombreEvento") String nombreEvento, @WebParam(name = "visitas") int visitas) {
        controladorEventos.setVisitasEvento(nombreEvento, visitas);
    }
    
    @WebMethod
    public String[][] obtenerEventosMasVisitados(@WebParam(name = "cantidad") int cantidad) {
        List<String[]> visitados = controladorEventos.obtenerEventosMasVisitados(cantidad);
        return visitados.toArray(new String[0][]);
    }
    
    @WebMethod
    public Object[] buscarEventosYEdiciones(@WebParam(name = "query") String query) {
        List<Object> resultados = controladorEventos.buscarEventosYEdiciones(query);
        return resultados.toArray(new Object[0]);
    }
}
