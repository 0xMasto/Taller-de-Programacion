package logica.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateAdapter;
import util.LocalDateTimeAdapter;

/**
 * Data Transfer Object para Edición
 * Utilizado para transferir datos de edición al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EdicionDTO {
    private String nombre;
    private String sigla;
    private String descripcion;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaInicio;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaFin;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaAlta;
    private String organizador;
    private String pais;
    private String ciudad;
    private String evento;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaCreacion;
    
    private String estado;
    private List<TipoRegistroDTO> tiposRegistro;
    private List<PatrocinioDTO> patrocinios;
    private int totalRegistros;
    private int cupoTotal;
    private String imagen;
    private String videoUrl;
    private boolean archivada;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaArchivo;
    
    // Constructor por defecto para serialización
    public EdicionDTO() {}
    
    public EdicionDTO(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                     String organizador, String ciudad, String pais, String evento, String imagen, String estado) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.evento = evento;
        this.fechaCreacion = LocalDateTime.now();
        this.imagen = imagen;
        this.estado = estado;
    }
    
    public EdicionDTO(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                     String organizador, String ciudad, String pais, String evento, String imagen) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.evento = evento;
        this.imagen = imagen;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "ACTIVA";
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getOrganizador() {
        return organizador;
    }
    
    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }
    
    public String getPais() {
        return pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getEvento() {
        return evento;
    }
    
    public void setEvento(String evento) {
        this.evento = evento;
    }
    
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<TipoRegistroDTO> getTiposRegistro() {
        return tiposRegistro;
    }
    
    public void setTiposRegistro(List<TipoRegistroDTO> tiposRegistro) {
        this.tiposRegistro = tiposRegistro;
    }
    
    public List<PatrocinioDTO> getPatrocinios() {
        return patrocinios;
    }
    
    public void setPatrocinios(List<PatrocinioDTO> patrocinios) {
        this.patrocinios = patrocinios;
    }
    
    public int getTotalRegistros() {
        return totalRegistros;
    }
    
    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
    
    public int getCupoTotal() {
        return cupoTotal;
    }
    
    public void setCupoTotal(int cupoTotal) {
        this.cupoTotal = cupoTotal;
    }
    
    public boolean esActiva() {
        if (fechaInicio == null || fechaFin == null) {
            return "ACTIVA".equals(estado);
        }
        LocalDate hoy = LocalDate.now();
        return "ACTIVA".equals(estado) && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }
    
    public boolean esFutura() {
        return LocalDate.now().isBefore(fechaInicio);
    }
    
    public boolean esPasada() {
        return LocalDate.now().isAfter(fechaFin);
    }
    
    public boolean estaEnCurso() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }
    
    public boolean tieneCupoDisponible() {
        return totalRegistros < cupoTotal;
    }
    
    public int getCupoDisponible() {
        return Math.max(0, cupoTotal - totalRegistros);
    }
    
    public long getDuracionEnDias() {
        return java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
    }
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    public String getVideoUrl() {
        return videoUrl;
    }
    
    public boolean isArchivada() {
        return archivada;
    }
    
    public void setArchivada(boolean archivada) {
        this.archivada = archivada;
    }
    
    public LocalDate getFechaArchivo() {
        return fechaArchivo;
    }
    
    public void setFechaArchivo(LocalDate fechaArchivo) {
        this.fechaArchivo = fechaArchivo;
    }
    
}
