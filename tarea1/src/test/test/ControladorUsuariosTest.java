package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import logica.interfaces.Fabrica;
import logica.interfaces.iControladorUsuarios;
import logica.dto.*;
import exception.*;
import logica.model.*;
import java.time.LocalDate;
import java.util.*;

public class ControladorUsuariosTest {
    private iControladorUsuarios controlador;
    private ManejadorUsuarios manejadorUsuarios;
    private ManejadorEventos manejadorEventos;
    private Fabrica fab;
    
    @BeforeEach
    void setUp() {
        manejadorUsuarios = ManejadorUsuarios.getInstance();
        fab = Fabrica.getInstancia();
        controlador = fab.getControladorUsuario();
        // Setup test data
        setupTestData();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test data
        manejadorUsuarios.clearAllData();
        controlador = null;
    }
    
    private void setupTestData() {
        try {
            // Create test asistentes
            manejadorUsuarios.altaAsistente("testAsistente1", "Test Asistente 1", "asistente1@test.com","testPw","testImg", "Test Apellido 1", LocalDate.of(1990, 1, 1),null);
            manejadorUsuarios.altaAsistente("testAsistente2", "Test Asistente 2", "asistente2@test.com", "testPw","testImg","Test Apellido 2", LocalDate.of(1991, 2, 2),null);
            
            // Create test organizadores
            manejadorUsuarios.altaOrganizador("testOrg1", "Test Organizer 1", "org1@test.com","testPw","testImg", "Test Description 1", "http://test1.com");
            manejadorUsuarios.altaOrganizador("testOrg2", "Test Organizer 2", "org2@test.com","testPw","testImg", "Test Description 2", "http://test2.com");
            manejadorEventos.altaInstitucion("InstituTest", "Desc", "sitio.web");
        } catch (Exception e) {
            // Ignore setup errors for tests
        }
    }
    
    @Test
    void testListarUsuarios() {
        // Act
        List<String> usuarios = controlador.listarUsuarios();
        
        // Assert
        assertNotNull(usuarios);
        assertTrue(usuarios.size() >= 4); // At least 2 asistentes + 2 organizadores
        
        // Check that both asistentes and organizadores are included
        boolean hasAsistente = usuarios.stream().anyMatch(u -> u.contains("Asistente: testAsistente1"));
        boolean hasOrganizador = usuarios.stream().anyMatch(u -> u.contains("Organizador: testOrg1"));
        
        assertTrue(hasAsistente);
        assertTrue(hasOrganizador);
    }
    
    @Test
    void testConsultarUsuario_Asistente() {
        // Act
        Set<String> info = controlador.consultarUsuario("testAsistente1");
        
        // Assert
        assertNotNull(info);
        assertTrue(info.contains("Tipo: Asistente"));
        assertTrue(info.contains("Nickname: testAsistente1"));
        assertTrue(info.contains("Nombre: Test Asistente 1"));
        assertTrue(info.contains("Correo: asistente1@test.com"));
        assertTrue(info.contains("Apellido: Test Apellido 1"));
        assertTrue(info.contains("Fecha de Nacimiento: 1990-01-01"));
    }
    
    @Test
    void testConsultarUsuario_Organizador() {
        // Act
        Set<String> info = controlador.consultarUsuario("testOrg1");
        
        // Assert
        assertNotNull(info);
        assertTrue(info.contains("Tipo: Organizador"));
        assertTrue(info.contains("Nickname: testOrg1"));
        assertTrue(info.contains("Nombre: Test Organizer 1"));
        assertTrue(info.contains("Correo: org1@test.com"));
        assertTrue(info.contains("Descripción: Test Description 1"));
        assertTrue(info.contains("Sitio Web: http://test1.com"));
    }
    
    @Test
    void testConsultarUsuario_NotFound() {
        // Act
        Set<String> info = controlador.consultarUsuario("nonexistent");
        
        // Assert
        assertNotNull(info);
        assertTrue(info.isEmpty());
    }
    
    @Test
    void testAltaAsistente_Success() throws BusinessException {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act
        controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        
        // Assert
        Asistente asistente = controlador.buscarAsistente(nickname);
        assertNotNull(asistente);
        assertEquals(nickname, asistente.getNickname());
        assertEquals(nombre, asistente.getNombre());
        assertEquals(correo, asistente.getCorreo());
        assertEquals(pw, asistente.getContrasenia());
        assertEquals(img, asistente.getImagen());
        assertEquals(apellido, asistente.getApellido());
        assertEquals(fechaNacimiento, asistente.getFechaNacimiento());
        assertEquals(inst, asistente.getInstitucion().getNombre());
    }
    
    @Test
    void testAltaAsistente_NullNickname() {
        // Arrange
        String nickname = null;
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El nickname es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_EmptyNickname() {
        // Arrange
        String nickname = "";
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El nickname es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_NullNombre() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = null;
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El nombre es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_NullCorreo() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "New Asistente";
        String correo = null;
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El correo es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_NullApellido() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = null;
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El apellido es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_NullFechaNacimiento() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = null;
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("La fecha de nacimiento es obligatoria", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_WithInstitucion() throws BusinessException {
        // Arrange
        String nickname = "newAsistenteWithInst";
        String nombre = "New Asistente With Inst";
        String correo = "newinst@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido With Inst";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        Institucion institucion = new Institucion("Test Institution", "Test Description", "http://test.com");
        
        // Act
        controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, institucion.getNombre());
        
        // Assert
        Asistente asistente = controlador.buscarAsistente(nickname);
        assertNotNull(asistente);
        assertEquals(nickname, asistente.getNickname());
        assertEquals(nombre, asistente.getNombre());
        assertEquals(correo, asistente.getCorreo());
        assertEquals(apellido, asistente.getApellido());
        assertEquals(fechaNacimiento, asistente.getFechaNacimiento());
        assertNotNull(asistente.getInstitucion().getNombre());
        assertEquals("Test Institution", asistente.getInstitucion().getNombre());
    }
    
    @Test
    void testAltaOrganizador_Success() {
        // Arrange
        String nickname = "newOrg";
        String nombre = "New Organizer";
        String correo = "neworg@test.com";
        String pw = "testPw";
        String img = "testImg";
        String descripcion = "New Description";
        String sitioWeb = "http://neworg.com";
        
        // Act
        controlador.altaOrganizador(nickname, nombre, correo, pw, img, descripcion, sitioWeb);
        
        // Assert
        Organizador organizador = controlador.buscarOrganizador(nickname);
        assertNotNull(organizador);
        assertEquals(nickname, organizador.getNickname());
        assertEquals(nombre, organizador.getNombre());
        assertEquals(correo, organizador.getCorreo());
        assertEquals(pw, organizador.getContrasenia());
        assertEquals(img, organizador.getImagen());
        assertEquals(descripcion, organizador.getDescripcion());
        assertEquals(sitioWeb, organizador.getSitioWeb());
    }
    
    @Test
    void testListarOrganizadores() {
        // Act
        List<String> organizadores = controlador.listarOrganizadores();
        
        // Assert
        assertNotNull(organizadores);
        assertTrue(organizadores.size() >= 2); // At least the 2 organizadores from setup
        assertTrue(organizadores.contains("testOrg1"));
        assertTrue(organizadores.contains("testOrg2"));
    }
    
    @Test
    void testListarAsistentes() {
        // Act
        Set<String> asistentes = controlador.listarAsistentes();
        
        // Assert
        assertNotNull(asistentes);
        assertTrue(asistentes.size() >= 2); // At least the 2 asistentes from setup
        assertTrue(asistentes.contains("testAsistente1"));
        assertTrue(asistentes.contains("testAsistente2"));
    }
    
    @Test
    void testConsultarAsistentes() {
        // Act
        Set<Asistente> asistentes = controlador.consultarAsistentes();
        
        // Assert
        assertNotNull(asistentes);
        assertTrue(asistentes.size() >= 2); // At least the 2 asistentes from setup
        
        boolean hasAsistente1 = asistentes.stream().anyMatch(a -> a.getNickname().equals("testAsistente1"));
        boolean hasAsistente2 = asistentes.stream().anyMatch(a -> a.getNickname().equals("testAsistente2"));
        
        assertTrue(hasAsistente1);
        assertTrue(hasAsistente2);
    }
    
    @Test
    void testConsultarOrganizadores() {
        // Act
        Set<Organizador> organizadores = controlador.consultarOrganizadores();
        
        // Assert
        assertNotNull(organizadores);
        assertTrue(organizadores.size() >= 2); // At least the 2 organizadores from setup
        
        boolean hasOrg1 = organizadores.stream().anyMatch(o -> o.getNickname().equals("testOrg1"));
        boolean hasOrg2 = organizadores.stream().anyMatch(o -> o.getNickname().equals("testOrg2"));
        
        assertTrue(hasOrg1);
        assertTrue(hasOrg2);
    }
    
    @Test
    void testBuscarAsistente_Found() {
        // Act
        Asistente asistente = controlador.buscarAsistente("testAsistente1");
        
        // Assert
        assertNotNull(asistente);
        assertEquals("testAsistente1", asistente.getNickname());
        assertEquals("Test Asistente 1", asistente.getNombre());
        assertEquals("asistente1@test.com", asistente.getCorreo());
    }
    
    @Test
    void testBuscarAsistente_NotFound() {
        // Act
        Asistente asistente = controlador.buscarAsistente("nonexistent");
        
        // Assert
        assertNull(asistente);
    }
    
    @Test
    void testBuscarOrganizador_Found() {
        // Act
        Organizador organizador = controlador.buscarOrganizador("testOrg1");
        
        // Assert
        assertNotNull(organizador);
        assertEquals("testOrg1", organizador.getNickname());
        assertEquals("Test Organizer 1", organizador.getNombre());
        assertEquals("org1@test.com", organizador.getCorreo());
    }
    
    @Test
    void testBuscarOrganizador_NotFound() {
        // Act
        Organizador organizador = controlador.buscarOrganizador("nonexistent");
        
        // Assert
        assertNull(organizador);
    }
    
    @Test
    void testExisteNickname_True() {
        // Act
        boolean exists = controlador.existeNickname("testAsistente1");
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    void testExisteNickname_TrueOrganizador() {
        // Act
        boolean exists = controlador.existeNickname("testOrg1");
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    void testExisteNickname_False() {
        // Act
        boolean exists = controlador.existeNickname("nonexistent");
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testExisteCorreo_True() {
        // Act
        boolean exists = controlador.existeCorreo("asistente1@test.com");
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    void testExisteCorreo_TrueOrganizador() {
        // Act
        boolean exists = controlador.existeCorreo("org1@test.com");
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    void testExisteCorreo_False() {
        // Act
        boolean exists = controlador.existeCorreo("nonexistent@test.com");
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testObtenerTodosLosRegistros() {
        // Act
        List<String> registros = controlador.obtenerTodosLosRegistros();
        
        // Assert
        assertNotNull(registros);
        // Should be empty initially since no registrations have been made
        assertEquals(0, registros.size());
    }
    
    @Test
    void testBuscarAsistenteDTO_Found() {
        // Act
        AsistenteDTO asistenteDTO = controlador.buscarAsistenteDTO("testAsistente1");
        
        // Assert
        assertNotNull(asistenteDTO);
        assertEquals("testAsistente1", asistenteDTO.getNickname());
        assertEquals("Test Asistente 1", asistenteDTO.getNombre());
        assertEquals("asistente1@test.com", asistenteDTO.getCorreo());
        assertEquals("Test Apellido 1", asistenteDTO.getApellido());
        assertEquals(LocalDate.of(1990, 1, 1), asistenteDTO.getFechaNacimiento());
    }
    
    @Test
    void testBuscarAsistenteDTO_NotFound() {
        // Act
        AsistenteDTO asistenteDTO = controlador.buscarAsistenteDTO("nonexistent");
        
        // Assert
        assertNull(asistenteDTO);
    }
    
    @Test
    void testBuscarOrganizadorDTO_Found() {
        // Act
        OrganizadorDTO organizadorDTO = controlador.buscarOrganizadorDTO("testOrg1");
        
        // Assert
        assertNotNull(organizadorDTO);
        assertEquals("testOrg1", organizadorDTO.getNickname());
        assertEquals("Test Organizer 1", organizadorDTO.getNombre());
        assertEquals("org1@test.com", organizadorDTO.getCorreo());
        assertEquals("Test Description 1", organizadorDTO.getDescripcion());
        assertEquals("http://test1.com", organizadorDTO.getSitioWeb());
    }
    
    @Test
    void testBuscarOrganizadorDTO_NotFound() {
        // Act
        OrganizadorDTO organizadorDTO = controlador.buscarOrganizadorDTO("nonexistent");
        
        // Assert
        assertNull(organizadorDTO);
    }
    
    @Test
    void testListarAsistentesDTO() {
        // Act
        List<AsistenteDTO> asistentesDTO = controlador.listarAsistentesDTO();
        
        // Assert
        assertNotNull(asistentesDTO);
        assertTrue(asistentesDTO.size() >= 2); // At least the 2 asistentes from setup
        
        boolean hasAsistente1 = asistentesDTO.stream().anyMatch(a -> a.getNickname().equals("testAsistente1"));
        boolean hasAsistente2 = asistentesDTO.stream().anyMatch(a -> a.getNickname().equals("testAsistente2"));
        
        assertTrue(hasAsistente1);
        assertTrue(hasAsistente2);
    }
    
    @Test
    void testListarOrganizadoresDTO() {
        // Act
        List<OrganizadorDTO> organizadoresDTO = controlador.listarOrganizadoresDTO();
        
        // Assert
        assertNotNull(organizadoresDTO);
        assertTrue(organizadoresDTO.size() >= 2); // At least the 2 organizadores from setup
        
        boolean hasOrg1 = organizadoresDTO.stream().anyMatch(o -> o.getNickname().equals("testOrg1"));
        boolean hasOrg2 = organizadoresDTO.stream().anyMatch(o -> o.getNickname().equals("testOrg2"));
        
        assertTrue(hasOrg1);
        assertTrue(hasOrg2);
    }
    
    @Test
    void testAsistenteEstaRegistradoEnEdicion_False() {
        // Act
        boolean registrado = controlador.asistenteEstaRegistradoEnEdicion("testAsistente1", "nonexistent");
        
        // Assert
        assertFalse(registrado);
    }
    
    @Test
    void testAsistenteEstaRegistradoEnEdicion_AsistenteNotFound() {
        // Act
        boolean registrado = controlador.asistenteEstaRegistradoEnEdicion("nonexistent", "nonexistent");
        
        // Assert
        assertFalse(registrado);
    }
    
    @Test
    void testObtenerEdicionesRegistradasDeAsistente_Empty() {
        // Act
        List<String> ediciones = controlador.obtenerEdicionesRegistradasDeAsistente("testAsistente1");
        
        // Assert
        assertNotNull(ediciones);
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testObtenerEdicionesRegistradasDeAsistente_AsistenteNotFound() {
        // Act
        List<String> ediciones = controlador.obtenerEdicionesRegistradasDeAsistente("nonexistent");
        
        // Assert
        assertNotNull(ediciones);
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testObtenerRegistrosDeAsistente_Empty() {
        // Act
        List<String> registros = controlador.obtenerRegistrosDeAsistente("testAsistente1");
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testObtenerRegistrosDeAsistente_AsistenteNotFound() {
        // Act
        List<String> registros = controlador.obtenerRegistrosDeAsistente("nonexistent");
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testGetRegistrosPorAsistentes() {
        // Act
        Set<RegistroDTO> registros = controlador.getRegistrosPorAsistentes("testAsistente1");
        
        // Assert
        assertNotNull(registros);
        // Should be empty initially since no registrations have been made
        assertEquals(0, registros.size());
    }
    
    // ========== ADDITIONAL TESTS ==========
    
    @Test
    void testAltaAsistente_DuplicateNickname() {
        // Arrange
        String nickname = "testAsistente1"; // Already exists from setup
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
    }
    
    @Test
    void testAltaAsistente_EmptyNombre() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El nombre es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_EmptyCorreo() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "New Asistente";
        String correo = "";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El correo es obligatorio", exception.getMessage());
    }
    
    @Test
    void testAltaAsistente_EmptyApellido() {
        // Arrange
        String nickname = "newAsistente";
        String nombre = "New Asistente";
        String correo = "new@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        String inst = "InstituTest";
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, inst);
        });
        assertEquals("El apellido es obligatorio", exception.getMessage());
    }
    
    /*
    ###### Lo comento porque la institucion ahora es tipo String #####
    
    @Test
    void testAltaAsistente_WithInstitucion_NullInstitucion() throws BusinessException {
        // Arrange
        String nickname = "newAsistenteWithNullInst";
        String nombre = "New Asistente With Null Inst";
        String correo = "newnullinst@test.com";
        String pw = "testPw";
        String img = "testImg";
        String apellido = "New Apellido With Null Inst";
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 5);
        Institucion institucion = null;
        
        // Act & Assert - Should not throw exception for null institution
        assertDoesNotThrow(() -> {
            controlador.altaAsistente(nickname, nombre, correo, pw, img, apellido, fechaNacimiento, institucion.getNombre());
        });
        
        // Verify asistente was created
        Asistente asistente = controlador.buscarAsistente(nickname);
        assertNotNull(asistente);
        assertEquals(nickname, asistente.getNickname());
        assertNull(asistente.getInstitucion());
    }
    */
    @Test
    void testAltaOrganizador_DuplicateNickname() {
        // Arrange
        String nickname = "testOrg1"; // Already exists from setup
        String nombre = "New Organizer";
        String correo = "neworg@test.com";
        String pw = "testPw";
        String img = "testImg";
        String descripcion = "New Description";
        String sitioWeb = "http://neworg.com";
        
        // Act & Assert - Should not throw exception, but should not create duplicate
        assertDoesNotThrow(() -> {
            controlador.altaOrganizador(nickname, nombre, correo, pw, img, descripcion, sitioWeb);
        });
        
        // Verify the original organizer still exists
        Organizador organizador = controlador.buscarOrganizador(nickname);
        assertNotNull(organizador);
        assertEquals("Test Organizer 1", organizador.getNombre()); // Original name
    }
    
    @Test
    void testAltaOrganizador_NullParameters() {
        // Arrange
        String nickname = "newOrgNull";
        String nombre = null;
        String correo = null;
        String pw = null;
        String img = null;
        String descripcion = null;
        String sitioWeb = null;
        
        // Act & Assert - Should handle null parameters gracefully
        assertDoesNotThrow(() -> {
            controlador.altaOrganizador(nickname, nombre, correo, pw, img, descripcion, sitioWeb);
        });
        
        // Verify organizador was created
        Organizador organizador = controlador.buscarOrganizador(nickname);
        assertNotNull(organizador);
        assertEquals(nickname, organizador.getNickname());
    }
    
    @Test
    void testConsultarUsuario_EmptyNickname() {
        // Act
        Set<String> info = controlador.consultarUsuario("");
        
        // Assert
        assertNotNull(info);
        assertTrue(info.isEmpty());
    }
    
    @Test
    void testConsultarUsuario_NullNickname() {
        // Act
        Set<String> info = controlador.consultarUsuario(null);
        
        // Assert
        assertNotNull(info);
        assertTrue(info.isEmpty());
    }
    
    @Test
    void testListarUsuarios_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        List<String> usuarios = controlador.listarUsuarios();
        
        // Assert
        assertNotNull(usuarios);
        assertEquals(0, usuarios.size());
    }
    
    @Test
    void testListarOrganizadores_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        List<String> organizadores = controlador.listarOrganizadores();
        
        // Assert
        assertNotNull(organizadores);
        assertEquals(0, organizadores.size());
    }
    
    @Test
    void testListarAsistentes_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        Set<String> asistentes = controlador.listarAsistentes();
        
        // Assert
        assertNotNull(asistentes);
        assertEquals(0, asistentes.size());
    }
    
    @Test
    void testConsultarAsistentes_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        Set<Asistente> asistentes = controlador.consultarAsistentes();
        
        // Assert
        assertNotNull(asistentes);
        assertEquals(0, asistentes.size());
    }
    
    @Test
    void testConsultarOrganizadores_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        Set<Organizador> organizadores = controlador.consultarOrganizadores();
        
        // Assert
        assertNotNull(organizadores);
        assertEquals(0, organizadores.size());
    }
    
    @Test
    void testExisteNickname_EmptyString() {
        // Act
        boolean exists = controlador.existeNickname("");
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testExisteNickname_Null() {
        // Act
        boolean exists = controlador.existeNickname(null);
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testExisteCorreo_EmptyString() {
        // Act
        boolean exists = controlador.existeCorreo("");
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testExisteCorreo_Null() {
        // Act
        boolean exists = controlador.existeCorreo(null);
        
        // Assert
        assertFalse(exists);
    }
    
    @Test
    void testExisteCorreo_CaseInsensitive() {
        // Act - Test with different case
        boolean exists = controlador.existeCorreo("ASISTENTE1@TEST.COM");
        
        // Assert
        assertTrue(exists);
    }
    
    @Test
    void testObtenerTodosLosRegistros_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        List<String> registros = controlador.obtenerTodosLosRegistros();
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testBuscarAsistenteDTO_EmptyString() {
        // Act
        AsistenteDTO asistenteDTO = controlador.buscarAsistenteDTO("");
        
        // Assert
        assertNull(asistenteDTO);
    }
    
    @Test
    void testBuscarAsistenteDTO_Null() {
        // Act
        AsistenteDTO asistenteDTO = controlador.buscarAsistenteDTO(null);
        
        // Assert
        assertNull(asistenteDTO);
    }
    
    @Test
    void testBuscarOrganizadorDTO_EmptyString() {
        // Act
        OrganizadorDTO organizadorDTO = controlador.buscarOrganizadorDTO("");
        
        // Assert
        assertNull(organizadorDTO);
    }
    
    @Test
    void testBuscarOrganizadorDTO_Null() {
        // Act
        OrganizadorDTO organizadorDTO = controlador.buscarOrganizadorDTO(null);
        
        // Assert
        assertNull(organizadorDTO);
    }
    
    @Test
    void testListarAsistentesDTO_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        List<AsistenteDTO> asistentesDTO = controlador.listarAsistentesDTO();
        
        // Assert
        assertNotNull(asistentesDTO);
        assertEquals(0, asistentesDTO.size());
    }
    
    @Test
    void testListarOrganizadoresDTO_EmptySystem() {
        // Arrange - Clear all data
        manejadorUsuarios.clearAllData();
        
        // Act
        List<OrganizadorDTO> organizadoresDTO = controlador.listarOrganizadoresDTO();
        
        // Assert
        assertNotNull(organizadoresDTO);
        assertEquals(0, organizadoresDTO.size());
    }
    
    @Test
    void testAsistenteEstaRegistradoEnEdicion_EmptyString() {
        // Act
        boolean registrado = controlador.asistenteEstaRegistradoEnEdicion("", "nonexistent");
        
        // Assert
        assertFalse(registrado);
    }
    
    @Test
    void testAsistenteEstaRegistradoEnEdicion_NullNickname() {
        // Act
        boolean registrado = controlador.asistenteEstaRegistradoEnEdicion(null, "nonexistent");
        
        // Assert
        assertFalse(registrado);
    }
    
    @Test
    void testObtenerEdicionesRegistradasDeAsistente_EmptyString() {
        // Act
        List<String> ediciones = controlador.obtenerEdicionesRegistradasDeAsistente("");
        
        // Assert
        assertNotNull(ediciones);
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testObtenerEdicionesRegistradasDeAsistente_NullNickname() {
        // Act
        List<String> ediciones = controlador.obtenerEdicionesRegistradasDeAsistente(null);
        
        // Assert
        assertNotNull(ediciones);
        assertEquals(0, ediciones.size());
    }
    
    @Test
    void testObtenerRegistrosDeAsistente_EmptyString() {
        // Act
        List<String> registros = controlador.obtenerRegistrosDeAsistente("");
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testObtenerRegistrosDeAsistente_NullNickname() {
        // Act
        List<String> registros = controlador.obtenerRegistrosDeAsistente(null);
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testGetRegistrosPorAsistentes_EmptyString() {
        // Act
        Set<RegistroDTO> registros = controlador.getRegistrosPorAsistentes("");
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testGetRegistrosPorAsistentes_NullNickname() {
        // Act
        Set<RegistroDTO> registros = controlador.getRegistrosPorAsistentes(null);
        
        // Assert
        assertNotNull(registros);
        assertEquals(0, registros.size());
    }
    
    @Test
    void testBuscarAsistente_EmptyString() {
        // Act
        Asistente asistente = controlador.buscarAsistente("");
        
        // Assert
        assertNull(asistente);
    }
    
    @Test
    void testBuscarAsistente_Null() {
        // Act
        Asistente asistente = controlador.buscarAsistente(null);
        
        // Assert
        assertNull(asistente);
    }
    
    @Test
    void testBuscarOrganizador_EmptyString() {
        // Act
        Organizador organizador = controlador.buscarOrganizador("");
        
        // Assert
        assertNull(organizador);
    }
    
    @Test
    void testBuscarOrganizador_Null() {
        // Act
        Organizador organizador = controlador.buscarOrganizador(null);
        
        // Assert
        assertNull(organizador);
    }
    
    @Test
    void testConsultarUsuario_WithInstitucion() {
        // Arrange - Create asistente with institution
        try {
            Institucion institucion = new Institucion("Test Institution", "Test Description", "http://test.com");
            controlador.altaAsistente("testAsistenteWithInst", "Test Asistente With Inst", 
                                    "asistenteinst@test.com","testPW","testImg", "Test Apellido With Inst", 
                                    LocalDate.of(1990, 1, 1), institucion.getNombre());
        } catch (BusinessException e) {
            // Asistente might already exist
        }
        
        // Act
        Set<String> info = controlador.consultarUsuario("testAsistenteWithInst");
        
        // Assert
        assertNotNull(info);
        assertTrue(info.contains("Tipo: Asistente"));
        assertTrue(info.contains("Nickname: testAsistenteWithInst"));
        assertTrue(info.contains("Institución: Test Institution"));
        assertTrue(info.contains("Descripción Institución: Test Description"));
    }
    /*
    @Test
    void testListarUsuarios_WithInstituciones() {
        // Act
        List<String> usuarios = controlador.listarUsuarios();
        
        // Assert
        assertNotNull(usuarios);
        assertTrue(usuarios.size() >= 4);
        
        // Check that usuarios with institutions show institution info
        boolean hasInstitutionInfo = usuarios.stream()
            .anyMatch(u -> u.contains("(") && u.contains(")"));
        
        // This might be true if any asistente has an institution
        assertNotNull(usuarios);
    }
    */
    // ==========================
    // TESTS PARA CLASE ORGANIZADOR
    // ==========================
    
    @Test
    void testConstructorYGettersOrganizador() {
        Organizador org = new Organizador("nickOrg", "Nombre Org", "org@test.com", "pw123", "img.png", "Descripcion inicial");
        
        assertEquals("nickOrg", org.getNickname());
        assertEquals("Nombre Org", org.getNombre());
        assertEquals("org@test.com", org.getCorreo());
        assertEquals("pw123", org.getContrasenia());
        assertEquals("img.png", org.getImagen());
        assertEquals("Descripcion inicial", org.getDescripcion());
        assertEquals("", org.getSitioWeb()); // valor por defecto
        assertNull(org.getEventos());
    }
    
    @Test
    void testSettersOrganizador() {
        Organizador org = new Organizador("nickOrg", "Nombre Org", "org@test.com", "pw123", "img.png", "Descripcion inicial");
        
        org.setDescripcion("Nueva descripción");
        org.setSitioWeb("https://nuevo.sitio");
        
        assertEquals("Nueva descripción", org.getDescripcion());
        assertEquals("https://nuevo.sitio", org.getSitioWeb());
    }
    
    @Test
    void testListarEventosConEventos() {
        Organizador org = new Organizador("nickOrg", "Nombre Org", "org@test.com", "pw123", "img.png", "Descripcion inicial");
        
        // Inicializar la lista de eventos
        List<Evento> eventos = new ArrayList<>();
        Evento ev1 = new Evento("Evento 1", "Lugar 1", null, null);
        Evento ev2 = new Evento("Evento 2", "Lugar 2", null, null);
        eventos.add(ev1);
        eventos.add(ev2);
        org.setSitioWeb("http://sitio.test");
        
        // Usamos reflexión o setter si tienes uno, para asignar eventos
        try {
            java.lang.reflect.Field field = Organizador.class.getDeclaredField("eventos");
            field.setAccessible(true);
            field.set(org, eventos);
        } catch (Exception e) {
            fail("No se pudo asignar lista de eventos mediante reflexión");
        }

        List<String> nombres = org.listarEventos();
        assertEquals(2, nombres.size());
        assertTrue(nombres.contains("Evento 1"));
        assertTrue(nombres.contains("Evento 2"));
    }

    @Test
    void testListarEventosSinEventos() {
        Organizador org = new Organizador("nickOrg", "Nombre Org", "org@test.com", "pw123", "img.png", "Descripcion inicial");
        List<String> nombres = org.listarEventos();
        assertNotNull(nombres);
        assertTrue(nombres.isEmpty());
    }

    @Test
    void testToDTO() {
        Organizador org = new Organizador("nickOrg", "Nombre Org", "org@test.com", "pw123", "img.png", "Descripcion inicial");
        org.setSitioWeb("https://sitio.org");
        
        OrganizadorDTO dto = org.toDTO();
        
        assertEquals("nickOrg", dto.getNickname());
        assertEquals("Nombre Org", dto.getNombre());
        assertEquals("org@test.com", dto.getCorreo());
        assertEquals("pw123", dto.getContrasenia());
        assertEquals("img.png", dto.getImagen());
        assertEquals("Descripcion inicial", dto.getDescripcion());
        assertEquals("https://sitio.org", dto.getSitioWeb());
    }

    
    // ==========================
    // TESTS PARA CLASE EVENTO
    // ==========================

    @Test
    void testConstructorBasicoYGetters() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Cat1"));
        categorias.add(new Categoria("Cat2"));

        Evento evento = new Evento("Evento Prueba", "EVT1", "Descripción de prueba", categorias);

        assertEquals("Evento Prueba", evento.getNombre());
        assertEquals("EVT1", evento.getSigla());
        assertEquals("Descripción de prueba", evento.getDescripcion());
        assertNotNull(evento.getFechaAlta());
        assertEquals(2, evento.getCategorias().size());
        assertTrue(evento.getEdiciones().isEmpty());
        assertNull(evento.getImagen());
    }

    @Test
    void testConstructorConImagen() {
        List<Categoria> categorias = new ArrayList<>();
        Evento evento = new Evento("Evento Imagen", "EIMG", "Evento con imagen", categorias, "imagen.png");

        assertEquals("imagen.png", evento.getImagen());
        assertEquals("Evento Imagen", evento.getNombre());
        assertEquals("EIMG", evento.getSigla());
    }

    @Test
    void testSetters() {
        Evento evento = new Evento("Evento 1", "E1", "Desc 1", new ArrayList<>());

        evento.setNombre("Nuevo Evento");
        evento.setSigla("NE1");
        evento.setDescripcion("Nueva descripción");
        evento.setImagen("nuevo.png");

        List<Categoria> nuevasCategorias = new ArrayList<>();
        nuevasCategorias.add(new Categoria("NuevaCat"));
        evento.setCategorias(nuevasCategorias);

        assertEquals("Nuevo Evento", evento.getNombre());
        assertEquals("NE1", evento.getSigla());
        assertEquals("Nueva descripción", evento.getDescripcion());
        assertEquals("nuevo.png", evento.getImagen());
    }
    
    @Test
    void testAgregarYRemoverSeguidor() {
        Asistente userA = new Asistente("userA", "Nombre A", "a@test.com", "pw", "imgA", "ApellidoA", LocalDate.now(), null);
        Asistente userB = new Asistente("userB", "Nombre B", "b@test.com", "pw", "imgB", "ApellidoB", LocalDate.now(), null);
        
        // userB sigue a userA
        userA.agregarSeguidor(userB);
        assertTrue(userA.getSeguidores().contains(userB));
        
        // userB deja de seguir a userA
        userA.removerSeguidor(userB);
        assertFalse(userA.getSeguidores().contains(userB));
    }

    @Test
    void testAgregarYRemoverSeguido() {
        Asistente userA = new Asistente("userA", "Nombre A", "a@test.com", "pw", "imgA", "ApellidoA", LocalDate.now(), null);
        Asistente userB = new Asistente("userB", "Nombre B", "b@test.com", "pw", "imgB", "ApellidoB", LocalDate.now(), null);

        // userA sigue a userB
        userA.agregarSeguido(userB);
        assertTrue(userA.getSeguidos().contains(userB));

        // userA deja de seguir a userB
        userA.removerSeguido(userB);
        assertFalse(userA.getSeguidos().contains(userB));
    }

    @Test
    void testEstaSiguiendoA() {
        Asistente userA = new Asistente("userA", "Nombre A", "a@test.com", "pw", "imgA", "ApellidoA", LocalDate.now(), null);
        Asistente userB = new Asistente("userB", "Nombre B", "b@test.com", "pw", "imgB", "ApellidoB", LocalDate.now(), null);
        Asistente userC = new Asistente("userC", "Nombre C", "c@test.com", "pw", "imgC", "ApellidoC", LocalDate.now(), null);

        userA.agregarSeguido(userB);

        assertTrue(userA.estaSiguiendoA(userB));
        assertFalse(userA.estaSiguiendoA(userC));
    }

    @Test
    void testGetSeguidoresYSeguidosIniciales() {
        Asistente userA = new Asistente("userA", "Nombre A", "a@test.com", "pw", "imgA", "ApellidoA", LocalDate.now(), null);
        assertNotNull(userA.getSeguidores());
        assertNotNull(userA.getSeguidos());
        assertTrue(userA.getSeguidores().isEmpty());
        assertTrue(userA.getSeguidos().isEmpty());
    }

   

    @Test
    void testAltaAsistente_Success1() throws BusinessException {
        // Act
        controlador.altaAsistente("nuevoAsistente", "Nombre", "correo@test.com", "pass", "img", "Apellido", LocalDate.of(2000,1,1));
        
        // Assert
        Asistente a = manejadorUsuarios.buscarAsistente("nuevoAsistente");
        assertNotNull(a);
        assertEquals("Nombre", a.getNombre());
        assertEquals("Apellido", a.getApellido());
    }

    @Test
    void testAltaAsistente_Failure() {
        // Invalid nickname
        BusinessException ex = assertThrows(BusinessException.class, () ->
            controlador.altaAsistente("", "Nombre", "correo@test.com", "pass", "img", "Apellido", LocalDate.of(2000,1,1))
        );
        assertEquals("El nickname es obligatorio", ex.getMessage());
        
        // Duplicate nickname
        ex = assertThrows(BusinessException.class, () ->
            controlador.altaAsistente("testAsistente1", "Nombre", "correo@test.com", "pass", "img", "Apellido", LocalDate.of(2000,1,1))
        );
        assertEquals("El nickname ya existe en el sistema", ex.getMessage());
    }

    @Test
    void testSeguirYDejarDeSeguirUsuario() throws BusinessException {
        // Act
        controlador.seguirUsuario("testAsistente1", "testAsistente2");
        
        // Assert
        List<String> seguidos = controlador.obtenerSeguidos("testAsistente1");
        List<String> seguidores = controlador.obtenerSeguidores("testAsistente2");
        assertTrue(seguidos.contains("testAsistente2"));
        assertTrue(seguidores.contains("testAsistente1"));
        
        // Dejar de seguir
        controlador.dejarDeSeguirUsuario("testAsistente1", "testAsistente2");
        seguidos = controlador.obtenerSeguidos("testAsistente1");
        seguidores = controlador.obtenerSeguidores("testAsistente2");
        assertFalse(seguidos.contains("testAsistente2"));
        assertFalse(seguidores.contains("testAsistente1"));
    }

    @Test
    void testSeguirUsuario_Errores() {
        // Seguirse a sí mismo
        BusinessException ex = assertThrows(BusinessException.class, () ->
            controlador.seguirUsuario("testAsistente1", "testAsistente1")
        );
        assertEquals("Un usuario no puede seguirse a sí mismo", ex.getMessage());
        
        // Usuario inexistente
        ex = assertThrows(BusinessException.class, () ->
            controlador.seguirUsuario("testAsistente1", "usuarioInexistente")
        );
        assertEquals("Uno o ambos usuarios no existen", ex.getMessage());
    }

    @Test
    void testObtenerSeguidoresYSeguidos_Vacio() {
        List<String> seguidores = controlador.obtenerSeguidores("testAsistente1");
        List<String> seguidos = controlador.obtenerSeguidos("testAsistente1");
        assertNotNull(seguidores);
        assertNotNull(seguidos);
        assertEquals(0, seguidores.size());
        assertEquals(0, seguidos.size());
    }

    @Test
    void testVerificarLogin() {
        // Correct login by nickname
        assertTrue(controlador.verificarLogin("testAsistente1", "testPw"));
        assertTrue(controlador.verificarLogin("testOrg1", "testPw"));
        
        // Incorrect password
        assertFalse(controlador.verificarLogin("testAsistente1", "wrongPw"));
        assertFalse(controlador.verificarLogin("testOrg1", "wrongPw"));
        
        // Correct login by email
        assertTrue(controlador.verificarLogin("asistente1@test.com", "testPw"));
        assertTrue(controlador.verificarLogin("org1@test.com", "testPw"));
        
        // Non-existent user
        assertFalse(controlador.verificarLogin("noexiste@test.com", "pass"));
    }

}
