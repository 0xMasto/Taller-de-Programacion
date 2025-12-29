package logica.persistence;

import jakarta.persistence.*;

/**
 * JPA Entity for archived users (base class with JOINED inheritance)
 */
@Entity
@Table(name = "USUARIO_ARCHIVADO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_USUARIO", discriminatorType = DiscriminatorType.STRING)
public abstract class UsuarioArchivado {
    
    @Id
    @Column(name = "NICKNAME", nullable = false, unique = true, length = 50)
    private String nickname;
    
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "CORREO", nullable = false, unique = true, length = 100)
    private String correo;
    
    @Column(name = "IMAGEN", length = 255)
    private String imagen;
    
    // Constructors
    public UsuarioArchivado() {}
    
    public UsuarioArchivado(String nickname, String nombre, String correo, String imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.imagen = imagen;
    }
    
    // Getters and Setters
    
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}

