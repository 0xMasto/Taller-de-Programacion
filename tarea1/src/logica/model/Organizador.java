package logica.model;

import java.util.List;
import java.util.stream.Collectors;
import logica.dto.OrganizadorDTO;

/**
 * Clase para usuarios organizadores en el sistema
 * Extiende la funcionalidad base de Usuario
 */
public class Organizador extends Usuario {
    private String descripcion;
    private String sitioWeb;
    private List<Evento> eventos;
    
    public Organizador(String nickname, String nombre, String correo, String contrasenia, String imagen, String descripcion) {
        super(nickname, nombre, correo, contrasenia, imagen);
        this.descripcion = descripcion;
        this.sitioWeb = "";
        this.eventos = null;
    }
    
    // Getters
    
    public String getDescripcion() {
    	return descripcion; 
    }
    
    public String getSitioWeb() { 
    	return sitioWeb; 
    }
    
    public List<Evento> getEventos() { 
    	return eventos; 
    }
    
    public void addEventos(Evento event) { 
    	this.eventos.add(event); 
    }
    
    public void remove(Evento event) { 
    	this.eventos.remove(event); 
    }
    
    public List<String> listarEventos() {
    	if (eventos == null) {
    		return new java.util.ArrayList<>();
    	}
    	return eventos.stream().map(Evento::getNombre).collect(Collectors.toList());
    }
    
    // Setters
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    
    /**
     * Convierte el organizador a DTO
     */
	@Override
    public OrganizadorDTO toDTO() {
        return new OrganizadorDTO(
            getNickname(), 
            getNombre(), 
            getCorreo(), 
            getContrasenia(),
            getImagen(),
            descripcion, 
            sitioWeb
        );
    }
    
    @Override
    protected String getTipoUsuario() {
        return "Organizador";
    }
    
    
}
