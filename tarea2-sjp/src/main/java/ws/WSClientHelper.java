package ws;

import java.net.URL;
import publicar.ws.client.WebServicesService;
import publicar.ws.client.WebServices;

/**
 * Web Services Client Wrapper
 * 
 * This class uses the generated WS client stubs to communicate with Servidor Central.
 */
public class WSClientHelper {
    
    private static WSClientHelper instance;
    private WebServices port;
    
    private WSClientHelper() {
        try {
            String wsdlURL = util.WebConfigHelper.getWebServicesWSDL();
            
            URL url = new URL(wsdlURL);
            WebServicesService service = new WebServicesService(url);
            this.port = service.getWebServicesPort();
            System.out.println("WS Client initialized successfully: " + wsdlURL);
            
        } catch (Exception e) {
            System.err.println("Error initializing WS client: " + e.getMessage());
            throw new RuntimeException("Failed to initialize WS client", e);
        }
    }
    
    public static synchronized WSClientHelper getInstance() {
        if (instance == null) {
            instance = new WSClientHelper();
        }
        return instance;
    }
    
    public WebServices getPort() {
        return port;
    }
}

