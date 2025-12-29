package logica.controllers;

import logica.interfaces.iControladorEventos;
import logica.dto.EventoDTO;
import exception.CupoLlenoException;
import exception.BusinessException;
import exception.CostoRegistrosExcedidoException;
import exception.NombreEdicionException;
import exception.PatrocinioDuplicadoException;
import exception.RegistroDuplicadoException;
import exception.TipoRegistroDuplicadoException;

import logica.model.Edicion;
import logica.model.EstadoEnum;
import logica.model.Evento;
import logica.model.Asistente;
import logica.model.Institucion;
import logica.model.Categoria;
import logica.model.ManejadorEventos;
import logica.model.ManejadorUsuarios;
import logica.model.Organizador;
import logica.model.Registro;
import logica.model.TipoRegistro;
import logica.model.Patrocinio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import java.util.stream.Collectors;

import logica.dto.EdicionDTO;
import logica.dto.CategoriaDTO;
import logica.dto.TipoRegistroDTO;
import logica.dto.PatrocinioDTO;
import logica.dto.RegistroDTO;
import logica.dto.InstitucionDTO;


/**
 * Controlador para la gestión de eventos
 * Implementa la lógica de presentación siguiendo el patrón MVC
 * @param <Institucion>
 */
public class ControladorEventos implements iControladorEventos {
    private final ManejadorEventos manejadorEventos;
    private final ManejadorUsuarios manejadorUsuarios;
    public ControladorEventos(ManejadorEventos manejadorEventos, ManejadorUsuarios manejadorUsuarios) {
        this.manejadorEventos = manejadorEventos;
        this.manejadorUsuarios = manejadorUsuarios;
    }
    
 
        
    public void altaEvento(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias) throws BusinessException {
        // Add validation
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre del evento es obligatorio");
        }
        if (sigla == null || sigla.trim().isEmpty()) {
            throw new BusinessException("La sigla del evento es obligatoria");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new BusinessException("La descripción del evento es obligatoria");
        }
        if (categorias == null || categorias.isEmpty()) {
            throw new BusinessException("El evento debe tener al menos una categoría");
        }
        
        List<Categoria> categoriasModel = new ArrayList<>();
        for (CategoriaDTO categoriaDTO : categorias) {
            Categoria categoria = manejadorEventos.buscarCategoria(categoriaDTO.getNombre());
            if (categoria != null) {
                categoriasModel.add(categoria);
            } else {
                throw new BusinessException("La categoría '" + categoriaDTO.getNombre() + "' no existe en el sistema");
            }
        }
        manejadorEventos.altaEvento(nombre, sigla, descripcion, categoriasModel, "-");
    }
    public void altaEvento(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias, String imagen) throws BusinessException {
        // Add validation
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre del evento es obligatorio");
        }
        if (sigla == null || sigla.trim().isEmpty()) {
            throw new BusinessException("La sigla del evento es obligatoria");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new BusinessException("La descripción del evento es obligatoria");
        }
        if (categorias == null || categorias.isEmpty()) {
            throw new BusinessException("El evento debe tener al menos una categoría");
        }
        
        List<Categoria> categoriasModel = new ArrayList<>();
        for (CategoriaDTO categoriaDTO : categorias) {
            Categoria categoria = manejadorEventos.buscarCategoria(categoriaDTO.getNombre());
            if (categoria != null) {
                categoriasModel.add(categoria);
            } else {
                throw new BusinessException("La categoría '" + categoriaDTO.getNombre() + "' no existe en el sistema");
            }
        }
        manejadorEventos.altaEvento(nombre, sigla, descripcion, categoriasModel, imagen);
    }
    
    public void altaEvento(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias, String imagen, LocalDate fechaAlta) throws BusinessException {
        // Add validation
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre del evento es obligatorio");
        }
        if (sigla == null || sigla.trim().isEmpty()) {
            throw new BusinessException("La sigla del evento es obligatoria");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new BusinessException("La descripción del evento es obligatoria");
        }
        if (categorias == null || categorias.isEmpty()) {
            throw new BusinessException("El evento debe tener al menos una categoría");
        }
        
        List<Categoria> categoriasModel = new ArrayList<>();
        for (CategoriaDTO categoriaDTO : categorias) {
            Categoria categoria = manejadorEventos.buscarCategoria(categoriaDTO.getNombre());
            if (categoria != null) {
                categoriasModel.add(categoria);
            } else {
                throw new BusinessException("La categoría '" + categoriaDTO.getNombre() + "' no existe en el sistema");
            }
        }
        manejadorEventos.altaEvento(nombre, sigla, descripcion, categoriasModel, imagen, fechaAlta);
    }
    
    /**
     * Crear una nueva edición de evento
     * @throws NombreEdicionException 
     */
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            String nombreOrganizador, String ciudad, String pais, String nombreEvento) throws NombreEdicionException {
        // Buscar el organizador
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();    
        Organizador organizador = manejadorUsuarios.buscarOrganizador(nombreOrganizador);
       // Organizador organizador = ManejadorUsuarios.getInstance().buscarOrganizador(nombreOrganizador);
        if (organizador == null) {
            throw new RuntimeException("Organizador no encontrado: " + nombreOrganizador);
        }
        // Buscar el evento
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento == null) {
            throw new RuntimeException("Evento no encontrado: " + nombreEvento);
        }
        
        manejadorEventos.altaEdicion( nombre,  sigla,  fechaInicio,  fechaFin, organizador.getNickname(),  ciudad,  pais,  evento.getNombre());

    }
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String nombreOrganizador, String ciudad, String pais, String nombreEvento) throws NombreEdicionException {
        // Buscar el organizador
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();    
        Organizador organizador = manejadorUsuarios.buscarOrganizador(nombreOrganizador);
       // Organizador organizador = ManejadorUsuarios.getInstance().buscarOrganizador(nombreOrganizador);
        if (organizador == null) {
            throw new RuntimeException("Organizador no encontrado: " + nombreOrganizador);
        }
        // Buscar el evento
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento == null) {
            throw new RuntimeException("Evento no encontrado: " + nombreEvento);
        }
        // Validar que el evento no esté finalizado
        if (evento.isFinalizado()) {
            throw new NombreEdicionException("No se pueden crear ediciones para un evento finalizado");
        }
        
        manejadorEventos.altaEdicion( nombre,  sigla,  fechaInicio,  fechaFin, fechaAlta, organizador.getNickname(),  ciudad,  pais,  evento.getNombre());

    }
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            String nombreOrganizador, String ciudad, String pais, String nombreEvento, String imagen) throws NombreEdicionException {
        // Buscar el organizador
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();    
        Organizador organizador = manejadorUsuarios.buscarOrganizador(nombreOrganizador);
        if (organizador == null) {
            throw new RuntimeException("Organizador no encontrado: " + nombreOrganizador);
        }
        // Buscar el evento
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento == null) {
            throw new RuntimeException("Evento no encontrado: " + nombreEvento);
        }
        // Validar que el evento no esté finalizado
        if (evento.isFinalizado()) {
            throw new NombreEdicionException("No se pueden crear ediciones para un evento finalizado");
        }
        
        manejadorEventos.altaEdicion( nombre,  sigla,  fechaInicio,  fechaFin, organizador.getNickname(),  ciudad,  pais,  evento.getNombre(), imagen);

    }
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String nombreOrganizador, String ciudad, String pais, String nombreEvento, String imagen) throws NombreEdicionException {
        // Buscar el organizador
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();    
        Organizador organizador = manejadorUsuarios.buscarOrganizador(nombreOrganizador);
        if (organizador == null) {
            throw new RuntimeException("Organizador no encontrado: " + nombreOrganizador);
        }
        // Buscar el evento
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento == null) {
            throw new RuntimeException("Evento no encontrado: " + nombreEvento);
        }
        // Validar que el evento no esté finalizado
        if (evento.isFinalizado()) {
            throw new NombreEdicionException("No se pueden crear ediciones para un evento finalizado");
        }
        
        manejadorEventos.altaEdicion( nombre,  sigla,  fechaInicio,  fechaFin, fechaAlta, organizador.getNickname(),  ciudad,  pais,  evento.getNombre(), imagen);

    }
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String nombreOrganizador, String ciudad, String pais, String nombreEvento, String imagen, String estado) throws NombreEdicionException {
        // Buscar el organizador
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();    
        Organizador organizador = manejadorUsuarios.buscarOrganizador(nombreOrganizador);
        if (organizador == null) {
            throw new RuntimeException("Organizador no encontrado: " + nombreOrganizador);
        }
        // Buscar el evento
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento == null) {
            throw new RuntimeException("Evento no encontrado: " + nombreEvento);
        }
        // Validar que el evento no esté finalizado
        if (evento.isFinalizado()) {
            throw new NombreEdicionException("No se pueden crear ediciones para un evento finalizado");
        }
        
        manejadorEventos.altaEdicion( nombre,  sigla,  fechaInicio,  fechaFin, fechaAlta, organizador.getNickname(),  ciudad,  pais,  evento.getNombre(), imagen, estado);

    }
    
    /**
     * Obtener todas las ediciones de eventos
     */
    public java.util.Set<Edicion> obtenerTodasLasEdiciones() {
        return manejadorEventos.obtenerTodasLasEdiciones();
    }
    public java.util.Set<Evento> obtenerTodosLosEventos() {
        return manejadorEventos.obtenerTodosLosEventos();
    }
    
    /**
     * Get all ediciones organized by a specific organizador
     */
    public List<String> obtenerEdicionesOrganizadasPor(String nicknameOrganizador) {
        Set<Edicion> todasLasEdiciones = manejadorEventos.obtenerTodasLasEdiciones();
        return todasLasEdiciones.stream()
            .filter(edicion -> edicion.getOrganizador().equals(nicknameOrganizador))
            .map(Edicion::getNombre)
            .collect(Collectors.toList());
    }

    /**
     * Buscar evento por nombre
     */
    public Evento buscarEvento(String nombre) {
        return manejadorEventos.buscarEvento(nombre);
    }
    public EventoDTO getEventoDTO(String nombre) {
        Evento evento = manejadorEventos.buscarEvento(nombre);
        return evento != null ? evento.toDTO() : null;
    }
    /**
     * Buscar edición por nombre
     */
    public Edicion buscarEdicion(String nombre) {
        return manejadorEventos.buscarEdicion(nombre);
    }

    
    /**
     * Lista las instituciones
     */
    public List<String> listarNombresInstituciones() {
        return manejadorEventos.listarNombresInstituciones();
    }
    public boolean altaInstitucion(String nombre, String descripcion, String sitioWeb) {
        return manejadorEventos.altaInstitucion(nombre, descripcion, sitioWeb);
    }
    public InstitucionDTO getInstitucionDTO(String nombre) {
        return manejadorEventos.buscarInstitucion(nombre).toDTO();
    }
    public void registrarAEvento(String nickAsistente, String tipoRegistro, String nombreEdicion) {
        
    }

    public void altaTipoRegistro(String nombreEvento, String nombreEdicion, String nombre, String descripcion, BigDecimal costo, int cupo) throws TipoRegistroDuplicadoException, IllegalArgumentException {

        manejadorEventos.altaTipoRegistro(nombreEdicion, nombre.trim(), 
                                                descripcion, costo, cupo);
        }
    
    public List<String> listarEventos(){
    	return manejadorEventos.listarEventos();
    }

    public List<String> listarEdicionesDeEvento(String eventoSeleccionado) {
        return manejadorEventos.obtenerEdicionesDeEvento(eventoSeleccionado);
    }

    public List<String> listarTodasLasEdiciones() {
        return manejadorEventos.listarTodasLasEdiciones();
    }

    public Set<String> obtenerTiposDeRegistroDeEdicion(String edicionSeleccionada) {
        Edicion edicion = manejadorEventos.buscarEdicion(edicionSeleccionada);
        if (edicion == null) {
            return new HashSet<>();
        }
        return manejadorEventos.obtenerTiposRegistroDeEdicion(edicion);
    }

    public void registrarAsistente(String nickAsistente, String tipoRegistro, String edicion) throws RegistroDuplicadoException, CupoLlenoException {
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
        Asistente asistente = manejadorUsuarios.buscarAsistente(nickAsistente);

        manejadorEventos.registrarAsistente(asistente, tipoRegistro, edicion);
    }
    
    public void registrarAsistenteConAsistente(String nickAsistente, String tipoRegistro, String edicion, Boolean asistio) throws RegistroDuplicadoException, CupoLlenoException {
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
        Asistente asistente = manejadorUsuarios.buscarAsistente(nickAsistente);

        manejadorEventos.registrarAsistenteConAsistente(asistente, tipoRegistro, edicion, asistio);
    }
    public List<String> listarInstituciones() {
        return manejadorEventos.listarNombresInstituciones();
    }
    
    public Institucion buscarInstitucion(String nombre) {
        return manejadorEventos.buscarInstitucion(nombre);
    }

    public InstitucionDTO buscarInstitucionDTO(String nombre) {
        return manejadorEventos.buscarInstitucion(nombre).toDTO();
    }
    public boolean existeInstitucion(String nombre) {
        return manejadorEventos.existeInstitucion(nombre);
    }
    /**
     * Agregar un evento al sistema
     */
    public void agregarEvento(Evento evento) {
        manejadorEventos.agregarEvento(evento);
    }
    
    public void altaCategoria(String nombre) throws BusinessException {
        manejadorEventos.altaCategoria(nombre);
    }
    
    public List<CategoriaDTO> listarCategoriasDTO() {
        Set<Categoria> categorias = manejadorEventos.obtenerTodasLasCategorias();
        return categorias.stream()
            .map(Categoria::toDTO)
            .collect(Collectors.toList());
    }

public void altaPatrocinio(String nombreEvento, String nombreEdicion, String nombreTipoRegistro,
                              String nombreInstitucion, String nivelPatrocinio, 
                              BigDecimal aporteEconomico, int cantidadRegistros, String codigoPatrocinio)
            throws PatrocinioDuplicadoException, CostoRegistrosExcedidoException {
        
        manejadorEventos.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro,
                                       nombreInstitucion, nivelPatrocinio, aporteEconomico,
                                       cantidadRegistros, codigoPatrocinio);
 }

public BigDecimal calcularCostoRegistros(String evento, String edicion, String tipoRegistro, int cantidad) {
    return manejadorEventos.calcularCostoRegistros(evento, edicion, tipoRegistro, cantidad);
    }

public BigDecimal calcularPorcentajeAporte(BigDecimal aporte, BigDecimal costoRegistros) {
    return manejadorEventos.calcularPorcentajeAporte(aporte, costoRegistros);
}

public Set<Evento> obtenerTodosLosEventosSet() {
    return manejadorEventos.obtenerTodosLosEventos();
}

public List<String> listarPatrocinios() {
    return manejadorEventos.listarPatrocinios();
}

/**
 * Buscar patrocinio por código y retornar DTO
 */
public PatrocinioDTO buscarPatrocinioDTO(String codigoPatrocinio) {
    Patrocinio patrocinio = manejadorEventos.buscarPatrocinio(codigoPatrocinio);
    if (patrocinio != null) {
        return patrocinio.toDTO();
    }
    return null;
}

/**
 * Buscar patrocinio por código y retornar el modelo completo
 */
public Patrocinio buscarPatrocinio(String codigoPatrocinio) {
    return manejadorEventos.buscarPatrocinio(codigoPatrocinio);
}

    /**
     * Obtiene todos los registros de una edición específica
     */
    public List<Registro> obtenerRegistrosDeEdicion(String nombreEdicion) {
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion == null) {
            return new ArrayList<>();
        }
        
        // Get all asistentes and check their registros
        List<Registro> registros = new ArrayList<>();
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
        Set<Asistente> asistentes = manejadorUsuarios.consultarAsistentes();
        
        for (Asistente asistente : asistentes) {
            Set<Registro> registrosAsistente = asistente.getRegistros();
            for (Registro registro : registrosAsistente) {
                if (registro.getEdicion().equals(edicion)) {
                    registros.add(registro);
                }
            }
        }
        
        return registros;
    }

    /**
     * Buscar evento por nombre y retornar DTO
     */
    public EventoDTO buscarEventoDTO(String nombre) {
        Evento evento = manejadorEventos.buscarEvento(nombre);
        if (evento != null) {
            EventoDTO dto = evento.toDTO();
            dto.setVisitas(obtenerVisitasDeEvento(nombre));
            return dto;
        }
        return null;
    }
    
    /**
     * Buscar edición por nombre y retornar DTO
     */
    public EdicionDTO buscarEdicionDTO(String nombre) {
        Edicion edicion = manejadorEventos.buscarEdicion(nombre);
        if (edicion != null) {
            return edicion.toDTO();
        }
        return null;
    }
    
    /**
     * Obtener todos los eventos como DTOs
     */
    public List<EventoDTO> listarEventosDTO() {
        Set<Evento> eventos = manejadorEventos.obtenerTodosLosEventos();
        return eventos.stream()
            .filter(evento -> !evento.isFinalizado())
            .map(Evento::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener todas las ediciones como DTOs
     */
    public List<EdicionDTO> listarTodasLasEdicionesDTO() {
        Set<Edicion> ediciones = manejadorEventos.obtenerTodasLasEdiciones();
        return ediciones.stream()
            .map(Edicion::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener ediciones de un evento específico como DTOs
     */
    public List<EdicionDTO> listarEdicionesDeEventoDTO(String nombreEvento) {
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento != null && evento.getEdiciones() != null) {
            return evento.getEdiciones().stream()
                .map(Edicion::toDTO)
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    /**
     * Obtener tipos de registro de una edición como DTOs
     */
    public List<TipoRegistroDTO> obtenerTiposDeRegistroDeEdicionDTO(String nombreEdicion) {
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion != null && edicion.getTiposRegistro() != null) {
            return edicion.getTiposRegistro().stream()
                .map(TipoRegistro::toDTO)
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    /**
     * Obtener patrocinios de una edición como DTOs
     */
    public List<PatrocinioDTO> obtenerPatrociniosDeEdicionDTO(String nombreEdicion) {
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion != null && edicion.getPatrocinios() != null) {
            return edicion.getPatrocinios().stream()
                .map(Patrocinio::toDTO)
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    
 
    /**
     * Obtiene todos los registros de una edición específica como DTOs
     */
    public List<RegistroDTO> obtenerRegistrosDeEdicionDTO(String nombreEdicion) {
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion == null) {
            return new ArrayList<>();
        }
        
        // Get all asistentes and check their registros
        List<RegistroDTO> registrosDTO = new ArrayList<>();
        ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
        Set<Asistente> asistentes = manejadorUsuarios.consultarAsistentes();
        
        for (Asistente asistente : asistentes) {
            Set<Registro> registrosAsistente = asistente.getRegistros();
            for (Registro registro : registrosAsistente) {
                if (registro.getEdicion().equals(edicion)) {
                    // Convert to DTO
                    RegistroDTO registroDTO = registro.toDTO();
                    // Populate asistente field
                    registroDTO.setAsistente(asistente.toDTO());
                    registrosDTO.add(registroDTO);
                }
            }
        }
        
        return registrosDTO;
    }
    
    /**
     * Buscar un tipo de registro específico por edición y nombre
     * Calcula correctamente el cupo total y disponible
     */
    public TipoRegistroDTO buscarTipoRegistroDTO(String nombreEdicion, String nombreTipo) {
        // Buscar la edición
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion == null) {
            return null;
        }
        
        // Buscar el tipo de registro en la edición
        TipoRegistro tipoRegistro = null;
        if (edicion.getTiposRegistro() != null) {
            for (TipoRegistro tipo : edicion.getTiposRegistro()) {
                if (tipo.getNombre().equals(nombreTipo)) {
                    tipoRegistro = tipo;
                    break;
                }
            }
        }
        
        if (tipoRegistro == null) {
            return null;
        }
        
        // Convertir a DTO
        TipoRegistroDTO dto = tipoRegistro.toDTO();
        
        // Calcular cupo: el campo 'cupo' en el modelo YA es el cupo disponible
        // porque se decrementa en cada registro (ManejadorEventos.java:321)
        int cupoDisponible = tipoRegistro.getCupo();
        
        // Contar registros para este tipo en esta edición para calcular cupo total
        List<Registro> registros = obtenerRegistrosDeEdicion(nombreEdicion);
        int registrosCount = 0;
        for (Registro registro : registros) {
            if (registro.getTipoRegistro().equals(tipoRegistro)) {
                registrosCount++;
            }
        }
        
        // Cupo total = cupo disponible + registros realizados
        int cupoTotal = cupoDisponible + registrosCount;
        
        // Establecer valores en el DTO
        dto.setCupo(cupoTotal);
        dto.setCupoDisponible(cupoDisponible);
        
        return dto;
    }
    
    public void cambiarEstadoEdicion(String nombreEdicion, EstadoEnum nuevoEstado) {
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);

        if (edicion == null) {
            throw new IllegalArgumentException("No se encontró la edición con nombre: " + nombreEdicion);
        }

        if (nuevoEstado == EstadoEnum.ACEPTADA || nuevoEstado == EstadoEnum.RECHAZADA) {
        	edicion.setEstado(nuevoEstado);
        } else {
            throw new IllegalArgumentException("Solo se puede aceptar o rechazar una edición.");
        }
    }

    public void agregarEventoAOrganizador(String nombre, String organizador) {
    	Organizador oobj = manejadorUsuarios.buscarOrganizador(organizador);
    	Evento eobj = manejadorEventos.buscarEvento(nombre);
    	oobj.addEventos(eobj);
    }
    
    @Override
    public List<String> listarEdicionesIngresadas(String nombreEvento) {
        List<EdicionDTO> todas = listarEdicionesDeEventoDTO(nombreEvento); // método que ya tengas
        List<String> ingresadas = new ArrayList<>();

        for (EdicionDTO ed : todas) {
            if (ed.getEstado() == EstadoEnum.INGRESADA.toString()) {
                ingresadas.add(ed.getNombre());
            }
        }

        return ingresadas;
    }
    
    /**
     * Finalizar un evento (no se podrán crear más ediciones)
     */
    public void finalizarEvento(String nombreEvento) throws BusinessException {
        Evento evento = manejadorEventos.buscarEvento(nombreEvento);
        if (evento == null) {
            throw new BusinessException("Evento no encontrado: " + nombreEvento);
        }
        evento.setFinalizado(true);
    }
    
    /**
     * Archivar una edición de evento (persistir en BD y remover de memoria)
     * NOTA: La persistencia real se implementará en la fase de JPA
     */
    public void archivarEdicion(String nombreEdicion, String nicknameOrganizador) throws BusinessException {
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion == null) {
            throw new BusinessException("Edición no encontrada: " + nombreEdicion);
        }
        
        // Verificar que el organizador es el dueño de la edición
        if (!edicion.getOrganizador().equals(nicknameOrganizador)) {
            throw new BusinessException("Solo el organizador de la edición puede archivarla");
        }
        
        // Verificar que la edición está aprobada y ya finalizó
        if (edicion.getEstado() != EstadoEnum.ACEPTADA) {
            throw new BusinessException("Solo se pueden archivar ediciones aprobadas");
        }
        
        if (edicion.getFechaFin().isAfter(LocalDate.now())) {
            throw new BusinessException("Solo se pueden archivar ediciones finalizadas");
        }
        
        // Marcar como archivada
        edicion.setArchivada(true);
        edicion.setFechaArchivo(LocalDate.now());
        
        // TODO: Persistir en BD usando JPA (fase posterior)
        // Por ahora solo marcamos la edición
    }
    
    /**
     * Registrar asistencia de un asistente a una edición
     */
    public void registrarAsistencia(String nicknameAsistente, String nombreEdicion) throws BusinessException {
        Asistente asistente = manejadorUsuarios.buscarAsistente(nicknameAsistente);
        if (asistente == null) {
            throw new BusinessException("Asistente no encontrado: " + nicknameAsistente);
        }
        
        Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
        if (edicion == null) {
            throw new BusinessException("Edición no encontrada: " + nombreEdicion);
        }
        
        // Buscar el registro del asistente para esta edición
        boolean encontrado = false;
        for (Registro registro : asistente.getRegistros()) {
            if (registro.getEdicion().getNombre().equals(edicion.getNombre())) {
                registro.setAsistio(true);
                encontrado = true;
                break;
            }
        }
        
        if (!encontrado) {
            throw new BusinessException("El asistente no está registrado en esta edición");
        }
    }
    
    // Mapa para tracking de visitas (concurrent para thread-safety)
    private static final java.util.concurrent.ConcurrentHashMap<String, Integer> visitasEventos = 
        new java.util.concurrent.ConcurrentHashMap<>();
    
    /**
     * Registrar visita a un evento
     */
    public void registrarVisita(String nombreEvento) {
        visitasEventos.merge(nombreEvento, 1, Integer::sum);
    }
    
    /**
     * Obtener eventos más visitados (para tracking)
     */
    public List<String[]> obtenerEventosMasVisitados(int cantidad) {
        return visitasEventos.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(cantidad)
            .map(entry -> new String[]{entry.getKey(), String.valueOf(entry.getValue())})
            .collect(Collectors.toList());
    }
    
    /**
     * Obtener cantidad de visitas de un evento
     */
    public int obtenerVisitasDeEvento(String nombreEvento) {
        return visitasEventos.getOrDefault(nombreEvento, 0);
    }
    
    /**
     * Establecer cantidad de visitas de un evento (útil para carga de datos)
     */
    public void setVisitasEvento(String nombreEvento, int visitas) {
        visitasEventos.put(nombreEvento, visitas);
    }
    
    /**
     * Buscar eventos y ediciones por query (nombre o descripción)
     */
    public List<Object> buscarEventosYEdiciones(String query) {
        List<Object> resultados = new ArrayList<>();
        
        if (query == null || query.trim().isEmpty()) {
            // Si no hay query, retornar todos
            resultados.addAll(listarEventosDTO());
            resultados.addAll(listarTodasLasEdicionesDTO());
        } else {
            String queryLower = query.toLowerCase();
            
            // Buscar eventos que coincidan
            for (EventoDTO evento : listarEventosDTO()) {
                boolean coincide = false;
                if (evento.getNombre() != null && evento.getNombre().toLowerCase().contains(queryLower)) {
                    coincide = true;
                }
                if (evento.getDescripcion() != null && evento.getDescripcion().toLowerCase().contains(queryLower)) {
                    coincide = true;
                }
                
                // No incluir eventos finalizados
                if (coincide && !evento.isFinalizado()) {
                    resultados.add(evento);
                }
            }
            
            // Buscar ediciones que coincidan  
            for (EdicionDTO edicion : listarTodasLasEdicionesDTO()) {
                boolean coincide = false;
                if (edicion.getNombre() != null && edicion.getNombre().toLowerCase().contains(queryLower)) {
                    coincide = true;
                }
                if (edicion.getDescripcion() != null && edicion.getDescripcion().toLowerCase().contains(queryLower)) {
                    coincide = true;
                }
                
                // No incluir ediciones archivadas
                if (coincide && !edicion.isArchivada()) {
                    resultados.add(edicion);
                }
            }
        }
        
        return resultados;
    }

}

