package logica.model;

import logica.dto.UsuarioDTO;
import java.util.HashSet;
import java.util.Set;


/**
 * Clase base abstracta para usuarios en el sistema
 */
public abstract class Usuario {
    private String nickname;
    private String nombre;
    private String correo;
    private String contrasenia;
    private String imagen;
    private Set<Usuario> seguidores;
    private Set<Usuario> seguidos;
    
    public Usuario(String nickname, String nombre, String correo, String contrasenia, String imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.setContrasenia(contrasenia);
        this.setImagen(imagen);
        this.seguidores = new HashSet<>();
        this.seguidos = new HashSet<>();
    }
    
    // Getters
    public String getNickname() { return nickname; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    
    // Setters (nickname y correo son inmutables después de la creación)
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    /**
     * Convierte el usuario a DTO
     * Las clases hijas deben sobrescribir este método para incluir información específica
     */
    public UsuarioDTO toDTO() {
        return new UsuarioDTO(nickname, nombre, correo, contrasenia, imagen, getTipoUsuario());
    }
    
    /**
     * Método abstracto para obtener el tipo de usuario
     * Debe ser implementado por las clases hijas
     */
    protected abstract String getTipoUsuario();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return nickname != null && nickname.equals(usuario.nickname);
    }

    @Override
    public int hashCode() {
        return nickname != null ? nickname.hashCode() : 0;
    }

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	// Métodos para manejar seguidores y seguidos
	public Set<Usuario> getSeguidores() {
		return seguidores;
	}
	
	public Set<Usuario> getSeguidos() {
		return seguidos;
	}
	
	public void agregarSeguidor(Usuario seguidor) {
		this.seguidores.add(seguidor);
	}
	
	public void removerSeguidor(Usuario seguidor) {
		this.seguidores.remove(seguidor);
	}
	
	public void agregarSeguido(Usuario seguido) {
		this.seguidos.add(seguido);
	}
	
	public void removerSeguido(Usuario seguido) {
		this.seguidos.remove(seguido);
	}
	
	public boolean estaSiguiendoA(Usuario usuario) {
		return this.seguidos.contains(usuario);
	}
}
