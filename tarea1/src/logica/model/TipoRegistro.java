package logica.model;

import java.math.BigDecimal;

import logica.dto.TipoRegistroDTO;

/**
 * Entidad Tipo de Registro con lógica de negocio (MVC Puro)
 */
public class TipoRegistro {
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private int cupo;
    
    public TipoRegistro(String nombre, String descripcion, BigDecimal costo, int cupo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.cupo = cupo;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getCosto() { return costo; }
    public int getCupo() { return cupo; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public void setCupo(int cupo) { this.cupo = cupo; }
    
    /**
     * Convierte el tipo de registro a DTO
     */
    public TipoRegistroDTO toDTO() {
        return new TipoRegistroDTO(nombre, descripcion, costo, cupo);
    }
    
    @Override
    public String toString() {
        return "TipoRegistro{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costo=" + costo +
                ", cupo=" + cupo +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TipoRegistro that = (TipoRegistro) obj;
        return nombre != null && nombre.equals(that.nombre);
    }
        
    public TipoRegistroDTO getRegistroDTO() {
    	TipoRegistroDTO tiporegistro = new TipoRegistroDTO();
    	 //nombre, descripci´on, costo y cupo
    	tiporegistro.setNombre(this.nombre);
    	tiporegistro.setDescripcion(this.descripcion);
    	tiporegistro.setCosto(this.costo);
    	tiporegistro.setCupo(this.cupo);
    	return tiporegistro;
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
}
