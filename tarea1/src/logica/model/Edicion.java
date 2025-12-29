package logica.model;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import exception.TipoRegistroDuplicadoException;
import logica.dto.PatrocinioDTO;
import logica.dto.EdicionDTO;
import logica.dto.TipoRegistroDTO;


/**
 * Entidad Edición de Evento con lógica de negocio (MVC Puro)
 */
public class Edicion {
    private String nombre;
    private String sigla;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaAlta;
    private String organizador;
    private String pais;
    private String ciudad;
    private String evento;
    private Set<TipoRegistro> tiposRegistro;
    private Set<Patrocinio> patrocinios;
    private EstadoEnum estado;
    private String imagen;
    private String videoUrl;
    private boolean archivada;
    private LocalDate fechaArchivo;
    
    public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                   String organizador, String ciudad, String pais) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = LocalDate.now();
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estado = EstadoEnum.INGRESADA;
        this.imagen = "-";
        this.videoUrl = null;
        this.archivada = false;
        this.fechaArchivo = null;
        //this.evento = evento;
        this.tiposRegistro = new HashSet<>();
        this.patrocinios = new HashSet<>();
    }
    
    public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                   LocalDate fechaAlta, String organizador, String ciudad, String pais) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estado = EstadoEnum.INGRESADA;
        this.imagen = "-";
        this.videoUrl = null;
        this.archivada = false;
        this.fechaArchivo = null;
        //this.evento = evento;
        this.tiposRegistro = new HashSet<>();
        this.patrocinios = new HashSet<>();
    }
    
    public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                   String organizador, String ciudad, String pais, String imagen) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = LocalDate.now();
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estado = EstadoEnum.INGRESADA;
        this.imagen = imagen;
        this.videoUrl = null;
        this.archivada = false;
        this.fechaArchivo = null;
        //this.evento = evento;
        this.tiposRegistro = new HashSet<>();
        this.patrocinios = new HashSet<>();
    }
    
    public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                   LocalDate fechaAlta, String organizador, String ciudad, String pais, String imagen) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.estado = EstadoEnum.INGRESADA;
        this.imagen = imagen;
        this.videoUrl = null;
        this.archivada = false;
        this.fechaArchivo = null;
        //this.evento = evento;
        this.tiposRegistro = new HashSet<>();
        this.patrocinios = new HashSet<>();
    }
    
    public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                   LocalDate fechaAlta, String organizador, String ciudad, String pais, String imagen, String estado) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.organizador = organizador;
        this.ciudad = ciudad;
        this.pais = pais;
        this.imagen = imagen;
        System.out.println("DEBUG: Creating Edicion '" + nombre + "' with estado parameter: '" + estado + "'");
        this.estado = EstadoEnum.valueOf(estado.toUpperCase());
        System.out.println("DEBUG: Edicion '" + nombre + "' estado field set to: " + this.estado);
        this.videoUrl = null;
        this.archivada = false;
        this.fechaArchivo = null;
        //this.evento = evento;
        this.tiposRegistro = new HashSet<>();
        this.patrocinios = new HashSet<>();
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getSigla() { return sigla; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public String getOrganizador() { return organizador; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public String getEvento() { return evento; }
    public Set<TipoRegistro> getTiposRegistro() { return tiposRegistro; }
    public Set<Patrocinio> getPatrocinios() { return patrocinios; }
    public String getImagen() { return imagen; }
    public String getVideoUrl() { return videoUrl; }
    public boolean isArchivada() { return archivada; }
    public LocalDate getFechaArchivo() { return fechaArchivo; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setSigla(String sigla) { this.sigla = sigla; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public void setOrganizador(String organizador) { this.organizador = organizador; }
    public void setPais(String pais) { this.pais = pais; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setEvento(String evento) { this.evento = evento; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public void setArchivada(boolean archivada) { this.archivada = archivada; }
    public void setFechaArchivo(LocalDate fechaArchivo) { this.fechaArchivo = fechaArchivo; }
    
// Métodos para gestionar relaciones
public boolean tienePatrocinioDeInstitucion(Institucion institucion) {
    return patrocinios.stream()
        .anyMatch(p -> p.getInstitucion().equals(institucion));
}
  public void agregarTipoRegistro(TipoRegistro tipoRegistro) throws TipoRegistroDuplicadoException {
        // Verificar si ya existe un tipo de registro con el mismo nombre
        for (TipoRegistro tr : this.tiposRegistro) {
            if (tr.getNombre().equalsIgnoreCase(tipoRegistro.getNombre())) {
                throw new TipoRegistroDuplicadoException("Ya existe un tipo de registro con el nombre: " + tipoRegistro.getNombre());
            }
        }
        
        this.tiposRegistro.add(tipoRegistro);
    }
    
     public TipoRegistro buscarTipoRegistroPorNombre(String nombre) {
        for (TipoRegistro tr : tiposRegistro) {
            if (tr.getNombre().equalsIgnoreCase(nombre)) {
                return tr;
            }
        }
        return null;
    }
    
    public void agregarPatrocinio(Patrocinio patrocinio) {
        this.patrocinios.add(patrocinio);
    }
    
    public void removerTipoRegistro(TipoRegistro tipoRegistro) {
        this.tiposRegistro.remove(tipoRegistro);
    }
    
    public void removerPatrocinio(Patrocinio patrocinio) {
        this.patrocinios.remove(patrocinio);
    }
    
    @Override
    public String toString() {
        return nombre + " (" + sigla + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edicion edicion = (Edicion) obj;
        return nombre != null && nombre.equals(edicion.nombre);
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

    public EdicionDTO toDTO() {
        List<TipoRegistroDTO> tiposRegistroDTO = tiposRegistro.stream()
                .map(TipoRegistro::toDTO)
                .collect(java.util.stream.Collectors.toList());

        List<PatrocinioDTO> patrociniosDTO = patrocinios.stream()
                .map(Patrocinio::toDTO)
                .collect(java.util.stream.Collectors.toList());
        
        String estadoStr = (estado != null) ? estado.toString() : "INGRESADA";
        EdicionDTO edicionDTO = new EdicionDTO(nombre, sigla, fechaInicio, fechaFin, organizador, ciudad, pais, evento, imagen, estadoStr);

        edicionDTO.setFechaAlta(fechaAlta);
        edicionDTO.setTiposRegistro(tiposRegistroDTO);
        edicionDTO.setPatrocinios(patrociniosDTO);
        edicionDTO.setVideoUrl(videoUrl);
        edicionDTO.setArchivada(archivada);
        edicionDTO.setFechaArchivo(fechaArchivo);
        
        return edicionDTO;
    }

	public EstadoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

}
