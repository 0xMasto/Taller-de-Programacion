package logica.controllers;

import logica.interfaces.iControladorUsuarios;
import logica.interfaces.iControladorEventos;
import logica.interfaces.Fabrica;
import logica.model.ManejadorUsuarios;
import logica.model.ManejadorEventos;
import logica.model.Asistente;
import logica.model.Organizador;

import logica.model.Registro;
import logica.model.Institucion;
import exception.BusinessException;
import logica.dto.AsistenteDTO;
import logica.dto.RegistroDTO;
import logica.dto.OrganizadorDTO;
import logica.dto.InstitucionDTO;




import java.time.LocalDate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Controlador para la gestión de usuarios
 * Implementa la lógica de presentación siguiendo el patrón MVC
 */
public class ControladorUsuarios implements iControladorUsuarios {
	 private final ManejadorUsuarios manejadorUsuarios;
		
	    
	    public ControladorUsuarios(ManejadorUsuarios manejadorUsuarios, ManejadorEventos controladorEventos) {
	        this.manejadorUsuarios = manejadorUsuarios;
	       
	    }
	    
	    /**
	     * Listar todos los usuarios del sistema
	     */
	    public List<String> listarUsuarios() {
	        Set<Asistente> asistentes = manejadorUsuarios.consultarAsistentes();
	        Set<Organizador> organizadores = manejadorUsuarios.consultarOrganizadores();
	        
	        List<String> usuarios = asistentes.stream()
	                .map(a -> "Asistente: " + a.getNickname() + " - " + a.getNombre() + 
	                     (a.getInstitucion() != null ? " (" + a.getInstitucion().getNombre() + ")" : ""))
	                .collect(Collectors.toList());
	        
	        usuarios.addAll(organizadores.stream()
	                .map(o -> "Organizador: " + o.getNickname() + " - " + o.getNombre())
	                .collect(Collectors.toList()));
	        
	        return usuarios;
	    }
	    
	    /**
	     * Consultar información de un usuario específico
	     */
	    public Set<String> consultarUsuario(String nickname) {
	        Set<String> info = new java.util.HashSet<>();
	        
	        Asistente asistente = manejadorUsuarios.buscarAsistente(nickname);
	        if (asistente != null) {
	            info.add("Tipo: Asistente");
	            info.add("Nickname: " + asistente.getNickname());
	            info.add("Nombre: " + asistente.getNombre());
	            info.add("Correo: " + asistente.getCorreo());
	            info.add("Apellido: " + asistente.getApellido());
	            info.add("Fecha de Nacimiento: " + asistente.getFechaNacimiento());
	            if (asistente.getInstitucion() != null) {
	                info.add("Institución: " + asistente.getInstitucion().getNombre());
	                info.add("Descripción Institución: " + asistente.getInstitucion().getDescripcion());
	            }
	        }
	        
	        Organizador organizador = manejadorUsuarios.buscarOrganizador(nickname);
	        if (organizador != null) {
	            info.add("Tipo: Organizador");
	            info.add("Nickname: " + organizador.getNickname());
	            info.add("Nombre: " + organizador.getNombre());
	            info.add("Correo: " + organizador.getCorreo());
	            info.add("Descripción: " + organizador.getDescripcion());
	            info.add("Sitio Web: " + organizador.getSitioWeb());
	        }
	        
	        return info;
	    }
	    
    /**
     * Crear un nuevo asistente con institución
     */
    public void altaAsistente(String nickname, String nombre, String correo, String contrasenia, String imagen,
                              String apellido, LocalDate fechaNacimiento, String inst) throws BusinessException {
        // Add validation
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new BusinessException("El nickname es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre es obligatorio");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new BusinessException("El correo es obligatorio");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new BusinessException("El apellido es obligatorio");
        }
        if (fechaNacimiento == null) {
            throw new BusinessException("La fecha de nacimiento es obligatoria");
        }
        
        // Check for duplicate nickname
        if (existeNickname(nickname)) {
            throw new BusinessException("El nickname ya existe en el sistema");
        }
		
		iControladorEventos controladorEventos = Fabrica.getInstancia().getControladorEventos();
		Institucion institucion = controladorEventos.buscarInstitucion(inst);
        
        manejadorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNacimiento, institucion);
    }
	
	/**  CREO QUE NO SE USA ESTA 
	     * Crear un nuevo asistente
	     
	    public void altaAsistente(String nickname, String nombre, String correo,  String contrasenia, String imagen,
	                              String apellido,LocalDate fechaNacimiento) throws BusinessException {
	        manejadorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNacimiento, null);
	    }  

	    */
    public void altaOrganizador(String nickname, String nombre, String correo, String contrasenia, String imagen,
                                String descripcion, String sitioWeb) {
        manejadorUsuarios.altaOrganizador(nickname, nombre, correo, contrasenia, imagen, descripcion, sitioWeb);
    }
	    
	    public List<String> listarOrganizadores(){
	    	return manejadorUsuarios.listarOrganizadores();
	    }

	    public Set<String> listarAsistentes() {
	        return manejadorUsuarios.listarAsistentes();
	    }
	    
	    public Set<Asistente> consultarAsistentes() {
	        return manejadorUsuarios.consultarAsistentes();
	    }
	    
	    public Set<Organizador> consultarOrganizadores() {
	        return manejadorUsuarios.consultarOrganizadores();
	    }
	    
	    public Asistente buscarAsistente(String nickname) {
	        return manejadorUsuarios.buscarAsistente(nickname);
	    }
	    
	    public Organizador buscarOrganizador(String nickname) {
	        return manejadorUsuarios.buscarOrganizador(nickname);
	    }
	    
	    public boolean existeNickname(String nickname) {
	        Asistente asistente = manejadorUsuarios.buscarAsistente(nickname);
	        if (asistente != null) {
	            return true; // ya existe como asistente
	        }

	        Organizador organizador = manejadorUsuarios.buscarOrganizador(nickname);
	        if (organizador != null) {
	            return true; // ya existe como organizador
	        }

	        return false; // no encontrado
	    }
		
		public boolean existeCorreo(String correo){
			// Revisar asistentes
		    for (Asistente a : manejadorUsuarios.consultarAsistentes()) {
		        if (a.getCorreo().equalsIgnoreCase(correo)) return true;
		    }
		    // Revisar organizadores
		    for (Organizador o : manejadorUsuarios.consultarOrganizadores()) {
		        if (o.getCorreo().equalsIgnoreCase(correo)) return true;
		    }
		    return false;
		}

	    public List<String> obtenerTodosLosRegistros() {
	        return manejadorUsuarios.consultarAsistentes().stream()
	                .flatMap(asistente -> asistente.getRegistros().stream())
	                .map(Registro::toString)
	                .collect(Collectors.toList());
	    }

    private AsistenteDTO convertirAsistenteADTO(Asistente asistente) {
        AsistenteDTO dto = new AsistenteDTO();
        dto.setNickname(asistente.getNickname());
        dto.setNombre(asistente.getNombre());
        dto.setCorreo(asistente.getCorreo());
        dto.setApellido(asistente.getApellido());
		dto.setImagen(asistente.getImagen());
		dto.setContrasenia(asistente.getContrasenia());
		dto.setTipoUsuario("Asistente");
        dto.setFechaNacimiento(asistente.getFechaNacimiento());
        if (asistente.getInstitucion() != null) {
            InstitucionDTO institucionDTO = new InstitucionDTO(
                asistente.getInstitucion().getNombre(),
                asistente.getInstitucion().getDescripcion(),
                asistente.getInstitucion().getSitioWeb()
            );
            dto.setInstitucion(institucionDTO);
        }
        
        // Convert and set registros
        if (asistente.getRegistros() != null && !asistente.getRegistros().isEmpty()) {
            List<RegistroDTO> registrosDTO = asistente.getRegistros().stream()
                .map(registro -> registro.toDTO())
                .collect(Collectors.toList());
            dto.setRegistros(registrosDTO);
        }
        
        return dto;
    }
	    
	    // New methods that return DTOs
	    public AsistenteDTO buscarAsistenteDTO(String nickname) {
	        Asistente asistente = manejadorUsuarios.buscarAsistente(nickname);
	        if (asistente != null) {
	            return convertirAsistenteADTO(asistente);
	        }
	        return null;
	    }
	    
	    public OrganizadorDTO buscarOrganizadorDTO(String nickname) {
	        Organizador organizador = manejadorUsuarios.buscarOrganizador(nickname);
	        if (organizador != null) {
	            return organizador.toDTO();
	        }
	        return null;
	    }
	    
	    public List<AsistenteDTO> listarAsistentesDTO() {
	        Set<Asistente> asistentes = manejadorUsuarios.consultarAsistentes();
	        return asistentes.stream()
	            .map(this::convertirAsistenteADTO)
	            .collect(Collectors.toList());
	    }
	    
	    public List<OrganizadorDTO> listarOrganizadoresDTO() {
	        Set<Organizador> organizadores = manejadorUsuarios.consultarOrganizadores();
	        return organizadores.stream()
	            .map(Organizador::toDTO)
	            .collect(Collectors.toList());
	    }
	    
	    /**
	     * Check if an asistente is registered in a specific edicion
	     */
	    public boolean asistenteEstaRegistradoEnEdicion(String nicknameAsistente, String nombreEdicion) {
	        Asistente asistente = manejadorUsuarios.buscarAsistente(nicknameAsistente);
	        if (asistente != null) {
	            return asistente.getRegistros().stream()
	                .anyMatch(registro -> registro.getEdicion().getNombre().equals(nombreEdicion));
	        }
	        return false;
	    }
	    
	    /**
	     * Get all ediciones where an asistente is registered
	     */
	    public List<String> obtenerEdicionesRegistradasDeAsistente(String nicknameAsistente) {
	        Asistente asistente = manejadorUsuarios.buscarAsistente(nicknameAsistente);
	        if (asistente != null) {
	            return asistente.getRegistros().stream()
	                .map(registro -> registro.getEdicion().getNombre())
	                .collect(Collectors.toList());
	        }
	        return new ArrayList<>();
	    }
	    
	    /**
	     * Get all registros for a specific asistente with details
	     */
	    public List<String> obtenerRegistrosDeAsistente(String nicknameAsistente) {
	        Asistente asistente = manejadorUsuarios.buscarAsistente(nicknameAsistente);
	        if (asistente != null) {
	            List<String> registros = asistente.getRegistros().stream()
	                .map(registro -> {
	                    String edicion = registro.getEdicion().getNombre();
	                    String tipoRegistro = registro.getTipoRegistro().getNombre();
	                    return edicion + " (" + tipoRegistro + ")";
	                })
	                .collect(Collectors.toList());
	            
	            return registros;
	        }
	        return new ArrayList<>();
	    }
	    
	    public Set<RegistroDTO> getRegistrosPorAsistentes(String nickname){
	    	return manejadorUsuarios.getRegistrosPorAsistentes(nickname);
	    }

		
		public List<String> listarEventosPorOrganizador(String nickname){
			return this.manejadorUsuarios.listarEventosPorOrganizador(nickname);
		}

		@Override
		public void altaAsistente(String nickname, String nombre, String correo, String contrasenia, String imagen,
				String apellido, LocalDate fechaNacimiento) throws BusinessException {
		if (nickname == null || nickname.trim().isEmpty()) {
			throw new BusinessException("El nickname es obligatorio");
		}
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new BusinessException("El nombre es obligatorio");
		}
		if (correo == null || correo.trim().isEmpty()) {
			throw new BusinessException("El correo es obligatorio");
		}
		if (apellido == null || apellido.trim().isEmpty()) {
			throw new BusinessException("El apellido es obligatorio");
		}
		if (fechaNacimiento == null) {
			throw new BusinessException("La fecha de nacimiento es obligatoria");
		}

		// Check for duplicate nickname
		if (existeNickname(nickname)) {
			throw new BusinessException("El nickname ya existe en el sistema");
		}
		manejadorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNacimiento, null);
			
		}
		
	/**
	 * Seguir a otro usuario
	 */
	public void seguirUsuario(String nicknameSeguidor, String nicknameSeguido) throws BusinessException {
		if (nicknameSeguidor == null || nicknameSeguido == null) {
			throw new BusinessException("Los nicknames no pueden ser nulos");
		}
		
		if (nicknameSeguidor.equals(nicknameSeguido)) {
			throw new BusinessException("Un usuario no puede seguirse a sí mismo");
		}
		
		// Buscar ambos usuarios (pueden ser asistentes u organizadores)
		Asistente asistenteSeguidor = manejadorUsuarios.buscarAsistente(nicknameSeguidor);
		Organizador organizadorSeguidor = manejadorUsuarios.buscarOrganizador(nicknameSeguidor);
		
		Asistente asistenteSeguido = manejadorUsuarios.buscarAsistente(nicknameSeguido);
		Organizador organizadorSeguido = manejadorUsuarios.buscarOrganizador(nicknameSeguido);
		
		logica.model.Usuario seguidor = asistenteSeguidor != null ? asistenteSeguidor : organizadorSeguidor;
		logica.model.Usuario seguido = asistenteSeguido != null ? asistenteSeguido : organizadorSeguido;
		
		if (seguidor == null || seguido == null) {
			throw new BusinessException("Uno o ambos usuarios no existen");
		}
		
		// Agregar relación bidireccional
		seguidor.agregarSeguido(seguido);
		seguido.agregarSeguidor(seguidor);
	}
	
	/**
	 * Dejar de seguir a un usuario
	 */
	public void dejarDeSeguirUsuario(String nicknameSeguidor, String nicknameSeguido) throws BusinessException {
		if (nicknameSeguidor == null || nicknameSeguido == null) {
			throw new BusinessException("Los nicknames no pueden ser nulos");
		}
		
		// Buscar ambos usuarios
		Asistente asistenteSeguidor = manejadorUsuarios.buscarAsistente(nicknameSeguidor);
		Organizador organizadorSeguidor = manejadorUsuarios.buscarOrganizador(nicknameSeguidor);
		
		Asistente asistenteSeguido = manejadorUsuarios.buscarAsistente(nicknameSeguido);
		Organizador organizadorSeguido = manejadorUsuarios.buscarOrganizador(nicknameSeguido);
		
		logica.model.Usuario seguidor = asistenteSeguidor != null ? asistenteSeguidor : organizadorSeguidor;
		logica.model.Usuario seguido = asistenteSeguido != null ? asistenteSeguido : organizadorSeguido;
		
		if (seguidor == null || seguido == null) {
			throw new BusinessException("Uno o ambos usuarios no existen");
		}
		
		// Remover relación bidireccional
		seguidor.removerSeguido(seguido);
		seguido.removerSeguidor(seguidor);
	}
	
	/**
	 * Obtener lista de seguidores de un usuario
	 */
	public List<String> obtenerSeguidores(String nickname) {
		Asistente asistente = manejadorUsuarios.buscarAsistente(nickname);
		Organizador organizador = manejadorUsuarios.buscarOrganizador(nickname);
		
		logica.model.Usuario usuario = asistente != null ? asistente : organizador;
		
		if (usuario == null) {
			return new ArrayList<>();
		}
		
		return usuario.getSeguidores().stream()
			.map(logica.model.Usuario::getNickname)
			.collect(Collectors.toList());
	}
	
	/**
	 * Obtener lista de usuarios seguidos por un usuario
	 */
	public List<String> obtenerSeguidos(String nickname) {
		Asistente asistente = manejadorUsuarios.buscarAsistente(nickname);
		Organizador organizador = manejadorUsuarios.buscarOrganizador(nickname);
		
		logica.model.Usuario usuario = asistente != null ? asistente : organizador;
		
		if (usuario == null) {
			return new ArrayList<>();
		}
		
		return usuario.getSeguidos().stream()
			.map(logica.model.Usuario::getNickname)
			.collect(Collectors.toList());
	}
	
	/**
	 * Verificar login de usuario (asistentes y organizadores)
	 */
	public boolean verificarLogin(String nicknameOEmail, String contrasenia) {
		// Buscar Asistente por nickname
		Asistente asistente = manejadorUsuarios.buscarAsistente(nicknameOEmail);
		
		// Si no se encuentra por nickname, buscar por email
		
		
		// Si es asistente y la contraseña coincide
		if (asistente != null && asistente.getContrasenia().equals(contrasenia)) {
			return true;
		}
		
		// Buscar Organizador por nickname
		Organizador organizador = manejadorUsuarios.buscarOrganizador(nicknameOEmail);
		
		// Si no se encuentra por nickname, buscar por email
		if (organizador == null) {
			for (Organizador o : manejadorUsuarios.consultarOrganizadores()) {
				if (o.getCorreo().equalsIgnoreCase(nicknameOEmail)) {
					organizador = o;
					break;
				}
			}
		}
		
		// Si es organizador y la contraseña coincide
		if (organizador != null && organizador.getContrasenia().equals(contrasenia)) {
			return true;
		}
		
		return false;
	}

    
}

