package logica.persistence;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for archived organizers (JOINED inheritance)
 */
@Entity
@Table(name = "ORGANIZADOR_ARCHIVADO")
@DiscriminatorValue("ORGANIZADOR")
public class OrganizadorArchivado extends UsuarioArchivado {
    
    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;
    
    @Column(name = "SITIO_WEB", length = 255)
    private String sitioWeb;
    
    // One to many relationship with editions
    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EdicionArchivada> ediciones = new HashSet<>();
    
    // Constructors
    public OrganizadorArchivado() {}
    
    public OrganizadorArchivado(String nickname, String nombre, String correo, String imagen,
                               String descripcion, String sitioWeb) {
        super(nickname, nombre, correo, imagen);
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
    }
    
    // Getters and Setters
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    
    public Set<EdicionArchivada> getEdiciones() { return ediciones; }
    public void setEdiciones(Set<EdicionArchivada> ediciones) { this.ediciones = ediciones; }
    
    public void addEdicion(EdicionArchivada edicion) {
        this.ediciones.add(edicion);
        edicion.setOrganizador(this);
    }
}

