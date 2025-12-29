
package publicar.ws.client;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registroDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="registroDTO">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="iden" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="asistente" type="{http://publicar/}asistenteDTO" minOccurs="0"/>
 *         <element name="edicion" type="{http://publicar/}edicionDTO" minOccurs="0"/>
 *         <element name="nombreEdicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="tipoRegistro" type="{http://publicar/}tipoRegistroDTO" minOccurs="0"/>
 *         <element name="fechaRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="costo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         <element name="asistio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroDTO", propOrder = {
    "iden",
    "asistente",
    "edicion",
    "nombreEdicion",
    "tipoRegistro",
    "fechaRegistro",
    "costo",
    "asistio"
})
public class RegistroDTO {

    protected String iden;
    protected AsistenteDTO asistente;
    protected EdicionDTO edicion;
    protected String nombreEdicion;
    protected TipoRegistroDTO tipoRegistro;
    protected String fechaRegistro;
    protected BigDecimal costo;
    protected boolean asistio;

    /**
     * Gets the value of the iden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIden() {
        return iden;
    }

    /**
     * Sets the value of the iden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIden(String value) {
        this.iden = value;
    }

    /**
     * Gets the value of the asistente property.
     * 
     * @return
     *     possible object is
     *     {@link AsistenteDTO }
     *     
     */
    public AsistenteDTO getAsistente() {
        return asistente;
    }

    /**
     * Sets the value of the asistente property.
     * 
     * @param value
     *     allowed object is
     *     {@link AsistenteDTO }
     *     
     */
    public void setAsistente(AsistenteDTO value) {
        this.asistente = value;
    }

    /**
     * Gets the value of the edicion property.
     * 
     * @return
     *     possible object is
     *     {@link EdicionDTO }
     *     
     */
    public EdicionDTO getEdicion() {
        return edicion;
    }

    /**
     * Sets the value of the edicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link EdicionDTO }
     *     
     */
    public void setEdicion(EdicionDTO value) {
        this.edicion = value;
    }

    /**
     * Gets the value of the nombreEdicion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreEdicion() {
        return nombreEdicion;
    }

    /**
     * Sets the value of the nombreEdicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreEdicion(String value) {
        this.nombreEdicion = value;
    }

    /**
     * Gets the value of the tipoRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link TipoRegistroDTO }
     *     
     */
    public TipoRegistroDTO getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Sets the value of the tipoRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoRegistroDTO }
     *     
     */
    public void setTipoRegistro(TipoRegistroDTO value) {
        this.tipoRegistro = value;
    }

    /**
     * Gets the value of the fechaRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Sets the value of the fechaRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaRegistro(String value) {
        this.fechaRegistro = value;
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
     * Gets the value of the asistio property.
     * 
     */
    public boolean isAsistio() {
        return asistio;
    }

    /**
     * Sets the value of the asistio property.
     * 
     */
    public void setAsistio(boolean value) {
        this.asistio = value;
    }

}
