package util;

import logica.model.*;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;
import exception.BusinessException;
import logica.dto.CategoriaDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Utility class for loading CSV data into the application
 */
public class CSVLoader {
   
    /** Absolute path to the CSV folder */
    private static final String CSV_FOLDER_ABSOLUTE = "/ens/devel01/tpgr01/datos-prueba-tarea3-csv-v1_0";
    
    private final iControladorEventos controladorEventos;
    private final iControladorUsuarios controladorUsuarios;
    
    /**
     * Constructor with dependency injection
     */
    public CSVLoader(iControladorEventos controladorEventos, iControladorUsuarios controladorUsuarios) {
        this.controladorEventos = controladorEventos;
        this.controladorUsuarios = controladorUsuarios;
    }
    
    /**
     * Test if CSV folder is accessible
     * @return Diagnostic message
     */
    public String testCSVAccess() {
        StringBuilder result = new StringBuilder();
        result.append("Diagnóstico de acceso a CSV:\n");
        result.append("Working directory: ").append(System.getProperty("user.dir")).append("\n");
        result.append("Carpeta CSV (absoluta): ").append(CSV_FOLDER_ABSOLUTE).append("\n\n");
        
        String[] testFiles = {
            CSV_FOLDER_ABSOLUTE + "/2025Usuarios.csv",
            CSV_FOLDER_ABSOLUTE + "/2025Categorias.csv"
        };
        
        for (String testFile : testFiles) {
            result.append("Probando: ").append(testFile).append("\n");
            try {
                InputStream stream = abrirStreamCSV(testFile);
                if (stream != null) {
                    result.append("  ✓ ENCONTRADO\n");
                    stream.close();
                } else {
                    result.append("  ✗ NO ENCONTRADO\n");
                }
            } catch (Exception e) {
                result.append("  ✗ ERROR: ").append(e.getMessage()).append("\n");
            }
        }
        
        return result.toString();
    }
    
    /**
     * Cargar datos desde archivos CSV en resources
     * @return Resultado de la carga con detalles de éxito/errores
     */
    public String cargarDatosDesdeCSV() {
        StringBuilder resultado = new StringBuilder();
        int errores = 0;
        
        // Debug info
        System.out.println("=== INICIANDO CARGA DE CSV ===");
        System.out.println("Carpeta CSV (absoluta): " + CSV_FOLDER_ABSOLUTE);
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("===============================");
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Maps to store CSV data for efficient lookups
            Map<String, String[]> categoriasMap = new HashMap<>();
            Map<String, String[]> institucionesMap = new HashMap<>();
            Map<String, String[]> usuariosMap = new HashMap<>();
            Map<String, String[]> usuariosAsistentesMap = new HashMap<>();
            Map<String, String[]> usuariosOrganizadoresMap = new HashMap<>();
            Map<String, String[]> eventosMap = new HashMap<>();
            Map<String, String[]> edicionesMap = new HashMap<>();
            Map<String, String[]> tiposRegistroMap = new HashMap<>();
            Map<String, String[]> patrociniosMap = new HashMap<>();
            Map<String, String[]> registrosMap = new HashMap<>();
            Map<String, String[]> seguidoresMap = new HashMap<>();
            
            // Map to store eventos that need to be finalized (after all editions are loaded)
            Map<String, Integer> eventosAFinalizar = new HashMap<>();
            
            // Load all CSV files into maps for reference lookups
            try {
                categoriasMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Categorias.csv");
                institucionesMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Instituciones.csv");
                usuariosMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Usuarios.csv");
                usuariosAsistentesMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Usuarios-Asistentes.csv");
                usuariosOrganizadoresMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Usuarios-Organizadores.csv");
                eventosMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Eventos.csv");
                edicionesMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025EdicionesEventos.csv");
                tiposRegistroMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025TipoRegistro.csv");
                patrociniosMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Patrocinios.csv");
                registrosMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025Registros.csv");
                seguidoresMap = cargarCSVMap(CSV_FOLDER_ABSOLUTE + "/2025SeguidoresSeguidos.csv");
                
                resultado.append("✓ Archivos CSV cargados en memoria\n");
                
            } catch (Exception e) {
                resultado.append("✗ Error al cargar archivos CSV: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 1. Cargar Categorías
            try {
                int cargadas = 0;
                for (String[] cat : categoriasMap.values()) {
                    try {
                        controladorEventos.altaCategoria(cat[1].trim());
                        cargadas++;
                    } catch (Exception e) {
                        // Ignorar categorías duplicadas
                    }
                }
                resultado.append("✓ Categorías cargadas: ").append(cargadas).append(" de ").append(categoriasMap.size()).append("\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar categorías: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 2. Cargar Instituciones
            try {
                int cargadas = 0;
                for (String[] inst : institucionesMap.values()) {
                    try {
                        String nombre = inst[1].trim();
                        String descripcion = inst[2].trim();
                        String sitioWeb = inst[3].trim();
                        controladorEventos.altaInstitucion(nombre, descripcion, sitioWeb);
                        cargadas++;
                    } catch (Exception e) {
                        // Ignorar instituciones duplicadas
                    }
                }
                resultado.append("✓ Instituciones cargadas: ").append(cargadas).append(" de ").append(institucionesMap.size()).append("\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar instituciones: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 3. Cargar Usuarios base
            try {
                int cargados = 0;
                int asistentes = 0;
                int organizadores = 0;
                for (String[] usr : usuariosMap.values()) {
                    String ref = usr[0].trim();
                    String tipo = usr[1].trim();
                    String nickname = usr[2].trim();
                    String nombre = usr[3].trim();
                    String correo = usr[4].trim();
                    String contrasenia = usr[5].trim();
                    String imagen = usr[6].trim();
             
                    try {
                        if ("A".equals(tipo)) {
                            // Es asistente - buscar datos adicionales
                            String[] datosAsistente = usuariosAsistentesMap.get(ref);
                            
                            String apellido = datosAsistente[1].trim();
                            LocalDate fechaNac = LocalDate.parse(datosAsistente[2].trim(), formatter);
                            String instRef = "";
                            if (datosAsistente.length >= 4) {
                                instRef = datosAsistente[3].trim();
                            } else {
                                instRef = "";
                            }
                            
                            String inst = "-";
                            if (!instRef.isEmpty() && !"-".equals(instRef)) {
                                String[] instData = institucionesMap.get(instRef);
                                if (instData != null && instData.length > 1) {
                                    inst = instData[1].trim();
                                }
                            }
                            
                            if ("-".equals(inst)) {
                                controladorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNac);
                            } else {
                                controladorUsuarios.altaAsistente(nickname, nombre, correo, contrasenia, imagen, apellido, fechaNac, inst);
                            }
                            cargados++;
                            asistentes++;
                            
                        } else if ("O".equals(tipo)) {
                            // Es organizador - buscar datos adicionales
                            String[] datosOrg = usuariosOrganizadoresMap.get(ref);
                            String descripcion = "";
                            String sitioWeb = "";
                          
                            descripcion = datosOrg[1].trim();
                            if (datosOrg.length >= 3) {
                                sitioWeb = datosOrg[2].trim();
                            } else {
                                sitioWeb = "";
                            }
                            
                            controladorUsuarios.altaOrganizador(nickname, nombre, correo, contrasenia, imagen, descripcion, sitioWeb);
                            cargados++;
                            organizadores++;
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar usuario ").append(nickname).append(": ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                resultado.append("✓ Usuarios cargados: ").append(cargados).append(" de ").append(usuariosMap.size())
                    .append(" (").append(asistentes).append(" asistentes, ").append(organizadores).append(" organizadores)\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar usuarios: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 3.5. Cargar Seguidores/Seguidos relationships
            try {
                for (Map.Entry<String, String[]> entry : seguidoresMap.entrySet()) {
                    String[] datos = entry.getValue();
                    // CSV format: Ref;RefSeguidor;NickNameSeguidor;RefSeguido;NickNameSeguido
                    if (datos.length >= 5) {
                        String nicknameSeguidor = datos[2].trim();
                        String nicknameSeguido = datos[4].trim();
                        try {
                            controladorUsuarios.seguirUsuario(nicknameSeguidor, nicknameSeguido);
                        } catch (Exception e) {
                            // Ignore if already following or users don't exist
                        }
                    }
                }
                resultado.append("✓ Relaciones de seguidores cargadas\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar seguidores: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 4. Cargar Eventos
            try {
                for (String[] evt : eventosMap.values()) {
                    try {
                        String nombre = evt[1].trim();
                        String descripcion = evt[2].trim();
                        String sigla = evt[3].trim();
                        String fechaAltaStr = evt[4].trim();
                        String[] categoriaRefs = evt[5].trim().split(",");
                        String imagen = evt[6].trim();
                        String finalizado = evt.length > 7 ? evt[7].trim() : "No";
                        int visitas = evt.length > 8 ? Integer.parseInt(evt[8].trim()) : 0;
                        
                        // Parse fechaAlta from CSV
                        LocalDate fechaAlta = LocalDate.parse(fechaAltaStr, formatter);
                        
                        List<CategoriaDTO> listaCategorias = new ArrayList<>();
                        for (String catRef : categoriaRefs) {
                            String[] catData = categoriasMap.get(catRef.trim());
                            if (catData != null) {
                                listaCategorias.add(new CategoriaDTO(catData[1].trim()));
                            }
                        }
                        
                        // Create evento with fechaAlta
                        controladorEventos.altaEvento(nombre, sigla, descripcion, listaCategorias, imagen, fechaAlta);
                        
                        // Almacenar eventos que necesitan ser finalizados (se hará después de cargar ediciones)
                        if ("Si".equalsIgnoreCase(finalizado)) {
                            eventosAFinalizar.put(nombre, visitas);
                        } else {
                            // Establecer visitas inmediatamente si el evento NO está finalizado
                            if (visitas > 0) {
                                controladorEventos.setVisitasEvento(nombre, visitas);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar evento: ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                resultado.append("✓ Eventos cargados\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar eventos: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 5. Cargar Ediciones
            try {
                for (String[] ed : edicionesMap.values()) {
                    try {
                        String eventoRef = ed[1].trim();
                        String organizadorRef = ed[2].trim();
                        String nombre = ed[3].trim();
                        String sigla = ed[4].trim();
                        String ciudad = ed[5].trim();
                        String pais = ed[6].trim();
                        LocalDate fechaInicio = LocalDate.parse(ed[7].trim(), formatter);
                        LocalDate fechaFin = LocalDate.parse(ed[8].trim(), formatter);
                        LocalDate fechaAlta = LocalDate.parse(ed[9].trim(), formatter);
                        String estado = ed[10].trim();
                        String imagen = ed[11].trim();
                        
                        String[] eventoData = eventosMap.get(eventoRef);
                        if (eventoData != null && eventoData.length >= 2) {
                            String nombreEvento = eventoData[1].trim();
                            
                            String[] orgData = usuariosMap.get(organizadorRef);
                            if (orgData != null && orgData.length >= 3) {
                                String nicknameOrganizador = orgData[2].trim();
                                
                                // Alta edición con estado
                                controladorEventos.altaEdicionEvento(nombre, sigla, fechaInicio, fechaFin, fechaAlta, 
                                                                     nicknameOrganizador, ciudad, pais, nombreEvento, imagen, estado);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar edición: ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                resultado.append("✓ Ediciones cargadas\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar ediciones: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 6. Cargar TipoRegistro
            try {
                for (String[] tr : tiposRegistroMap.values()) {
                    try {
                        String edicionRef = tr[1].trim();
                        String nombre = tr[2].trim();
                        String descripcion = tr[3].trim();
                        BigDecimal costo = new BigDecimal(tr[4].trim());
                        int cupo = Integer.parseInt(tr[5].trim());
                        
                        String[] edicionData = edicionesMap.get(edicionRef);
                        if (edicionData != null && edicionData.length >= 4) {
                            String nombreEdicion = edicionData[3].trim();
                            String eventoRef = edicionData[1].trim();
                            String[] eventoData = eventosMap.get(eventoRef);
                            if (eventoData != null && eventoData.length >= 2) {
                                String nombreEvento = eventoData[1].trim();
                                controladorEventos.altaTipoRegistro(nombreEvento, nombreEdicion, nombre, descripcion, costo, cupo);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar tipo de registro: ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                resultado.append("✓ Tipos de registro cargados\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar tipos de registro: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 7. Cargar Patrocinios
            try {
                for (String[] pat : patrociniosMap.values()) {
                    try {
                        String edicionRef = pat[1].trim();
                        String institucionRef = pat[2].trim();
                        String nivel = pat[3].trim();
                        String tipoRegistroRef = pat[4].trim();
                        BigDecimal aporte = new BigDecimal(pat[5].trim());
                        int cantidadRegistros = Integer.parseInt(pat[7].trim());
                        String codigoPatrocinio = pat[8].trim();
                        
                        String[] edicionData = edicionesMap.get(edicionRef);
                        String[] institucionData = institucionesMap.get(institucionRef);
                        String[] tipoRegistroData = tiposRegistroMap.get(tipoRegistroRef);
                        
                        if (edicionData != null && edicionData.length >= 4 &&
                            institucionData != null && institucionData.length >= 2 &&
                            tipoRegistroData != null && tipoRegistroData.length >= 3) {
                            
                            String nombreEdicion = edicionData[3].trim();
                            String nombreInstitucion = institucionData[1].trim();
                            String nombreTipoRegistro = tipoRegistroData[2].trim();
                            
                            String eventoRef = edicionData[1].trim();
                            String[] eventoData = eventosMap.get(eventoRef);
                            if (eventoData != null && eventoData.length >= 2) {
                                String nombreEvento = eventoData[1].trim();
                                
                                controladorEventos.altaPatrocinio(nombreEvento, nombreEdicion, nombreTipoRegistro,
                                                                 nombreInstitucion, nivel, aporte, cantidadRegistros, codigoPatrocinio);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar patrocinio: ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                resultado.append("✓ Patrocinios cargados\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar patrocinios: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 8. Cargar Registros
            try {
                for (String[] reg : registrosMap.values()) {
                    try {
                        String usuarioRef = reg[1].trim();
                        String edicionRef = reg[2].trim();
                        String tipoRegistroRef = reg[3].trim();
                        String asiste = reg[7].trim();
                        
                        Boolean asistio = "S".equalsIgnoreCase(asiste);
                        
                        String[] usuarioData = usuariosMap.get(usuarioRef);
                        String[] edicionData = edicionesMap.get(edicionRef);
                        String[] tipoRegistroData = tiposRegistroMap.get(tipoRegistroRef);
                        
                        if (usuarioData != null && usuarioData.length >= 3 &&
                            edicionData != null && edicionData.length >= 4 &&
                            tipoRegistroData != null && tipoRegistroData.length >= 3) {
                            
                            String nicknameUsuario = usuarioData[2].trim();
                            String nombreEdicion = edicionData[3].trim();
                            String nombreTipoRegistro = tipoRegistroData[2].trim();
                            
                            String tipoUsuario = usuarioData[1].trim();
                            if ("A".equals(tipoUsuario)) {
                                controladorEventos.registrarAsistenteConAsistente(nicknameUsuario, nombreTipoRegistro, 
                                                                                  nombreEdicion, asistio);
                            }
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al cargar registro: ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                resultado.append("✓ Registros cargados\n");
            } catch (Exception e) {
                resultado.append("✗ Error al cargar registros: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // 9. Finalizar eventos y establecer visitas (después de cargar todas las ediciones)
            try {
                for (Map.Entry<String, Integer> entry : eventosAFinalizar.entrySet()) {
                    String nombreEvento = entry.getKey();
                    int visitas = entry.getValue();
                    
                    try {
                        // Finalizar el evento
                        controladorEventos.finalizarEvento(nombreEvento);
                        
                        // Establecer visitas
                        if (visitas > 0) {
                            controladorEventos.setVisitasEvento(nombreEvento, visitas);
                        }
                    } catch (Exception e) {
                        resultado.append("✗ Error al finalizar evento ").append(nombreEvento).append(": ").append(e.getMessage()).append("\n");
                        errores++;
                    }
                }
                if (!eventosAFinalizar.isEmpty()) {
                    resultado.append("✓ Eventos finalizados\n");
                }
            } catch (Exception e) {
                resultado.append("✗ Error al finalizar eventos: ").append(e.getMessage()).append("\n");
                errores++;
            }
            
            // Resultado final
            if (errores == 0) {
                return "✅ Todos los datos cargados exitosamente:\n\n" + resultado.toString();
            } else {
                return "⚠️ Datos cargados con " + errores + " errores:\n\n" + resultado.toString();
            }
            
        } catch (Exception e) {
            return "❌ Error general al cargar datos: " + e.getMessage();
        }
    }
    
    /**
     * Cargar archivo CSV desde resources y retornar como Map
     */
    private Map<String, String[]> cargarCSVMap(String rutaArchivo) throws IOException {
        Map<String, String[]> mapa = new HashMap<>();
        
        InputStream inputStream = abrirStreamCSV(rutaArchivo);
        if (inputStream == null) {
            // Try to provide helpful debugging info
            String workingDir = System.getProperty("user.dir");
            StringBuilder paths = new StringBuilder();
            paths.append("\nArchivo no encontrado: ").append(rutaArchivo);
            paths.append("\nDirectorio de trabajo: ").append(workingDir);
            paths.append("\n\nIntentó buscar en:");
            paths.append("\n  1. Classpath: ").append(rutaArchivo);
            paths.append("\n  2. Classpath: resources/").append(rutaArchivo);
            paths.append("\n  3. Filesystem: src/main/resources/").append(rutaArchivo);
            paths.append("\n  4. Filesystem: tarea1/src/main/resources/").append(rutaArchivo);
            paths.append("\n  5. Filesystem: ").append(rutaArchivo);
            paths.append("\n  6. Filesystem: ").append(workingDir).append("/tarea1/src/main/resources/").append(rutaArchivo);
            paths.append("\n  7. Filesystem: tarea1\\src\\main\\resources\\").append(rutaArchivo.replace("/", "\\"));
            throw new IOException(paths.toString());
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea;
            int lineCount = 0;
            while ((linea = br.readLine()) != null) {
                lineCount++;
                String[] valores = linea.split(";");
                if (valores.length > 0 && !valores[0].equals("Ref")) {
                    mapa.put(valores[0].trim(), valores);
                }
            }
            // Successfully loaded
            System.out.println("✓ Cargado: " + rutaArchivo + " (" + mapa.size() + " registros de " + lineCount + " líneas)");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        
        return mapa;
    }
    
    /**
     * Intentar abrir un recurso CSV considerando distintas ubicaciones posibles.
     * Prioriza rutas absolutas del sistema de archivos.
     */
    private InputStream abrirStreamCSV(String rutaArchivo) throws IOException {
        java.nio.file.Path filePath = java.nio.file.Paths.get(rutaArchivo);
        
        // Try 1: If path is absolute, use it directly
        if (filePath.isAbsolute()) {
            filePath = filePath.toAbsolutePath().normalize();
            if (java.nio.file.Files.exists(filePath)) {
                return java.nio.file.Files.newInputStream(filePath);
            }
        } else {
            // Try 2: Convert to absolute path based on current working directory
            filePath = java.nio.file.Paths.get(System.getProperty("user.dir"), rutaArchivo).toAbsolutePath().normalize();
            if (java.nio.file.Files.exists(filePath)) {
                return java.nio.file.Files.newInputStream(filePath);
            }
        }
        
        // Try 3: Direct resource path from classpath (fallback)
        InputStream stream = getClass().getClassLoader().getResourceAsStream(rutaArchivo);
        
        // Try 4: With resources/ prefix (fallback)
        if (stream == null) {
            stream = getClass().getClassLoader().getResourceAsStream("resources/" + rutaArchivo);
        }
        
        // Try 5: From filesystem - relative to tarea1 module (fallback)
        if (stream == null) {
            java.nio.file.Path path = java.nio.file.Paths.get("src/main/resources", rutaArchivo);
            if (java.nio.file.Files.exists(path)) {
                stream = java.nio.file.Files.newInputStream(path);
            }
        }
        
        // Try 6: From filesystem - relative to current directory (fallback)
        if (stream == null) {
            java.nio.file.Path path = java.nio.file.Paths.get("tarea1/src/main/resources", rutaArchivo);
            if (java.nio.file.Files.exists(path)) {
                stream = java.nio.file.Files.newInputStream(path);
            }
        }
        
        // Try 7: From working directory with tarea1 subfolder (fallback)
        if (stream == null) {
            String workingDir = System.getProperty("user.dir");
            java.nio.file.Path path = java.nio.file.Paths.get(workingDir, "tarea1", "src", "main", "resources", rutaArchivo);
            if (java.nio.file.Files.exists(path)) {
                stream = java.nio.file.Files.newInputStream(path);
            }
        }
        
        return stream;
    }
}
