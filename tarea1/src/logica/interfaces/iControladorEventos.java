package logica.interfaces;


import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import logica.dto.CategoriaDTO;
import logica.dto.EventoDTO;
import logica.dto.InstitucionDTO;
import exception.BusinessException;
import exception.NombreEdicionException;
import exception.CupoLlenoException;
import exception.RegistroDuplicadoException;
import exception.TipoRegistroDuplicadoException;
import exception.CostoRegistrosExcedidoException;
import exception.PatrocinioDuplicadoException;
import logica.model.Edicion;
import logica.model.EstadoEnum;
import logica.model.Evento;
import logica.model.Institucion;
import logica.model.Registro;
import logica.model.Patrocinio;


import logica.dto.EdicionDTO;
import logica.dto.TipoRegistroDTO;
import logica.dto.PatrocinioDTO;
import logica.dto.RegistroDTO;

public interface iControladorEventos {

    public void altaEvento(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias) throws BusinessException;
    public void altaEvento(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias, String imagen) throws BusinessException;
    public void altaEvento(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias, String imagen, LocalDate fechaAlta) throws BusinessException;

    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            String nombreOrganizador, String ciudad, String pais, String nombreEvento) throws NombreEdicionException;
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            String nombreOrganizador, String ciudad, String pais, String nombreEvento, String imagen) throws NombreEdicionException;
  
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String nombreOrganizador, String ciudad, String pais, String nombreEvento) throws NombreEdicionException;
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String nombreOrganizador, String ciudad, String pais, String nombreEvento, String imagen) throws NombreEdicionException;
    
    public void altaEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String nombreOrganizador, String ciudad, String pais, String nombreEvento, String imagen, String estado) throws NombreEdicionException;
    
    public InstitucionDTO getInstitucionDTO(String nombre);
    //public List<String> obtenerEdicionesDeEvento(String eventoSeleccionado);
    public Evento buscarEvento(String nombre);

    public EventoDTO getEventoDTO(String nombre);
   
    public Edicion buscarEdicion(String nombre);
    
    public List<String> listarEventos();
    
    public List<String> listarInstituciones();
    
    public Institucion buscarInstitucion(String nombre);

    public boolean existeInstitucion(String nombre);
    public InstitucionDTO buscarInstitucionDTO(String nombre);
    
    public void altaCategoria(String nombre) throws BusinessException;
    
    public List<CategoriaDTO> listarCategoriasDTO();
    
    public List<String> listarEdicionesDeEvento(String nombreEvento);
    
    public Set<Evento> obtenerTodosLosEventos();
    
    public Set<String> obtenerTiposDeRegistroDeEdicion(String nombreEdicion);
    
    public void registrarAsistente(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro) throws CupoLlenoException, RegistroDuplicadoException;
    
    public void registrarAsistenteConAsistente(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, Boolean asistio) throws CupoLlenoException, RegistroDuplicadoException;
    
    public Set<Evento> obtenerTodosLosEventosSet();
    
    public void altaTipoRegistro(String nombreEvento, String nombreEdicion, String nombre, 
                                String descripcion, BigDecimal costo, int cupo) 
                                throws TipoRegistroDuplicadoException, IllegalArgumentException;
    
    public boolean altaInstitucion(String nombre, String descripcion, String sitioWeb);
    
    public BigDecimal calcularCostoRegistros(String nombreEvento, String nombreEdicion, String nombreTipoRegistro, int cantidad);
    
    public BigDecimal calcularPorcentajeAporte(BigDecimal aporteEconomico, BigDecimal costoRegistros);
    
    public void altaPatrocinio(String nombreEvento, String nombreEdicion, String nombreTipoRegistro, String nombreInstitucion, String nivelPatrocinio, BigDecimal aporteEconomico, int cantidadRegistros, String codigoPatrocinio) throws CostoRegistrosExcedidoException, PatrocinioDuplicadoException;
    
    public List<String> listarPatrocinios();
    
    /**
     * Buscar patrocinio por código y retornar DTO
     */
    public PatrocinioDTO buscarPatrocinioDTO(String codigoPatrocinio);
    
    /**
     * Buscar patrocinio por código y retornar el modelo completo
     */
    public Patrocinio buscarPatrocinio(String codigoPatrocinio);
    
    /**
     * Get all ediciones organized by a specific organizador
     */
    public List<String> obtenerEdicionesOrganizadasPor(String nicknameOrganizador);
    
    /**
     * Obtiene todos los registros de una edición específica
     */
    public List<Registro> obtenerRegistrosDeEdicion(String nombreEdicion);
    
    // New DTO methods
    public EventoDTO buscarEventoDTO(String nombre);
    
    public EdicionDTO buscarEdicionDTO(String nombre);
    
    public List<EventoDTO> listarEventosDTO();
    
    public List<EdicionDTO> listarTodasLasEdicionesDTO();
    
    public List<EdicionDTO> listarEdicionesDeEventoDTO(String nombreEvento);
    
    public List<TipoRegistroDTO> obtenerTiposDeRegistroDeEdicionDTO(String nombreEdicion);
    
    public List<PatrocinioDTO> obtenerPatrociniosDeEdicionDTO(String nombreEdicion);
    
    /**
     * Obtiene todos los registros de una edición específica como DTOs
     */
    public List<RegistroDTO> obtenerRegistrosDeEdicionDTO(String nombreEdicion);
    
    /**
     * Buscar un tipo de registro específico por edición y nombre
     * @param nombreEdicion Nombre de la edición
     * @param nombreTipo Nombre del tipo de registro
     * @return TipoRegistroDTO con información completa incluyendo cupo disponible calculado
     */
    public TipoRegistroDTO buscarTipoRegistroDTO(String nombreEdicion, String nombreTipo);
    
    //espero que no se haya roto todo
    public void cambiarEstadoEdicion(String nombreEdicion, EstadoEnum nuevoEstado);
	
    public List<String> listarEdicionesIngresadas(String nombreEvento);
    
    /**
     * Finalizar un evento (no se podrán crear más ediciones)
     */
    public void finalizarEvento(String nombreEvento) throws BusinessException;
    
    /**
     * Archivar una edición de evento (persistir en BD y remover de memoria)
     */
    public void archivarEdicion(String nombreEdicion, String nicknameOrganizador) throws BusinessException;
    
    /**
     * Registrar asistencia de un asistente a una edición
     */
    public void registrarAsistencia(String nicknameAsistente, String nombreEdicion) throws BusinessException;
    
    /**
     * Obtener eventos más visitados (para tracking)
     */
    public List<String[]> obtenerEventosMasVisitados(int cantidad);
    
    /**
     * Registrar visita a un evento
     */
    public void registrarVisita(String nombreEvento);
    
    /**
     * Obtener cantidad de visitas de un evento
     */
    public int obtenerVisitasDeEvento(String nombreEvento);
    
    /**
     * Establecer cantidad de visitas de un evento (útil para carga de datos)
     */
    public void setVisitasEvento(String nombreEvento, int visitas);
    
    /**
     * Buscar eventos y ediciones por query (nombre o descripción)
     */
    public List<Object> buscarEventosYEdiciones(String query);

}
