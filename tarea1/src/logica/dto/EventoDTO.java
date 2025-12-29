package logica.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateAdapter;

/**
 * Data Transfer Object para Evento
 * Utilizado para transferir datos de evento al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EventoDTO {
    private String nombre;
    private String sigla;
    private String descripcion;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaAlta;
    private List<CategoriaDTO> categorias;
    private List<EdicionDTO> ediciones;
    private String imagen;
    private boolean finalizado;
    private int visitas;

    // Constructor por defecto para serialización
    public EventoDTO() {}
    public EventoDTO(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias, List<EdicionDTO> ediciones, String imagen){
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.categorias = categorias;
        this.ediciones = ediciones;
        this.imagen = imagen;
    }
    
    public EventoDTO(String nombre, String sigla, String descripcion, List<CategoriaDTO> categorias, List<EdicionDTO> ediciones) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.categorias = categorias;
        this.ediciones = ediciones;
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
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }
    
    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }
    
    public List<EdicionDTO> getEdiciones() {
        return ediciones;
    }
    
    public void setEdiciones(List<EdicionDTO> ediciones) {
        this.ediciones = ediciones;
    }
    
    public boolean isFinalizado() {
        return finalizado;
    }
    
    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
    
    public int getVisitas() {
        return visitas;
    }
    
    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }
    
    }
    
    

