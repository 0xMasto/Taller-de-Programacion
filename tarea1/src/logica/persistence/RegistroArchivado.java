package logica.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * JPA Entity for archived registrations
 */
@Entity
@Table(name = "REGISTRO_ARCHIVADO")
public class RegistroArchivado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro;
    
    @Column(name = "COSTO", precision = 10, scale = 2)
    private BigDecimal costo;
    
    @Column(name = "ASISTIO", nullable = false)
    private boolean asistio;
    
    @Column(name = "NOMBRE_TIPO_REGISTRO", nullable = false, length = 100)
    private String nombreTipoRegistro;
    
    // Foreign key to edition
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDICION_ID", nullable = false)
    private EdicionArchivada edicion;
    
    // Foreign key to asistente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASISTENTE_ID", nullable = false)
    private AsistenteArchivado asistente;
    
    // Constructors
    public RegistroArchivado() {}
    
    public RegistroArchivado(LocalDate fechaRegistro, BigDecimal costo, boolean asistio, 
                            String nombreTipoRegistro) {
        this.fechaRegistro = fechaRegistro;
        this.costo = costo;
        this.asistio = asistio;
        this.nombreTipoRegistro = nombreTipoRegistro;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    
    public boolean isAsistio() { return asistio; }
    public void setAsistio(boolean asistio) { this.asistio = asistio; }
    
    public String getNombreTipoRegistro() { return nombreTipoRegistro; }
    public void setNombreTipoRegistro(String nombreTipoRegistro) { this.nombreTipoRegistro = nombreTipoRegistro; }
    
    public EdicionArchivada getEdicion() { return edicion; }
    public void setEdicion(EdicionArchivada edicion) { this.edicion = edicion; }
    
    public AsistenteArchivado getAsistente() { return asistente; }
    public void setAsistente(AsistenteArchivado asistente) { this.asistente = asistente; }
}

