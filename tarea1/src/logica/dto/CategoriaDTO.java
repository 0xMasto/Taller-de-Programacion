package logica.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaDTO {
   private String nombre;

   // Constructor por defecto para serialización
   public CategoriaDTO() {}
   
   public CategoriaDTO(String nombre) {
       this.nombre = nombre;
   }
   
   // Getters y Setters
   public String getNombre() {
       return nombre;
   }
   
   @Override
   public String toString() {
       return nombre;
   }
}

