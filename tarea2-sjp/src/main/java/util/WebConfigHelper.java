package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration helper for web application
 */
public class WebConfigHelper {

    
    public static String getWebServicesWSDL() {
        Properties props = new Properties();



        try (FileInputStream fis = new FileInputStream("/ens/devel01/tpgr01/web.properties")) {
            props.load(fis);
             String host = props.getProperty("webservices.host" );
            String port = props.getProperty("webservices.port");
            String path = props.getProperty("webservices.path");
        return "http://" + host + ":" + port + path+ "?wsdl";

        } catch (FileNotFoundException e) {
            System.err.println("No se encontró ");
           
        } catch (IOException e) {
            System.err.println("Error al cargar configuración web desde " + e.getMessage());
    
        }

        
        return "";
    }
}

