package logica.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import exception.CupoLlenoException;
import exception.BusinessException;
import exception.CostoRegistrosExcedidoException;
import exception.NombreEdicionException;
import exception.PatrocinioDuplicadoException;
import exception.RegistroDuplicadoException;
import exception.TipoRegistroDuplicadoException;


/**
 * Manejador de eventos del sistema (Singleton)
 * Gestiona la creación y consulta de eventos y ediciones
 */
public class ManejadorEventos {
    private static ManejadorEventos instance;
    private Set<Evento> eventos;
    private Set<Edicion> ediciones;
    private Set<TipoRegistro> tiposRegistro;
    private Set<Institucion> instituciones;
    private Set<Categoria> categorias;

    
    private ManejadorEventos() {
        this.eventos = new HashSet<>();
        this.ediciones = new HashSet<>();

        this.instituciones = new HashSet<>();

        this.tiposRegistro = new HashSet<>();
        this.categorias = new HashSet<>();
    }
    
    public static ManejadorEventos getInstance() {
        if (instance == null) {
            instance = new ManejadorEventos();
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
        this.eventos.clear();
        this.ediciones.clear();
        this.tiposRegistro.clear();
        this.instituciones.clear();
        this.categorias.clear();
    }
    
    // Métodos para gestionar eventos
    public void agregarEvento(Evento evento) {
        eventos.add(evento);
    }
    
    public void altaEvento(String nombre, String sigla, String descripcion, List<Categoria> categorias, String imagen) throws BusinessException {
        // Add business rule validation
        if (buscarEvento(nombre) != null) {
            throw new BusinessException("Ya existe un evento con el nombre: " + nombre);
        }
  
        
        Evento evento = new Evento(nombre, sigla, descripcion, categorias, imagen);
        this.agregarEvento(evento);
    }
    
    public void altaEvento(String nombre, String sigla, String descripcion, List<Categoria> categorias, String imagen, LocalDate fechaAlta) throws BusinessException {
        // Add business rule validation
        if (buscarEvento(nombre) != null) {
            throw new BusinessException("Ya existe un evento con el nombre: " + nombre);
        }
    
        
        Evento evento = new Evento(nombre, sigla, descripcion, categorias, imagen, fechaAlta);
        this.agregarEvento(evento);
    }
    
    public void eliminarEvento(Evento evento) {
        eventos.remove(evento);
    }
    
    public Evento buscarEvento(String nombre) {
        return eventos.stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }
    
    public Evento buscarEventoPorSigla(String sigla) {
        return eventos.stream()
                .filter(e -> e.getSigla().equals(sigla))
                .findFirst()
                .orElse(null);
    }
    
    // Métodos para gestionar ediciones
    public void agregarEdicion(Edicion edicion) {
        ediciones.add(edicion);
    }
    
    public void eliminarEdicion(Edicion edicion) {
        ediciones.remove(edicion);
    }
    
    public Edicion buscarEdicion(String nombre) {
        return ediciones.stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }  
    
    public Institucion buscarInstitucion(String nombre) {
        return instituciones.stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }  
    
    public Categoria buscarCategoria(String nombre) {
        return categorias.stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }  
    
    public void altaEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            String organizador, String ciudad, String pais, String evento) throws NombreEdicionException {
    	Evento event = buscarEvento(evento);
    	if (ediciones.stream().anyMatch(e -> e.getNombre().equals(nombre))) {
    		throw new NombreEdicionException("Ya existe una edicion con nombre: " +nombre);
    	}else {
            Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, organizador, ciudad, pais);
            edicion.setEvento(evento);
            agregarEdicion(edicion);
            event.agregarEdicion(edicion);
    	}
    }
    
    public void altaEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String organizador, String ciudad, String pais, String evento) throws NombreEdicionException {
    	Evento event = buscarEvento(evento);
    	if (ediciones.stream().anyMatch(e -> e.getNombre().equals(nombre))) {
    		throw new NombreEdicionException("Ya existe una edicion con nombre: " +nombre);
    	}else {
        Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, organizador, ciudad, pais);
        edicion.setEvento(evento);
        agregarEdicion(edicion);
        event.agregarEdicion(edicion);
    	}
    }
    
    public void altaEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            String organizador, String ciudad, String pais, String evento, String imagen) throws NombreEdicionException {
    	Evento event = buscarEvento(evento);
    	if (ediciones.stream().anyMatch(e -> e.getNombre().equals(nombre))) {
    		throw new NombreEdicionException("Ya existe una edicion con nombre: " +nombre);
    	}	else {
            Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, organizador, ciudad, pais, imagen);
            edicion.setEvento(evento);
            agregarEdicion(edicion);
            event.agregarEdicion(edicion);
    	}
    }
    
    public void altaEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String organizador, String ciudad, String pais, String evento, String imagen) throws NombreEdicionException {
    	Evento event = buscarEvento(evento);
    	if (ediciones.stream().anyMatch(e -> e.getNombre().equals(nombre))) {
    		throw new NombreEdicionException("Ya existe una edicion con nombre: " +nombre);
   
    	}else {
        Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, organizador, ciudad, pais, imagen);
        edicion.setEvento(evento);
        agregarEdicion(edicion);
        event.agregarEdicion(edicion);
    	}
    }
    
    public void altaEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,
            LocalDate fechaAlta, String organizador, String ciudad, String pais, String evento, String imagen, String estado) throws NombreEdicionException {
    	Evento event = buscarEvento(evento);
    	if (ediciones.stream().anyMatch(e -> e.getNombre().equals(nombre))) {
    		throw new NombreEdicionException("Ya existe una edicion con nombre: " +nombre);
    		}else {
        Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, organizador, ciudad, pais, imagen, estado);
        edicion.setEvento(evento);
        agregarEdicion(edicion);
        event.agregarEdicion(edicion);
    	}
    }
    
    // Métodos de consulta
    public Set<Evento> obtenerTodosLosEventos() {
        return new HashSet<>(eventos);
    }
    
    public Set<Edicion> obtenerTodasLasEdiciones() {
        return new HashSet<>(ediciones);
    }
    
    public void agregarTipoRegistro(TipoRegistro tipoRegistro) {
        tiposRegistro.add(tipoRegistro);
    }

    public List<String> listarEventos() {
    	return eventos.stream()
    	    .filter(evento -> !evento.isFinalizado())
    	    .map(Evento::getNombre)
    	    .collect(Collectors.toList());
    }
    
    /**
     * Lista los nombres de eventos que tienen al menos una edición creada por el organizador
     */
    public List<String> listarEventosPorOrganizador(String nicknameOrganizador) {
        return eventos.stream()
            .filter(evento -> evento.getEdiciones() != null && 
                   evento.getEdiciones().stream()
                       .anyMatch(edicion -> nicknameOrganizador.equals(edicion.getOrganizador())))
            .map(Evento::getNombre)
            .collect(Collectors.toList());
    }

public List<TipoRegistro> buscarTiposRegistroPorEdicion(Edicion edicion) {
    return ediciones.stream()
            .filter(e -> e.equals(edicion))
            .findFirst()
            .map(Edicion::getTiposRegistro)
            .orElse(Collections.emptySet())
            .stream()
            .collect(Collectors.toList());
}

public boolean existeTipoRegistroConNombre(Edicion edicion, String nombre) {
    return !(edicion.buscarTipoRegistroPorNombre(nombre) == null);
}

 public void altaTipoRegistro(String nombreEdicion, String nombre, 
                                 String descripcion, BigDecimal costo, int cupo) 
                                 throws TipoRegistroDuplicadoException, IllegalArgumentException {
        
        Edicion edicion = buscarEdicion(nombreEdicion);
        
           
        
        if (edicion == null) {
            throw new IllegalArgumentException("Edicion no encontrada: " + nombreEdicion);

        }        

        // Validar datos de entrada
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        if (cupo <= 0) {
            throw new IllegalArgumentException("El cupo debe ser mayor a cero");
        }
        
        TipoRegistro nuevoTipo = new TipoRegistro(nombre, descripcion, costo, cupo);
        // Agregar a la edición (puede lanzar TipoRegistroDuplicadoException)
        edicion.agregarTipoRegistro(nuevoTipo);
    }
    
    /**
     * Obtiene todas las ediciones de un evento
     */
    public List<String> obtenerEdicionesDeEvento(String nomEvento) {
        Evento evento = buscarEvento(nomEvento);
        if (evento == null) {
            return new ArrayList<>();
        }
        return evento.getEdiciones().stream().map(Edicion::getNombre).collect(Collectors.toList());
    }
    
    /**
     * Obtiene todos los tipos de registro de una edición
     */
    public Set<String> obtenerTiposRegistroDeEdicion(Edicion edicion) {
        return edicion.getTiposRegistro().stream().map(TipoRegistro::getNombre).collect(Collectors.toSet());
    }

    public void registrarAsistente(Asistente asistente, String nameTR, String nameEdicion) throws RegistroDuplicadoException, 
                                CupoLlenoException, IllegalArgumentException {
        // Validar que la edición existe
        Edicion edicion = buscarEdicion(nameEdicion);
        if (edicion == null) {
            throw new IllegalArgumentException("Edición no encontrada: " + nameEdicion);
        }
        
        // Buscar el tipo de registro en la edición
        TipoRegistro tipoRegistro = edicion.buscarTipoRegistroPorNombre(nameTR);
       
        if (tipoRegistro == null) {
            throw new IllegalArgumentException("Tipo de registro no encontrado: " + nameTR);
        }
        if (tipoRegistro.getCupo() == 0) {
            throw new CupoLlenoException("El cupo de " + nameTR + " esta lleno");
        }
        

    
      
            if (asistente.estaRegistradoEnEdicion(edicion)){
                throw new RegistroDuplicadoException("El asistente ya esta registrado en la edicion");  
            }
            
            Set<Patrocinio> patrocinios = edicion.getPatrocinios();
            BigDecimal monto = tipoRegistro.getCosto();
            Institucion asistenteInstitucion = asistente.getInstitucion();

            // Check if there's a patrocinio for this tipoRegistro that can provide free registration
            for (Patrocinio patrocinio : patrocinios) {
                if (patrocinio.getTipoRegistro().equals(tipoRegistro)) {   
                      
                    if ( asistenteInstitucion != null && asistenteInstitucion.equals(patrocinio.getInstitucion())){
                    // Check if the patrocinio has available free registrations
                        if (patrocinio.getRegistrosGratuitos() > 0) {
                        // Check if adding one more registration would exceed the 20% limit
                            if (patrocinio.validarMontoRegistros()) {
                                monto = BigDecimal.ZERO;
                                patrocinio.setRegistrosGratuitos(patrocinio.getRegistrosGratuitos() - 1);
                                break;
                            }                                                                                                                                                                
                    }
                    }
                   
                }
            }                              
            
            Registro registro = new Registro(LocalDate.now(), monto,  edicion, tipoRegistro);
            asistente.agregarRegistro(registro);
            tipoRegistro.setCupo(tipoRegistro.getCupo() - 1);

        
        
    }
    
    public void registrarAsistenteConAsistente(Asistente asistente, String nameTR, String nameEdicion, Boolean asistio) throws RegistroDuplicadoException, 
                                CupoLlenoException, IllegalArgumentException {
        // Validar que la edición existe
        Edicion edicion = buscarEdicion(nameEdicion);
        if (edicion == null) {
            throw new IllegalArgumentException("Edición no encontrada: " + nameEdicion);
        }
        
        // Buscar el tipo de registro en la edición
        TipoRegistro tipoRegistro = edicion.buscarTipoRegistroPorNombre(nameTR);
       
        if (tipoRegistro == null) {
            throw new IllegalArgumentException("Tipo de registro no encontrado: " + nameTR);
        }
        if (tipoRegistro.getCupo() == 0) {
            throw new CupoLlenoException("El cupo de " + nameTR + " esta lleno");
        }
        
            if (asistente.estaRegistradoEnEdicion(edicion)){
                throw new RegistroDuplicadoException("El asistente ya esta registrado en la edicion");  
            }
            
            Set<Patrocinio> patrocinios = edicion.getPatrocinios();
            BigDecimal monto = tipoRegistro.getCosto();
            Institucion asistenteInstitucion = asistente.getInstitucion();

            // Check if there's a patrocinio for this tipoRegistro that can provide free registration
            for (Patrocinio patrocinio : patrocinios) {
                if (patrocinio.getTipoRegistro().equals(tipoRegistro)) {   
                      
                    if ( asistenteInstitucion != null && asistenteInstitucion.equals(patrocinio.getInstitucion())){
                    // Check if the patrocinio has available free registrations
                        if (patrocinio.getRegistrosGratuitos() > 0) {
                        // Check if adding one more registration would exceed the 20% limit
                            if (patrocinio.validarMontoRegistros()) {
                                monto = BigDecimal.ZERO;
                                patrocinio.setRegistrosGratuitos(patrocinio.getRegistrosGratuitos() - 1);
                                break;
                            }                                                                                                                                                                
                    }
                    }
                   
                }
            }                              
            
            Registro registro = new Registro(LocalDate.now(), monto,  edicion, tipoRegistro);
            asistente.agregarRegistro(registro);
            tipoRegistro.setCupo(tipoRegistro.getCupo() - 1);
            registro.setAsistio(asistio);
    }
    
    public Set<Institucion> obtenerTodasLasInstituciones() {
        return new HashSet<>(instituciones);
    }
     
 // Métodos para gestionar ediciones
    public void agregarInstitucion(Institucion institucion) {
        instituciones.add(institucion);
    }
    
    public void eliminarInstitucion(Institucion institucion) {
    	instituciones.remove(institucion);
    }
    
    //Función para dar de alta una institución
    public boolean altaInstitucion(String nombre, String descripcion, String sitioWeb) {
        // Verificar si ya existe una institución con el mismo nombre
        for (Institucion inst : instituciones) {
            if (inst.getNombre().equalsIgnoreCase(nombre)) {
                return false; // No se puede dar de alta
            }
        }

        // Si no existe, crear la institución y agregarla
        Institucion nuevaInstitucion = new Institucion(nombre, descripcion, sitioWeb);
        agregarInstitucion(nuevaInstitucion);
        return true; // Alta exitosa
    }
    
    public List<String> listarNombresInstituciones() {
        List<String> nombres = new ArrayList<>();
        for (Institucion inst : instituciones) {
            nombres.add(inst.getNombre());
        }
        return nombres;
    }

    public Set<Categoria> obtenerTodasLasCategorias() {
    	
        return new HashSet<>(categorias);
    }

    public void agregarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public void eliminarCategoria(Categoria categoria) {
        categorias.remove(categoria);
    }

    // Método para alta de categoría con validación
    public void altaCategoria(String nombre) throws BusinessException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BusinessException("El nombre de la categoría no puede estar vacío.");
        }

        // Verificar duplicados
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre.trim())) {
                throw new BusinessException("Ya existe una categoría con ese nombre.");
            }
        }

        // Agregar nueva categoría
        categorias.add(new Categoria(nombre.trim()));
    }
    public boolean existeInstitucion(String nombre) {
    	return instituciones.stream().anyMatch(i -> i.getNombre().equals(nombre));
    }

    public List<String> listarTodasLasEdiciones() {
        return ediciones.stream().map(Edicion::getNombre).collect(Collectors.toList());
     }

    public boolean existePatrocinio(String nombreEvento, String nombreEdicion, String nombreInstitucion) {
    Evento evento = buscarEvento(nombreEvento);
    Edicion edicion = buscarEdicion(nombreEdicion);
    Institucion institucion = buscarInstitucion(nombreInstitucion);
    
    if (evento != null && edicion != null && institucion != null && 
        evento.getEdiciones().contains(edicion)) {
        return edicion.tienePatrocinioDeInstitucion(institucion);
    }
    return false;
}

    public void altaPatrocinio(String nombreEvento, String nombreEdicion, String nombreTipoRegistro,
                          String nombreInstitucion, String nivelPatrocinioStr, 
                          BigDecimal aporteEconomico, int cantidadRegistros, String codigoPatrocinio) throws PatrocinioDuplicadoException, CostoRegistrosExcedidoException {
    // Buscar las entidades usando funciones existentes
    Evento evento = buscarEvento(nombreEvento);
    Edicion edicion = buscarEdicion(nombreEdicion);
    Institucion institucion = buscarInstitucion(nombreInstitucion);
    
    if (evento == null) {
        throw new IllegalArgumentException("Evento no encontrado: " + nombreEvento);
    }
    if (edicion == null) {
        throw new IllegalArgumentException("Edición no encontrada: " + nombreEdicion);
    }
    if (institucion == null) {
        throw new IllegalArgumentException("Institución no encontrada: " + nombreInstitucion);
    }
    
    // Verificar que la edición pertenece al evento (usando función existente)
    if (!evento.getEdiciones().contains(edicion)) {
        throw new IllegalArgumentException("La edición no pertenece al evento seleccionado");
    }
    
    // Buscar tipo de registro (usando función existente de Edicion)
    TipoRegistro tipoRegistro = edicion.buscarTipoRegistroPorNombre(nombreTipoRegistro);
    if (tipoRegistro == null) {
        throw new IllegalArgumentException("Tipo de registro no encontrado: " + nombreTipoRegistro);
    }
    
    // Convertir nivel de patrocinio
    NivelPatrocinio nivel;
    try {
        // Buscar el enum por la descripción (toString)
        nivel = Arrays.stream(NivelPatrocinio.values())
            .filter(n -> n.getDescripcion().equalsIgnoreCase(nivelPatrocinioStr) || 
                        n.name().equalsIgnoreCase(nivelPatrocinioStr))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Nivel de patrocinio inválido: " + nivelPatrocinioStr));
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Nivel de patrocinio inválido: " + nivelPatrocinioStr);
    }
    
    // Verificar patrocinio duplicado
    if (existePatrocinio(nombreEvento, nombreEdicion, nombreInstitucion)) {
        throw new PatrocinioDuplicadoException("Ya existe un patrocinio de " + nombreInstitucion + 
                                             " para la edición " + nombreEdicion);
    }
    
    
    // Calcular costo de registros y porcentaje 
    BigDecimal costoRegistros = calcularCostoRegistros(nombreEvento, nombreEdicion, nombreTipoRegistro, cantidadRegistros);
    BigDecimal porcentaje = calcularPorcentajeAporte(aporteEconomico, costoRegistros);
    
    // Verificar regla del 20%
    if (porcentaje.compareTo(new BigDecimal(20)) > 0) {
        throw new CostoRegistrosExcedidoException("El costo de los registros (" + porcentaje + 
                                                "%) supera el 20% del aporte económico");
    }
    
    Patrocinio patrocinio = new Patrocinio(codigoPatrocinio, aporteEconomico, nivel, 
                                              cantidadRegistros, edicion, institucion, tipoRegistro);
    edicion.agregarPatrocinio(patrocinio);
}


public BigDecimal calcularCostoRegistros(String nombreEvento, String nombreEdicion, String nombreTipoRegistro, int cantidad) {
    Evento evento = buscarEvento(nombreEvento);
    Edicion edicion = buscarEdicion(nombreEdicion);
    
    if (evento != null && edicion != null && evento.getEdiciones().contains(edicion)) {
        TipoRegistro tipoRegistro = edicion.buscarTipoRegistroPorNombre(nombreTipoRegistro);
        if (tipoRegistro != null) {
            return tipoRegistro.getCosto().multiply(new BigDecimal(cantidad));
        }
    }
    return BigDecimal.ZERO;
}

public List<String> listarPatrocinios() {
    return ediciones.stream().map(Edicion::getPatrocinios).flatMap(Set::stream).map(Patrocinio::getCodigo).collect(Collectors.toList());
}

public Patrocinio buscarPatrocinio(String codigoPatrocinio) {
    return ediciones.stream()
        .map(Edicion::getPatrocinios)
        .flatMap(Set::stream)
        .filter(p -> p.getCodigo().equals(codigoPatrocinio))
        .findFirst()
        .orElse(null);
}
/* public BigDecimal calcularPorcentajeAporte(BigDecimal costoRegistros, BigDecimal aporteEconomico) {
    if (aporteEconomico.compareTo(BigDecimal.ZERO) == 0) {
        return BigDecimal.ZERO;
    }
    return costoRegistros.multiply(new BigDecimal(100)).divide(aporteEconomico, 2, RoundingMode.HALF_UP);
} */
public BigDecimal calcularPorcentajeAporte(BigDecimal aporteEconomico, BigDecimal costoRegistros) {
    if (aporteEconomico.compareTo(BigDecimal.ZERO) == 0) {
        return BigDecimal.ZERO;
    }
    // Calculate percentage: (costoRegistros / aporteEconomico) * 100
    return costoRegistros.multiply(new BigDecimal(100)).divide(aporteEconomico, 2, RoundingMode.HALF_UP);
}

}
