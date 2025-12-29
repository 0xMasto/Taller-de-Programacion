package test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.*;
import logica.interfaces.Fabrica;
import logica.interfaces.iControladorUsuarios;
import logica.model.*;
import logica.persistence.*;
import org.junit.jupiter.api.*;

import exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unified persistence tests for all archived entities
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersistenceTest {

    protected EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction tx;

    @BeforeAll
    void initEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("EdicionesArchivadasPU");
    }

    @AfterAll
    void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterEach
    void tearDown() {
        if (em.isOpen()) em.close();
    }

    // ==========================
    // ASISTENTE_ARCHIVADO TESTS
    // ==========================
    @Test
    void testPersistirYRecuperarAsistenteArchivado() {
        AsistenteArchivado asistente = new AsistenteArchivado(
            "nickTest",
            "Nombre Test",
            "correo@test.com",
            "imagen.png",
            "Apellido Test",
            LocalDate.of(1990, 1, 1),
            "Instituto Test"
        );

        tx.begin();
        em.persist(asistente);
        tx.commit();

        AsistenteArchivado encontrado = em.find(AsistenteArchivado.class, asistente.getNickname());
        assertNotNull(encontrado);
        assertEquals("nickTest", encontrado.getNickname());
        assertEquals("Apellido Test", encontrado.getApellido());
        assertEquals(LocalDate.of(1990, 1, 1), encontrado.getFechaNacimiento());
        assertEquals("Instituto Test", encontrado.getNombreInstitucion());
    }

    @Test
    void testActualizarAsistenteArchivado() {
        AsistenteArchivado asistente = new AsistenteArchivado(
            "nickUpdate",
            "Nombre Original",
            "correo@original.com",
            "imagen1.png",
            "Apellido Original",
            LocalDate.of(1985, 5, 5),
            "Instituto Original"
        );

        tx.begin();
        em.persist(asistente);
        tx.commit();

        tx.begin();
        AsistenteArchivado existente = em.find(AsistenteArchivado.class, asistente.getNickname());
        existente.setApellido("Apellido Modificado");
        existente.setNombreInstitucion("Instituto Modificado");
        tx.commit();

        AsistenteArchivado actualizado = em.find(AsistenteArchivado.class, asistente.getNickname());
        assertEquals("Apellido Modificado", actualizado.getApellido());
        assertEquals("Instituto Modificado", actualizado.getNombreInstitucion());
    }

    @Test
    void testEliminarAsistenteArchivado() {
        AsistenteArchivado asistente = new AsistenteArchivado(
            "nickDelete",
            "Nombre Delete",
            "correo@delete.com",
            "imagen2.png",
            "Apellido Delete",
            LocalDate.of(1995, 12, 15),
            "Instituto Delete"
        );

        tx.begin();
        em.persist(asistente);
        tx.commit();

        tx.begin();
        AsistenteArchivado existente = em.find(AsistenteArchivado.class, asistente.getNickname());
        em.remove(existente);
        tx.commit();

        AsistenteArchivado eliminado = em.find(AsistenteArchivado.class, asistente.getNickname());
        assertNull(eliminado);
    }

    // ==========================
    // ORGANIZADOR_ARCHIVADO TESTS
    // ==========================
    @Test
    void testPersistirYRecuperarOrganizadorArchivado() {
        OrganizadorArchivado org = new OrganizadorArchivado(
            "orgNick",
            "Organizador Uno",
            "uno@test.com",
            "imagen.png",
            "Descripción del organizador",
            "https://org.com"
        );

        tx.begin();
        em.persist(org);
        tx.commit();

        OrganizadorArchivado encontrado = em.find(OrganizadorArchivado.class, "orgNick");
        assertNotNull(encontrado);
        assertEquals("Organizador Uno", encontrado.getNombre());
        assertEquals("https://org.com", encontrado.getSitioWeb());
    }

    @Test
    void testRelacionOrganizadorYEdiciones() {
        OrganizadorArchivado org = new OrganizadorArchivado(
            "orgRel",
            "Organizador Rel",
            "rel@test.com",
            "img.png",
            "Desc rel",
            "https://rel.com"
        );

        EdicionArchivada ed1 = new EdicionArchivada(
            "Edición 2023", "E23",
            LocalDate.of(2023, 1, 10),
            LocalDate.of(2023, 2, 10),
            LocalDate.of(2022, 12, 1),
            "Montevideo", "Uruguay", "img1.png",
            "https://video1.com", "Evento X",
            LocalDate.of(2023, 3, 1)
        );

        EdicionArchivada ed2 = new EdicionArchivada(
            "Edición 2024", "E24",
            LocalDate.of(2024, 1, 10),
            LocalDate.of(2024, 2, 10),
            LocalDate.of(2023, 12, 1),
            "Buenos Aires", "Argentina", "img2.png",
            "https://video2.com", "Evento Y",
            LocalDate.of(2024, 3, 1)
        );

        org.addEdicion(ed1);
        org.addEdicion(ed2);

        tx.begin();
        em.persist(org);
        tx.commit();

        OrganizadorArchivado encontrado = em.find(OrganizadorArchivado.class, "orgRel");
        assertEquals(2, encontrado.getEdiciones().size());
    }

    // ==========================
    // EDICION_ARCHIVADA TESTS
    // ==========================
    @Test
    void testPersistirYRecuperarEdicionArchivada() {
        OrganizadorArchivado org = new OrganizadorArchivado(
            "orgBase",
            "Organizador Base",
            "base@test.com",
            "img.png",
            "Descripcion base",
            "https://base.com"
        );

        EdicionArchivada edicion = new EdicionArchivada(
            "Edición Test", "EDT",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 6, 1),
            LocalDate.of(2023, 4, 10),
            "Montevideo", "Uruguay",
            "imgEd.png", "https://video.com",
            "Evento Base",
            LocalDate.of(2023, 6, 15)
        );
        edicion.setOrganizador(org);

        tx.begin();
        em.persist(org);
        em.persist(edicion);
        tx.commit();

        EdicionArchivada encontrada = em.find(EdicionArchivada.class, edicion.getId());
        assertNotNull(encontrada);
        assertEquals("Edición Test", encontrada.getNombre());
        assertEquals("Montevideo", encontrada.getCiudad());
        assertEquals("orgBase", encontrada.getOrganizador().getNickname());
    }

    @Test
    void testActualizarEdicionArchivada() {
        OrganizadorArchivado org = new OrganizadorArchivado(
            "orgEditEdi",
            "Org Edit",
            "edit@test.com",
            "img.png",
            "Desc org",
            "https://org.com"
        );

        EdicionArchivada edicion = new EdicionArchivada(
            "Edición Original", "EO",
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 2, 1),
            LocalDate.of(2022, 12, 1),
            "Canelones", "Uruguay",
            "img.png", null,
            "Evento Original",
            LocalDate.of(2023, 3, 1)
        );
        edicion.setOrganizador(org);

        tx.begin();
        em.persist(org);
        em.persist(edicion);
        tx.commit();

        tx.begin();
        EdicionArchivada existente = em.find(EdicionArchivada.class, edicion.getId());
        existente.setCiudad("Salto");
        existente.setPais("Uruguay");
        existente.setVideoUrl("https://nuevo-video.com");
        tx.commit();

        EdicionArchivada actualizada = em.find(EdicionArchivada.class, edicion.getId());
        assertEquals("Salto", actualizada.getCiudad());
        assertEquals("https://nuevo-video.com", actualizada.getVideoUrl());
    }

    @Test
    void testEliminarEdicionArchivada() {
        OrganizadorArchivado org = new OrganizadorArchivado(
            "orgDelEdi",
            "Org Del",
            "del@test.com",
            "img.png",
            "Desc delete",
            "https://delete.com"
        );

        EdicionArchivada edicion = new EdicionArchivada(
            "Edición Borrar", "EB",
            LocalDate.of(2022, 1, 1),
            LocalDate.of(2022, 2, 1),
            LocalDate.of(2021, 12, 1),
            "Maldonado", "Uruguay",
            "img.png", null,
            "Evento Borrar",
            LocalDate.of(2022, 3, 1)
        );
        edicion.setOrganizador(org);

        tx.begin();
        em.persist(org);
        em.persist(edicion);
        tx.commit();

        tx.begin();
        EdicionArchivada existente = em.find(EdicionArchivada.class, edicion.getId());
        em.remove(existente);
        tx.commit();

        EdicionArchivada eliminado = em.find(EdicionArchivada.class, edicion.getId());
        assertNull(eliminado);
    }
 // ==========================
 // ASISTENTE_ARCHIVADO ADDITIONAL TESTS
 // ==========================
 @Test
 void testSettersYGettersAsistenteArchivado() {
     AsistenteArchivado asistente = new AsistenteArchivado();

     // Probar setters
     asistente.setNickname("nickSetGet");
     asistente.setNombre("Nombre SetGet");
     asistente.setCorreo("correo@setget.com");
     asistente.setImagen("imagenSetGet.png");
     asistente.setApellido("Apellido SetGet");
     asistente.setFechaNacimiento(LocalDate.of(2000, 1, 1));
     asistente.setNombreInstitucion("Instituto SetGet");

     // Verificar getters
     assertEquals("nickSetGet", asistente.getNickname());
     assertEquals("Nombre SetGet", asistente.getNombre());
     assertEquals("correo@setget.com", asistente.getCorreo());
     assertEquals("imagenSetGet.png", asistente.getImagen());
     assertEquals("Apellido SetGet", asistente.getApellido());
     assertEquals(LocalDate.of(2000, 1, 1), asistente.getFechaNacimiento());
     assertEquals("Instituto SetGet", asistente.getNombreInstitucion());
 }

 @Test
 void testPersistirAsistenteArchivadoCompleto() {
     AsistenteArchivado asistente = new AsistenteArchivado(
         "nickCompleto",
         "Nombre Completo",
         "correo@completo.com",
         "imagenCompleta.png",
         "Apellido Completo",
         LocalDate.of(1992, 7, 15),
         "Instituto Completo"
     );

     tx.begin();
     em.persist(asistente);
     tx.commit();

     AsistenteArchivado encontrado = em.find(AsistenteArchivado.class, asistente.getNickname());
     assertNotNull(encontrado);
     assertEquals("nickCompleto", encontrado.getNickname());
     assertEquals("Nombre Completo", encontrado.getNombre());
     assertEquals("correo@completo.com", encontrado.getCorreo());
     assertEquals("imagenCompleta.png", encontrado.getImagen());
     assertEquals("Apellido Completo", encontrado.getApellido());
     assertEquals(LocalDate.of(1992, 7, 15), encontrado.getFechaNacimiento());
     assertEquals("Instituto Completo", encontrado.getNombreInstitucion());
 }

//==========================
//PERSISTENCE MANAGER TESTS
//==========================
@Test
void testArchivarEdicionCompleta() {
  // Crear organizador
  Organizador org = new Organizador(null, null, null, null, null, null);
  org.setNickname("orgTest");
  org.setNombre("Organizador Test");
  org.setCorreo("org@test.com");
  org.setImagen("org.png");
  org.setDescripcion("Descripcion organizador");
  org.setSitioWeb("www.organizador.com");

  // Crear asistentes
  Asistente asist1 = new Asistente(null, null, null, null, null, null, null, null);
  asist1.setNickname("asist1");
  asist1.setNombre("Asistente Uno");
  asist1.setCorreo("asist1@test.com");
  asist1.setImagen("asist1.png");
  asist1.setApellido("Apellido1");
  asist1.setFechaNacimiento(LocalDate.of(1990,1,1));
  
  Asistente asist2 = new Asistente(null, null, null, null, null, null, null, null);
  asist2.setNickname("asist2");
  asist2.setNombre("Asistente Dos");
  asist2.setCorreo("asist2@test.com");
  asist2.setImagen("asist2.png");
  asist2.setApellido("Apellido2");
  asist2.setFechaNacimiento(LocalDate.of(1991,2,2));

  // Crear registros
  Registro reg1 = new Registro(null, null, null, null);
  reg1.setFecha(LocalDate.now());
  reg1.setCosto(new BigDecimal("100.0"));
  reg1.setAsistio(true);
  reg1.setTipoRegistro(new TipoRegistro("VIP", null, null, 0));

  Registro reg2 = new Registro(null, null, null, null);
  reg2.setFecha(LocalDate.now());
  reg2.setCosto(new BigDecimal("50.0"));
  reg2.setAsistio(false);
  reg2.setTipoRegistro(new TipoRegistro("Normal", null, null, 0));

  // Crear edición
  Edicion ed = new Edicion(null, null, null, null, null, null, null);
  ed.setNombre("Edicion Test");
  ed.setSigla("EDT");
  ed.setFechaInicio(LocalDate.of(2025,1,1));
  ed.setFechaFin(LocalDate.of(2025,1,5));
  ed.setFechaAlta(LocalDate.of(2024,12,1));
  ed.setCiudad("Montevideo");
  ed.setPais("Uruguay");
  ed.setImagen("ed.png");
  ed.setVideoUrl("video.mp4");

  // Archivar
  PersistenceManager.archivarEdicion(
      ed,
      org,
      List.of(reg1, reg2),
      List.of(asist1, asist2)
  );

  // Validar organizador archivado
  List<EdicionArchivada> ediciones = PersistenceManager.getEdicionesArchivadasPorOrganizador("orgTest");
  assertEquals(1, ediciones.size());
  EdicionArchivada edArch = ediciones.get(0);
  assertEquals("Edicion Test", edArch.getNombre());
  assertEquals("EDT", edArch.getSigla());

  // Validar registros archivados
  assertEquals(2, edArch.getRegistros().size());

  // Validar asistente archivado
  boolean tieneReg1 = PersistenceManager.tieneRegistrosArchivados("asist1", "Edicion Test");
  boolean tieneReg2 = PersistenceManager.tieneRegistrosArchivados("asist2", "Edicion Test");
  assertTrue(tieneReg1);
  assertTrue(tieneReg2);
}

@Test
void testGetEdicionesArchivadasPorOrganizadorVacio() {
  List<EdicionArchivada> ediciones = PersistenceManager.getEdicionesArchivadasPorOrganizador("organizadorInexistente");
  assertTrue(ediciones.isEmpty());
}

@Test
void testTieneRegistrosArchivadosFalso() {
  boolean tiene = PersistenceManager.tieneRegistrosArchivados("asistenteInexistente", "EdicionInexistente");
  assertFalse(tiene);
}
//==========================
//ASISTENTE_ARCHIVADO ADDITIONAL TESTS
//==========================
@Test
void testSettersYGettersAsistenteArchivado1() {
 AsistenteArchivado asistente = new AsistenteArchivado();

 // Probar setters
 asistente.setNickname("nickSetGet");
 asistente.setNombre("Nombre SetGet");
 asistente.setCorreo("correo@setget.com");
 asistente.setImagen("imagenSetGet.png");
 asistente.setApellido("Apellido SetGet");
 asistente.setFechaNacimiento(LocalDate.of(2000, 1, 1));
 asistente.setNombreInstitucion("Instituto SetGet");

 // Verificar getters
 assertEquals("nickSetGet", asistente.getNickname());
 assertEquals("Nombre SetGet", asistente.getNombre());
 assertEquals("correo@setget.com", asistente.getCorreo());
 assertEquals("imagenSetGet.png", asistente.getImagen());
 assertEquals("Apellido SetGet", asistente.getApellido());
 assertEquals(LocalDate.of(2000, 1, 1), asistente.getFechaNacimiento());
 assertEquals("Instituto SetGet", asistente.getNombreInstitucion());
}

@Test
void testPersistirAsistenteArchivadoCompleto1() throws BusinessException {
 AsistenteArchivado asistente = new AsistenteArchivado(
     "nickCompleto",
     "Nombre Completo",
     "correo2@completo.com",
     "imagenCompleta.png",
     "Apellido Completo",
     LocalDate.of(1992, 7, 15),
     "Instituto Completo"
 );

 tx.begin();
 em.persist(asistente);
 tx.commit();
 Fabrica f = Fabrica.getInstancia();
 iControladorUsuarios cu = f.getControladorUsuario();
 cu.altaAsistente("nickCompleto",
     "Nombre Completo",
     "correo2@completo.com",
     "imagenCompleta.png",
     "Apellido Completo",
     null, LocalDate.of(1992, 7, 15),
     "Instituto Completo");
 AsistenteArchivado encontrado = em.find(AsistenteArchivado.class, "nickCompleto");
 assertNotNull(encontrado);
 assertEquals("nickCompleto", encontrado.getNickname());
 assertEquals("Nombre Completo", encontrado.getNombre());
 assertEquals("correo2@completo.com", encontrado.getCorreo());
 assertEquals("imagenCompleta.png", encontrado.getImagen());
 assertEquals("Apellido Completo", encontrado.getApellido());
 assertEquals(LocalDate.of(1992, 7, 15), encontrado.getFechaNacimiento());
 assertEquals("Instituto Completo", encontrado.getNombreInstitucion());
}
//==========================
//PERSISTENCE MANAGER TESTS
//==========================
@Test
void testArchivarEdicionCompleta1() {
 // Crear organizador
 Organizador org = new Organizador(null, null, null, null, null, null);
 org.setNickname("orgTest");
 org.setNombre("Organizador Test");
 org.setCorreo("org@test.com");
 org.setImagen("org.png");
 org.setDescripcion("Descripcion organizador");
 org.setSitioWeb("www.organizador.com");

 // Crear asistentes
 Asistente asist1 = new Asistente(null, null, null, null, null, null, null, null);
 asist1.setNickname("asist1");
 asist1.setNombre("Asistente Uno");
 asist1.setCorreo("asist1@test.com");
 asist1.setImagen("asist1.png");
 asist1.setApellido("Apellido1");
 asist1.setFechaNacimiento(LocalDate.of(1990,1,1));
 
 Asistente asist2 = new Asistente(null, null, null, null, null, null, null, null);
 asist2.setNickname("asist2");
 asist2.setNombre("Asistente Dos");
 asist2.setCorreo("asist2@test.com");
 asist2.setImagen("asist2.png");
 asist2.setApellido("Apellido2");
 asist2.setFechaNacimiento(LocalDate.of(1991,2,2));

 // Crear registros
 Registro reg1 = new Registro(null, null, null, null);
 reg1.setFecha(LocalDate.now());
 reg1.setCosto(new BigDecimal("100.0"));
 reg1.setAsistio(true);
 reg1.setTipoRegistro(new TipoRegistro("VIP", null, null, 0));

 Registro reg2 = new Registro(null, null, null, null);
 reg2.setFecha(LocalDate.now());
 reg2.setCosto(new BigDecimal("50.0"));
 reg2.setAsistio(false);
 reg2.setTipoRegistro(new TipoRegistro("Normal", null, null, 0));

 // Crear edición
 Edicion ed = new Edicion(null, null, null, null, null, null, null);
 ed.setNombre("Edicion Test");
 ed.setSigla("EDT");
 ed.setFechaInicio(LocalDate.of(2025,1,1));
 ed.setFechaFin(LocalDate.of(2025,1,5));
 ed.setFechaAlta(LocalDate.of(2024,12,1));
 ed.setCiudad("Montevideo");
 ed.setPais("Uruguay");
 ed.setImagen("ed.png");
 ed.setVideoUrl("video.mp4");

 // Archivar
 PersistenceManager.archivarEdicion(
     ed,
     org,
     List.of(reg1, reg2),
     List.of(asist1, asist2)
 );

 // Validar organizador archivado
 List<EdicionArchivada> ediciones = PersistenceManager.getEdicionesArchivadasPorOrganizador("orgTest");
 assertEquals(1, ediciones.size());
 EdicionArchivada edArch = ediciones.get(0);
 assertEquals("Edicion Test", edArch.getNombre());
 assertEquals("EDT", edArch.getSigla());

 // Validar registros archivados
 assertEquals(2, edArch.getRegistros().size());

 // Validar asistente archivado
 boolean tieneReg1 = PersistenceManager.tieneRegistrosArchivados("asist1", "Edicion Test");
 boolean tieneReg2 = PersistenceManager.tieneRegistrosArchivados("asist2", "Edicion Test");
 assertTrue(tieneReg1);
 assertTrue(tieneReg2);
}

@Test
void testGetEdicionesArchivadasPorOrganizadorVacio1() {
 List<EdicionArchivada> ediciones = PersistenceManager.getEdicionesArchivadasPorOrganizador("organizadorInexistente");
 assertTrue(ediciones.isEmpty());
}

@Test
void testTieneRegistrosArchivadosFalso1() {
 boolean tiene = PersistenceManager.tieneRegistrosArchivados("asistenteInexistente", "EdicionInexistente");
 assertFalse(tiene);
}


//==========================
//REGISTRO_ARCHIVADO TESTS
//==========================
@Test
void testPersistirYRecuperarRegistroArchivado() {
 // Crear asistente archivado
 AsistenteArchivado asistente = new AsistenteArchivado(
     "nickRegistro",
     "Nombre Asist",
     "correo@asist.com",
     "imagen.png",
     "Apellido Asist",
     LocalDate.of(1990, 1, 1),
     "Instituto Test"
 );
 
 // Persistir asistente
 tx.begin();
 em.persist(asistente);
 tx.commit();

 // Crear edición archivada
 OrganizadorArchivado org = new OrganizadorArchivado(
     "orgRegistro",
     "Nombre Org",
     "correo@org.com",
     "org.png",
     "Descripcion Org",
     "www.org.com"
 );
 tx.begin();
 em.persist(org);
 tx.commit();

 EdicionArchivada edicion = new EdicionArchivada(
     "Edicion Registro",
     "ER",
     LocalDate.of(2025, 1, 1),
     LocalDate.of(2025, 1, 5),
     LocalDate.of(2024, 12, 1),
     "Montevideo",
     "Uruguay",
     "ed.png",
     "video.mp4",
     "Evento Test",
     LocalDate.now()
 );
 edicion.setOrganizador(org);

 tx.begin();
 em.persist(edicion);
 tx.commit();

 // Crear registro archivado
 RegistroArchivado registro = new RegistroArchivado(
     LocalDate.of(2025, 1, 2),
     new BigDecimal("150.50"),
     true,
     "VIP"
 );
 registro.setEdicion(edicion);
 registro.setAsistente(asistente);

 // Persistir registro
 tx.begin();
 em.persist(registro);
 tx.commit();

 // Recuperar y verificar
 RegistroArchivado encontrado = em.find(RegistroArchivado.class, registro.getId());
 assertNotNull(encontrado);
 assertEquals(LocalDate.of(2025, 1, 2), encontrado.getFechaRegistro());
 assertEquals(new BigDecimal("150.50"), encontrado.getCosto());
 assertTrue(encontrado.isAsistio());
 assertEquals("VIP", encontrado.getNombreTipoRegistro());
 assertEquals("Edicion Registro", encontrado.getEdicion().getNombre());
 assertEquals("nickRegistro", encontrado.getAsistente().getNickname());
}

@Test
void testActualizarRegistroArchivado() {
 // Crear y persistir registro
 RegistroArchivado registro = new RegistroArchivado(
     LocalDate.of(2025, 1, 3),
     new BigDecimal("100.00"),
     false,
     "Normal"
 );
 tx.begin();
 em.persist(registro);
 tx.commit();

 // Actualizar
 tx.begin();
 RegistroArchivado existente = em.find(RegistroArchivado.class, registro.getId());
 existente.setCosto(new BigDecimal("200.00"));
 existente.setAsistio(true);
 existente.setNombreTipoRegistro("VIP");
 tx.commit();

 // Verificar
 RegistroArchivado actualizado = em.find(RegistroArchivado.class, registro.getId());
 assertEquals(new BigDecimal("200.00"), actualizado.getCosto());
 assertTrue(actualizado.isAsistio());
 assertEquals("VIP", actualizado.getNombreTipoRegistro());
}

@Test
void testEliminarRegistroArchivado() {
 RegistroArchivado registro = new RegistroArchivado(
     LocalDate.of(2025, 1, 4),
     new BigDecimal("75.00"),
     true,
     "Estándar"
 );
 tx.begin();
 em.persist(registro);
 tx.commit();

 // Eliminar
 tx.begin();
 RegistroArchivado existente = em.find(RegistroArchivado.class, registro.getId());
 em.remove(existente);
 tx.commit();

 RegistroArchivado eliminado = em.find(RegistroArchivado.class, registro.getId());
 assertNull(eliminado);
}
@Test
void testGettersSettersEdicionArchivada() {
    EdicionArchivada edicion = new EdicionArchivada();
    
    LocalDate fechaInicio = LocalDate.of(2025, 1, 1);
    LocalDate fechaFin = LocalDate.of(2025, 1, 5);
    LocalDate fechaAlta = LocalDate.of(2024, 12, 1);
    LocalDate fechaArchivo = LocalDate.now();
    
    edicion.setNombre("Nombre Test");
    edicion.setSigla("SIG");
    edicion.setFechaInicio(fechaInicio);
    edicion.setFechaFin(fechaFin);
    edicion.setFechaAlta(fechaAlta);
    edicion.setFechaArchivo(fechaArchivo);
    edicion.setCiudad("Montevideo");
    edicion.setPais("Uruguay");
    edicion.setImagen("imagen.png");
    edicion.setVideoUrl("video.mp4");
    edicion.setNombreEvento("Evento Test");
    
    OrganizadorArchivado org = new OrganizadorArchivado();
    edicion.setOrganizador(org);
    
    assertEquals("Nombre Test", edicion.getNombre());
    assertEquals("SIG", edicion.getSigla());
    assertEquals(fechaInicio, edicion.getFechaInicio());
    assertEquals(fechaFin, edicion.getFechaFin());
    assertEquals(fechaAlta, edicion.getFechaAlta());
    assertEquals(fechaArchivo, edicion.getFechaArchivo());
    assertEquals("Montevideo", edicion.getCiudad());
    assertEquals("Uruguay", edicion.getPais());
    assertEquals("imagen.png", edicion.getImagen());
    assertEquals("video.mp4", edicion.getVideoUrl());
    assertEquals("Evento Test", edicion.getNombreEvento());
    assertEquals(org, edicion.getOrganizador());
}
@Test
void testGettersSettersOrganizadorArchivado() {
    OrganizadorArchivado org = new OrganizadorArchivado();
    
    org.setNickname("nickOrg");
    org.setNombre("Nombre Org");
    org.setCorreo("org@correo.com");
    org.setImagen("imagenOrg.png");
    org.setDescripcion("Descripción del organizador");
    org.setSitioWeb("https://sitio.org");
    
    // Crear algunas ediciones de prueba
    EdicionArchivada ed1 = new EdicionArchivada();
    ed1.setNombre("Edición 1");
    
    EdicionArchivada ed2 = new EdicionArchivada();
    ed2.setNombre("Edición 2");
    
    // Probar addEdicion y setter de ediciones
    org.addEdicion(ed1);
    org.addEdicion(ed2);
    
    Set<EdicionArchivada> edicionesSet = new HashSet<>();
    edicionesSet.add(ed1);
    edicionesSet.add(ed2);
    
    org.setEdiciones(edicionesSet); // probando setter directamente
    
    // Asserts
    assertEquals("nickOrg", org.getNickname());
    assertEquals("Nombre Org", org.getNombre());
    assertEquals("org@correo.com", org.getCorreo());
    assertEquals("imagenOrg.png", org.getImagen());
    assertEquals("Descripción del organizador", org.getDescripcion());
    assertEquals("https://sitio.org", org.getSitioWeb());
    
    assertEquals(2, org.getEdiciones().size());
    assertTrue(org.getEdiciones().contains(ed1));
    assertTrue(org.getEdiciones().contains(ed2));
    
    // Verificar que las ediciones apuntan al organizador
    for (EdicionArchivada e : org.getEdiciones()) {
        assertEquals(org, e.getOrganizador());
    }
}

@Test
void testFindOrCreateOrganizadorYAsistente_CreaNuevos() {
    // Preparar datos de prueba
    Organizador org = new Organizador(null, null, null, null, null, null);
    org.setNickname("nuevoOrg");
    org.setNombre("Nombre Nuevo Org");
    org.setCorreo("nuevoOrg@test.com");
    org.setImagen("imagenOrg.png");
    org.setDescripcion("Descripcion");
    org.setSitioWeb("https://nuevo.org");

    Asistente asist = new Asistente(null, null, null, null, null, null, null, null);
    asist.setNickname("nuevoAsist");
    asist.setNombre("Nombre Nuevo Asist");
    asist.setCorreo("nuevoAsist@test.com");
    asist.setImagen("imagenAsist.png");
    asist.setApellido("Apellido Asist");
    asist.setFechaNacimiento(LocalDate.of(1995, 5, 5));
    asist.setInstitucion(new Institucion("Instituto Test", null, null));

    Edicion ed = new Edicion(null, null, null, null, null, null, null);
    ed.setNombre("Edicion Test");
    ed.setSigla("ET2025");
    ed.setFechaInicio(LocalDate.now().minusDays(1));
    ed.setFechaFin(LocalDate.now().plusDays(1));
    ed.setFechaAlta(LocalDate.now());
    ed.setCiudad("Ciudad Test");
    ed.setPais("Pais Test");
    ed.setImagen("imagenEd.png");
    ed.setVideoUrl("videoUrl");
    List<Registro> registros = List.of(new Registro(LocalDate.now(), BigDecimal.TEN, ed, new TipoRegistro("Tipo1", null, null, 0)));
    List<Asistente> asistentes = List.of(asist);

    // Llamada al método que usa findOrCreate internamente
    PersistenceManager.archivarEdicion(ed, org, registros, asistentes);

    // Verificar que se creó el OrganizadorArchivado
    List<EdicionArchivada> ediciones = PersistenceManager.getEdicionesArchivadasPorOrganizador("nuevoOrg");
    assertEquals(1, ediciones.size());
    assertEquals("nuevoOrg", ediciones.get(0).getOrganizador().getNickname());

    // Verificar que se creó el AsistenteArchivado
    assertTrue(PersistenceManager.tieneRegistrosArchivados("nuevoAsist", "Edicion Test"));
}
@Test
void testFindOrCreateAsistente_CrearNuevo() {
    EntityManager em = PersistenceManager.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    // Asegurarse de que no existe previamente
    String nickname = "asistenteNuevo_" + System.currentTimeMillis();

    Asistente asist = new Asistente(null, null, null, null, null, null, null, null);
    asist.setNickname(nickname);
    asist.setNombre("Nombre Nuevo");
    asist.setCorreo("correoNuevo@test.com");
    asist.setImagen("imagen.png");
    asist.setApellido("Apellido");
    asist.setFechaNacimiento(LocalDate.of(1990, 1, 1));
    asist.setInstitucion(new Institucion("Instituto Test", "desc", "web"));

    // Ejecutar método privado indirectamente vía archivarEdicion
    Edicion ed = new Edicion(nickname, nickname, null, null, nickname, nickname, nickname);
    ed.setNombre("Edicion Test " + System.currentTimeMillis());
    ed.setSigla("ET");
    ed.setFechaInicio(LocalDate.now());
    ed.setFechaFin(LocalDate.now().plusDays(1));
    ed.setFechaAlta(LocalDate.now());
    ed.setCiudad("Ciudad Test");
    ed.setPais("Pais Test");
    ed.setImagen("imagenEd.png");
    ed.setVideoUrl("videoUrl");

    PersistenceManager.archivarEdicion(
        ed,
        new Organizador("orgNuevo", "Org Nombre", "org@test.com", "img.png", "Desc", "www.site.com"),
        List.of(new Registro(LocalDate.now(), BigDecimal.TEN, ed, new TipoRegistro("Tipo1", null, null, 0))),
        List.of(asist)
    );

    // Verificar que se creó el AsistenteArchivado
    TypedQuery<AsistenteArchivado> q = em.createQuery(
        "SELECT a FROM AsistenteArchivado a WHERE a.nickname = :nick", AsistenteArchivado.class);
    q.setParameter("nick", nickname);
    AsistenteArchivado creado = q.getSingleResult();

    assertNotNull(creado);
    assertEquals("Nombre Nuevo", creado.getNombre());
    assertEquals("Apellido", creado.getApellido());
    assertEquals("Instituto Test", creado.getNombreInstitucion());

    tx.commit();
    em.close();
}

}
