package logica.model;



import java.math.BigDecimal;
import java.time.LocalDate;

import logica.dto.RegistroDTO;
import logica.dto.EdicionDTO;
import logica.dto.TipoRegistroDTO;

/**
 * Entidad Registro con lógica de negocio (MVC Puro)
 */
public class Registro {
    private LocalDate fecha;
    private BigDecimal costo;
    private Edicion edicion;
    private TipoRegistro tipoRegistro;
    private boolean asistio;
    
    public Registro(LocalDate fecha, BigDecimal costo,  Edicion edicion, TipoRegistro tipoRegistro) {
        this.fecha = fecha;
        this.costo = costo;
        this.edicion = edicion;
        this.tipoRegistro = tipoRegistro;
        this.asistio = false;
        
        // Note: The registration type should already exist in the edition
        // We don't add it again here to avoid duplicates
    }
    
    // Getters
    public LocalDate getFecha() { return fecha; }
    public BigDecimal getCosto() { return costo; }
    //public Asistente getAsistente() { return asistente; }
    public Edicion getEdicion() { return edicion; }
    public TipoRegistro getTipoRegistro() { return tipoRegistro; }
    public boolean isAsistio() { return asistio; }
    
    // Setters
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public void setEdicion(Edicion edicion) { this.edicion = edicion; }
    public void setTipoRegistro(TipoRegistro tipoRegistro) { this.tipoRegistro = tipoRegistro; }
    public void setAsistio(boolean asistio) { this.asistio = asistio; }
    
    /**
     * Convierte el registro a DTO
     */
    public RegistroDTO toDTO() {
        String iden = edicion != null && tipoRegistro != null ? 
            edicion.getNombre() + "_" + tipoRegistro.getNombre() + "_" + fecha.toString() : "N/A";
        EdicionDTO edicionDTO = edicion != null ? edicion.toDTO() : null;
        TipoRegistroDTO tipoRegistroDTO = tipoRegistro != null ? tipoRegistro.toDTO() : null;
        RegistroDTO registro = new RegistroDTO(
        		iden,
            null, // AsistenteDTO - se debe establecer desde fuera si es necesario
            edicionDTO,
            tipoRegistroDTO,
            costo,
            edicionDTO.getNombre()
        );
        registro.setAsistio(isAsistio());
        return registro;
    }
    
    @Override
    public String toString() {
        return "Registro{" +
                "fecha=" + fecha +
                ", costo=" + costo +
                ", edicion=" + edicion +
                ", tipoRegistro=" + tipoRegistro +
                '}';
    }
}
