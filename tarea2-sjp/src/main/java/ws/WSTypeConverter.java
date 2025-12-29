package ws;

import publicar.ws.client.*;
import net.java.dev.jaxb.array.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility class to convert between WS-generated types and Java standard types
 */
public class WSTypeConverter {
    
    // ==================== Array Conversions ====================
    
    public static String[] toStringArray(StringArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new String[0];
        }
        List<String> items = wsArray.getItem();
        return items.toArray(new String[0]);
    }
    
    public static List<String> toStringList(StringArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static EventoDTO[] toEventoDTOArray(EventoDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new EventoDTO[0];
        }
        List<EventoDTO> items = wsArray.getItem();
        return items.toArray(new EventoDTO[0]);
    }
    
    public static List<EventoDTO> toEventoDTOList(EventoDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static EdicionDTO[] toEdicionDTOArray(EdicionDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new EdicionDTO[0];
        }
        List<EdicionDTO> items = wsArray.getItem();
        return items.toArray(new EdicionDTO[0]);
    }
    
    public static List<EdicionDTO> toEdicionDTOList(EdicionDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static RegistroDTO[] toRegistroDTOArray(RegistroDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new RegistroDTO[0];
        }
        List<RegistroDTO> items = wsArray.getItem();
        return items.toArray(new RegistroDTO[0]);
    }
    
    public static List<RegistroDTO> toRegistroDTOList(RegistroDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static CategoriaDTO[] toCategoriaDTOArray(CategoriaDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new CategoriaDTO[0];
        }
        List<CategoriaDTO> items = wsArray.getItem();
        return items.toArray(new CategoriaDTO[0]);
    }
    
    public static List<CategoriaDTO> toCategoriaDTOList(CategoriaDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static AsistenteDTO[] toAsistenteDTOArray(AsistenteDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new AsistenteDTO[0];
        }
        List<AsistenteDTO> items = wsArray.getItem();
        return items.toArray(new AsistenteDTO[0]);
    }
    
    public static List<AsistenteDTO> toAsistenteDTOList(AsistenteDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static OrganizadorDTO[] toOrganizadorDTOArray(OrganizadorDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new OrganizadorDTO[0];
        }
        List<OrganizadorDTO> items = wsArray.getItem();
        return items.toArray(new OrganizadorDTO[0]);
    }
    
    public static List<OrganizadorDTO> toOrganizadorDTOList(OrganizadorDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static TipoRegistroDTO[] toTipoRegistroDTOArray(TipoRegistroDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new TipoRegistroDTO[0];
        }
        List<TipoRegistroDTO> items = wsArray.getItem();
        return items.toArray(new TipoRegistroDTO[0]);
    }
    
    public static List<TipoRegistroDTO> toTipoRegistroDTOList(TipoRegistroDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    public static PatrocinioDTO[] toPatrocinioDTOArray(PatrocinioDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new PatrocinioDTO[0];
        }
        List<PatrocinioDTO> items = wsArray.getItem();
        return items.toArray(new PatrocinioDTO[0]);
    }
    
    public static List<PatrocinioDTO> toPatrocinioDTOList(PatrocinioDTOArray wsArray) {
        if (wsArray == null || wsArray.getItem() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(wsArray.getItem());
    }
    
    // ==================== LocalDate Conversions ====================
    
    /**
     * Parse ISO date string to LocalDate
     */
    public static java.time.LocalDate parseDate(String isoDateString) {
        if (isoDateString == null || isoDateString.isEmpty()) return null;
        try {
            return java.time.LocalDate.parse(isoDateString);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Convert java.time.LocalDate to String for WS calls (ISO format: YYYY-MM-DD)
     */
    public static String localDateToString(java.time.LocalDate date) {
        if (date == null) return null;
        return date.toString(); // Already in ISO format
    }
    
    /**
     * Parse String date (from DTO) and format for display
     * DTOs store dates as String in ISO format (YYYY-MM-DD)
     */
    public static String formatDateString(String isoDateString, String pattern) {
        if (isoDateString == null || isoDateString.isEmpty()) return "";
        try {
            java.time.LocalDate date = java.time.LocalDate.parse(isoDateString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return date.format(formatter);
        } catch (Exception e) {
            return isoDateString; // Return as-is if parsing fails
        }
    }
    
    /**
     * Format ISO date string for display (dd/MM/yyyy)
     */
    public static String formatDateString(String isoDateString) {
        return formatDateString(isoDateString, "dd/MM/yyyy");
    }
    
    // ==================== BigDecimal Conversions ====================
    
    /**
     * Convert BigDecimal to String for WS calls
     */
    public static String bigDecimalToString(java.math.BigDecimal value) {
        if (value == null) return "0";
        return value.toString();
    }
    
    /**
     * Convert String to BigDecimal
     */
    public static java.math.BigDecimal stringToBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) return java.math.BigDecimal.ZERO;
        return new java.math.BigDecimal(value);
    }
    
    // ==================== DTO Helper Methods ====================
    
    /**
     * Create CategoriaDTO from name (helper for creation)
     */
    public static CategoriaDTO createCategoriaDTO(String nombre) {
        CategoriaDTO cat = new CategoriaDTO();
        cat.setNombre(nombre);
        return cat;
    }
    
    /**
     * Convert List<CategoriaDTO> to CategoriaDTOArray for WS calls
     */
    public static CategoriaDTOArray toCategoriaDTOArray(List<CategoriaDTO> list) {
        CategoriaDTOArray array = new CategoriaDTOArray();
        if (list != null) {
            array.getItem().addAll(list);
        }
        return array;
    }
    
    /**
     * Check if date string (ISO format) is before another date
     */
    public static boolean isBeforeDateString(String isoDateString, java.time.LocalDate date2) {
        if (isoDateString == null || isoDateString.isEmpty() || date2 == null) return false;
        try {
            java.time.LocalDate date1 = java.time.LocalDate.parse(isoDateString);
            return date1.isBefore(date2);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if EdicionDTO is active (fechaFin >= today)
     * Note: Dates in DTOs are stored as String in ISO format
     */
    public static boolean esActiva(EdicionDTO edicion) {
        if (edicion == null) return false;
        java.time.LocalDate fechaFin = parseDate(edicion.getFechaFin());
        if (fechaFin == null) return false;
        return !fechaFin.isBefore(java.time.LocalDate.now());
    }
    
    /**
     * Check if EdicionDTO is future (fechaInicio > today)
     */
    public static boolean esFutura(EdicionDTO edicion) {
        if (edicion == null) return false;
        java.time.LocalDate fechaInicio = parseDate(edicion.getFechaInicio());
        if (fechaInicio == null) return false;
        return fechaInicio.isAfter(java.time.LocalDate.now());
    }
    
    /**
     * Check if EdicionDTO is past (fechaFin < today)
     */
    public static boolean esPasada(EdicionDTO edicion) {
        if (edicion == null) return false;
        java.time.LocalDate fechaFin = parseDate(edicion.getFechaFin());
        if (fechaFin == null) return false;
        return fechaFin.isBefore(java.time.LocalDate.now());
    }
    
    /**
     * Check if EdicionDTO is in progress (fechaInicio <= today <= fechaFin)
     */
    public static boolean estaEnCurso(EdicionDTO edicion) {
        if (edicion == null) return false;
        java.time.LocalDate now = java.time.LocalDate.now();
        java.time.LocalDate fechaInicio = parseDate(edicion.getFechaInicio());
        java.time.LocalDate fechaFin = parseDate(edicion.getFechaFin());
        if (fechaInicio == null || fechaFin == null) return false;
        return !fechaInicio.isAfter(now) && !fechaFin.isBefore(now);
    }
}

