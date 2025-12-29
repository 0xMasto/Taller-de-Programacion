
package publicar.ws.client;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for organizadorDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="organizadorDTO">
 *   <complexContent>
 *     <extension base="{http://publicar/}usuarioDTO">
 *       <sequence>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sitioWeb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="eventosOrganizados" type="{http://publicar/}eventoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="edicionesOrganizadas" type="{http://publicar/}edicionDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </extension>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "organizadorDTO", propOrder = {
    "descripcion",
    "sitioWeb",
    "eventosOrganizados",
    "edicionesOrganizadas"
})
public class OrganizadorDTO
    extends UsuarioDTO
{

    protected String descripcion;
    protected String sitioWeb;
    @XmlElement(nillable = true)
    protected List<EventoDTO> eventosOrganizados;
    @XmlElement(nillable = true)
    protected List<EdicionDTO> edicionesOrganizadas;

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
     * Gets the value of the eventosOrganizados property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the eventosOrganizados property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventosOrganizados().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventoDTO }
     * 
     * 
     * @return
     *     The value of the eventosOrganizados property.
     */
    public List<EventoDTO> getEventosOrganizados() {
        if (eventosOrganizados == null) {
            eventosOrganizados = new ArrayList<>();
        }
        return this.eventosOrganizados;
    }

    /**
     * Gets the value of the edicionesOrganizadas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the edicionesOrganizadas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEdicionesOrganizadas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EdicionDTO }
     * 
     * 
     * @return
     *     The value of the edicionesOrganizadas property.
     */
    public List<EdicionDTO> getEdicionesOrganizadas() {
        if (edicionesOrganizadas == null) {
            edicionesOrganizadas = new ArrayList<>();
        }
        return this.edicionesOrganizadas;
    }

}
