package logica.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import logica.model.Asistente;

import exception.BusinessException;
import logica.dto.AsistenteDTO;
import logica.dto.OrganizadorDTO;
import logica.dto.RegistroDTO;
import logica.model.Organizador;
public interface iControladorUsuarios {
	
   public List<String> listarUsuarios();
   
   public Set<String> consultarUsuario(String nickname);
    
   public void altaAsistente(String nickname, String nombre, String correo, String contrasenia, String imagen, String apellido, LocalDate fechaNacimiento, String institucion) throws BusinessException;
   
   public void altaAsistente(String nickname, String nombre, String correo, String contrasenia, String imagen, String apellido, LocalDate fechaNacimiento) throws BusinessException;
    
   public void altaOrganizador(String nickname, String nombre, String correo, String contrasenia, String imagen, String descripcion, String sitioWeb);
    
   public List<String> listarOrganizadores();
   
   public boolean existeNickname(String nickname);
   
   public boolean existeCorreo(String correo);
   
   public Set<String> listarAsistentes();
   
   public Set<Asistente> consultarAsistentes();
   
   public Set<Organizador> consultarOrganizadores();
   
   public Asistente buscarAsistente(String nickname);
   
   public Organizador buscarOrganizador(String nickname);

   public List<String> obtenerTodosLosRegistros();
   
   // New DTO methods
   public AsistenteDTO buscarAsistenteDTO(String nickname);
   
   public OrganizadorDTO buscarOrganizadorDTO(String nickname);
   
   public List<AsistenteDTO> listarAsistentesDTO();
   
   public List<OrganizadorDTO> listarOrganizadoresDTO();
   
   /**
    * Check if an asistente is registered in a specific edicion
    */
   public boolean asistenteEstaRegistradoEnEdicion(String nicknameAsistente, String nombreEdicion);
   
   /**
    * Get all ediciones where an asistente is registered
    */
   public List<String> obtenerEdicionesRegistradasDeAsistente(String nicknameAsistente);
   
   /**
    * Get all registros for a specific asistente with details
    */
   public List<String> obtenerRegistrosDeAsistente(String nicknameAsistente);
   
   public Set<RegistroDTO> getRegistrosPorAsistentes(String nickname);
   
   public List<String> listarEventosPorOrganizador(String nickname);
   
   /**
    * Seguir a otro usuario
    */
   public void seguirUsuario(String nicknameSeguidor, String nicknameSeguido) throws BusinessException;
   
   /**
    * Dejar de seguir a un usuario
    */
   public void dejarDeSeguirUsuario(String nicknameSeguidor, String nicknameSeguido) throws BusinessException;
   
   /**
    * Obtener lista de seguidores de un usuario
    */
   public List<String> obtenerSeguidores(String nickname);
   
   /**
    * Obtener lista de usuarios seguidos por un usuario
    */
   public List<String> obtenerSeguidos(String nickname);
   
   /**
    * Verificar login de usuario (para móvil)
    */
   public boolean verificarLogin(String nicknameOEmail, String contrasenia);


}
