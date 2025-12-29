package logica.dto;


import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Data Transfer Object para Organizador
 * Extiende UsuarioDTO con información específica de organizador
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizadorDTO extends UsuarioDTO {
    private String descripcion;
    private String sitioWeb;
    private List<EventoDTO> eventosOrganizados;
    private List<EdicionDTO> edicionesOrganizadas;
   
    
    // Constructor por defecto para serialización
    public OrganizadorDTO() {
        super();
        setTipoUsuario("Organizador");
    }
    
    public OrganizadorDTO(String nickname, String nombre, String correo, String contrasenia, String imagen,
                         String descripcion, String sitioWeb) {
        super(nickname, nombre, correo, contrasenia, imagen, "Organizador");
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
  
    }
    
    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getSitioWeb() {
        return sitioWeb;
    }
    
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    
    public List<EventoDTO> getEventosOrganizados() {
        return eventosOrganizados;
    }
    
    public void setEventosOrganizados(List<EventoDTO> eventosOrganizados) {
        this.eventosOrganizados = eventosOrganizados;
  
        
    }
    
    public List<EdicionDTO> getEdicionesOrganizadas() {
        return edicionesOrganizadas;
    }
    
    public void setEdicionesOrganizadas(List<EdicionDTO> edicionesOrganizadas) {
        this.edicionesOrganizadas = edicionesOrganizadas;

    }
    

   
    
    public boolean tieneSitioWeb() {
        return sitioWeb != null && !sitioWeb.trim().isEmpty();
    }
    
   
    
    
    
    
}
