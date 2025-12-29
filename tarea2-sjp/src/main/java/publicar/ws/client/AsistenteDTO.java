
package publicar.ws.client;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for asistenteDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="asistenteDTO">
 *   <complexContent>
 *     <extension base="{http://publicar/}usuarioDTO">
 *       <sequence>
 *         <element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="institucion" type="{http://publicar/}institucionDTO" minOccurs="0"/>
 *         <element name="registros" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="totalRegistros" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asistenteDTO", propOrder = {
    "apellido",
    "fechaNacimiento",
    "institucion",
    "registros",
    "totalRegistros"
})
public class AsistenteDTO
    extends UsuarioDTO
{

    protected String apellido;
    protected String fechaNacimiento;
    protected InstitucionDTO institucion;
    @XmlElement(nillable = true)
    protected List<Object> registros;
    protected int totalRegistros;

    /**
     * Gets the value of the apellido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Sets the value of the apellido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido(String value) {
        this.apellido = value;
    }

    /**
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaNacimiento(String value) {
        this.fechaNacimiento = value;
    }

    /**
     * Gets the value of the institucion property.
     * 
     * @return
     *     possible object is
     *     {@link InstitucionDTO }
     *     
     */
    public InstitucionDTO getInstitucion() {
        return institucion;
    }

    /**
     * Sets the value of the institucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstitucionDTO }
     *     
     */
    public void setInstitucion(InstitucionDTO value) {
        this.institucion = value;
    }

    /**
     * Gets the value of the registros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the registros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     * @return
     *     The value of the registros property.
     */
    public List<Object> getRegistros() {
        if (registros == null) {
            registros = new ArrayList<>();
        }
        return this.registros;
    }

    /**
     * Gets the value of the totalRegistros property.
     * 
     */
    public int getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * Sets the value of the totalRegistros property.
     * 
     */
    public void setTotalRegistros(int value) {
        this.totalRegistros = value;
    }

}
