package logica.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateTimeAdapter;

/**
 * Data Transfer Object para Patrocinio
 * Utilizado para transferir datos de patrocinio al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PatrocinioDTO {
    private String iden;
    private String evento;
    private String edicion;
    private String tipoRegistro;
    private InstitucionDTO institucion;
    private String nivelPatrocinio;
    private BigDecimal aporteEconomico;
    private int cantidadRegistros;
    private String codigoPatrocinio;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaCreacion;
    
    private BigDecimal costoRegistros;
    private BigDecimal porcentajeAporte;
    
    // Constructor por defecto para serialización
    public PatrocinioDTO() {}
    
    public PatrocinioDTO(String identificador, String evento, String edicion, String tipoRegistro,
                        InstitucionDTO institucion, String nivelPatrocinio, 
                        BigDecimal aporteEconomico, int cantidadRegistros, String codigoPatrocinio) {
        this.iden = identificador;
        this.evento = evento;
        this.edicion = edicion;
        this.tipoRegistro = tipoRegistro;
        this.institucion = institucion;
        this.nivelPatrocinio = nivelPatrocinio;
        this.aporteEconomico = aporteEconomico;
        this.cantidadRegistros = cantidadRegistros;
        this.codigoPatrocinio = codigoPatrocinio;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getId() {
        return iden;
    }
    
    public void setId(String identificador) {
        this.iden = identificador;
    }
    
    public String getEvento() {
        return evento;
    }
    
    public void setEvento(String evento) {
        this.evento = evento;
    }
    
    public String getEdicion() {
        return edicion;
    }
    
    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }
    
    public String getTipoRegistro() {
        return tipoRegistro;
    }
    
    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
    
    public InstitucionDTO getInstitucion() {
        return institucion;
    }
    
    public void setInstitucion(InstitucionDTO institucion) {
        this.institucion = institucion;
    }
    
    public String getNivelPatrocinio() {
        return nivelPatrocinio;
    }
    
    public void setNivelPatrocinio(String nivelPatrocinio) {
        this.nivelPatrocinio = nivelPatrocinio;
    }
    
    public BigDecimal getAporteEconomico() {
        return aporteEconomico;
    }
    
    public void setAporteEconomico(BigDecimal aporteEconomico) {
        this.aporteEconomico = aporteEconomico;
    }
    
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }
    
    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }
    
    public String getCodigoPatrocinio() {
        return codigoPatrocinio;
    }
    
    public void setCodigoPatrocinio(String codigoPatrocinio) {
        this.codigoPatrocinio = codigoPatrocinio;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public BigDecimal getCostoRegistros() {
        return costoRegistros;
    }
    
    public void setCostoRegistros(BigDecimal costoRegistros) {
        this.costoRegistros = costoRegistros;
    }
    
    public BigDecimal getPorcentajeAporte() {
        return porcentajeAporte;
    }
    
    public void setPorcentajeAporte(BigDecimal porcentajeAporte) {
        this.porcentajeAporte = porcentajeAporte;
    }
    
    public boolean excedeLimitePorcentaje() {
        return porcentajeAporte != null && porcentajeAporte.compareTo(new BigDecimal("20")) > 0;
    }
    
    public boolean esPatrocinioAlto() {
        return "ALTO".equalsIgnoreCase(nivelPatrocinio) || "PREMIUM".equalsIgnoreCase(nivelPatrocinio);
    }
    
    public String getFormattedAporte() {
        return "$" + aporteEconomico.toString();
    }
    
    public String getFormattedCostoRegistros() {
        if (costoRegistros == null) return "N/A";
        return "$" + costoRegistros.toString();
    }
    
    public String getFormattedPorcentaje() {
        if (porcentajeAporte == null) return "N/A";
        return porcentajeAporte.toString() + "%";
    }
    
    public BigDecimal getValorRegistro() {
        if (cantidadRegistros == 0) return BigDecimal.ZERO;
        return aporteEconomico.divide(new BigDecimal(cantidadRegistros), 2, java.math.RoundingMode.HALF_UP);
    }
    
}
