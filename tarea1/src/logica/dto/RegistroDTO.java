package logica.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateAdapter;


/**
 * Data Transfer Object para Registro
 * Utilizado para transferir datos de registro al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RegistroDTO {
    private String iden;
    private AsistenteDTO asistente;
    private EdicionDTO edicion;
    private String nombreEdicion;
    private TipoRegistroDTO tipoRegistro;
    
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaRegistro;
    
    private BigDecimal costo;
    private boolean asistio;
    
    // Constructor por defecto para serialización
    public RegistroDTO() {}
    
    public RegistroDTO(String iden, AsistenteDTO asistente, EdicionDTO edicion, 
                      TipoRegistroDTO tipoRegistro, BigDecimal costo, String nombreEdicion) {
        this.iden = iden;
        this.asistente = asistente;
        this.edicion = edicion;
        this.tipoRegistro = tipoRegistro;
        this.costo = costo;
        this.nombreEdicion = nombreEdicion;
        this.fechaRegistro = LocalDate.now();
    }
    

    
    // Getters y Setters
    public String getId() {
        return iden;
    }
    
    public void setId(String iden) {
        this.iden = iden;
    }
    
    public AsistenteDTO getAsistente() {
        return asistente;
    }
    
    public void setAsistente(AsistenteDTO asistente) {
        this.asistente = asistente;
    }
    
    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }
    
    public String getNombreEdicion() {
        return this.nombreEdicion;
    }
    
    
    public EdicionDTO getEdicion() {
        return edicion;
    }
    
    public void setEdicion(EdicionDTO edicion) {
        this.edicion = edicion;
    }
    
    public TipoRegistroDTO getTipoRegistro() {
        return tipoRegistro;
    }
    
    public void setTipoRegistro(TipoRegistroDTO tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public BigDecimal getCosto() {
        return costo;
    }
    
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    
    public String getFormattedCosto() {
        return "$" + costo.toString();
    }
    
    public boolean isAsistio() {
        return asistio;
    }
    
    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }
    
    
}
