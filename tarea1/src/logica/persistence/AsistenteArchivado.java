package logica.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * JPA Entity for archived assistants (JOINED inheritance)
 */
@Entity
@Table(name = "ASISTENTE_ARCHIVADO")
@DiscriminatorValue("ASISTENTE")
public class AsistenteArchivado extends UsuarioArchivado {
    
    @Column(name = "APELLIDO", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;
    
    @Column(name = "NOMBRE_INSTITUCION", length = 100)
    private String nombreInstitucion;
    
    // Constructors
    public AsistenteArchivado() {}
    
    public AsistenteArchivado(String nickname, String nombre, String correo, String imagen,
                             String apellido, LocalDate fechaNacimiento, String nombreInstitucion) {
        super(nickname, nombre, correo, imagen);
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nombreInstitucion = nombreInstitucion;
    }
    
    // Getters and Setters
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getNombreInstitucion() { return nombreInstitucion; }
    public void setNombreInstitucion(String nombreInstitucion) { this.nombreInstitucion = nombreInstitucion; }
}

