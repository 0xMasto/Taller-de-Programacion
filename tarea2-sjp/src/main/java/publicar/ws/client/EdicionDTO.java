
package publicar.ws.client;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for edicionDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="edicionDTO">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="sigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaInicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaFin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="organizador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="pais" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="evento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="tiposRegistro" type="{http://publicar/}tipoRegistroDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="patrocinios" type="{http://publicar/}patrocinioDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="totalRegistros" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="cupoTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="imagen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="videoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="archivada" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         <element name="fechaArchivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "edicionDTO", propOrder = {
    "nombre",
    "sigla",
    "descripcion",
    "fechaInicio",
    "fechaFin",
    "fechaAlta",
    "organizador",
    "pais",
    "ciudad",
    "evento",
    "fechaCreacion",
    "estado",
    "tiposRegistro",
    "patrocinios",
    "totalRegistros",
    "cupoTotal",
    "imagen",
    "videoUrl",
    "archivada",
    "fechaArchivo"
})
public class EdicionDTO {

    protected String nombre;
    protected String sigla;
    protected String descripcion;
    protected String fechaInicio;
    protected String fechaFin;
    protected String fechaAlta;
    protected String organizador;
    protected String pais;
    protected String ciudad;
    protected String evento;
    protected String fechaCreacion;
    protected String estado;
    @XmlElement(nillable = true)
    protected List<TipoRegistroDTO> tiposRegistro;
    @XmlElement(nillable = true)
    protected List<PatrocinioDTO> patrocinios;
    protected int totalRegistros;
    protected int cupoTotal;
    protected String imagen;
    protected String videoUrl;
    protected boolean archivada;
    protected String fechaArchivo;

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
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInicio(String value) {
        this.fechaInicio = value;
    }

    /**
     * Gets the value of the fechaFin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Sets the value of the fechaFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaFin(String value) {
        this.fechaFin = value;
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
     * Gets the value of the organizador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizador() {
        return organizador;
    }

    /**
     * Sets the value of the organizador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizador(String value) {
        this.organizador = value;
    }

    /**
     * Gets the value of the pais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPais() {
        return pais;
    }

    /**
     * Sets the value of the pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPais(String value) {
        this.pais = value;
    }

    /**
     * Gets the value of the ciudad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Sets the value of the ciudad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCiudad(String value) {
        this.ciudad = value;
    }

    /**
     * Gets the value of the evento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvento() {
        return evento;
    }

    /**
     * Sets the value of the evento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvento(String value) {
        this.evento = value;
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
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the tiposRegistro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the tiposRegistro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTiposRegistro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoRegistroDTO }
     * 
     * 
     * @return
     *     The value of the tiposRegistro property.
     */
    public List<TipoRegistroDTO> getTiposRegistro() {
        if (tiposRegistro == null) {
            tiposRegistro = new ArrayList<>();
        }
        return this.tiposRegistro;
    }

    /**
     * Gets the value of the patrocinios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the patrocinios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPatrocinios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PatrocinioDTO }
     * 
     * 
     * @return
     *     The value of the patrocinios property.
     */
    public List<PatrocinioDTO> getPatrocinios() {
        if (patrocinios == null) {
            patrocinios = new ArrayList<>();
        }
        return this.patrocinios;
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

    /**
     * Gets the value of the cupoTotal property.
     * 
     */
    public int getCupoTotal() {
        return cupoTotal;
    }

    /**
     * Sets the value of the cupoTotal property.
     * 
     */
    public void setCupoTotal(int value) {
        this.cupoTotal = value;
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
     * Gets the value of the videoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * Sets the value of the videoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoUrl(String value) {
        this.videoUrl = value;
    }

    /**
     * Gets the value of the archivada property.
     * 
     */
    public boolean isArchivada() {
        return archivada;
    }

    /**
     * Sets the value of the archivada property.
     * 
     */
    public void setArchivada(boolean value) {
        this.archivada = value;
    }

    /**
     * Gets the value of the fechaArchivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaArchivo() {
        return fechaArchivo;
    }

    /**
     * Sets the value of the fechaArchivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaArchivo(String value) {
        this.fechaArchivo = value;
    }

}
