
package publicar.ws.client;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for institucionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="institucionDTO">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sitioWeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="totalUsuarios" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="totalPatrocinios" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "institucionDTO", propOrder = {
    "nombre",
    "descripcion",
    "sitioWeb",
    "fechaCreacion",
    "totalUsuarios",
    "totalPatrocinios"
})
public class InstitucionDTO {

    protected String nombre;
    protected String descripcion;
    protected String sitioWeb;
    protected String fechaCreacion;
    protected int totalUsuarios;
    protected int totalPatrocinios;

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the sitioWeb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitioWeb() {
        return sitioWeb;
    }

    /**
     * Sets the value of the sitioWeb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitioWeb(String value) {
        this.sitioWeb = value;
    }

    /**
     * Gets the value of the fechaCreacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Sets the value of the fechaCreacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCreacion(String value) {
        this.fechaCreacion = value;
    }

    /**
     * Gets the value of the totalUsuarios property.
     * 
     */
    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    /**
     * Sets the value of the totalUsuarios property.
     * 
     */
    public void setTotalUsuarios(int value) {
        this.totalUsuarios = value;
    }

    /**
     * Gets the value of the totalPatrocinios property.
     * 
     */
    public int getTotalPatrocinios() {
        return totalPatrocinios;
    }

    /**
     * Sets the value of the totalPatrocinios property.
     * 
     */
    public void setTotalPatrocinios(int value) {
        this.totalPatrocinios = value;
    }

}
