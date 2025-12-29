
package publicar.ws.client;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patrocinioDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>{@code
 * <complexType name="patrocinioDTO">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="iden" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="evento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="edicion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="tipoRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="institucion" type="{http://publicar/}institucionDTO" minOccurs="0"/>
 *         <element name="nivelPatrocinio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="aporteEconomico" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         <element name="cantidadRegistros" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="codigoPatrocinio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="fechaCreacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="costoRegistros" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         <element name="porcentajeAporte" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patrocinioDTO", propOrder = {
    "iden",
    "evento",
    "edicion",
    "tipoRegistro",
    "institucion",
    "nivelPatrocinio",
    "aporteEconomico",
    "cantidadRegistros",
    "codigoPatrocinio",
    "fechaCreacion",
    "costoRegistros",
    "porcentajeAporte"
})
public class PatrocinioDTO {

    protected String iden;
    protected String evento;
    protected String edicion;
    protected String tipoRegistro;
    protected InstitucionDTO institucion;
    protected String nivelPatrocinio;
    protected BigDecimal aporteEconomico;
    protected int cantidadRegistros;
    protected String codigoPatrocinio;
    protected String fechaCreacion;
    protected BigDecimal costoRegistros;
    protected BigDecimal porcentajeAporte;

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
     * Gets the value of the edicion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdicion() {
        return edicion;
    }

    /**
     * Sets the value of the edicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdicion(String value) {
        this.edicion = value;
    }

    /**
     * Gets the value of the tipoRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Sets the value of the tipoRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistro(String value) {
        this.tipoRegistro = value;
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
     * Gets the value of the nivelPatrocinio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNivelPatrocinio() {
        return nivelPatrocinio;
    }

    /**
     * Sets the value of the nivelPatrocinio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNivelPatrocinio(String value) {
        this.nivelPatrocinio = value;
    }

    /**
     * Gets the value of the aporteEconomico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAporteEconomico() {
        return aporteEconomico;
    }

    /**
     * Sets the value of the aporteEconomico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAporteEconomico(BigDecimal value) {
        this.aporteEconomico = value;
    }

    /**
     * Gets the value of the cantidadRegistros property.
     * 
     */
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    /**
     * Sets the value of the cantidadRegistros property.
     * 
     */
    public void setCantidadRegistros(int value) {
        this.cantidadRegistros = value;
    }

    /**
     * Gets the value of the codigoPatrocinio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPatrocinio() {
        return codigoPatrocinio;
    }

    /**
     * Sets the value of the codigoPatrocinio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPatrocinio(String value) {
        this.codigoPatrocinio = value;
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
     * Gets the value of the costoRegistros property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCostoRegistros() {
        return costoRegistros;
    }

    /**
     * Sets the value of the costoRegistros property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCostoRegistros(BigDecimal value) {
        this.costoRegistros = value;
    }

    /**
     * Gets the value of the porcentajeAporte property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPorcentajeAporte() {
        return porcentajeAporte;
    }

    /**
     * Sets the value of the porcentajeAporte property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPorcentajeAporte(BigDecimal value) {
        this.porcentajeAporte = value;
    }

}
