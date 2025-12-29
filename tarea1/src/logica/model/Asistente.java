package logica.model;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import logica.dto.AsistenteDTO;
import logica.dto.RegistroDTO;
import logica.dto.InstitucionDTO;
import exception.RegistroDuplicadoException;


/**
 * Clase para usuarios asistentes en el sistema
 * Extiende la funcionalidad base de Usuario
 */
public class Asistente extends Usuario {
    private String apellido;
    private LocalDate fechaNacimiento;
    private Institucion institucion;
    private Set<Registro> registros;
    
    public Asistente(String nickname, String nombre, String correo, String contrasenia, String imagen, String apellido, LocalDate fechaNacimiento, Institucion institucion) {
        super(nickname, nombre, correo, contrasenia, imagen);
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.institucion = institucion;
        this.registros = new HashSet<>();
    }
    
    // Getters
    public String getApellido() { return apellido; }
    public Set<Registro> getRegistros() { return registros; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public Institucion getInstitucion() { return institucion; }
    
    // Setters
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }
    
    public void agregarRegistro(Registro registro) throws RegistroDuplicadoException {
        if (registros.contains(registro)) {
            throw new RegistroDuplicadoException("Ya está registrado en la edicion: " + registro.getEdicion().getNombre());
        } else {
            registros.add(registro);
    } 
    }
    public boolean estaRegistradoEnEdicion(Edicion edicion) {
        return registros.stream()
                .anyMatch(registro -> registro.getEdicion().equals(edicion));
    }
    
    /**
     * Convierte el asistente a DTO
     */
    public AsistenteDTO toDTO() {
        InstitucionDTO institucionDTO = institucion != null ? institucion.toDTO() : null;
        List<RegistroDTO> registrosDTO = registros.stream()
                .map(Registro::toDTO)
                .collect(Collectors.toList());
        
        AsistenteDTO asistenteDTO = new AsistenteDTO(
            getNickname(), 
            getNombre(), 
            getCorreo(), 
            getContrasenia(),
            getImagen(),
            apellido, 
            fechaNacimiento, 
            institucionDTO
        );
        asistenteDTO.setRegistros(registrosDTO);

        return asistenteDTO;
    }
    
    @Override
    protected String getTipoUsuario() {
        return "Asistente";
    }
}
