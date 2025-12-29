package logica.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for archived edition
 * Stores historical data of archived editions
 */
@Entity
@Table(name = "EDICION_ARCHIVADA")
public class EdicionArchivada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @Column(name = "SIGLA", length = 50)
    private String sigla;
    
    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDate fechaInicio;
    
    @Column(name = "FECHA_FIN", nullable = false)
    private LocalDate fechaFin;
    
    @Column(name = "FECHA_ALTA")
    private LocalDate fechaAlta;
    
    @Column(name = "FECHA_ARCHIVO", nullable = false)
    private LocalDate fechaArchivo;
    
    @Column(name = "CIUDAD", length = 100)
    private String ciudad;
    
    @Column(name = "PAIS", length = 100)
    private String pais;
    
    @Column(name = "IMAGEN", length = 255)
    private String imagen;
    
    @Column(name = "VIDEO_URL", length = 500)
    private String videoUrl;
    
    @Column(name = "NOMBRE_EVENTO", nullable = false, length = 100)
    private String nombreEvento;
    
    // Foreign key to organizer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORGANIZADOR_ID", nullable = false)
    private OrganizadorArchivado organizador;
    
    // One to many relationship with registrations
    @OneToMany(mappedBy = "edicion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RegistroArchivado> registros = new HashSet<>();
    
    // Constructors
    public EdicionArchivada() {}
    
    public EdicionArchivada(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
                           LocalDate fechaAlta, String ciudad, String pais, String imagen, 
                           String videoUrl, String nombreEvento, LocalDate fechaArchivo) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.ciudad = ciudad;
        this.pais = pais;
        this.imagen = imagen;
        this.videoUrl = videoUrl;
        this.nombreEvento = nombreEvento;
        this.fechaArchivo = fechaArchivo;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    
    public LocalDate getFechaArchivo() { return fechaArchivo; }
    public void setFechaArchivo(LocalDate fechaArchivo) { this.fechaArchivo = fechaArchivo; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    
    public String getNombreEvento() { return nombreEvento; }
    public void setNombreEvento(String nombreEvento) { this.nombreEvento = nombreEvento; }
    
    public OrganizadorArchivado getOrganizador() { return organizador; }
    public void setOrganizador(OrganizadorArchivado organizador) { this.organizador = organizador; }
    
    public Set<RegistroArchivado> getRegistros() { return registros; }
    public void setRegistros(Set<RegistroArchivado> registros) { this.registros = registros; }
    
    public void addRegistro(RegistroArchivado registro) {
        this.registros.add(registro);
        registro.setEdicion(this);
    }
}

