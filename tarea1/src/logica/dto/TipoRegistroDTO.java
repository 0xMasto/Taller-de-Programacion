package logica.dto;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Data Transfer Object para Tipo de Registro
 * Utilizado para transferir datos de tipo de registro al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoRegistroDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private int cupo;
    private int cupoDisponible;
    
    // Constructor por defecto para serialización
    public TipoRegistroDTO() {}
    
    public TipoRegistroDTO(String nombre, String descripcion, BigDecimal costo, int cupo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.cupo = cupo;
        this.cupoDisponible = cupo;
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
    
    public BigDecimal getCosto() {
        return costo;
    }
    
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    
    public int getCupo() {
        return cupo;
    }
    
    public void setCupo(int cupo) {
        this.cupo = cupo;
    }
    
    public int getCupoDisponible() {
        return cupoDisponible;
    }
    
    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }
   
   
    public boolean tieneCupoDisponible() {
        return cupoDisponible > 0;
    }
  
    
    
    
   
  
}
