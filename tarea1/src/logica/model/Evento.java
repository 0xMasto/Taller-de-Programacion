package logica.model;

import logica.dto.CategoriaDTO;
import logica.dto.EventoDTO;
import logica.dto.EdicionDTO;
import exception.BusinessException;

import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;
/**
 * Entidad Evento con lógica de negocio (MVC Puro)
 */
public class Evento {
    private String nombre;
    private String sigla;
    private String descripcion;
    private LocalDate fechaAlta;
    private List<Categoria> categorias;
    private List<Edicion> ediciones;
    private String imagen;
    private boolean finalizado;
    
    public Evento(String nombre, String sigla, String descripcion, List<Categoria> categorias) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.fechaAlta = LocalDate.now();
        this.categorias = categorias;
        this.ediciones = new ArrayList<Edicion>();
        this.finalizado = false;
    }
    public Evento(String nombre, String sigla, String descripcion, List<Categoria> categorias, String imagen) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.fechaAlta = LocalDate.now();
        this.categorias = categorias;
        this.ediciones = new ArrayList<Edicion>();
        this.imagen = imagen;
        this.finalizado = false;
    }
    
    public Evento(String nombre, String sigla, String descripcion, List<Categoria> categorias, String imagen, LocalDate fechaAlta) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.categorias = categorias;
        this.ediciones = new ArrayList<Edicion>();
        this.imagen = imagen;
        this.finalizado = false;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getSigla() { return sigla; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public List<Categoria> getCategorias() { return categorias; }
    public List<Edicion> getEdiciones() { return ediciones; }
    public String getImagen() { return imagen; }
    public boolean isFinalizado() { return finalizado; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setSigla(String sigla) { this.sigla = sigla; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setFinalizado(boolean finalizado) { this.finalizado = finalizado; }
    
    public void agregarEdicion(Edicion edicion) {
        ediciones.add(edicion);
    }
    
 
    
    /**
     * Crear una nueva Edición de Evento
     */
    public Edicion crearEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                               String organizador, String ciudad, String pais) throws BusinessException {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new BusinessException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        
        Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, organizador, ciudad, pais);
        agregarEdicion(edicion);
        return edicion;
    }
    
    

    public EventoDTO toDTO() {
        List<CategoriaDTO> categoriasDTO = categorias.stream()
                .map(Categoria::toDTO)
                .collect(java.util.stream.Collectors.toList());
        List<EdicionDTO> edicionesDTO = ediciones.stream()
                .map(Edicion::toDTO)
                .collect(java.util.stream.Collectors.toList());
        EventoDTO eventoDTO = new EventoDTO(nombre, sigla, descripcion, categoriasDTO, edicionesDTO, imagen);
        eventoDTO.setFechaAlta(fechaAlta);
        eventoDTO.setFinalizado(finalizado);
        return eventoDTO;
    }
     

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evento evento = (Evento) obj;
        return nombre != null && nombre.equals(evento.nombre);
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
}
