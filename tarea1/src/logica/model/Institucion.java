package logica.model;


import logica.dto.InstitucionDTO;

/**
 * Entidad Institución con lógica de negocio (MVC Puro)
 */
public class Institucion {
    private String nombre;
    private String descripcion;
    private String sitioWeb;
    
    public Institucion(String nombre, String descripcion, String sitioWeb) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getSitioWeb() { return sitioWeb; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    
    /**
     * Convierte la institución a DTO
     */
    public InstitucionDTO toDTO() {
        return new InstitucionDTO(nombre, descripcion, sitioWeb);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Institucion that = (Institucion) obj;
        return nombre != null ? nombre.equals(that.nombre) : that.nombre == null;
    }
    
    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
    
    
}
