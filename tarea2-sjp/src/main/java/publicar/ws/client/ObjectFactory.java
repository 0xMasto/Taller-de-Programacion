
package publicar.ws.client;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the publicar package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BusinessException_QNAME = new QName("http://publicar/", "BusinessException");
    private final static QName _CostoRegistrosExcedidoException_QNAME = new QName("http://publicar/", "CostoRegistrosExcedidoException");
    private final static QName _CupoLlenoException_QNAME = new QName("http://publicar/", "CupoLlenoException");
    private final static QName _NombreEdicionException_QNAME = new QName("http://publicar/", "NombreEdicionException");
    private final static QName _PatrocinioDuplicadoException_QNAME = new QName("http://publicar/", "PatrocinioDuplicadoException");
    private final static QName _RegistroDuplicadoException_QNAME = new QName("http://publicar/", "RegistroDuplicadoException");
    private final static QName _TipoRegistroDuplicadoException_QNAME = new QName("http://publicar/", "TipoRegistroDuplicadoException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: publicar
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BusinessException }
     * 
     * @return
     *     the new instance of {@link BusinessException }
     */
    public BusinessException createBusinessException() {
        return new BusinessException();
    }

    /**
     * Create an instance of {@link CostoRegistrosExcedidoException }
     * 
     * @return
     *     the new instance of {@link CostoRegistrosExcedidoException }
     */
    public CostoRegistrosExcedidoException createCostoRegistrosExcedidoException() {
        return new CostoRegistrosExcedidoException();
    }

    /**
     * Create an instance of {@link CupoLlenoException }
     * 
     * @return
     *     the new instance of {@link CupoLlenoException }
     */
    public CupoLlenoException createCupoLlenoException() {
        return new CupoLlenoException();
    }

    /**
     * Create an instance of {@link NombreEdicionException }
     * 
     * @return
     *     the new instance of {@link NombreEdicionException }
     */
    public NombreEdicionException createNombreEdicionException() {
        return new NombreEdicionException();
    }

    /**
     * Create an instance of {@link PatrocinioDuplicadoException }
     * 
     * @return
     *     the new instance of {@link PatrocinioDuplicadoException }
     */
    public PatrocinioDuplicadoException createPatrocinioDuplicadoException() {
        return new PatrocinioDuplicadoException();
    }

    /**
     * Create an instance of {@link RegistroDuplicadoException }
     * 
     * @return
     *     the new instance of {@link RegistroDuplicadoException }
     */
    public RegistroDuplicadoException createRegistroDuplicadoException() {
        return new RegistroDuplicadoException();
    }

    /**
     * Create an instance of {@link TipoRegistroDuplicadoException }
     * 
     * @return
     *     the new instance of {@link TipoRegistroDuplicadoException }
     */
    public TipoRegistroDuplicadoException createTipoRegistroDuplicadoException() {
        return new TipoRegistroDuplicadoException();
    }

    /**
     * Create an instance of {@link AsistenteDTO }
     * 
     * @return
     *     the new instance of {@link AsistenteDTO }
     */
    public AsistenteDTO createAsistenteDTO() {
        return new AsistenteDTO();
    }

    /**
     * Create an instance of {@link UsuarioDTO }
     * 
     * @return
     *     the new instance of {@link UsuarioDTO }
     */
    public UsuarioDTO createUsuarioDTO() {
        return new UsuarioDTO();
    }

    /**
     * Create an instance of {@link InstitucionDTO }
     * 
     * @return
     *     the new instance of {@link InstitucionDTO }
     */
    public InstitucionDTO createInstitucionDTO() {
        return new InstitucionDTO();
    }

    /**
     * Create an instance of {@link RegistroDTO }
     * 
     * @return
     *     the new instance of {@link RegistroDTO }
     */
    public RegistroDTO createRegistroDTO() {
        return new RegistroDTO();
    }

    /**
     * Create an instance of {@link EdicionDTO }
     * 
     * @return
     *     the new instance of {@link EdicionDTO }
     */
    public EdicionDTO createEdicionDTO() {
        return new EdicionDTO();
    }

    /**
     * Create an instance of {@link TipoRegistroDTO }
     * 
     * @return
     *     the new instance of {@link TipoRegistroDTO }
     */
    public TipoRegistroDTO createTipoRegistroDTO() {
        return new TipoRegistroDTO();
    }

    /**
     * Create an instance of {@link PatrocinioDTO }
     * 
     * @return
     *     the new instance of {@link PatrocinioDTO }
     */
    public PatrocinioDTO createPatrocinioDTO() {
        return new PatrocinioDTO();
    }

    /**
     * Create an instance of {@link EventoDTO }
     * 
     * @return
     *     the new instance of {@link EventoDTO }
     */
    public EventoDTO createEventoDTO() {
        return new EventoDTO();
    }

    /**
     * Create an instance of {@link CategoriaDTO }
     * 
     * @return
     *     the new instance of {@link CategoriaDTO }
     */
    public CategoriaDTO createCategoriaDTO() {
        return new CategoriaDTO();
    }

    /**
     * Create an instance of {@link OrganizadorDTO }
     * 
     * @return
     *     the new instance of {@link OrganizadorDTO }
     */
    public OrganizadorDTO createOrganizadorDTO() {
        return new OrganizadorDTO();
    }

    /**
     * Create an instance of {@link AsistenteDTOArray }
     * 
     * @return
     *     the new instance of {@link AsistenteDTOArray }
     */
    public AsistenteDTOArray createAsistenteDTOArray() {
        return new AsistenteDTOArray();
    }

    /**
     * Create an instance of {@link RegistroDTOArray }
     * 
     * @return
     *     the new instance of {@link RegistroDTOArray }
     */
    public RegistroDTOArray createRegistroDTOArray() {
        return new RegistroDTOArray();
    }

    /**
     * Create an instance of {@link TipoRegistroDTOArray }
     * 
     * @return
     *     the new instance of {@link TipoRegistroDTOArray }
     */
    public TipoRegistroDTOArray createTipoRegistroDTOArray() {
        return new TipoRegistroDTOArray();
    }

    /**
     * Create an instance of {@link CategoriaDTOArray }
     * 
     * @return
     *     the new instance of {@link CategoriaDTOArray }
     */
    public CategoriaDTOArray createCategoriaDTOArray() {
        return new CategoriaDTOArray();
    }

    /**
     * Create an instance of {@link EventoDTOArray }
     * 
     * @return
     *     the new instance of {@link EventoDTOArray }
     */
    public EventoDTOArray createEventoDTOArray() {
        return new EventoDTOArray();
    }

    /**
     * Create an instance of {@link OrganizadorDTOArray }
     * 
     * @return
     *     the new instance of {@link OrganizadorDTOArray }
     */
    public OrganizadorDTOArray createOrganizadorDTOArray() {
        return new OrganizadorDTOArray();
    }

    /**
     * Create an instance of {@link EdicionDTOArray }
     * 
     * @return
     *     the new instance of {@link EdicionDTOArray }
     */
    public EdicionDTOArray createEdicionDTOArray() {
        return new EdicionDTOArray();
    }

    /**
     * Create an instance of {@link PatrocinioDTOArray }
     * 
     * @return
     *     the new instance of {@link PatrocinioDTOArray }
     */
    public PatrocinioDTOArray createPatrocinioDTOArray() {
        return new PatrocinioDTOArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BusinessException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "BusinessException")
    public JAXBElement<BusinessException> createBusinessException(BusinessException value) {
        return new JAXBElement<>(_BusinessException_QNAME, BusinessException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CostoRegistrosExcedidoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CostoRegistrosExcedidoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "CostoRegistrosExcedidoException")
    public JAXBElement<CostoRegistrosExcedidoException> createCostoRegistrosExcedidoException(CostoRegistrosExcedidoException value) {
        return new JAXBElement<>(_CostoRegistrosExcedidoException_QNAME, CostoRegistrosExcedidoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CupoLlenoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CupoLlenoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "CupoLlenoException")
    public JAXBElement<CupoLlenoException> createCupoLlenoException(CupoLlenoException value) {
        return new JAXBElement<>(_CupoLlenoException_QNAME, CupoLlenoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NombreEdicionException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NombreEdicionException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "NombreEdicionException")
    public JAXBElement<NombreEdicionException> createNombreEdicionException(NombreEdicionException value) {
        return new JAXBElement<>(_NombreEdicionException_QNAME, NombreEdicionException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatrocinioDuplicadoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PatrocinioDuplicadoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "PatrocinioDuplicadoException")
    public JAXBElement<PatrocinioDuplicadoException> createPatrocinioDuplicadoException(PatrocinioDuplicadoException value) {
        return new JAXBElement<>(_PatrocinioDuplicadoException_QNAME, PatrocinioDuplicadoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistroDuplicadoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RegistroDuplicadoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "RegistroDuplicadoException")
    public JAXBElement<RegistroDuplicadoException> createRegistroDuplicadoException(RegistroDuplicadoException value) {
        return new JAXBElement<>(_RegistroDuplicadoException_QNAME, RegistroDuplicadoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoRegistroDuplicadoException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TipoRegistroDuplicadoException }{@code >}
     */
    @XmlElementDecl(namespace = "http://publicar/", name = "TipoRegistroDuplicadoException")
    public JAXBElement<TipoRegistroDuplicadoException> createTipoRegistroDuplicadoException(TipoRegistroDuplicadoException value) {
        return new JAXBElement<>(_TipoRegistroDuplicadoException_QNAME, TipoRegistroDuplicadoException.class, null, value);
    }

}
