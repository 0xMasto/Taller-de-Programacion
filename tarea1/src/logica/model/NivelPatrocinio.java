package logica.model;

/**
 * Enum para los niveles de patrocinio
 */
public enum NivelPatrocinio {
    BRONCE("Bronce"),
    PLATA("Plata"),
    ORO("Oro"),
    PLATINO("Platino");
    
    private final String descripcion;
    
    NivelPatrocinio(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString() {
        return name(); // Returns the enum constant name (BRONCE, PLATA, ORO)
    }
}
