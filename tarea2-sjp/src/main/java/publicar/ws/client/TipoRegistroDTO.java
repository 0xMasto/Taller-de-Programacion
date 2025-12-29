
package publicar.ws.client;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoRegistroDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="tipoRegistroDTO">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         <element name="cupo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="cupoDisponible" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRegistroDTO", propOrder = {
    "nombre",
    "descripcion",
    "costo",
    "cupo",
    "cupoDisponible"
})
public class TipoRegistroDTO {

    protected String nombre;
    protected String descripcion;
    protected BigDecimal costo;
    protected int cupo;
    protected int cupoDisponible;

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
     * Gets the value of the costo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCosto() {
        return costo;
    }

    /**
     * Sets the value of the costo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCosto(BigDecimal value) {
        this.costo = value;
    }

    /**
     * Gets the value of the cupo property.
     * 
     */
    public int getCupo() {
        return cupo;
    }

    /**
     * Sets the value of the cupo property.
     * 
     */
    public void setCupo(int value) {
        this.cupo = value;
    }

    /**
     * Gets the value of the cupoDisponible property.
     * 
     */
    public int getCupoDisponible() {
        return cupoDisponible;
    }

    /**
     * Sets the value of the cupoDisponible property.
     * 
     */
    public void setCupoDisponible(int value) {
        this.cupoDisponible = value;
    }

}
