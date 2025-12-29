package logica.model;



import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import logica.dto.RegistroDTO;

/**
 * Manejador de usuarios del sistema (Singleton)
 * Gestiona la creación y consulta de usuarios
 */
public class ManejadorUsuarios {
    private static ManejadorUsuarios instance;
    private Set<Asistente> asistentes;
    private Set<Organizador> organizadores;

    
    private ManejadorUsuarios() {
        this.asistentes = new HashSet<>();
        this.organizadores = new HashSet<>();
    }
    
    public static ManejadorUsuarios getInstance() {
        if (instance == null) {
            instance = new ManejadorUsuarios();
        }
        return instance;
    }
    
    /**
     * Reset the singleton instance for testing purposes
     */
    public static void resetInstance() {
        instance = null;
    }
    
    /**
     * Clear all data from the instance for testing purposes
     */
    public void clearAllData() {
        this.asistentes.clear();
        this.organizadores.clear();
    }
    
    // Métodos para gestionar asistentes
    public void agregarAsistente(Asistente asistente) {
        asistentes.add(asistente);
    }
    
    public Set<Asistente> consultarAsistentes() {
        return new HashSet<>(asistentes);
    }
    
    public Asistente buscarAsistente(String nicknameOEmail) {
        return asistentes.stream()
            .filter(a -> a.getNickname().equals(nicknameOEmail) || 
                         (a.getCorreo() != null && a.getCorreo().equalsIgnoreCase(nicknameOEmail)))
            .findFirst()
            .orElse(null);
    }
    
    // Métodos para gestionar organizadores
    public void agregarOrganizador(Organizador organizador) {
        organizadores.add(organizador);
    }
    
    public Set<Organizador> consultarOrganizadores() {
        return new HashSet<>(organizadores);
    }
    
    public Organizador buscarOrganizador(String nickname) {
        return organizadores.stream()
                .filter(o -> o.getNickname().equals(nickname) || 
                         (o.getCorreo() != null && o.getCorreo().equalsIgnoreCase(nickname)))
                .findFirst()
                .orElse(null);
    }
    
    // Método para crear y agregar un organizador
    public void altaOrganizador(String nickname, String nombre, String correo, String contrasenia, String imagen,
                                String descripcion, String sitioWeb) {
        Organizador organizador = new Organizador(nickname, nombre, correo, contrasenia, imagen, descripcion);
        if (sitioWeb != null && !sitioWeb.trim().isEmpty()) {
            organizador.setSitioWeb(sitioWeb);
        }
        agregarOrganizador(organizador);
    }
    
    // Método para crear y agregar un asistente
/*     public void altaAsistente(String nickname, String nombre, String correo, String contrasenia, String imagen,
                              String apellido, LocalDate fechaNacimiento) {
        Asistente asistente = new Asistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNacimiento);
        agregarAsistente(asistente);
    } */
    
    // Método para crear y agregar un asistente con institución
    public void altaAsistente(String nickname, String nombre, String correo, String contrasenia, String imagen,
                              String apellido, LocalDate fechaNacimiento, Institucion institucion) {
        Asistente asistente = new Asistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNacimiento, null);
        asistente.setInstitucion(institucion);
        agregarAsistente(asistente);
    }
    
    public List<String> listarOrganizadores() {
    	return organizadores.stream().map(Organizador::getNickname).collect(Collectors.toList());
    }

    public Set<String> listarAsistentes() {
    	return asistentes.stream().map(Asistente::getNickname).collect(Collectors.toSet());
    }
    
    //ESTA FUNCIONA ASUME QUE SIEMPRE NICKNAME PERTENECE A UN ASISTENTE VALIDO EN EL SISTEMA 
    public Set<RegistroDTO> getRegistrosPorAsistentes(String nickname){
    	// Handle null or empty nickname
    	if (nickname == null || nickname.trim().isEmpty()) {
    		return new HashSet<>();
    	}
    	
    	Asistente asistente = asistentes.stream().filter(a -> a.getNickname().equals(nickname)).findFirst().orElse(null);
    	if (asistente == null) {
    		return new HashSet<>();
    	}
    	
    	Set<Registro> registros = asistente.getRegistros();
    	Set<RegistroDTO> resul = new HashSet<RegistroDTO>();
    	for (Registro registro: registros) {
    		// Use the toDTO() method which properly converts all fields including EdicionDTO
    		RegistroDTO regis = registro.toDTO();
    		resul.add(regis);
    	}
		return resul;
	}
    
    public List<String> listarEventosPorOrganizador(String nickname) {
    	if (this.buscarOrganizador(nickname) != null) {
    		// Get events from ManejadorEventos that have editions by this organizer
    		return ManejadorEventos.getInstance().listarEventosPorOrganizador(nickname);
    	}
    	return new java.util.ArrayList<>();
    }

    
}
