package logica.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import logica.dto.PatrocinioDTO;
import logica.dto.InstitucionDTO;

/**
 * Entidad Patrocinio con lógica de negocio (MVC Puro)
 */
public class Patrocinio {
    private String codigo;
    private LocalDate fecha;
    private BigDecimal monto;
    private NivelPatrocinio nivel;
    private int registrosGratuitos;
    private Edicion edicion;
    private Institucion institucion;
    private TipoRegistro tipoRegistro;
    
    public Patrocinio(String codigo, BigDecimal monto, NivelPatrocinio nivel, int registrosGratuitos,
                     Edicion edicion, Institucion institucion, TipoRegistro tipoRegistro) {
        this.codigo = codigo;
        this.fecha = LocalDate.now();
        this.monto = monto;
        this.nivel = nivel;
        this.registrosGratuitos = registrosGratuitos;
        this.edicion = edicion;
        this.institucion = institucion;
        this.tipoRegistro = tipoRegistro;
        
        // Establecer las relaciones bidireccionales
        if (edicion != null) {
            edicion.agregarPatrocinio(this);
        }
    }
    
    // Getters
    public String getCodigo() { return codigo; }
    public LocalDate getFecha() { return fecha; }
    public BigDecimal getMonto() { return monto; }
    public NivelPatrocinio getNivel() { return nivel; }
    public int getRegistrosGratuitos() { return registrosGratuitos; }
    public Edicion getEdicion() { return edicion; }
    public Institucion getInstitucion() { return institucion; }
    public TipoRegistro getTipoRegistro() { return tipoRegistro; }
    
    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public void setNivel(NivelPatrocinio nivel) { this.nivel = nivel; }
    public void setRegistrosGratuitos(int registrosGratuitos) { this.registrosGratuitos = registrosGratuitos; }
    public void setEdicion(Edicion edicion) { this.edicion = edicion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }
    
    public boolean validarMontoRegistros() {
        BigDecimal valorMaximoRegistros = monto.multiply(BigDecimal.valueOf(0.2));
        BigDecimal valorRegistros = tipoRegistro.getCosto()
                .multiply(BigDecimal.valueOf(registrosGratuitos));
        
        return valorRegistros.compareTo(valorMaximoRegistros) <= 0;
    }
/*     public boolean tieneRegistrosGratuitos() {
        int rg = this.registrosGratuitos + 1;
        if (registrosGratuitos > 0) {
            return true;
        }

        return false;
    } */

    /**
     * Convierte el patrocinio a DTO
     */
    public PatrocinioDTO toDTO() {
        String eventoNombre = edicion != null && edicion.getNombre() != null ? "Evento de " + edicion.getNombre() : "N/A";
        String edicionNombre = edicion != null ? edicion.getNombre() : "N/A";
        String tipoRegistroNombre = tipoRegistro != null ? tipoRegistro.getNombre() : "N/A";
        InstitucionDTO institucionDTO = institucion != null ? institucion.toDTO() : null;
        String nivelPatrocinio = nivel != null ? nivel.getDescripcion() : "N/A";
        
        return new PatrocinioDTO(
            codigo,
            eventoNombre,
            edicionNombre,
            tipoRegistroNombre,
            institucionDTO,
            nivelPatrocinio,
            monto,
            registrosGratuitos,
            codigo
        );
    }

    
   
}
