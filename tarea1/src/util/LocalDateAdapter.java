package util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * JAXB adapter to serialize/deserialize java.time.LocalDate as ISO-8601 String (YYYY-MM-DD)
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    
    @Override
    public LocalDate unmarshal(String v) {
        if (v == null || v.isEmpty()) {
            return null;
        }
        return LocalDate.parse(v);
    }
    
    @Override
    public String marshal(LocalDate v) {
        if (v == null) {
            return null;
        }
        return v.toString();
    }
}






