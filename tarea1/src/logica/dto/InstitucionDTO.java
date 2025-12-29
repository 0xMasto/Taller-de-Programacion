package logica.dto;


import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateTimeAdapter;

/**
 * Data Transfer Object para Institución
 * Utilizado para transferir datos de institución al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitucionDTO {
    private String nombre;
    private String descripcion;
    private String sitioWeb;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaCreacion;
    
    private int totalUsuarios;
    private int totalPatrocinios;
    
    // Constructor por defecto para serialización
    public InstitucionDTO() {}
    
    public InstitucionDTO(String nombre, String descripcion, String sitioWeb) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getSitioWeb() {
        return sitioWeb;
    }
    
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public int getTotalUsuarios() {
        return totalUsuarios;
    }
    
    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }
    
    public int getTotalPatrocinios() {
        return totalPatrocinios;
    }
    
    public void setTotalPatrocinios(int totalPatrocinios) {
        this.totalPatrocinios = totalPatrocinios;
    }
    
    public boolean tieneSitioWeb() {
        return sitioWeb != null && !sitioWeb.trim().isEmpty();
    }
    
    public boolean esInstitucionGrande() {
        return totalUsuarios > 100;
    }
    
   
}
