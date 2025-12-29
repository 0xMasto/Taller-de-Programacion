package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import logica.controllers.ControladorEventos;
import logica.dto.*;
import exception.*;
import logica.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ControladorEventosTest {
    private ControladorEventos controlador;
    private ManejadorEventos manejadorEventos;
    private ManejadorUsuarios manejadorUsuarios;
    
    @BeforeEach
    void setUp() {
        manejadorEventos = ManejadorEventos.getInstance();
        manejadorUsuarios = ManejadorUsuarios.getInstance();
        controlador = new ControladorEventos(manejadorEventos, manejadorUsuarios);
        
        // Setup test data
        setupTestData();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test data
        manejadorEventos.clearAllData();
        manejadorUsuarios.clearAllData();
        manejadorEventos = null;
        controlador = null;
    }
    
    private void setupTestData() {
        try {
            // Create test categories
            manejadorEventos.altaCategoria("Test Category 1");
            manejadorEventos.altaCategoria("Test Category 2");
            
            // Create test organizer
            manejadorUsuarios.altaOrganizador("testOrg", "Test Organizer", "org@test.com","testPW","testImagen", "Test Description", "http://test.com");
            
            // Create test institution
            manejadorEventos.altaInstitucion("Test Institution", "Test Description", "http://test.com");
            
            // Create test asistente
            manejadorUsuarios.altaAsistente("testAsistente", "Test Asistente", "asistente@test.com","testPW","testImagen", "Test Apellido", LocalDate.of(1990, 1, 1), null);
            
        } catch (Exception e) {
            // Ignore setup errors for tests
        }
    }
    
    @Test
    void testAltaEvento_Success() throws BusinessException {
        // Arrange
        String nombre = "Test Event";
        String sigla = "TE";
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        
        // Act
        controlador.altaEvento(nombre, sigla, descripcion, categorias);
        
        // Assert
        Evento evento = controlador.buscarEvento(nombre);
        assertNotNull(evento);
        assertEquals(nombre, evento.getNombre());
        assertEquals(sigla, evento.getSigla());
        assertEquals(descripcion, evento.getDescripcion());
    }
    
    @Test
    void testAltaEvento_NullNombre() {
        // Arrange
        String nombre = null;
        String sigla = "TE";
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
        assertEquals("El nombre del evento es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaEvento_EmptyNombre() {
        // Arrange
        String nombre = "";
        String sigla = "TE";
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
        assertEquals("El nombre del evento es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaEvento_NullSigla() {
        // Arrange
        String nombre = "Test Event";
        String sigla = null;
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
        assertEquals("La sigla del evento es obligatoria", exception.getMessage());
    }
    
    @Test
    void testAltaEvento_NullDescripcion() {
        // Arrange
        String nombre = "Test Event";
        String sigla = "TE";
        String descripcion = null;
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
        assertEquals("La descripción del evento es obligatoria", exception.getMessage());
    }
    
    @Test
    void testAltaEvento_EmptyCategorias() {
        // Arrange
        String nombre = "Test Event";
        String sigla = "TE";
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = new ArrayList<>();
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
        assertEquals("El evento debe tener al menos una categoría", exception.getMessage());
    }
    
    @Test
    void testAltaEvento_NullCategorias() {
        // Arrange
        String nombre = "Test Event";
        String sigla = "TE";
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = null;
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
        assertEquals("El evento debe tener al menos una categoría", exception.getMessage());
    }
    
    @Test
    void testAltaEdicionEvento_Success() throws NombreEdicionException {
        // Arrange
        String nombre = "Test Edition";
        String sigla = "TE2024";
        LocalDate fechaInicio = LocalDate.of(2024, 6, 1);
        LocalDate fechaFin = LocalDate.of(2024, 6, 3);
        String nombreOrganizador = "testOrg";
        String ciudad = "Test City";
        String pais = "Test Country";
        String nombreEvento = "Test Event";
        
        // Create event first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
        } catch (BusinessException e) {
            // Event might already exist
        }
        
        // Act
        controlador.altaEdicionEvento(nombre, sigla, fechaInicio, fechaFin, 
                                    nombreOrganizador, ciudad, pais, nombreEvento);
        
        // Assert
        Edicion edicion = controlador.buscarEdicion(nombre);
        assertNotNull(edicion);
        assertEquals(nombre, edicion.getNombre());
        assertEquals(sigla, edicion.getSigla());
        assertEquals(fechaInicio, edicion.getFechaInicio());
        assertEquals(fechaFin, edicion.getFechaFin());
        assertEquals(ciudad, edicion.getCiudad());
        assertEquals(pais, edicion.getPais());
    }
    
    @Test
    void testAltaEdicionEvento_OrganizadorNotFound() {
        // Arrange
        String nombre = "Test Edition";
        String sigla = "TE2024";
        LocalDate fechaInicio = LocalDate.of(2024, 6, 1);
        LocalDate fechaFin = LocalDate.of(2024, 6, 3);
        String nombreOrganizador = "nonexistentOrg";
        String ciudad = "Test City";
        String pais = "Test Country";
        String nombreEvento = "Test Event";
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controlador.altaEdicionEvento(nombre, sigla, fechaInicio, fechaFin, 
                                        nombreOrganizador, ciudad, pais, nombreEvento);
        });
        assertEquals("Organizador no encontrado: nonexistentOrg", exception.getMessage());
    }
    
    @Test
    void testAltaEdicionEvento_EventoNotFound() {
        // Arrange
        String nombre = "Test Edition";
        String sigla = "TE2024";
        LocalDate fechaInicio = LocalDate.of(2024, 6, 1);
        LocalDate fechaFin = LocalDate.of(2024, 6, 3);
        String nombreOrganizador = "testOrg";
        String ciudad = "Test City";
        String pais = "Test Country";
        String nombreEvento = "nonexistentEvent";
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controlador.altaEdicionEvento(nombre, sigla, fechaInicio, fechaFin, 
                                        nombreOrganizador, ciudad, pais, nombreEvento);
        });
        assertEquals("Evento no encontrado: nonexistentEvent", exception.getMessage());
    }
    
    @Test
    void testObtenerTodasLasEdiciones() {
        // Act
        Set<Edicion> ediciones = controlador.obtenerTodasLasEdiciones();
        
        // Assert
        assertNotNull(ediciones);
        // Should be empty initially
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testObtenerTodosLosEventos() {
        // Act
        Set<Evento> eventos = controlador.obtenerTodosLosEventos();
        
        // Assert
        assertNotNull(eventos);
        // Should be empty initially
        assertEquals(0, eventos.size());
    }
    
    @Test
    void testObtenerEdicionesOrganizadasPor() {
        // Act
        List<String> ediciones = controlador.obtenerEdicionesOrganizadasPor("testOrg");
        
        // Assert
        assertNotNull(ediciones);
        // Should be empty initially
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testBuscarEvento() {
        // Act
        Evento evento = controlador.buscarEvento("nonexistent");
        
        // Assert
        assertNull(evento);
    }
    
    @Test
    void testGetEventoDTO() {
        // Act
        EventoDTO eventoDTO = controlador.getEventoDTO("nonexistent");
        
        // Assert
        assertNull(eventoDTO);
    }
    
    @Test
    void testBuscarEdicion() {
        // Act
        Edicion edicion = controlador.buscarEdicion("nonexistent");
        
        // Assert
        assertNull(edicion);
    }
    
    @Test
    void testListarNombresInstituciones() {
        // Act
        List<String> instituciones = controlador.listarNombresInstituciones();
        
        // Assert
        assertNotNull(instituciones);
        assertTrue(instituciones.contains("Test Institution"));
    }
    
    @Test
    void testAltaInstitucion() {
        // Act
        boolean result = controlador.altaInstitucion("New Institution", "New Description", "http://new.com");
        
        // Assert
        assertTrue(result);
        
        // Verify it was added
        List<String> instituciones = controlador.listarNombresInstituciones();
        assertTrue(instituciones.contains("New Institution"));
    }
    
    @Test
    void testGetInstitucionDTO() {
        // Act
        InstitucionDTO institucionDTO = controlador.getInstitucionDTO("Test Institution");
        
        // Assert
        assertNotNull(institucionDTO);
        assertEquals("Test Institution", institucionDTO.getNombre());
        assertEquals("Test Description", institucionDTO.getDescripcion());
        assertEquals("http://test.com", institucionDTO.getSitioWeb());
    }
    
    @Test
    void testAltaTipoRegistro_Success() throws TipoRegistroDuplicadoException {
        // Arrange
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombre = "Test Tipo Registro";
        String descripcion = "Test Description";
        BigDecimal costo = new BigDecimal("100.00");
        int cupo = 50;
        
        // Create event and edition first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
        } catch (Exception e) {
            // Event/edition might already exist
        }
        
        // Act
        controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, costo, cupo);
        
        // Assert
        Set<String> tiposRegistro = controlador.obtenerTiposDeRegistroDeEdicion(nombreEdicion);
        assertTrue(tiposRegistro.contains(nombre));
    }
    
    @Test
    void testListarEventos() {
        // Act
        List<String> eventos = controlador.listarEventos();
        
        // Assert
        assertNotNull(eventos);
        // Should be empty initially
        assertEquals(0, eventos.size());
    }
    
    @Test
    void testListarEdicionesDeEvento() {
        // Act
        List<String> ediciones = controlador.listarEdicionesDeEvento("nonexistent");
        
        // Assert
        assertNotNull(ediciones);
        // Should be empty for nonexistent event
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testListarTodasLasEdiciones() {
        // Act
        List<String> ediciones = controlador.listarTodasLasEdiciones();
        
        // Assert
        assertNotNull(ediciones);
        // Should be empty initially
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testObtenerTiposDeRegistroDeEdicion() {
        // Act
        Set<String> tiposRegistro = controlador.obtenerTiposDeRegistroDeEdicion("nonexistent");
        
        // Assert
        assertNotNull(tiposRegistro);
        // Should be empty for nonexistent edition
        assertEquals(0, tiposRegistro.size());
    }
    
    @Test
    void testRegistrarAsistente_Success() throws RegistroDuplicadoException, CupoLlenoException {
        // Arrange
        String nickAsistente = "testAsistente";
        String tipoRegistro = "Test Tipo Registro";
        String edicion = "Test Edition";
        
        // Create event, edition, and tipo registro first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento("Test Event", "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(edicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", "Test Event");
            controlador.altaTipoRegistro("Test Event", edicion, tipoRegistro, "Test Description", new BigDecimal("100.00"), 50);
        } catch (Exception e) {
            // Event/edition/tipo registro might already exist
        }
        
        // Act
        controlador.registrarAsistente(nickAsistente, tipoRegistro, edicion);
        
        // Assert - verify registration was successful
        List<Registro> registros = controlador.obtenerRegistrosDeEdicion(edicion);
        assertFalse(registros.isEmpty());
    }
    
    @Test
    void testListarInstituciones() {
        // Act
        List<String> instituciones = controlador.listarInstituciones();
        
        // Assert
        assertNotNull(instituciones);
        assertTrue(instituciones.contains("Test Institution"));
    }
    
    @Test
    void testBuscarInstitucion() {
        // Act
        Institucion institucion = controlador.buscarInstitucion("Test Institution");
        
        // Assert
        assertNotNull(institucion);
        assertEquals("Test Institution", institucion.getNombre());
    }
    
    @Test
    void testAgregarEvento() {
        // Arrange
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Test Category"));
        Evento evento = new Evento("Test Event 2", "TE2", "Test Description 2", categorias);
        
        // Act
        controlador.agregarEvento(evento);
        
        // Assert
        Evento foundEvento = controlador.buscarEvento("Test Event 2");
        assertNotNull(foundEvento);
        assertEquals("Test Event 2", foundEvento.getNombre());
    }
    
    @Test
    void testAltaCategoria_Success() throws BusinessException {
        // Act
        controlador.altaCategoria("Test Category 3");
        
        // Assert
        List<CategoriaDTO> categorias = controlador.listarCategoriasDTO();
        boolean found = categorias.stream()
            .anyMatch(cat -> cat.getNombre().equals("Test Category 3"));
        assertTrue(found);
    }
    
    @Test
    void testListarCategoriasDTO() {
        // Act
        List<CategoriaDTO> categorias = controlador.listarCategoriasDTO();
        
        // Assert
        assertNotNull(categorias);
        assertTrue(categorias.size() >= 2); // At least the 2 categories from setup
    }
    
    @Test
    void testAltaPatrocinio_Success() throws PatrocinioDuplicadoException, CostoRegistrosExcedidoException {
        // Arrange
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombreTipoRegistro = "Test Tipo Registro";
        String nombreInstitucion = "Test Institution";
        String nivelPatrocinio = "PLATA";
        BigDecimal aporteEconomico = new BigDecimal("5000.00");
        int cantidadRegistros = 10;
        String codigoPatrocinio = "PAT001";
        
        // Create required entities first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", new BigDecimal("100.00"), 50);
        } catch (Exception e) {
            // Entities might already exist
        }
        
        // Act
        controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                  nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                  cantidadRegistros, codigoPatrocinio);
        
        // Assert
        List<String> patrocinios = controlador.listarPatrocinios();
        assertTrue(patrocinios.contains(codigoPatrocinio));
    }
    
    @Test
    void testCalcularCostoRegistros() {
        // Arrange
        String evento = "Test Event";
        String edicion = "Test Edition";
        String tipoRegistro = "Test Tipo Registro";
        int cantidad = 5;
        
        // Act
        BigDecimal costo = controlador.calcularCostoRegistros(evento, edicion, tipoRegistro, cantidad);
        
        // Assert
        assertNotNull(costo);
        // Should be zero for nonexistent entities
        assertEquals(BigDecimal.ZERO, costo);
    }
    
    @Test
    void testCalcularPorcentajeAporte() {
        // Arrange
        BigDecimal costoRegistros = new BigDecimal("1000.00");
        BigDecimal aporte = new BigDecimal("500.00");
        
        // Act
        BigDecimal porcentaje = controlador.calcularPorcentajeAporte(costoRegistros, aporte);
        
        // Assert
        assertNotNull(porcentaje);
        assertEquals(new BigDecimal("50.00"), porcentaje);
    }
    
    @Test
    void testObtenerTodosLosEventosSet() {
        // Act
        Set<Evento> eventos = controlador.obtenerTodosLosEventosSet();
        
        // Assert
        assertNotNull(eventos);
        // Should be empty initially
        assertEquals(0, eventos.size());
    }
    
    @Test
    void testListarPatrocinios() {
        // Act
        List<String> patrocinios = controlador.listarPatrocinios();
        
        // Assert
        assertNotNull(patrocinios);
        // Should be empty initially
        assertEquals(0, patrocinios.size());
    }
    
    @Test
    void testObtenerRegistrosDeEdicion() {
        // Act
        List<Registro> registros = controlador.obtenerRegistrosDeEdicion("nonexistent");
        
        // Assert
        assertNotNull(registros);
        // Should be empty for nonexistent edition
        assertEquals(0, registros.size());
    }
    
    @Test
    void testBuscarEventoDTO() {
        // Act
        EventoDTO eventoDTO = controlador.buscarEventoDTO("nonexistent");
        
        // Assert
        assertNull(eventoDTO);
    }
    
    @Test
    void testBuscarEdicionDTO() {
        // Act
        EdicionDTO edicionDTO = controlador.buscarEdicionDTO("nonexistent");
        
        // Assert
        assertNull(edicionDTO);
    }
    
    @Test
    void testListarEventosDTO() {
        // Act
        List<EventoDTO> eventosDTO = controlador.listarEventosDTO();
        
        // Assert
        assertNotNull(eventosDTO);
        // Should be empty initially
        assertEquals(0, eventosDTO.size());
    }
    
    @Test
    void testListarTodasLasEdicionesDTO() {
        // Act
        List<EdicionDTO> edicionesDTO = controlador.listarTodasLasEdicionesDTO();
        
        // Assert
        assertNotNull(edicionesDTO);
        // Should be empty initially
        assertEquals(0, edicionesDTO.size());
    }
    
    @Test
    void testListarEdicionesDeEventoDTO() {
        // Act
        List<EdicionDTO> edicionesDTO = controlador.listarEdicionesDeEventoDTO("nonexistent");
        
        // Assert
        assertNotNull(edicionesDTO);
        // Should be empty for nonexistent event
        assertEquals(0, edicionesDTO.size());
    }
    
    @Test
    void testObtenerTiposDeRegistroDeEdicionDTO() {
        // Act
        List<TipoRegistroDTO> tiposRegistroDTO = controlador.obtenerTiposDeRegistroDeEdicionDTO("nonexistent");
        
        // Assert
        assertNotNull(tiposRegistroDTO);
        // Should be empty for nonexistent edition
        assertEquals(0, tiposRegistroDTO.size());
    }
    
    @Test
    void testObtenerPatrociniosDeEdicionDTO() {
        // Act
        List<PatrocinioDTO> patrociniosDTO = controlador.obtenerPatrociniosDeEdicionDTO("nonexistent");
        
        // Assert
        assertNotNull(patrociniosDTO);
        // Should be empty for nonexistent edition
        assertEquals(0, patrociniosDTO.size());
    }
    
    @Test
    void testObtenerRegistrosDeEdicionDTO() {
        // Act
        List<RegistroDTO> registrosDTO = controlador.obtenerRegistrosDeEdicionDTO("nonexistent");
        
        // Assert
        assertNotNull(registrosDTO);
        // Should be empty for nonexistent edition
        assertEquals(0, registrosDTO.size());
    }
    
    // ========== ADDITIONAL TESTS ==========
    
    @Test
    void testBuscarPatrocinioDTO_Success() throws PatrocinioDuplicadoException, CostoRegistrosExcedidoException {
        // Arrange - Create a sponsorship first
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombreTipoRegistro = "Test Tipo Registro";
        String nombreInstitucion = "Test Institution";
        String nivelPatrocinio = "PLATA";
        BigDecimal aporteEconomico = new BigDecimal("5000.00");
        int cantidadRegistros = 10;
        String codigoPatrocinio = "PAT001";
        
        // Create required entities first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", new BigDecimal("100.00"), 50);
            controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                      nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                      cantidadRegistros, codigoPatrocinio);
        } catch (Exception e) {
            // Entities might already exist
        }
        
        // Act
        PatrocinioDTO patrocinioDTO = controlador.buscarPatrocinioDTO(codigoPatrocinio);
        
        // Assert
        assertNotNull(patrocinioDTO);
        assertEquals(codigoPatrocinio, patrocinioDTO.getCodigoPatrocinio());
        assertEquals("Plata", patrocinioDTO.getNivelPatrocinio()); // Case sensitive - should be "Plata" not "PLATA"
        assertEquals(aporteEconomico, patrocinioDTO.getAporteEconomico());
        assertEquals(cantidadRegistros, patrocinioDTO.getCantidadRegistros());
    }
    
    @Test
    void testBuscarPatrocinioDTO_NotFound() {
        // Act
        PatrocinioDTO patrocinioDTO = controlador.buscarPatrocinioDTO("nonexistent");
        
        // Assert
        assertNull(patrocinioDTO);
    }
    
    @Test
    void testBuscarInstitucionDTO_Success() {
        // Act
        InstitucionDTO institucionDTO = controlador.buscarInstitucionDTO("Test Institution");
        
        // Assert
        assertNotNull(institucionDTO);
        assertEquals("Test Institution", institucionDTO.getNombre());
        assertEquals("Test Description", institucionDTO.getDescripcion());
        assertEquals("http://test.com", institucionDTO.getSitioWeb());
    }
    
    @Test
    void testBuscarInstitucionDTO_NotFound() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            controlador.buscarInstitucionDTO("nonexistent");
        });
    }
    
    @Test
    void testExisteInstitucion_True() {
        // Act
        boolean exists = controlador.existeInstitucion("Test Institution");
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    void testExisteInstitucion_False() {
        // Act
        boolean exists = controlador.existeInstitucion("nonexistent");
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testAltaPatrocinio_DuplicateException() throws PatrocinioDuplicadoException, CostoRegistrosExcedidoException {
        // Arrange - Create a sponsorship first
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombreTipoRegistro = "Test Tipo Registro";
        String nombreInstitucion = "Test Institution";
        String nivelPatrocinio = "PLATA";
        BigDecimal aporteEconomico = new BigDecimal("5000.00");
        int cantidadRegistros = 10;
        String codigoPatrocinio = "PAT002";
        
        // Create required entities first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", new BigDecimal("100.00"), 50);
            controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                      nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                      cantidadRegistros, codigoPatrocinio);
        } catch (Exception e) {
            // First creation might fail, continue with test
        }
        
        // Act & Assert - Try to create the same sponsorship again
        assertThrows(PatrocinioDuplicadoException.class, () -> {
            controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                      nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                      cantidadRegistros, codigoPatrocinio);
        });
    }
    
    @Test
    void testAltaPatrocinio_CostoRegistrosExcedidoException() {
        // Arrange - Create entities with high cost tipo registro
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombreTipoRegistro = "Expensive Tipo";
        String nombreInstitucion = "Test Institution";
        String nivelPatrocinio = "PLATA";
        BigDecimal aporteEconomico = new BigDecimal("1000.00"); // Small contribution
        int cantidadRegistros = 50; // Many registrations
        String codigoPatrocinio = "PAT003";
        
        // Create required entities first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", new BigDecimal("1000.00"), 50);
        } catch (Exception e) {
            // Entities might already exist
        }
        
        // Act & Assert - This should exceed the 20% limit
        assertThrows(CostoRegistrosExcedidoException.class, () -> {
            controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                      nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                      cantidadRegistros, codigoPatrocinio);
        });
    }
    
    @Test
    void testCalcularPorcentajeAporte_ValidCalculation() {
        // Arrange
        BigDecimal aporte = new BigDecimal("1000.00");
        BigDecimal costoRegistros = new BigDecimal("500.00");
        
        // Act
        BigDecimal porcentaje = controlador.calcularPorcentajeAporte(aporte, costoRegistros);
        
        // Assert
        // The calculation is (costoRegistros / aporte) * 100
        // (500 / 1000) * 100 = 50.00
        assertEquals(new BigDecimal("50.00"), porcentaje);
    }
    
    @Test
    void testCalcularPorcentajeAporte_ZeroCosto() {
        // Arrange
        BigDecimal aporte = new BigDecimal("500.00");
        BigDecimal costoRegistros = BigDecimal.ZERO;
        
        // Act
        BigDecimal porcentaje = controlador.calcularPorcentajeAporte(aporte, costoRegistros);
        
        // Assert
        assertNotNull(porcentaje);
        // Should handle division by zero gracefully
    }
    
    @Test
    void testAltaTipoRegistro_DuplicateException() throws TipoRegistroDuplicadoException {
        // Arrange
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombre = "Test Tipo Registro";
        String descripcion = "Test Description";
        BigDecimal costo = new BigDecimal("100.00");
        int cupo = 50;
        
        // Create event and edition first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, costo, cupo);
        } catch (Exception e) {
            // First creation might fail, continue with test
        }
        
        // Act & Assert - Try to create the same tipo registro again
        assertThrows(TipoRegistroDuplicadoException.class, () -> {
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, costo, cupo);
        });
    }
    
    @Test
    void testAltaTipoRegistro_InvalidArgumentException() {
        // Arrange
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombre = "Test Tipo Registro";
        String descripcion = "Test Description";
        BigDecimal costo = new BigDecimal("-100.00"); // Negative cost
        int cupo = 50;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, costo, cupo);
        });
    }
    
    @Test
    void testRegistrarAsistente_CupoLlenoException() throws RegistroDuplicadoException, CupoLlenoException {
        // Arrange - Create a tipo registro with cupo = 1
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombreTipoRegistro = "Limited Tipo";
        String descripcion = "Test Description";
        BigDecimal costo = new BigDecimal("100.00");
        int cupo = 1; // Very limited capacity
        
        // Create required entities first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, descripcion, costo, cupo);
            
            // Register first asistente (fills the cupo)
            controlador.registrarAsistente("testAsistente", nombreTipoRegistro, nombreEdicion);
        } catch (Exception e) {
            // Setup might fail, continue with test
        }
        
        // Act & Assert - Try to register a second asistente (should fail)
        assertThrows(CupoLlenoException.class, () -> {
            controlador.registrarAsistente("testAsistente2", nombreTipoRegistro, nombreEdicion);
        });
    }
    
    @Test
    void testRegistrarAsistente_RegistroDuplicadoException() throws RegistroDuplicadoException, CupoLlenoException {
        // Arrange - Create entities
        String nombreEvento = "Test Event";
        String nombreEdicion = "Test Edition";
        String nombreTipoRegistro = "Test Tipo";
        String descripcion = "Test Description";
        BigDecimal costo = new BigDecimal("100.00");
        int cupo = 50;
        
        // Create required entities first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, descripcion, costo, cupo);
            
            // Register asistente first time
            controlador.registrarAsistente("testAsistente", nombreTipoRegistro, nombreEdicion);
        } catch (Exception e) {
            // Setup might fail, continue with test
        }
        
        // Act & Assert - Try to register the same asistente again (should fail)
        // The exception is wrapped in RuntimeException, so we need to check for that
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controlador.registrarAsistente("testAsistente", nombreTipoRegistro, nombreEdicion);
        });
        assertTrue(exception.getMessage().contains("El asistente ya esta registrado en la edicion"));
    }
    
    @Test
    void testAltaCategoria_BusinessException() {
        // Act & Assert - Try to create category with empty name
        assertThrows(BusinessException.class, () -> {
            controlador.altaCategoria("");
        });
    }
    
    @Test
    void testAltaCategoria_NullName() {
        // Act & Assert - Try to create category with null name
        assertThrows(BusinessException.class, () -> {
            controlador.altaCategoria(null);
        });
    }
    
    @Test
    void testAltaEvento_WithNonExistentCategory() {
        // Arrange
        String nombre = "Test Event";
        String sigla = "TE";
        String descripcion = "Test Description";
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("NonExistentCategory"));
        
        // Act & Assert
        assertThrows(BusinessException.class, () -> {
            controlador.altaEvento(nombre, sigla, descripcion, categorias);
        });
    }
    
    @Test
    void testAltaEdicionEvento_WithFechaAlta() throws NombreEdicionException {
        // Arrange
        String nombre = "Test Edition With Fecha Alta";
        String sigla = "TEWFA";
        LocalDate fechaInicio = LocalDate.of(2024, 6, 1);
        LocalDate fechaFin = LocalDate.of(2024, 6, 3);
        LocalDate fechaAlta = LocalDate.of(2024, 1, 1);
        String nombreOrganizador = "testOrg";
        String ciudad = "Test City";
        String pais = "Test Country";
        String nombreEvento = "Test Event";
        
        // Create event first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Test Description", categorias);
        } catch (BusinessException e) {
            // Event might already exist
        }
        
        // Act
        controlador.altaEdicionEvento(nombre, sigla, fechaInicio, fechaFin, fechaAlta,
                                    nombreOrganizador, ciudad, pais, nombreEvento);
        
        // Assert
        Edicion edicion = controlador.buscarEdicion(nombre);
        assertNotNull(edicion);
        assertEquals(nombre, edicion.getNombre());
        assertEquals(sigla, edicion.getSigla());
    }
    
    // ========== TESTS FOR REGISTRO ENTITY ==========
    
    @Test
    void testRegistro_Creation() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        BigDecimal costo = new BigDecimal("150.00");
        Edicion edicion = new Edicion("Test Edition", "TE", fecha, fecha.plusDays(2), "Test Org", "Test City", "Test Country");
        TipoRegistro tipoRegistro = new TipoRegistro("Test Tipo", "Test Description", costo, 50);
        
        // Act
        Registro registro = new Registro(fecha, costo, edicion, tipoRegistro);
        
        // Assert
        assertNotNull(registro);
        assertEquals(fecha, registro.getFecha());
        assertEquals(costo, registro.getCosto());
        assertEquals(edicion, registro.getEdicion());
        assertEquals(tipoRegistro, registro.getTipoRegistro());
    }
    
    @Test
    void testRegistro_Setters() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        BigDecimal costo = new BigDecimal("150.00");
        Edicion edicion = new Edicion("Test Edition", "TE", fecha, fecha.plusDays(2), "Test Org", "Test City", "Test Country");
        TipoRegistro tipoRegistro = new TipoRegistro("Test Tipo", "Test Description", costo, 50);
        Registro registro = new Registro(fecha, costo, edicion, tipoRegistro);
        
        // Act
        LocalDate nuevaFecha = LocalDate.now().plusDays(1);
        BigDecimal nuevoCosto = new BigDecimal("200.00");
        Edicion nuevaEdicion = new Edicion("New Edition", "NE", nuevaFecha, nuevaFecha.plusDays(2), "New Org", "New City", "New Country");
        TipoRegistro nuevoTipoRegistro = new TipoRegistro("New Tipo", "New Description", nuevoCosto, 30);
        
        registro.setFecha(nuevaFecha);
        registro.setCosto(nuevoCosto);
        registro.setEdicion(nuevaEdicion);
        registro.setTipoRegistro(nuevoTipoRegistro);
        
        // Assert
        assertEquals(nuevaFecha, registro.getFecha());
        assertEquals(nuevoCosto, registro.getCosto());
        assertEquals(nuevaEdicion, registro.getEdicion());
        assertEquals(nuevoTipoRegistro, registro.getTipoRegistro());
    }
    
    @Test
    void testRegistro_ToDTO() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 6, 1);
        BigDecimal costo = new BigDecimal("150.00");
        Edicion edicion = new Edicion("Test Edition", "TE", fecha, fecha.plusDays(2), "Test Org", "Test City", "Test Country");
        TipoRegistro tipoRegistro = new TipoRegistro("Test Tipo", "Test Description", costo, 50);
        Registro registro = new Registro(fecha, costo, edicion, tipoRegistro);
        
        // Act
        RegistroDTO dto = registro.toDTO();
        
        // Assert
        assertNotNull(dto);
        assertEquals(costo, dto.getCosto());
        assertEquals(edicion.getNombre(), dto.getEdicion().getNombre());
        assertEquals(tipoRegistro.getNombre(), dto.getTipoRegistro().getNombre());
        assertTrue(dto.getId().contains("Test Edition"));
        assertTrue(dto.getId().contains("Test Tipo"));
        assertTrue(dto.getId().contains("2024-06-01"));
    }
    
    @Test
    void testRegistro_ToString() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 6, 1);
        BigDecimal costo = new BigDecimal("150.00");
        Edicion edicion = new Edicion("Test Edition", "TE", fecha, fecha.plusDays(2), "Test Org", "Test City", "Test Country");
        TipoRegistro tipoRegistro = new TipoRegistro("Test Tipo", "Test Description", costo, 50);
        Registro registro = new Registro(fecha, costo, edicion, tipoRegistro);
        
        // Act
        String result = registro.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Registro{"));
        assertTrue(result.contains("fecha=2024-06-01"));
        assertTrue(result.contains("costo=150.00"));
        assertTrue(result.contains("edicion="));
        assertTrue(result.contains("tipoRegistro="));
    }
    
    // ========== ADDITIONAL TESTS FOR REGISTRARASISTENTE ==========
    
    @Test
    void testRegistrarAsistente_InvalidAsistente() {
        // Arrange
        String nonExistentAsistente = "nonExistentAsistente";
        String tipoRegistro = "Test Tipo Registro";
        String edicion = "Test Edition";
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controlador.registrarAsistente(nonExistentAsistente, tipoRegistro, edicion);
        });
        // Just verify that an exception was thrown, don't check the specific message
        assertNotNull(exception.getMessage());
    }
    
    @Test
    void testRegistrarAsistente_InvalidEdicion() {
        // Arrange
        String nickAsistente = "testAsistente";
        String tipoRegistro = "Test Tipo Registro";
        String nonExistentEdicion = "nonExistentEdicion";
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente(nickAsistente, tipoRegistro, nonExistentEdicion);
        });
        assertTrue(exception.getMessage().contains("Edición no encontrada"));
    }
    
    @Test
    void testRegistrarAsistente_InvalidTipoRegistro() {
        // Arrange
        String nickAsistente = "testAsistente";
        String nonExistentTipoRegistro = "nonExistentTipoRegistro";
        String edicion = "Test Edition";
        
        // Create event and edition first
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento("Test Event", "TE", "Test Description", categorias);
            controlador.altaEdicionEvento(edicion, "TE2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", "Test Event");
        } catch (Exception e) {
            // Event/edition might already exist
        }
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente(nickAsistente, nonExistentTipoRegistro, edicion);
        });
        assertTrue(exception.getMessage().contains("Tipo de registro no encontrado"));
    }
    
    @Test
    void testRegistrarAsistente_CupoDecreases() throws RegistroDuplicadoException, CupoLlenoException {
        // Arrange
        String nickAsistente = "testAsistente";
        String tipoRegistro = "Test Tipo Registro Cupo";
        String edicion = "Test Edition Cupo";
        int cupoInicial = 5;
        
        // Create required entities
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento("Test Event Cupo", "TEC", "Test Description", categorias);
            controlador.altaEdicionEvento(edicion, "TEC2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", "Test Event Cupo");
            controlador.altaTipoRegistro("Test Event Cupo", edicion, tipoRegistro, "Test Description", new BigDecimal("100.00"), cupoInicial);
        } catch (Exception e) {
            // Entities might already exist
        }
        
        // Get initial cupo
        Edicion edicionObj = controlador.buscarEdicion(edicion);
        TipoRegistro tipoRegistroObj = edicionObj.buscarTipoRegistroPorNombre(tipoRegistro);
        int cupoAntes = tipoRegistroObj.getCupo();
        
        // Act
        controlador.registrarAsistente(nickAsistente, tipoRegistro, edicion);
        
        // Assert
        int cupoDespues = tipoRegistroObj.getCupo();
        assertEquals(cupoAntes - 1, cupoDespues);
    }
    
    @Test
    void testRegistrarAsistente_MultipleRegistrations() throws RegistroDuplicadoException, CupoLlenoException {
        // Arrange
        String tipoRegistro = "Test Tipo Registro Multiple";
        String edicion = "Test Edition Multiple";
        int cupoInicial = 3;
        
        // Create required entities
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento("Test Event Multiple", "TEM", "Test Description", categorias);
            controlador.altaEdicionEvento(edicion, "TEM2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", "Test Event Multiple");
            controlador.altaTipoRegistro("Test Event Multiple", edicion, tipoRegistro, "Test Description", new BigDecimal("100.00"), cupoInicial);
            
            // Create required asistentes
            manejadorUsuarios.altaAsistente("testAsistente", "Test Asistente", "test1@test.com","testPW1","testImg1", "Test Apellido", LocalDate.of(1990, 1, 1), null);
            manejadorUsuarios.altaAsistente("testAsistente2", "Test Asistente 2", "test2@test.com","testPW2","testImg2", "Test Apellido 2", LocalDate.of(1991, 2, 2), null);
            manejadorUsuarios.altaAsistente("testAsistente3", "Test Asistente 3", "test3@test.com","testPW3","testImg3", "Test Apellido 3", LocalDate.of(1992, 3, 3), null);
        } catch (Exception e) {
            // Entities might already exist
        }
        

        
        // Act - Register multiple asistentes
        controlador.registrarAsistente("testAsistente", tipoRegistro, edicion);
        controlador.registrarAsistente("testAsistente2", tipoRegistro, edicion);
        controlador.registrarAsistente("testAsistente3", tipoRegistro, edicion);
        
        // Assert - Verify all registrations were successful
        List<Registro> registros = controlador.obtenerRegistrosDeEdicion(edicion);
        assertEquals(3, registros.size());
        
        // Verify cupo is now 0
        Edicion edicionObj = controlador.buscarEdicion(edicion);
        TipoRegistro tipoRegistroObj = edicionObj.buscarTipoRegistroPorNombre(tipoRegistro);
        assertEquals(0, tipoRegistroObj.getCupo());
    }
    
    @Test
    void testRegistrarAsistente_WithNullParameters() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente(null, "tipoRegistro", "edicion");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente("asistente", null, "edicion");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente("asistente", "tipoRegistro", null);
        });
    }
    
    @Test
    void testRegistrarAsistente_WithEmptyParameters() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente("", "tipoRegistro", "edicion");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente("asistente", "", "edicion");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarAsistente("asistente", "tipoRegistro", "");
        });
    }
    @Test
    void testRegistroConPatrocinio_CostoDeberiaSerCero() throws Exception {
        // Arrange - Create a complete setup with patrocinio
        String nombreEvento = "Test Event Patrocinio";
        String nombreEdicion = "Test Edition Patrocinio";
        String nombreTipoRegistro = "Test Tipo Patrocinio";
        String nombreInstitucion = "Test Institution Patrocinio";
        String nivelPatrocinio = "PLATA";
        BigDecimal aporteEconomico = new BigDecimal("15000.00"); // Large enough to cover registrations (25% of 15000 = 3750, which covers 5 * 500 = 2500)
        int cantidadRegistros = 5; // Allow 5 free registrations
        String codigoPatrocinio = "PAT_FREE";
        String nickAsistente = "testAsistentePatrocinio";
        
        // Create required entities
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        controlador.altaEvento(nombreEvento, "TEP", "Test Description", categorias);
        controlador.altaEdicionEvento(nombreEdicion, "TEP2024", LocalDate.of(2024, 6, 1), 
                                    LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
        controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", new BigDecimal("500.00"), 50);
        
        // Create institution first
        controlador.altaInstitucion(nombreInstitucion, "Test Description Patrocinio", "http://testpatrocinio.com");
        
        controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                  nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                  cantidadRegistros, codigoPatrocinio);
        
        // Create asistente
        manejadorUsuarios.altaAsistente(nickAsistente, "Test Asistente Patrocinio", "testpatrocinio@test.com","testPW","testImg", 
                                       "Test Apellido Patrocinio", LocalDate.of(1990, 1, 1),null);
        
        // Act - Register an asistente to the edition with patrocinio
        // First verify the asistente exists
        Asistente asistenteVerificado = manejadorUsuarios.buscarAsistente(nickAsistente);
        assertNotNull(asistenteVerificado, "Asistente should exist before registration");
        
        controlador.registrarAsistente(nickAsistente, nombreTipoRegistro, nombreEdicion);
        
        // Assert - Verify that the registration was created with costo = 0
        List<Registro> registros = controlador.obtenerRegistrosDeEdicion(nombreEdicion);
        assertFalse(registros.isEmpty(), "Should have at least one registration");
        
        // Find the registration for our asistente
        Registro registroEncontrado = null;
        for (Registro registro : registros) {
            // Since Registro doesn't have a direct reference to Asistente, 
            // we need to check through the asistente's registros
            ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
            Asistente asistente = manejadorUsuarios.buscarAsistente(nickAsistente);
            if (asistente != null && asistente.getRegistros().contains(registro)) {
                registroEncontrado = registro;
                break;
            }
        }
        
        assertNotNull(registroEncontrado, "Should find the registration for the asistente");
        assertEquals(BigDecimal.ZERO, registroEncontrado.getCosto(), 
                    "Registration cost should be 0 when there's a patrocinio for the tipoRegistro");
    }
    
    @Test
    void testRegistroSinPatrocinio_CostoDeberiaSerNormal() throws Exception {
        // Arrange - Create setup WITHOUT patrocinio
        String nombreEvento = "Test Event Sin Patrocinio";
        String nombreEdicion = "Test Edition Sin Patrocinio";
        String nombreTipoRegistro = "Test Tipo Sin Patrocinio";
        String nickAsistente = "testAsistenteSinPatrocinio";
        BigDecimal costoNormal = new BigDecimal("300.00");
        
        // Create required entities WITHOUT patrocinio
        try {
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TESP", "Test Description", categorias);
            controlador.altaEdicionEvento(nombreEdicion, "TESP2024", LocalDate.of(2024, 6, 1), 
                                        LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
            controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", costoNormal, 50);
            // NO patrocinio is created
            
            // Create asistente
            ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
            manejadorUsuarios.altaAsistente(nickAsistente, "Test Asistente Sin Patrocinio", "testsinpatrocinio@test.com","testPW", "testImg", 
                                           "Test Apellido Sin Patrocinio", LocalDate.of(1990, 1, 1),null);
        } catch (Exception e) {
            // Entities might already exist, continue with test
        }
        
        // Act - Register an asistente to the edition WITHOUT patrocinio
        controlador.registrarAsistente(nickAsistente, nombreTipoRegistro, nombreEdicion);
        
        // Assert - Verify that the registration was created with normal costo
        List<Registro> registros = controlador.obtenerRegistrosDeEdicion(nombreEdicion);
        assertFalse(registros.isEmpty(), "Should have at least one registration");
        
        // Find the registration for our asistente
        Registro registroEncontrado = null;
        for (Registro registro : registros) {
            ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
            Asistente asistente = manejadorUsuarios.buscarAsistente(nickAsistente);
            if (asistente != null && asistente.getRegistros().contains(registro)) {
                registroEncontrado = registro;
                break;
            }
        }
        
        assertNotNull(registroEncontrado, "Should find the registration for the asistente");
        assertEquals(costoNormal, registroEncontrado.getCosto(), 
                    "Registration cost should be normal when there's NO patrocinio for the tipoRegistro");
    }
    
    @Test
    void testPatrocinioConRegistrosGratuitosLimitados() throws Exception {
        // Arrange - Create patrocinio with limited free registrations
        String nombreEvento = "Test Event Limitado";
        String nombreEdicion = "Test Edition Limitado";
        String nombreTipoRegistro = "Test Tipo Limitado";
        String nombreInstitucion = "Test Institution Limitado";
        String nivelPatrocinio = "PLATA";
        BigDecimal aporteEconomico = new BigDecimal("5000.00");
        int cantidadRegistros = 2; // Only 2 free registrations
        String codigoPatrocinio = "PAT_LIMIT";
        String nickAsistente1 = "testAsistente1";
        String nickAsistente2 = "testAsistente2";
        String nickAsistente3 = "testAsistente3";
        
        // Create required entities
        List<CategoriaDTO> categorias = new ArrayList<>();
        categorias.add(new CategoriaDTO("Test Category 1"));
        controlador.altaEvento(nombreEvento, "TEL", "Test Description", categorias);
        controlador.altaEdicionEvento(nombreEdicion, "TEL2024", LocalDate.of(2024, 6, 1), 
                                    LocalDate.of(2024, 6, 3), "testOrg", "Test City", "Test Country", nombreEvento);
        controlador.altaTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro, "Test Description", new BigDecimal("200.00"), 50);
        
        // Create institution first
        controlador.altaInstitucion(nombreInstitucion, "Test Description Limitado", "http://testlimitado.com");
        
        controlador.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro, 
                                  nombreInstitucion, nivelPatrocinio, aporteEconomico, 
                                  cantidadRegistros, codigoPatrocinio);
        
        // Create asistentes
        manejadorUsuarios.altaAsistente(nickAsistente1, "Test Asistente 1", "test1@test.com","testPW","testImg",
                                       "Test Apellido 1", LocalDate.of(1990, 1, 1), null);
        manejadorUsuarios.altaAsistente(nickAsistente2, "Test Asistente 2", "test2@test.com", "testPW","testImg",
                                       "Test Apellido 2", LocalDate.of(1991, 2, 2), null);
        manejadorUsuarios.altaAsistente(nickAsistente3, "Test Asistente 3", "test3@test.com", "testPW","testImg",
                                       "Test Apellido 3", LocalDate.of(1992, 3, 3), null);
        
        // Act - Register first two asistentes (should be free)
        controlador.registrarAsistente(nickAsistente1, nombreTipoRegistro, nombreEdicion);
        controlador.registrarAsistente(nickAsistente2, nombreTipoRegistro, nombreEdicion);
        
        // Register third asistente (should pay normal cost)
        controlador.registrarAsistente(nickAsistente3, nombreTipoRegistro, nombreEdicion);
        
        // Assert - Verify that first two registrations are free, third is paid
        List<Registro> registros = controlador.obtenerRegistrosDeEdicion(nombreEdicion);
        assertEquals(3, registros.size(), "Should have exactly 3 registrations");
        
        // Check that first two registrations are free
        boolean foundFree1 = false, foundFree2 = false, foundPaid = false;
        for (Registro registro : registros) {
            ManejadorUsuarios manejadorUsuarios = ManejadorUsuarios.getInstance();
            
            Asistente asistente1 = manejadorUsuarios.buscarAsistente(nickAsistente1);
            Asistente asistente2 = manejadorUsuarios.buscarAsistente(nickAsistente2);
            Asistente asistente3 = manejadorUsuarios.buscarAsistente(nickAsistente3);
            
            if (asistente1 != null && asistente1.getRegistros().contains(registro)) {
                if (registro.getCosto().equals(BigDecimal.ZERO)) {
                    foundFree1 = true;
                }
            } else if (asistente2 != null && asistente2.getRegistros().contains(registro)) {
                if (registro.getCosto().equals(BigDecimal.ZERO)) {
                    foundFree2 = true;
                }
            } else if (asistente3 != null && asistente3.getRegistros().contains(registro)) {
                if (registro.getCosto().equals(new BigDecimal("200.00"))) {
                    foundPaid = true;
                }
            }
        }
        
        assertTrue(foundFree1, "First registration should be free");
        assertTrue(foundFree2, "Second registration should be free");
        assertTrue(foundPaid, "Third registration should be paid");
    }
    
        // ⚠️ Nombre vacío
        @Test
        void testAltaEvento_NombreVacio() {
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento(" ", "SIG", "desc", categorias, "img")
            );
            assertEquals("El nombre del evento es obligatorio", ex.getMessage());
        }

        // ⚠️ Sigla vacía
        @Test
        void testAltaEvento_SiglaVacia() {
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento("Evento", "", "desc", categorias, "img")
            );
            assertEquals("La sigla del evento es obligatoria", ex.getMessage());
        }

        // ⚠️ Descripción vacía
        @Test
        void testAltaEvento_DescripcionVacia() {
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento("Evento", "SIG", " ", categorias, "img")
            );
            assertEquals("La descripción del evento es obligatoria", ex.getMessage());
        }

        // ⚠️ Categorías vacías
        @Test
        void testAltaEvento_SinCategorias() {
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento("Evento", "SIG", "desc", new ArrayList<>(), "img")
            );
            assertEquals("El evento debe tener al menos una categoría", ex.getMessage());
        }

        // ⚠️ Categoría inexistente
        @Test
        void testAltaEvento_CategoriaInexistente() {
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("NoExiste"));
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento("Evento", "SIG", "desc", categorias, "img")
            );
            assertEquals("La categoría 'NoExiste' no existe en el sistema", ex.getMessage());
        }
        
        @Test
        void testAltaEdicionEvento_Success_SinFechaAlta() throws Exception {
            // Arrange
            String nombreEvento = "Evento Base";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EVT", "Evento base para test", categorias, "img.png");

            String nombreEdicion = "Edición 2025";
            String sigla = "E25";
            LocalDate inicio = LocalDate.of(2025, 1, 1);
            LocalDate fin = LocalDate.of(2025, 1, 5);
            String organizador = "testOrg";
            String ciudad = "Montevideo";
            String pais = "Uruguay";
            String imagen = "imagen_edicion.png";

            // Act
            controlador.altaEdicionEvento(nombreEdicion, sigla, inicio, fin, organizador, ciudad, pais, nombreEvento, imagen);

            // Assert
            Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
            assertNotNull(edicion);
            assertEquals(nombreEdicion, edicion.getNombre());
            assertEquals(sigla, edicion.getSigla());
            assertEquals(ciudad, edicion.getCiudad());
            assertEquals(pais, edicion.getPais());
        }

        @Test
        void testAltaEdicionEvento_Success_ConFechaAlta() throws Exception {
            // Arrange
            String nombreEvento = "Evento Base 2";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 2"));
            controlador.altaEvento(nombreEvento, "EV2", "Evento base para test 2", categorias, "img2.png");

            String nombreEdicion = "Edición 2026";
            String sigla = "E26";
            LocalDate inicio = LocalDate.of(2026, 3, 1);
            LocalDate fin = LocalDate.of(2026, 3, 10);
            LocalDate fechaAlta = LocalDate.of(2025, 12, 1);
            String organizador = "testOrg";
            String ciudad = "Punta del Este";
            String pais = "Uruguay";
            String imagen = "imagen_edicion2.png";

            // Act
            controlador.altaEdicionEvento(nombreEdicion, sigla, inicio, fin, fechaAlta, organizador, ciudad, pais, nombreEvento, imagen);

            // Assert
            Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
            assertNotNull(edicion);
            assertEquals(nombreEdicion, edicion.getNombre());
            assertEquals(sigla, edicion.getSigla());
            assertEquals(ciudad, edicion.getCiudad());
            assertEquals(pais, edicion.getPais());
            assertEquals(fechaAlta, edicion.getFechaAlta());
        }

        @Test
        void testAltaEdicionEvento_OrganizadorNoExiste() {
            String nombreEvento = "Evento Base 3";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            assertDoesNotThrow(() -> controlador.altaEvento(nombreEvento, "EV3", "desc", categorias, "img.png"));

            Exception ex = assertThrows(RuntimeException.class, () ->
                controlador.altaEdicionEvento("Edición Error", "EERR", LocalDate.now(), LocalDate.now().plusDays(1),
                    "organizadorInexistente", "Ciudad", "Pais", nombreEvento, "img.png")
            );
            assertTrue(ex.getMessage().contains("Organizador no encontrado"));
        }

        @Test
        void testAltaEdicionEvento_EventoNoExiste() {
            Exception ex = assertThrows(RuntimeException.class, () ->
                controlador.altaEdicionEvento("Edición Error", "EERR", LocalDate.now(), LocalDate.now().plusDays(1),
                    "testOrg", "Ciudad", "Pais", "EventoInexistente", "img.png")
            );
            assertTrue(ex.getMessage().contains("Evento no encontrado"));
        }
        
        @Test
        void testObtenerRegistrosDeEdicionDTO_SinRegistros() throws Exception {
            // Arrange: crear evento y edición sin registros
            String nombreEvento = "Evento Sin Registros";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "ESR", "Evento sin registros", categorias, "img.png");
            controlador.altaEdicionEvento("Edición SinReg", "ESR2025",
                    LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            // Act
            List<RegistroDTO> registros = controlador.obtenerRegistrosDeEdicionDTO("Edición SinReg");

            // Assert
            assertNotNull(registros);
            assertTrue(registros.isEmpty(), "No debe haber registros en la edición recién creada");
        }

        @Test
        void testObtenerRegistrosDeEdicionDTO_EdicionNoExiste() {
            // Act
            List<RegistroDTO> registros = controlador.obtenerRegistrosDeEdicionDTO("Inexistente");
            // Assert
            assertNotNull(registros);
            assertTrue(registros.isEmpty());
        }

        @Test
        void testBuscarTipoRegistroDTO_TipoExiste() throws Exception {
            // Arrange: crear evento y edición
            String nombreEvento = "Evento TipoRegistro";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "ETR", "Evento tipo registro", categorias, "img.png");

            controlador.altaEdicionEvento("Edición TipoReg", "ETR2025",
                    LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 10),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            // Crear un tipo de registro en esa edición
            Edicion ed = manejadorEventos.buscarEdicion("Edición TipoReg");
            TipoRegistro tipo = new TipoRegistro("General", "Entrada general", new BigDecimal("500.0"), 100);
            ed.getTiposRegistro().add(tipo);

            // Act
            TipoRegistroDTO dto = controlador.buscarTipoRegistroDTO("Edición TipoReg", "General");

            // Assert
            assertNotNull(dto);
            assertEquals("General", dto.getNombre());
            assertEquals(100, dto.getCupoDisponible());
            assertEquals(100, dto.getCupo());
        }

        @Test
        void testBuscarTipoRegistroDTO_TipoNoExiste() throws Exception {
            // Arrange: crear evento y edición
            String nombreEvento = "Evento SinTipo";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EST", "Evento sin tipo", categorias, "img.png");

            controlador.altaEdicionEvento("Edición SinTipo", "EST2025",
                    LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 5),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            // Act
            TipoRegistroDTO dto = controlador.buscarTipoRegistroDTO("Edición SinTipo", "Inexistente");

            // Assert
            assertNull(dto);
        }

        @Test
        void testBuscarTipoRegistroDTO_EdicionNoExiste() {
            TipoRegistroDTO dto = controlador.buscarTipoRegistroDTO("NoExiste", "General");
            assertNull(dto);
        }

        @Test
        void testCambiarEstadoEdicion_Aceptada() throws Exception {
            // Arrange
            String nombreEvento = "Evento Estado";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 2"));
            controlador.altaEvento(nombreEvento, "EE", "Evento para estados", categorias, "img.png");

            controlador.altaEdicionEvento("Edición Estado", "EE2025",
                    LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 10),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            // Act
            controlador.cambiarEstadoEdicion("Edición Estado", EstadoEnum.ACEPTADA);

            // Assert
            Edicion ed = manejadorEventos.buscarEdicion("Edición Estado");
            assertEquals(EstadoEnum.ACEPTADA, ed.getEstado());
        }

        @Test
        void testCambiarEstadoEdicion_Rechazada() throws Exception {
            String nombreEvento = "Evento Estado2";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EE2", "Evento estado 2", categorias, "img.png");
            controlador.altaEdicionEvento("Edición Estado2", "EE2026",
                    LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 10),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            controlador.cambiarEstadoEdicion("Edición Estado2", EstadoEnum.RECHAZADA);

            Edicion ed = manejadorEventos.buscarEdicion("Edición Estado2");
            assertEquals(EstadoEnum.RECHAZADA, ed.getEstado());
        }

        @Test
        void testCambiarEstadoEdicion_Invalido() throws Exception {
            String nombreEvento = "Evento Estado3";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EE3", "Evento estado 3", categorias, "img.png");
            controlador.altaEdicionEvento("Edición Estado3", "EE2027",
                    LocalDate.of(2027, 5, 1), LocalDate.of(2027, 5, 10),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                controlador.cambiarEstadoEdicion("Edición Estado3", EstadoEnum.INGRESADA)
            );
            assertTrue(ex.getMessage().contains("Solo se puede aceptar o rechazar"));
        }

        @Test
        void testCambiarEstadoEdicion_EdicionNoExiste() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                controlador.cambiarEstadoEdicion("NoExiste", EstadoEnum.ACEPTADA)
            );
            assertTrue(ex.getMessage().contains("No se encontró la edición"));
        }

        @Test
        void testAgregarEventoAOrganizador_Success() throws Exception {
            String nombreEvento = "Evento Org";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EO", "Evento de org", categorias, "img.png");

            controlador.agregarEventoAOrganizador(nombreEvento, "testOrg");

            Organizador org = manejadorUsuarios.buscarOrganizador("testOrg");
            boolean contiene = org.getEventos().stream().anyMatch(e -> e.getNombre().equals(nombreEvento));
            assertTrue(contiene);
        }

        @Test
        void testListarEdicionesIngresadas_SoloRetornaIngresadas() throws Exception {
            String nombreEvento = "Evento Ingresadas";
            List<CategoriaDTO> categorias = List.of(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EI", "Evento Ingresadas", categorias, "img.png");

            // Crear dos ediciones
            controlador.altaEdicionEvento("Edición Ingresada", "EI2025",
                    LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            controlador.altaEdicionEvento("Edición Aceptada", "EI2026",
                    LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 5),
                    "testOrg", "Montevideo", "Uruguay", nombreEvento, "img.png");

            // Cambiar una de ellas a ACEPTADA
            controlador.cambiarEstadoEdicion("Edición Aceptada", EstadoEnum.ACEPTADA);

            // Act
            List<String> ingresadas = controlador.listarEdicionesIngresadas(nombreEvento);

            // Assert
            assertTrue(ingresadas.contains("Edición Ingresada"));
            assertFalse(ingresadas.contains("Edición Aceptada"));
        }

        // -------------------------
        // TESTS DE LA CLASE PATROCINIO
        // -------------------------

        @Test
        void testConstructorPatrocinioInicializaCamposYRelacionConEdicion() throws Exception {
            // Crear datos base usando el manejador real
            String eventoNombre = "EventoPatrocinado";
            manejadorEventos.altaEvento(eventoNombre, "EP", "Evento de prueba",
                    List.of(new Categoria("Test Category 1")),null);
            manejadorEventos.altaEdicion("EdicionPatrocinada", "EP2025",
                    LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31),
                    "testOrg", "Montevideo", "Uruguay", eventoNombre, null);

            Edicion edicion = manejadorEventos.buscarEdicion("EdicionPatrocinada");
            Institucion institucion = manejadorEventos.buscarInstitucion("Test Institution");
            TipoRegistro tipo = new TipoRegistro("VIP", "nombre", new BigDecimal("100"), 10);

            Patrocinio patrocinio = new Patrocinio(
                    "PAT001",
                    new BigDecimal("1000"),
                    NivelPatrocinio.ORO,
                    2,
                    edicion,
                    institucion,
                    tipo
            );

            assertEquals("PAT001", patrocinio.getCodigo());
            assertEquals(NivelPatrocinio.ORO, patrocinio.getNivel());
            assertEquals(2, patrocinio.getRegistrosGratuitos());
            assertEquals(edicion, patrocinio.getEdicion());
            assertEquals(institucion, patrocinio.getInstitucion());
            assertEquals(tipo, patrocinio.getTipoRegistro());
            assertNotNull(patrocinio.getFecha());
        }

        @Test
        void testSettersPatrocinio() {
            Patrocinio p = new Patrocinio("X1", BigDecimal.ONE, NivelPatrocinio.BRONCE, 1, null, null, null);

            LocalDate fecha = LocalDate.of(2025, 10, 12);
            Institucion inst2 = new Institucion("Inst 2", "Desc", "http://test.com");

            p.setCodigo("C123");
            p.setFecha(fecha);
            p.setMonto(new BigDecimal("500"));
            p.setNivel(NivelPatrocinio.PLATA);
            p.setRegistrosGratuitos(10);
            p.setInstitucion(inst2);

            assertAll(
                    () -> assertEquals("C123", p.getCodigo()),
                    () -> assertEquals(fecha, p.getFecha()),
                    () -> assertEquals(new BigDecimal("500"), p.getMonto()),
                    () -> assertEquals(NivelPatrocinio.PLATA, p.getNivel()),
                    () -> assertEquals(10, p.getRegistrosGratuitos()),
                    () -> assertEquals(inst2, p.getInstitucion())
            );
        }

        @Test
        void testValidarMontoRegistros_True() {
            TipoRegistro tipo = new TipoRegistro("VIP","desc", new BigDecimal("100"), 10);
            
            Patrocinio p = new Patrocinio(
                    "PAT002", new BigDecimal("1000"), NivelPatrocinio.PLATA, 2, null, null, tipo
            );

            assertTrue(p.validarMontoRegistros(), "2 * 100 = 200 <= 20% de 1000");
        }

        @Test
        void testValidarMontoRegistros_False() {
            TipoRegistro tipo = new TipoRegistro("VIP","desc", new BigDecimal("100"), 10);
            Patrocinio p = new Patrocinio(
                    "PAT003", new BigDecimal("1000"), NivelPatrocinio.BRONCE, 3, null, null, tipo
            );

            assertFalse(p.validarMontoRegistros(), "3 * 100 = 300 > 20% de 1000");
        }

        @Test
        void testToDTO_MapeaCorrectamenteCampos() throws Exception {
            // Crear datos de prueba reales
            String eventoNombre = "EventoPatrocinadoDTO";
            manejadorEventos.altaEvento(eventoNombre, "EPDTO", "Evento DTO",
                    List.of(new Categoria("Test Category 1")), null);

            manejadorEventos.altaEdicion("EdicionPatrocinadaDTO", "EP2026",
                    LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31),
                    "testOrg", "Montevideo", "Uruguay", eventoNombre, null);

            Edicion ed = manejadorEventos.buscarEdicion("EdicionPatrocinadaDTO");
            Institucion inst = manejadorEventos.buscarInstitucion("Test Institution");
            TipoRegistro tipo = new TipoRegistro("Premium", "", new BigDecimal("200"), 10);

            Patrocinio p = new Patrocinio(
                    "PAT004",
                    new BigDecimal("1500"),
                    NivelPatrocinio.ORO,
                    1,
                    ed,
                    inst,
                    tipo
            );

            PatrocinioDTO dto = p.toDTO();

            assertAll(
                    
                    () -> assertEquals("Evento de " + ed.getNombre(), dto.getEvento()),
                    () -> assertEquals(ed.getNombre(), dto.getEdicion()),
                    () -> assertEquals("Premium", dto.getTipoRegistro()),
                    () -> assertNotNull(dto.getInstitucion()),
                    () -> assertEquals(NivelPatrocinio.ORO.getDescripcion(), dto.getNivelPatrocinio())
                    
            );
        }

        @Test
        void testToDTO_ManejaNulosSinExcepcion() {
            Patrocinio p = new Patrocinio(
                    "PAT005",
                    new BigDecimal("800"),
                    null,
                    0,
                    null,
                    null,
                    null
            );

            PatrocinioDTO dto = p.toDTO();

            assertAll(
                    () -> assertEquals("N/A", dto.getEvento()),
                    () -> assertEquals("N/A", dto.getEdicion()),
                    () -> assertEquals("N/A", dto.getTipoRegistro()),
                    () -> assertNull(dto.getInstitucion()),
                    () -> assertEquals("N/A", dto.getNivelPatrocinio())
            );
        }
        
        @Test
        void testEdicionConstructorWithEstado() {
            LocalDate inicio = LocalDate.of(2025, 1, 1);
            LocalDate fin = LocalDate.of(2025, 12, 31);
            LocalDate alta = LocalDate.of(2024, 6, 15);

            Edicion edicion = new Edicion(
                "Edicion Test", 
                "EDT", 
                inicio, 
                fin, 
                alta, 
                "Organizador Test", 
                "Montevideo", 
                "Uruguay", 
                "img.png", 
                "INGRESADA"
            );

            assertEquals("Edicion Test", edicion.getNombre());
            assertEquals("EDT", edicion.getSigla());
            assertEquals(inicio, edicion.getFechaInicio());
            assertEquals(fin, edicion.getFechaFin());
            assertEquals(alta, edicion.getFechaAlta());
            assertEquals("Organizador Test", edicion.getOrganizador());
            assertEquals("Montevideo", edicion.getCiudad());
            assertEquals("Uruguay", edicion.getPais());
            assertEquals("img.png", edicion.getImagen());
            assertNotNull(edicion.getTiposRegistro());
            assertNotNull(edicion.getPatrocinios());
            assertEquals(EstadoEnum.INGRESADA, edicion.getEstado());
        }

        @Test
        void testEdicionConstructorWithLowercaseEstado() {
            Edicion edicion = new Edicion(
                "Edicion Lower", 
                "ELW", 
                LocalDate.now(), 
                LocalDate.now().plusDays(5), 
                LocalDate.now(), 
                "Org", 
                "Ciudad", 
                "Pais", 
                "imagen.png", 
                "ingresada"
            );
            assertEquals(EstadoEnum.INGRESADA, edicion.getEstado());
        }

        @Test
        void testEdicionSettersAndGetters() {
            LocalDate inicio = LocalDate.of(2025, 5, 1);
            LocalDate fin = LocalDate.of(2025, 5, 10);
            LocalDate alta = LocalDate.of(2024, 8, 20);

            Edicion edicion = new Edicion(
                "E1", "SIG", inicio, fin, alta,
                "Org1", "Ciudad1", "Pais1", "img1.png", "activa"
            );

            edicion.setNombre("Nuevo Nombre");
            edicion.setSigla("NUEVA");
            edicion.setDescripcion("Nueva descripcion");
            edicion.setFechaInicio(LocalDate.of(2026, 1, 1));
            edicion.setFechaFin(LocalDate.of(2026, 2, 1));
            edicion.setFechaAlta(LocalDate.of(2024, 9, 1));
            edicion.setOrganizador("Nuevo Organizador");
            edicion.setPais("Argentina");
            edicion.setCiudad("Buenos Aires");
            edicion.setImagen("nueva.png");

            assertEquals("Nuevo Nombre", edicion.getNombre());
            assertEquals("NUEVA", edicion.getSigla());
            assertEquals("Nueva descripcion", edicion.getDescripcion());
            assertEquals(LocalDate.of(2026, 1, 1), edicion.getFechaInicio());
            assertEquals(LocalDate.of(2026, 2, 1), edicion.getFechaFin());
            assertEquals(LocalDate.of(2024, 9, 1), edicion.getFechaAlta());
            assertEquals("Nuevo Organizador", edicion.getOrganizador());
            assertEquals("Argentina", edicion.getPais());
            assertEquals("Buenos Aires", edicion.getCiudad());
            assertEquals("nueva.png", edicion.getImagen());
        }

        @Test
        void testGetSetCategorias() {
            EventoDTO eventoDTO = new EventoDTO();
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Categoria 1"));
            categorias.add(new CategoriaDTO("Categoria 2"));
            
            eventoDTO.setCategorias(categorias);
            
            List<CategoriaDTO> result = eventoDTO.getCategorias();
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Categoria 1", result.get(0).getNombre());
        }

        @Test
        void testGetSetEdiciones() {
            EventoDTO eventoDTO = new EventoDTO();
            List<EdicionDTO> ediciones = new ArrayList<>();
            ediciones.add(new EdicionDTO("Edicion 2023", null, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), null, null, null, null, null));
            ediciones.add(new EdicionDTO("Edicion 2024", null, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), null, null, null, null, null));
            
            eventoDTO.setEdiciones(ediciones);
            
            List<EdicionDTO> result = eventoDTO.getEdiciones();
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Edicion 2023", result.get(0).getNombre());
        }

        @Test
        void testGetSetFinalizado() {
            EventoDTO eventoDTO = new EventoDTO();
            
            eventoDTO.setFinalizado(true);
            assertTrue(eventoDTO.isFinalizado());
            
            eventoDTO.setFinalizado(false);
            assertFalse(eventoDTO.isFinalizado());
        }

        @Test
        void testGetSetVisitas() {
            EventoDTO eventoDTO = new EventoDTO();
            
            eventoDTO.setVisitas(42);
            assertEquals(42, eventoDTO.getVisitas());
            
            eventoDTO.setVisitas(0);
            assertEquals(0, eventoDTO.getVisitas());
        }

        @Test
        void testEventoDTOInicializacionVacia() {
            EventoDTO eventoDTO = new EventoDTO();
            
            assertNull(eventoDTO.getCategorias());
            assertNull(eventoDTO.getEdiciones());
            assertFalse(eventoDTO.isFinalizado());
            assertEquals(0, eventoDTO.getVisitas());
        }

        @Test
        void testFinalizarEvento() throws BusinessException {
            // Arrange
            String nombreEvento = "EventoFinalizar";
            manejadorEventos.altaEvento(nombreEvento, "EF", "Desc", null, manejadorUsuarios.buscarOrganizador("testOrg").getNickname());
            Evento evento = controlador.buscarEvento(nombreEvento);
            assertFalse(evento.isFinalizado());

            // Act
            controlador.finalizarEvento(nombreEvento);

            // Assert
            assertTrue(evento.isFinalizado());
        }

        @Test
        void testFinalizarEvento_EventoNoExiste() {
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.finalizarEvento("EventoInexistente")
            );
            assertEquals("Evento no encontrado: EventoInexistente", ex.getMessage());
        }

        @Test
        void testArchivarEdicion_Success() throws BusinessException, NombreEdicionException {
            // Arrange
            String nombreEdicion = "EdicionTest";
            manejadorEventos.altaEvento("EventoArchivar", "EA", "Desc", null, manejadorUsuarios.buscarOrganizador("testOrg").getNickname());
            controlador.altaEdicionEvento(nombreEdicion, "EventoArchivar", LocalDate.of(2020,1,1), LocalDate.of(2020,1,2),
            		 manejadorUsuarios.buscarOrganizador("testOrg").getNickname(), EstadoEnum.ACEPTADA.toString(), nombreEdicion, nombreEdicion);
            Edicion edicion = controlador.buscarEdicion("EdicionTest");
            // Act
            controlador.archivarEdicion(nombreEdicion, "testOrg");

            // Assert
            assertTrue(edicion.isArchivada());
            assertEquals(LocalDate.now(), edicion.getFechaArchivo());
        }

        @Test
        void testArchivarEdicion_Errores() throws BusinessException, NombreEdicionException {
            String nombreEdicion = "EdicionError";
            manejadorEventos.altaEvento("EventoError", "EE", "Desc", null, manejadorUsuarios.buscarOrganizador("testOrg").getNickname());
            Evento evento = manejadorEventos.buscarEvento("EventoError");
            controlador.altaEdicionEvento(nombreEdicion, "EventoError", LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 
            	 manejadorUsuarios.buscarOrganizador("testOrg").getNickname(), EstadoEnum.INGRESADA.toString(), nombreEdicion, nombreEdicion);
            Edicion edicion = controlador.buscarEdicion(nombreEdicion);
            // Edicion no aprobada
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.archivarEdicion(nombreEdicion, "testOrg")
            );
            assertEquals("Solo se pueden archivar ediciones aprobadas", ex.getMessage());

            // Edicion con fecha futura
            edicion.setEstado(EstadoEnum.ACEPTADA);
            ex = assertThrows(BusinessException.class, () ->
                controlador.archivarEdicion(nombreEdicion, "testOrg")
            );
            assertEquals("Solo se pueden archivar ediciones finalizadas", ex.getMessage());

            // Organizador incorrecto
            ex = assertThrows(BusinessException.class, () ->
                controlador.archivarEdicion(nombreEdicion, "otroOrg")
            );
            assertEquals("Solo el organizador de la edición puede archivarla", ex.getMessage());
        }


        @Test
        void testRegistrarAsistencia_Errores() {
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.registrarAsistencia("inexistente", "EdicionInexistente")
            );
            assertEquals("Asistente no encontrado: inexistente", ex.getMessage());
        }

        @Test
        void testRegistrarVisitaYObtenerVisitas() {
            String evento = "EventoVisitas";
            controlador.setVisitasEvento(evento, 5);

            controlador.registrarVisita(evento);
            controlador.registrarVisita(evento);

            int visitas = controlador.obtenerVisitasDeEvento(evento);
            assertEquals(7, visitas);

            List<String[]> topEventos = controlador.obtenerEventosMasVisitados(1);
            assertEquals(1, topEventos.size());
            assertEquals(evento, topEventos.get(0)[0]);
            assertEquals("7", topEventos.get(0)[1]);
        }

        @Test
        void testBuscarEventosYEdiciones() throws BusinessException, NombreEdicionException {
            // Arrange
            manejadorEventos.altaEvento("EventoBuscar", "EB", "Descripcion unica", null, manejadorUsuarios.buscarOrganizador("testOrg").getNickname());
            controlador.altaEdicionEvento("EdicionBuscar", "EventoBuscar", LocalDate.of(2020,1,1), LocalDate.of(2020,1,2),
            	 manejadorUsuarios.buscarOrganizador("testOrg").getNickname(), EstadoEnum.ACEPTADA.toString(), null, null);

            // Act
            List<Object> resultados = controlador.buscarEventosYEdiciones("buscar");

            // Assert
            assertTrue(resultados.stream().anyMatch(r -> r instanceof EventoDTO && ((EventoDTO) r).getNombre().equals("EventoBuscar")));
            assertTrue(resultados.stream().anyMatch(r -> r instanceof EdicionDTO && ((EdicionDTO) r).getNombre().equals("EdicionBuscar")));

            // Query vacía devuelve todos
            resultados = controlador.buscarEventosYEdiciones("");
            assertTrue(resultados.size() >= 2);
        }

        @Test
        void testAltaEdicionEvento() throws Exception {
            // Arrange
            String nombreEdicion = "Edicion Test";
            String sigla = "ET";
            LocalDate fechaInicio = LocalDate.of(2025, 11, 15);
            LocalDate fechaFin = LocalDate.of(2025, 11, 20);
            LocalDate fechaAlta = LocalDate.now();
            String nombreOrganizador = "testOrg";
            String ciudad = "Montevideo";
            String pais = "Uruguay";
            String nombreEvento = "Test Event";
            String imagen = "imagen.png";
            String estado = "ACEPTADA";

            // Crear un evento de prueba
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "TE", "Descripcion", categorias);

            // Act
            controlador.altaEdicionEvento(nombreEdicion, sigla, fechaInicio, fechaFin, fechaAlta,
                                           nombreOrganizador, ciudad, pais, nombreEvento, imagen, estado);

            // Assert
            Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
            assertNotNull(edicion);
            assertEquals(nombreEdicion, edicion.getNombre());
            assertEquals(sigla, edicion.getSigla());
            assertEquals(fechaInicio, edicion.getFechaInicio());
            assertEquals(fechaFin, edicion.getFechaFin());
            assertEquals(nombreOrganizador, edicion.getOrganizador());
            assertEquals(nombreEvento, edicion.getEvento());
        }

        // Test para evento finalizado
        @Test
        void testAltaEdicionEvento_EventoFinalizado() throws Exception {
            // Arrange
            String nombreEvento = "EventoFinalizado";
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            controlador.altaEvento(nombreEvento, "EF", "Descripcion", categorias);
            
            // Marcar el evento como finalizado
            controlador.finalizarEvento(nombreEvento);

            // Act & Assert
            assertThrows(NombreEdicionException.class, () -> {
                controlador.altaEdicionEvento("EdicionFinalizada", "EF1", LocalDate.now(), LocalDate.now().plusDays(1),
                                               LocalDate.now(), "testOrg", "Montevideo", "Uruguay",
                                               nombreEvento, "imagen.png", "ACEPTADA");
            });
        }

        @Test
        void testArchivarEdicion_Success1() throws Exception {
            // Arrange: crear evento y edición aprobada y finalizada
            String nombreEvento = "EventoArchivar";
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category"));
            controlador.altaEvento(nombreEvento, "EA", "Descripcion", categorias);

            String nombreEdicion = "EdicionArchivar";
            LocalDate fechaInicio = LocalDate.now().minusDays(5);
            LocalDate fechaFin = LocalDate.now().minusDays(1);
            controlador.altaEdicionEvento(nombreEdicion, "EA1", fechaInicio, fechaFin, LocalDate.now(),
                                           "testOrg", "Montevideo", "Uruguay", nombreEvento, "imagen.png", "ACEPTADA");

            // Act
            controlador.archivarEdicion(nombreEdicion, "testOrg");

            // Assert
            Edicion edicion = manejadorEventos.buscarEdicion(nombreEdicion);
            assertTrue(edicion.isArchivada());
            assertEquals(LocalDate.now(), edicion.getFechaArchivo());
        }

        @Test
        void testArchivarEdicion_EdicionNoEncontrada() {
            assertThrows(BusinessException.class, () -> {
                controlador.archivarEdicion("NoExiste", "testOrg");
            });
        }

        @Test
        void testArchivarEdicion_OrganizadorIncorrecto() throws Exception {
            String nombreEdicion = "EdicionOtra";
            LocalDate fechaInicio = LocalDate.now().minusDays(5);
            LocalDate fechaFin = LocalDate.now().minusDays(1);
            controlador.altaEdicionEvento(nombreEdicion, "EO1", fechaInicio, fechaFin, LocalDate.now(),
                                           "testOrg", "Montevideo", "Uruguay", "Test Event", "imagen.png", "ACEPTADA");

            assertThrows(BusinessException.class, () -> {
                controlador.archivarEdicion(nombreEdicion, "OtroOrg");
            });
        }

        @Test
        void testArchivarEdicion_EdicionNoAprobada() throws Exception {
            String nombreEdicion = "EdicionPendiente";
            LocalDate fechaInicio = LocalDate.now().minusDays(5);
            LocalDate fechaFin = LocalDate.now().minusDays(1);
            controlador.altaEdicionEvento(nombreEdicion, "EP1", fechaInicio, fechaFin, LocalDate.now(),
                                           "testOrg", "Montevideo", "Uruguay", "Test Event", "imagen.png", "PENDIENTE");

            assertThrows(BusinessException.class, () -> {
                controlador.archivarEdicion(nombreEdicion, "testOrg");
            });
        }

        @Test
        void testArchivarEdicion_EdicionNoFinalizada() throws Exception {
            String nombreEdicion = "EdicionFutura";
            LocalDate fechaInicio = LocalDate.now().plusDays(1);
            LocalDate fechaFin = LocalDate.now().plusDays(5);
            controlador.altaEdicionEvento(nombreEdicion, "EF1", fechaInicio, fechaFin, LocalDate.now(),
                                           "testOrg", "Montevideo", "Uruguay", "Test Event", "imagen.png", "ACEPTADA");

            assertThrows(BusinessException.class, () -> {
                controlador.archivarEdicion(nombreEdicion, "testOrg");
            });
        }

        @Test
        void testBuscarEventosYEdiciones_QueryNullOrEmpty() throws Exception {
            // Arrange: crear evento y edicion
            String nombreEvento = "EventoBusqueda";
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category"));
            controlador.altaEvento(nombreEvento, "EB", "Descripcion evento", categorias);

            String nombreEdicion = "EdicionBusqueda";
            LocalDate fechaInicio = LocalDate.now().minusDays(5);
            LocalDate fechaFin = LocalDate.now().minusDays(1);
            controlador.altaEdicionEvento(nombreEdicion, "EB1", fechaInicio, fechaFin, LocalDate.now(),
                                           "testOrg", "Montevideo", "Uruguay", nombreEvento, "imagen.png", "ACEPTADA");

            // Act
            List<Object> resultadosNull = controlador.buscarEventosYEdiciones(null);
            List<Object> resultadosEmpty = controlador.buscarEventosYEdiciones("   ");

            // Assert
            assertTrue(resultadosNull.size() >= 2);
            assertTrue(resultadosEmpty.size() >= 2);
            boolean contieneEvento = resultadosNull.stream().anyMatch(r -> r instanceof EventoDTO && ((EventoDTO) r).getNombre().equals(nombreEvento));
            boolean contieneEdicion = resultadosNull.stream().anyMatch(r -> r instanceof EdicionDTO && ((EdicionDTO) r).getNombre().equals(nombreEdicion));
            assertTrue(contieneEvento);
            assertTrue(contieneEdicion);
        }

        @Test
        void testBuscarEventosYEdiciones_QueryCoincideEvento() throws Exception {
            // Arrange
            String nombreEvento = "EventoEspecial";
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Categoria1"));
            controlador.altaEvento(nombreEvento, "EE", "Descripcion especial", categorias);

            // Act
            List<Object> resultados = controlador.buscarEventosYEdiciones("especial");

            // Assert
            assertTrue(resultados.stream().anyMatch(r -> r instanceof EventoDTO && ((EventoDTO) r).getNombre().equals(nombreEvento)));
        }

        @Test
        void testBuscarEventosYEdiciones_QueryCoincideEdicion() throws Exception {
            // Arrange
            String nombreEvento = "EventoConEdicion";
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Categoria2"));
            controlador.altaEvento(nombreEvento, "ECE", "Descripcion evento", categorias);

            String nombreEdicion = "EdicionUnica";
            LocalDate fechaInicio = LocalDate.now().minusDays(5);
            LocalDate fechaFin = LocalDate.now().minusDays(1);
            controlador.altaEdicionEvento(nombreEdicion, "EU1", fechaInicio, fechaFin, LocalDate.now(),
                                           "testOrg", "Montevideo", "Uruguay", nombreEvento, "imagen.png", "ACEPTADA");

            // Act
            List<Object> resultados = controlador.buscarEventosYEdiciones("unica");

            // Assert
            assertTrue(resultados.stream().anyMatch(r -> r instanceof EdicionDTO && ((EdicionDTO) r).getNombre().equals(nombreEdicion)));
        }

        @Test
        void testBuscarEventosYEdiciones_QueryNoCoincide() throws Exception {
            // Act
            List<Object> resultados = controlador.buscarEventosYEdiciones("noExiste");

            // Assert
            assertTrue(resultados.isEmpty());
        }

        @Test
        void testAltaEvento_Success_WithImagenYFechaAlta() throws BusinessException {
            // Arrange
            String nombre = "EventoCompleto";
            String sigla = "EC";
            String descripcion = "Descripcion completa del evento";
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));
            String imagen = "imagen.png";
            LocalDate fechaAlta = LocalDate.now();

            // Act
            controlador.altaEvento(nombre, sigla, descripcion, categorias, imagen, fechaAlta);

            // Assert
            Evento evento = manejadorEventos.buscarEvento(nombre);
            assertNotNull(evento);
            assertEquals(nombre, evento.getNombre());
            assertEquals(sigla, evento.getSigla());
            assertEquals(descripcion, evento.getDescripcion());
            assertEquals(imagen, evento.getImagen());
            assertEquals(fechaAlta, evento.getFechaAlta());
        }

        @Test
        void testAltaEvento_Fallo_SinNombre() {
            // Arrange
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("Test Category 1"));

            // Act & Assert
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento(null, "SIG", "Descripcion", categorias, "img.png", LocalDate.now())
            );
            assertEquals("El nombre del evento es obligatorio", ex.getMessage());
        }

        @Test
        void testAltaEvento_Fallo_SinCategorias() {
            // Act & Assert
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento("EventoSinCat", "ESC", "Descripcion", new ArrayList<>(), "img.png", LocalDate.now())
            );
            assertEquals("El evento debe tener al menos una categoría", ex.getMessage());
        }

        @Test
        void testAltaEvento_Fallo_CategoriaNoExistente() {
            // Arrange
            List<CategoriaDTO> categorias = new ArrayList<>();
            categorias.add(new CategoriaDTO("CategoriaInexistente"));

            // Act & Assert
            BusinessException ex = assertThrows(BusinessException.class, () ->
                controlador.altaEvento("EventoCatFallo", "ECF", "Descripcion", categorias, "img.png", LocalDate.now())
            );
            assertEquals("La categoría 'CategoriaInexistente' no existe en el sistema", ex.getMessage());
        }

}

    
    

