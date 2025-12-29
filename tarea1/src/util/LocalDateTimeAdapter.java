package util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter to serialize/deserialize java.time.LocalDateTime as ISO-8601 String
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    @Override
    public LocalDateTime unmarshal(String v) {
        if (v == null || v.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(v, FORMATTER);
    }
    
    @Override
    public String marshal(LocalDateTime v) {
        if (v == null) {
            return null;
        }
        return v.format(FORMATTER);
    }
}






