package logica.dto;


import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.LocalDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class AsistenteDTO extends UsuarioDTO {
   private String apellido;
   
   @XmlJavaTypeAdapter(LocalDateAdapter.class)
   private LocalDate fechaNacimiento;
   private InstitucionDTO institucion;
   private List registros;
   private int totalRegistros;

   public AsistenteDTO() {
   }

   public AsistenteDTO(String nickname, String nombre, String correo, String contrasenia, String imagen, String apellido, LocalDate fechaNacimiento, InstitucionDTO institucion) {
      super(nickname, nombre, correo, contrasenia, imagen, "Asistente");
      this.apellido = apellido;
      this.fechaNacimiento = fechaNacimiento;
      this.institucion = institucion;
   }

   public String getApellido() {
      return this.apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   public LocalDate getFechaNacimiento() {
      return this.fechaNacimiento;
   }

   public void setFechaNacimiento(LocalDate fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
   }

   public InstitucionDTO getInstitucion() {
      return this.institucion;
   }

   public void setInstitucion(InstitucionDTO institucion) {
      this.institucion = institucion;
   }

   public List getRegistros() {
      return this.registros;
   }

   public void setRegistros(List registros) {
      this.registros = registros;
      this.totalRegistros = registros != null ? registros.size() : 0;
   }

   public int getTotalRegistros() {
      return this.totalRegistros;
   }

   public void setTotalRegistros(int totalRegistros) {
      this.totalRegistros = totalRegistros;
   }

   public int getEdad() {
      return this.fechaNacimiento == null ? 0 : LocalDate.now().getYear() - this.fechaNacimiento.getYear();
   }

   public boolean esMayorDeEdad() {
      return this.getEdad() >= 18;
   }
}
