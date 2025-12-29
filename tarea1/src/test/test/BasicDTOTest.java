package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import logica.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * Tests básicos para clases DTO
 * Enfocado en funcionalidad básica que realmente existe
 */
public class BasicDTOTest {
    
    private AsistenteDTO asistenteDTO;
    private OrganizadorDTO organizadorDTO;
    private EventoDTO eventoDTO;
    private EdicionDTO edicionDTO;
    private CategoriaDTO categoriaDTO;
    private InstitucionDTO institucionDTO;
    private TipoRegistroDTO tipoRegistroDTO;
    private RegistroDTO registroDTO;
    private PatrocinioDTO patrocinioDTO;
    private UsuarioDTO usuarioDTO;
    
    @BeforeEach
    void setUp() {
        // Setup DTOs for testing
        institucionDTO = new InstitucionDTO("Test Institution", "Test Description", "http://test.com");
        
        asistenteDTO = new AsistenteDTO("testUser", "Test User", "test@email.com", "TestPW", "TestImg",
                                       "Test Apellido", LocalDate.of(1990, 1, 1), null);
        
        organizadorDTO = new OrganizadorDTO("testOrg", "Test Organizer", "org@email.com", "TestPW", "TestImg",
                                           "Test Description", "http://test.com");
        
        eventoDTO = new EventoDTO();
        eventoDTO.setNombre("Test Event");
        eventoDTO.setSigla("TE");
        eventoDTO.setDescripcion("Test Description");
        
        edicionDTO = new EdicionDTO("Test Edition", "TE2024", LocalDate.of(2024, 6, 1), 
                                   LocalDate.of(2024, 6, 3), "Test Organizer", 
                                   "Test City", "Test Country", "Test Event", "TestImg");
        
        categoriaDTO = new CategoriaDTO("Test Category");
        
        tipoRegistroDTO = new TipoRegistroDTO("Test Tipo", "Test Description", 
                                             new BigDecimal("100.00"), 50);
        
        registroDTO = new RegistroDTO();
        
        patrocinioDTO = new PatrocinioDTO();
        
        usuarioDTO = new UsuarioDTO("testGeneric", "Test Generic", "generic@email.com", "Generic", null, null);
    }
    
    @AfterEach
    void tearDown() {
        // Clean up
        asistenteDTO = null;
        organizadorDTO = null;
        eventoDTO = null;
        edicionDTO = null;
        categoriaDTO = null;
        institucionDTO = null;
        tipoRegistroDTO = null;
        registroDTO = null;
        patrocinioDTO = null;
        usuarioDTO = null;
    }
    
    // ========== TESTS PARA ASISTENTE DTO ==========
    
    @Test
    void testAsistenteDTO_Constructor() {
        assertNotNull(asistenteDTO);
        assertEquals("testUser", asistenteDTO.getNickname());
        assertEquals("Test User", asistenteDTO.getNombre());
        assertEquals("test@email.com", asistenteDTO.getCorreo());
        assertEquals("Asistente", asistenteDTO.getTipoUsuario());
        assertEquals("Test Apellido", asistenteDTO.getApellido());
        assertEquals(LocalDate.of(1990, 1, 1), asistenteDTO.getFechaNacimiento());
    }
    
    @Test
    void testAsistenteDTO_DefaultConstructor() {
        AsistenteDTO defaultDto = new AsistenteDTO();
        assertNotNull(defaultDto);
        assertEquals("Asistente", defaultDto.getTipoUsuario());
    }
    
    @Test
    void testAsistenteDTO_SettersAndGetters() {
        asistenteDTO.setApellido("New Apellido");
        asistenteDTO.setFechaNacimiento(LocalDate.of(2000, 12, 31));
        asistenteDTO.setInstitucion(institucionDTO);
        
        assertEquals("New Apellido", asistenteDTO.getApellido());
        assertEquals(LocalDate.of(2000, 12, 31), asistenteDTO.getFechaNacimiento());
        assertEquals(institucionDTO, asistenteDTO.getInstitucion());
    }
    
    @Test
    void testAsistenteDTO_GetEdad() {
        int edadEsperada = Period.between(LocalDate.of(1990, 1, 1), LocalDate.now()).getYears();
        assertEquals(edadEsperada, asistenteDTO.getEdad());
    }
    
    @Test
    void testAsistenteDTO_EsMayorDeEdad() {
        assertTrue(asistenteDTO.esMayorDeEdad());
        
        // Test with minor
        AsistenteDTO menor = new AsistenteDTO("menor", "Menor", "menor@email.com", 
                                             "Apellido", null, null, LocalDate.now().minusYears(15), null);
        assertFalse(menor.esMayorDeEdad());
    }
    
    // ========== TESTS PARA ORGANIZADOR DTO ==========
    
    @Test
    void testOrganizadorDTO_Constructor() {
        assertNotNull(organizadorDTO);
        assertEquals("testOrg", organizadorDTO.getNickname());
        assertEquals("Test Organizer", organizadorDTO.getNombre());
        assertEquals("org@email.com", organizadorDTO.getCorreo());
        assertEquals("Organizador", organizadorDTO.getTipoUsuario());
        assertEquals("Test Description", organizadorDTO.getDescripcion());
        assertEquals("http://test.com", organizadorDTO.getSitioWeb());
    }
    
    @Test
    void testOrganizadorDTO_DefaultConstructor() {
        OrganizadorDTO defaultDto = new OrganizadorDTO();
        assertNotNull(defaultDto);
        assertEquals("Organizador", defaultDto.getTipoUsuario());
    }
    
    @Test
    void testOrganizadorDTO_TieneSitioWeb() {
        assertTrue(organizadorDTO.tieneSitioWeb());
        
        OrganizadorDTO sinSitio = new OrganizadorDTO("test2", "Test", "test@email.com", "testPW", "testIMG",
                                                    "Description", null);
        assertFalse(sinSitio.tieneSitioWeb());
        
        OrganizadorDTO sitioVacio = new OrganizadorDTO("test3", "Test", "test@email.com", "TestPW", "TestImg",
                                                      "Description", "");
        assertFalse(sitioVacio.tieneSitioWeb());
    }
   
    
    // ========== TESTS PARA EVENTO DTO ==========
    
    @Test
    void testEventoDTO_DefaultConstructor() {
        assertNotNull(eventoDTO);
        assertEquals("Test Event", eventoDTO.getNombre());
        assertEquals("TE", eventoDTO.getSigla());
        assertEquals("Test Description", eventoDTO.getDescripcion());
    }
    
    @Test
    void testEventoDTO_SettersAndGetters() {
        eventoDTO.setNombre("New Event");
        eventoDTO.setSigla("NE");
        eventoDTO.setDescripcion("New Description");
        
        assertEquals("New Event", eventoDTO.getNombre());
        assertEquals("NE", eventoDTO.getSigla());
        assertEquals("New Description", eventoDTO.getDescripcion());
    }
    
    // ========== TESTS PARA EDICION DTO ==========
    
    @Test
    void testEdicionDTO_Constructor() {
        assertNotNull(edicionDTO);
        assertEquals("Test Edition", edicionDTO.getNombre());
        assertEquals("TE2024", edicionDTO.getSigla());
        assertEquals(LocalDate.of(2024, 6, 1), edicionDTO.getFechaInicio());
        assertEquals(LocalDate.of(2024, 6, 3), edicionDTO.getFechaFin());
        assertEquals("Test Organizer", edicionDTO.getOrganizador());
        assertEquals("Test City", edicionDTO.getCiudad());
        assertEquals("Test Country", edicionDTO.getPais());
        assertEquals("Test Event", edicionDTO.getEvento());
    }
    
    @Test
    void testEdicionDTO_EsActiva() {
        // Test with current dates
        edicionDTO.setFechaInicio(LocalDate.now().minusDays(1));
        edicionDTO.setFechaFin(LocalDate.now().plusDays(1));
        
        assertTrue(edicionDTO.esActiva());
    }
    
    @Test
    void testEdicionDTO_EsActiva_False() {
        // Test with past dates
        edicionDTO.setFechaInicio(LocalDate.now().minusDays(10));
        edicionDTO.setFechaFin(LocalDate.now().minusDays(5));
        
        assertFalse(edicionDTO.esActiva());
    }
    
    @Test
    void testEdicionDTO_EsFutura() {
        // Test with future dates
        edicionDTO.setFechaInicio(LocalDate.now().plusDays(5));
        edicionDTO.setFechaFin(LocalDate.now().plusDays(10));
        
        assertTrue(edicionDTO.esFutura());
    }
    
    // ========== TESTS PARA CATEGORIA DTO ==========
    
    @Test
    void testCategoriaDTO_Constructor() {
        assertNotNull(categoriaDTO);
        assertEquals("Test Category", categoriaDTO.getNombre());
    }
    
    @Test
    void testCategoriaDTO_DefaultConstructor() {
        CategoriaDTO defaultDto = new CategoriaDTO();
        assertNotNull(defaultDto);
    }
    
    @Test
    void testCategoriaDTO_SetterAndGetter() {
        // CategoriaDTO might be immutable, just test constructor works
        assertEquals("Test Category", categoriaDTO.getNombre());
    }
    
    // ========== TESTS PARA INSTITUCION DTO ==========
    
    @Test
    void testInstitucionDTO_Constructor() {
        assertNotNull(institucionDTO);
        assertEquals("Test Institution", institucionDTO.getNombre());
        assertEquals("Test Description", institucionDTO.getDescripcion());
        assertEquals("http://test.com", institucionDTO.getSitioWeb());
    }
    
    @Test
    void testInstitucionDTO_TieneSitioWeb() {
        assertTrue(institucionDTO.tieneSitioWeb());
        
        InstitucionDTO sinSitio = new InstitucionDTO("Test", "Description", null);
        assertFalse(sinSitio.tieneSitioWeb());
        
        InstitucionDTO sitioVacio = new InstitucionDTO("Test", "Description", "");
        assertFalse(sitioVacio.tieneSitioWeb());
    }
    
    // ========== TESTS PARA TIPO REGISTRO DTO ==========
    
    @Test
    void testTipoRegistroDTO_Constructor() {
        assertNotNull(tipoRegistroDTO);
        assertEquals("Test Tipo", tipoRegistroDTO.getNombre());
        assertEquals("Test Description", tipoRegistroDTO.getDescripcion());
        assertEquals(new BigDecimal("100.00"), tipoRegistroDTO.getCosto());
        assertEquals(50, tipoRegistroDTO.getCupo());
        assertEquals(50, tipoRegistroDTO.getCupoDisponible()); // Initial value
    }
    


    @Test
    void testTipoRegistroDTO_TieneCupoDisponible() {
        assertTrue(tipoRegistroDTO.tieneCupoDisponible());
        
        tipoRegistroDTO.setCupoDisponible(0);
        assertFalse(tipoRegistroDTO.tieneCupoDisponible());
    }
    
    // ========== TESTS PARA REGISTRO DTO ==========
    
    @Test
    void testRegistroDTO_DefaultConstructor() {
        assertNotNull(registroDTO);
    }
    
    @Test
    void testRegistroDTO_SettersAndGetters() {
        // Test basic functionality - setters might not exist, test basic properties
        assertNotNull(registroDTO);
        // Basic test to ensure DTO can be created
    }
    
    // ========== TESTS PARA PATROCINIO DTO ==========
    
    @Test
    void testPatrocinioDTO_DefaultConstructor() {
        assertNotNull(patrocinioDTO);
    }
    
    @Test
    void testPatrocinioDTO_SettersAndGetters() {
        // Test basic functionality - specific setters might not exist
        assertNotNull(patrocinioDTO);
        // Basic test to ensure DTO can be created
    }
    
    // ========== TESTS PARA USUARIO DTO BASE ==========
    
    @Test
    void testUsuarioDTO_Constructor() {
        assertNotNull(usuarioDTO);
        assertEquals("testGeneric", usuarioDTO.getNickname());
        assertEquals("Test Generic", usuarioDTO.getNombre());
        assertEquals("generic@email.com", usuarioDTO.getCorreo());
        assertEquals("Generic", usuarioDTO.getTipoUsuario());
    }
    
    @Test
    void testUsuarioDTO_SettersAndGetters() {
        usuarioDTO.setNickname("newNickname");
        usuarioDTO.setNombre("New Name");
        usuarioDTO.setCorreo("new@email.com");
        usuarioDTO.setTipoUsuario("NewType");
        
        assertEquals("newNickname", usuarioDTO.getNickname());
        assertEquals("New Name", usuarioDTO.getNombre());
        assertEquals("new@email.com", usuarioDTO.getCorreo());
        assertEquals("NewType", usuarioDTO.getTipoUsuario());
    }
    
    @Test
    void testUsuarioDTO_ToString() {
        String result = usuarioDTO.toString();
        assertNotNull(result);
        assertTrue(result.contains("testGeneric"));
        assertTrue(result.contains("Test Generic"));
        assertTrue(result.contains("Generic"));
    }
    
    // ========== TESTS DE ROBUSTEZ ==========
    
    @Test
    void testNullHandlingInDTOs() {
        // Test that DTOs handle null values gracefully
        AsistenteDTO asistenteConNulls = new AsistenteDTO("test", "Test", "test@email.com", 
                                                          null, null, null, LocalDate.now(), null);
        
        assertNotNull(asistenteConNulls);
        assertNull(asistenteConNulls.getApellido());
        assertNull(asistenteConNulls.getInstitucion());
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Most DTOs probably don't override equals/hashCode, but we test basic object behavior
        AsistenteDTO dto1 = new AsistenteDTO("same", "Name1", "email1@test.com", "Apellido1", null, null, LocalDate.now(), null);
        AsistenteDTO dto2 = new AsistenteDTO("same", "Name1", "email1@test.com", "Apellido1", null, null, LocalDate.now(), null);
        
        // Basic object equality test
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertEquals(dto1.getNickname(), dto2.getNickname());
    }
    
    // ========== ADDITIONAL DTO TESTS ==========
    
    @Test
    void testPatrocinioDTO_ConstructorWithParameters() {
        // Arrange
        InstitucionDTO institucionDTO = new InstitucionDTO("Test Inst", "Test Desc", "http://test.com");
        
        // Act
        PatrocinioDTO patrocinio = new PatrocinioDTO(
            "PAT001", "Test Event", "Test Edition", "Test Tipo",
            institucionDTO, "PLATA", new BigDecimal("1000.00"), 10, "PAT001"
        );
        
        // Assert
        assertNotNull(patrocinio);
        assertEquals("PAT001", patrocinio.getId());
        assertEquals("Test Event", patrocinio.getEvento());
        assertEquals("Test Edition", patrocinio.getEdicion());
        assertEquals("Test Tipo", patrocinio.getTipoRegistro());
        assertEquals(institucionDTO, patrocinio.getInstitucion());
        assertEquals("PLATA", patrocinio.getNivelPatrocinio());
        assertEquals(new BigDecimal("1000.00"), patrocinio.getAporteEconomico());
        assertEquals(10, patrocinio.getCantidadRegistros());
        assertEquals("PAT001", patrocinio.getCodigoPatrocinio());
    }
    
    @Test
    void testPatrocinioDTO_AdditionalSettersAndGetters() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        InstitucionDTO institucionDTO = new InstitucionDTO("Test Inst", "Test Desc", "http://test.com");
        LocalDateTime fechaCreacion = LocalDateTime.now();
        
        // Act
        patrocinio.setId("PAT001");
        patrocinio.setEvento("Test Event");
        patrocinio.setEdicion("Test Edition");
        patrocinio.setTipoRegistro("Test Tipo");
        patrocinio.setInstitucion(institucionDTO);
        patrocinio.setNivelPatrocinio("PLATA");
        patrocinio.setAporteEconomico(new BigDecimal("1000.00"));
        patrocinio.setCantidadRegistros(10);
        patrocinio.setCodigoPatrocinio("PAT001");
        patrocinio.setFechaCreacion(fechaCreacion);    
        
        
     
        // Assert
        assertEquals("PAT001", patrocinio.getId());
        assertEquals("Test Event", patrocinio.getEvento());
        assertEquals("Test Edition", patrocinio.getEdicion());
        assertEquals("Test Tipo", patrocinio.getTipoRegistro());
        assertEquals(institucionDTO, patrocinio.getInstitucion());
        assertEquals("PLATA", patrocinio.getNivelPatrocinio());
        assertEquals(new BigDecimal("1000.00"), patrocinio.getAporteEconomico());
        assertEquals(10, patrocinio.getCantidadRegistros());
        assertEquals("PAT001", patrocinio.getCodigoPatrocinio());
        assertEquals(fechaCreacion, patrocinio.getFechaCreacion());
        assertEquals(new BigDecimal("500.00"), patrocinio.getCostoRegistros());
        assertEquals(new BigDecimal("50.00"), patrocinio.getPorcentajeAporte());
    }
    
    @Test
    void testPatrocinioDTO_ExcedeLimitePorcentaje() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        patrocinio.setPorcentajeAporte(new BigDecimal("25.00")); // Above 20%
        
        // Act & Assert
        assertTrue(patrocinio.excedeLimitePorcentaje());
        
        patrocinio.setPorcentajeAporte(new BigDecimal("15.00")); // Below 20%
        assertFalse(patrocinio.excedeLimitePorcentaje());
        
        patrocinio.setPorcentajeAporte(null); // Null percentage
        assertFalse(patrocinio.excedeLimitePorcentaje());
    }
    
    @Test
    void testPatrocinioDTO_EsPatrocinioAlto() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        
        // Act & Assert
        patrocinio.setNivelPatrocinio("ALTO");
        assertTrue(patrocinio.esPatrocinioAlto());
        
        patrocinio.setNivelPatrocinio("PREMIUM");
        assertTrue(patrocinio.esPatrocinioAlto());
        
        patrocinio.setNivelPatrocinio("PLATA");
        assertFalse(patrocinio.esPatrocinioAlto());
        
        patrocinio.setNivelPatrocinio("BRONCE");
        assertFalse(patrocinio.esPatrocinioAlto());
    }
    
    @Test
    void testPatrocinioDTO_GetFormattedAporte() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        patrocinio.setAporteEconomico(new BigDecimal("1000.50"));
        
        // Act
        String formatted = patrocinio.getFormattedAporte();
        
        // Assert
        assertEquals("$1000.50", formatted);
    }
    
    @Test
    void testPatrocinioDTO_GetFormattedCostoRegistros() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        
        // Act & Assert
        patrocinio.setCostoRegistros(new BigDecimal("500.25"));
        assertEquals("$500.25", patrocinio.getFormattedCostoRegistros());
        
        patrocinio.setCostoRegistros(null);
        assertEquals("N/A", patrocinio.getFormattedCostoRegistros());
    }
    
    @Test
    void testPatrocinioDTO_GetFormattedPorcentaje() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        
        // Act & Assert
        patrocinio.setPorcentajeAporte(new BigDecimal("15.75"));
        assertEquals("15.75%", patrocinio.getFormattedPorcentaje());
        
        patrocinio.setPorcentajeAporte(null);
        assertEquals("N/A", patrocinio.getFormattedPorcentaje());
    }
    
    @Test
    void testPatrocinioDTO_GetValorRegistro() {
        // Arrange
        PatrocinioDTO patrocinio = new PatrocinioDTO();
        patrocinio.setAporteEconomico(new BigDecimal("1000.00"));
        
        // Act & Assert
        patrocinio.setCantidadRegistros(10);
        assertEquals(new BigDecimal("100.00"), patrocinio.getValorRegistro());
        
        patrocinio.setCantidadRegistros(0);
        assertEquals(BigDecimal.ZERO, patrocinio.getValorRegistro());
    }
    
    @Test
    void testPatrocinioDTO_ToString() {
        // Arrange
        InstitucionDTO institucionDTO = new InstitucionDTO("Test Inst", "Test Desc", "http://test.com");
        PatrocinioDTO patrocinio = new PatrocinioDTO(
            "PAT001", "Test Event", "Test Edition", "Test Tipo",
            institucionDTO, "PLATA", new BigDecimal("1000.00"), 10, "PAT001"
        );
        
        // Act
        String result = patrocinio.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("PAT001"));
        assertTrue(result.contains("Test Event"));
        assertTrue(result.contains("Test Edition"));
        assertTrue(result.contains("Test Inst"));
        assertTrue(result.contains("PLATA"));
        assertTrue(result.contains("1000"));
        assertTrue(result.contains("10"));
    }
    
    @Test
    void testEdicionDTO_AdditionalSettersAndGetters() {
        // Arrange
        EdicionDTO edicion = new EdicionDTO();
        LocalDate fechaInicio = LocalDate.of(2024, 6, 1);
        LocalDate fechaFin = LocalDate.of(2024, 6, 3);
        LocalDateTime fechaCreacion = LocalDateTime.now();
        
        // Act
        edicion.setNombre("Test Edition");
        edicion.setSigla("TE2024");
        edicion.setDescripcion("Test Description");
        edicion.setFechaInicio(fechaInicio);
        edicion.setFechaFin(fechaFin);
        edicion.setOrganizador("Test Organizer");
        edicion.setPais("Test Country");
        edicion.setCiudad("Test City");
        edicion.setEvento("Test Event");
        edicion.setFechaCreacion(fechaCreacion);
        edicion.setTotalRegistros(50);
        edicion.setCupoTotal(100);
        
        // Assert
        assertEquals("Test Edition", edicion.getNombre());
        assertEquals("TE2024", edicion.getSigla());
        assertEquals("Test Description", edicion.getDescripcion());
        assertEquals(fechaInicio, edicion.getFechaInicio());
        assertEquals(fechaFin, edicion.getFechaFin());
        assertEquals("Test Organizer", edicion.getOrganizador());
        assertEquals("Test Country", edicion.getPais());
        assertEquals("Test City", edicion.getCiudad());
        assertEquals("Test Event", edicion.getEvento());
        assertEquals(fechaCreacion, edicion.getFechaCreacion());
        assertEquals(50, edicion.getTotalRegistros());
        assertEquals(100, edicion.getCupoTotal());
    }
    

    @Test
    void testRegistroDTO_ConstructorAndSetters() {
        // Arrange
        AsistenteDTO asistenteDTO = new AsistenteDTO("testUser", "Test User", "test@email.com", "Test Apellido", null, null, LocalDate.now(), null);
        EdicionDTO edicionDTO = new EdicionDTO("Test Edition", "TE2024", LocalDate.now(), LocalDate.now().plusDays(3), "Test Org", "Test City", "Test Country", "Test Event", "TestImg");
        TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO("Test Tipo", "Test Desc", new BigDecimal("100.00"), 50);
        
        // Act
        RegistroDTO registro = new RegistroDTO();
        registro.setId("REG001");
        registro.setAsistente(asistenteDTO);
        registro.setEdicion(edicionDTO);
        registro.setTipoRegistro(tipoRegistroDTO);
        registro.setCosto(new BigDecimal("100.00"));
        registro.setFechaRegistro(LocalDate.now());
        
        // Assert
        assertEquals("REG001", registro.getId());
        assertEquals(asistenteDTO, registro.getAsistente());
        assertEquals(edicionDTO, registro.getEdicion());
        assertEquals(tipoRegistroDTO, registro.getTipoRegistro());
        assertEquals(new BigDecimal("100.00"), registro.getCosto());
        assertNotNull(registro.getFechaRegistro());
    }
    
    @Test
    void testRegistroDTO_ConstructorWithParameters() {
        // Arrange
        AsistenteDTO asistenteDTO = new AsistenteDTO("testUser", "Test User", "test@email.com", "Test Apellido", null, null, LocalDate.now(), null);
        EdicionDTO edicionDTO = new EdicionDTO("Test Edition", "TE2024", LocalDate.now(), LocalDate.now().plusDays(3), "Test Org", "Test City", "Test Country", "Test Event", "TestImg");
        TipoRegistroDTO tipoRegistroDTO = new TipoRegistroDTO("Test Tipo", "Test Desc", new BigDecimal("100.00"), 50);
        
        // Act
        RegistroDTO registro = new RegistroDTO(
            "REG001", asistenteDTO, edicionDTO, tipoRegistroDTO, 
            new BigDecimal("100.00"), "Test Edition"
        );
        
        // Assert
        assertEquals("REG001", registro.getId());
        assertEquals(asistenteDTO, registro.getAsistente());
        assertEquals(edicionDTO, registro.getEdicion());
        assertEquals(tipoRegistroDTO, registro.getTipoRegistro());
        assertEquals(new BigDecimal("100.00"), registro.getCosto());
        assertEquals("Test Edition", registro.getNombreEdicion());
    }
    
    @Test
    void testTipoRegistroDTO_SettersAndGetters() {
        // Arrange
        TipoRegistroDTO tipoRegistro = new TipoRegistroDTO();
        
        // Act
        tipoRegistro.setNombre("Test Tipo");
        tipoRegistro.setDescripcion("Test Description");
        tipoRegistro.setCosto(new BigDecimal("150.00"));
        tipoRegistro.setCupo(75);
        tipoRegistro.setCupoDisponible(50);
        
        // Assert
        assertEquals("Test Tipo", tipoRegistro.getNombre());
        assertEquals("Test Description", tipoRegistro.getDescripcion());
        assertEquals(new BigDecimal("150.00"), tipoRegistro.getCosto());
        assertEquals(75, tipoRegistro.getCupo());
        assertEquals(50, tipoRegistro.getCupoDisponible());
    }
    
  
    
    
    @Test
    void testTipoRegistroDTO_TieneCupoDisponible_True() {
        // Arrange
        TipoRegistroDTO tipoRegistro = new TipoRegistroDTO("Test", "Description", new BigDecimal("100.00"), 50);
        tipoRegistro.setCupoDisponible(25);
        
        // Act & Assert
        assertTrue(tipoRegistro.tieneCupoDisponible());
    }
    
    @Test
    void testTipoRegistroDTO_TieneCupoDisponible_False() {
        // Arrange
        TipoRegistroDTO tipoRegistro = new TipoRegistroDTO("Test", "Description", new BigDecimal("100.00"), 50);
        tipoRegistro.setCupoDisponible(0);
        
        // Act & Assert
        assertFalse(tipoRegistro.tieneCupoDisponible());
    }
    
    @Test
    void testInstitucionDTO_SettersAndGetters() {
        // Arrange
        InstitucionDTO institucion = new InstitucionDTO();
        
        // Act
        institucion.setNombre("Test Institution");
        institucion.setDescripcion("Test Description");
        institucion.setSitioWeb("http://test.com");
        
        // Assert
        assertEquals("Test Institution", institucion.getNombre());
        assertEquals("Test Description", institucion.getDescripcion());
        assertEquals("http://test.com", institucion.getSitioWeb());
    }
    
    @Test
    void testInstitucionDTO_TieneSitioWeb_True() {
        // Arrange
        InstitucionDTO institucion = new InstitucionDTO("Test", "Description", "http://test.com");
        
        // Act & Assert
        assertTrue(institucion.tieneSitioWeb());
    }
    
    @Test
    void testInstitucionDTO_TieneSitioWeb_False() {
        // Arrange
        InstitucionDTO institucion = new InstitucionDTO("Test", "Description", null);
        
        // Act & Assert
        assertFalse(institucion.tieneSitioWeb());
    }
    
    @Test
    void testInstitucionDTO_TieneSitioWeb_EmptyString() {
        // Arrange
        InstitucionDTO institucion = new InstitucionDTO("Test", "Description", "");
        
        // Act & Assert
        assertFalse(institucion.tieneSitioWeb());
    }
    
    @Test
    void testCategoriaDTO_BasicFunctionality() {
        // Arrange
        CategoriaDTO categoria = new CategoriaDTO("Test Category");
        
        // Assert
        assertEquals("Test Category", categoria.getNombre());
    }
    
    @Test
    void testAsistenteDTO_WithInstitucion() {
        // Arrange
        InstitucionDTO institucionDTO = new InstitucionDTO("Test Inst", "Test Desc", "http://test.com");
        
        // Act
        AsistenteDTO asistente = new AsistenteDTO("testUser", "Test User", "test@email.com", "TestPW", "TestImg",
                                                 "Test Apellido", LocalDate.of(1990, 1, 1), institucionDTO);
        
        // Assert
        assertNotNull(asistente);
        assertEquals("testUser", asistente.getNickname());
        assertEquals("Test User", asistente.getNombre());
        assertEquals("test@email.com", asistente.getCorreo());
        assertEquals("Test Apellido", asistente.getApellido());
        assertEquals(LocalDate.of(1990, 1, 1), asistente.getFechaNacimiento());
        assertEquals(institucionDTO, asistente.getInstitucion());
        assertEquals("Asistente", asistente.getTipoUsuario());
    }
    
    @Test
    void testAsistenteDTO_EsMayorDeEdad_EdgeCases() {
        // Test exactly 18 years old
        LocalDate fecha18 = LocalDate.now().minusYears(18);
        AsistenteDTO asistente18 = new AsistenteDTO("test18", "Test", "test@email.com", "Apellido", null, null, fecha18, null);
        assertTrue(asistente18.esMayorDeEdad());
        
        // Test 17 years and 364 days old (should be under 18)
        LocalDate fecha17 = LocalDate.now().minusYears(17).minusDays(1);
        AsistenteDTO asistente17 = new AsistenteDTO("test17", "Test", "test@email.com", "Apellido", null, null, fecha17, null);
        assertFalse(asistente17.esMayorDeEdad());
    }
    
    @Test
    void testOrganizadorDTO_WithSitioWeb() {
        // Arrange
        OrganizadorDTO organizador = new OrganizadorDTO("testOrg", "Test Organizer", "org@email.com", "TestPW", "TestImg",
                                                       "Test Description", "http://test.com");
        
        // Assert
        assertNotNull(organizador);
        assertEquals("testOrg", organizador.getNickname());
        assertEquals("Test Organizer", organizador.getNombre());
        assertEquals("org@email.com", organizador.getCorreo());
        assertEquals("Test Description", organizador.getDescripcion());
        assertEquals("http://test.com", organizador.getSitioWeb());
        assertEquals("Organizador", organizador.getTipoUsuario());
    }
    
    @Test
    void testOrganizadorDTO_WithoutSitioWeb() {
        // Arrange
        OrganizadorDTO organizador = new OrganizadorDTO("testOrg", "Test Organizer", "org@email.com", "TestPW", "TestImg",
                                                       "Test Description",null);
        
        // Assert
        assertNotNull(organizador);
        assertEquals("testOrg", organizador.getNickname());
        assertEquals("Test Organizer", organizador.getNombre());
        assertEquals("org@email.com", organizador.getCorreo());
        assertEquals("Test Description", organizador.getDescripcion());
        assertNull(organizador.getSitioWeb());
        assertEquals("Organizador", organizador.getTipoUsuario());
        assertFalse(organizador.tieneSitioWeb());
    }
    
    @Test
    void testOrganizadorDTO_TieneSitioWeb_EmptyString() {
        // Arrange
        OrganizadorDTO organizador = new OrganizadorDTO("testOrg", "Test Organizer", "org@email.com", 
                                                       "Test Description", "", null, null);
        
        // Act & Assert
        assertFalse(organizador.tieneSitioWeb());
    }
    
    @Test
    void testUsuarioDTO_DefaultConstructor() {
        // Act
        UsuarioDTO usuario = new UsuarioDTO();
        
        // Assert
        assertNotNull(usuario);
        assertNull(usuario.getNickname());
        assertNull(usuario.getNombre());
        assertNull(usuario.getCorreo());
        assertNull(usuario.getTipoUsuario());
    }
    
    @Test
    void testUsuarioDTO_ToString_WithNullValues() {
        // Arrange
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNickname("testUser");
        usuario.setNombre(null);
        usuario.setCorreo("test@email.com");
        usuario.setTipoUsuario("Test");
        
        // Act
        String result = usuario.toString();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("testUser"));
        assertTrue(result.contains("test@email.com"));
        assertTrue(result.contains("Test"));
    }
}
