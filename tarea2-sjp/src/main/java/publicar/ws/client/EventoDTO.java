
package publicar.ws.client;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="eventoDTO">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="categorias" type="{http://publicar/}categoriaDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="ediciones" type="{http://publicar/}edicionDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="imagen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="finalizado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="visitas" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventoDTO", propOrder = {
    "nombre",
    "sigla",
    "descripcion",
    "fechaAlta",
    "categorias",
    "ediciones",
    "imagen",
    "finalizado",
    "visitas"
})
public class EventoDTO {

    protected String nombre;
    protected String sigla;
    protected String descripcion;
    protected String fechaAlta;
    @XmlElement(nillable = true)
    protected List<CategoriaDTO> categorias;
    @XmlElement(nillable = true)
    protected List<EdicionDTO> ediciones;
    protected String imagen;
    protected boolean finalizado;
    protected int visitas;

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
     * Gets the value of the sigla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Sets the value of the sigla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigla(String value) {
        this.sigla = value;
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
     * Gets the value of the fechaAlta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Sets the value of the fechaAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaAlta(String value) {
        this.fechaAlta = value;
    }

    /**
     * Gets the value of the categorias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the categorias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategorias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoriaDTO }
     * 
     * 
     * @return
     *     The value of the categorias property.
     */
    public List<CategoriaDTO> getCategorias() {
        if (categorias == null) {
            categorias = new ArrayList<>();
        }
        return this.categorias;
    }

    /**
     * Gets the value of the ediciones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the ediciones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEdiciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EdicionDTO }
     * 
     * 
     * @return
     *     The value of the ediciones property.
     */
    public List<EdicionDTO> getEdiciones() {
        if (ediciones == null) {
            ediciones = new ArrayList<>();
        }
        return this.ediciones;
    }

    /**
     * Gets the value of the imagen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Sets the value of the imagen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagen(String value) {
        this.imagen = value;
    }

    /**
     * Gets the value of the finalizado property.
     * 
     */
    public boolean isFinalizado() {
        return finalizado;
    }

    /**
     * Sets the value of the finalizado property.
     * 
     */
    public void setFinalizado(boolean value) {
        this.finalizado = value;
    }

    /**
     * Gets the value of the visitas property.
     * 
     */
    public int getVisitas() {
        return visitas;
    }

    /**
     * Sets the value of the visitas property.
     * 
     */
    public void setVisitas(int value) {
        this.visitas = value;
    }

}
