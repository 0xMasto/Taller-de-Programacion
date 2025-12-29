package logica.dto;


import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateTimeAdapter;

/**
 * Data Transfer Object para Usuario
 * Utilizado para transferir datos de usuario al frontend
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioDTO {
    private String nickname;
    private String nombre;
    private String correo;
    private String contrasenia;
    private String imagen;
    private String tipoUsuario;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaCreacion;
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaUltimaModificacion;
    
    // Constructor por defecto para serialización
    public UsuarioDTO() {}
    
    public UsuarioDTO(String nickname, String nombre, String correo, String contrasenia, String imagen, String tipoUsuario) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.imagen = imagen;
        this.tipoUsuario = tipoUsuario;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaUltimaModificacion = this.fechaCreacion;
    }
    
    // Getters y Setters
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }
    
    public void setFechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }
    
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "nickname='" + nickname + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
