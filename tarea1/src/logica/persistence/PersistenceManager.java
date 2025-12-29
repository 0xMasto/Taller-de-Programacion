package logica.persistence;

import jakarta.persistence.*;
import logica.model.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Manager class for handling archived editions persistence
 * Uses JPA with HSQLDB for storing historical data
 */
public class PersistenceManager {
    
    private static final String PERSISTENCE_UNIT_NAME = "EdicionesArchivadasPU";
    private static EntityManagerFactory emf = null;
    
    /**
     * Initialize EntityManagerFactory
     */
    public static void initialize() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                System.out.println("JPA EntityManagerFactory initialized successfully");
            } catch (Exception e) {
                System.err.println("Error initializing JPA: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get EntityManager
     */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            initialize();
        }
        return emf.createEntityManager();
    }
    
    /**
     * Archive an edition with all its related data
     */
    public static void archivarEdicion(Edicion edicion, Organizador organizador, List<Registro> registros, List<Asistente> asistentes) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = null;
        
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // Check if organizer already exists in DB
            OrganizadorArchivado organizadorArch = findOrCreateOrganizador(em, organizador);
            
            // Create archived edition
            EdicionArchivada edicionArch = new EdicionArchivada(
                edicion.getNombre(),
                edicion.getSigla(),
                edicion.getFechaInicio(),
                edicion.getFechaFin(),
                edicion.getFechaAlta(),
                edicion.getCiudad(),
                edicion.getPais(),
                edicion.getImagen(),
                edicion.getVideoUrl(),
                "Evento", // Need to pass event name from context
                LocalDate.now()
            );
            edicionArch.setOrganizador(organizadorArch);
            em.persist(edicionArch);
            
            // Archive registrations and asistentes
            for (int i = 0; i < registros.size() && i < asistentes.size(); i++) {
                Registro reg = registros.get(i);
                Asistente asist = asistentes.get(i);
                
                // Check if asistente already exists in DB
                AsistenteArchivado asistenteArch = findOrCreateAsistente(em, asist);
                
                // Create archived registration
                RegistroArchivado regArch = new RegistroArchivado(
                    reg.getFecha(),
                    reg.getCosto(),
                    reg.isAsistio(),
                    reg.getTipoRegistro().getNombre()
                );
                regArch.setEdicion(edicionArch);
                regArch.setAsistente(asistenteArch);
                
                em.persist(regArch);
                edicionArch.addRegistro(regArch);
            }
            
            tx.commit();
            System.out.println("Edición archivada exitosamente: " + edicion.getNombre());
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error archivando edición: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al archivar edición", e);
        } finally {
            em.close();
        }
    }
    
    /**
     * Find or create organizer in DB
     */
    private static OrganizadorArchivado findOrCreateOrganizador(EntityManager em, Organizador org) {
        try {
            TypedQuery<OrganizadorArchivado> query = em.createQuery(
                "SELECT o FROM OrganizadorArchivado o WHERE o.nickname = :nickname",
                OrganizadorArchivado.class
            );
            query.setParameter("nickname", org.getNickname());
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Create new
            OrganizadorArchivado orgArch = new OrganizadorArchivado(
                org.getNickname(),
                org.getNombre(),
                org.getCorreo(),
                org.getImagen(),
                org.getDescripcion(),
                org.getSitioWeb()
            );
            em.persist(orgArch);
            return orgArch;
        }
    }
    
    /**
     * Find or create asistente in DB
     */
    private static AsistenteArchivado findOrCreateAsistente(EntityManager em, Asistente asist) {
        try {
            TypedQuery<AsistenteArchivado> query = em.createQuery(
                "SELECT a FROM AsistenteArchivado a WHERE a.nickname = :nickname",
                AsistenteArchivado.class
            );
            query.setParameter("nickname", asist.getNickname());
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Create new
            String nombreInstitucion = asist.getInstitucion() != null ? 
                asist.getInstitucion().getNombre() : null;
            
            AsistenteArchivado asistArch = new AsistenteArchivado(
                asist.getNickname(),
                asist.getNombre(),
                asist.getCorreo(),
                asist.getImagen(),
                asist.getApellido(),
                asist.getFechaNacimiento(),
                nombreInstitucion
            );
            em.persist(asistArch);
            return asistArch;
        }
    }
    
    /**
     * Get archived editions for a specific organizer
     */
    public static List<EdicionArchivada> getEdicionesArchivadasPorOrganizador(String nicknameOrganizador) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<EdicionArchivada> query = em.createQuery(
                "SELECT e FROM EdicionArchivada e WHERE e.organizador.nickname = :nickname ORDER BY e.fechaArchivo DESC",
                EdicionArchivada.class
            );
            query.setParameter("nickname", nicknameOrganizador);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Check if asistente has any registration in archived editions
     */
    public static boolean tieneRegistrosArchivados(String nicknameAsistente, String nombreEdicion) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM RegistroArchivado r WHERE r.asistente.nickname = :nickname AND r.edicion.nombre = :edicion",
                Long.class
            );
            query.setParameter("nickname", nicknameAsistente);
            query.setParameter("edicion", nombreEdicion);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    /**
     * Shutdown EntityManagerFactory
     */
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("JPA EntityManagerFactory closed");
        }
    }
}

