package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import logica.interfaces.Fabrica;
import logica.interfaces.iControladorEventos;
import logica.interfaces.iControladorUsuarios;

public class TestFabrica {

	
	
	
	   @Test
	    void getInstancia_devuelveSiempreLaMismaInstancia() {
	        Fabrica a = Fabrica.getInstancia();
	        Fabrica b = Fabrica.getInstancia();
	        assertSame(a, b, "Fabrica debe comportarse como singleton (misma referencia).");
	    }
	    
	    
	    @Test
	    void getControladorUsuarioFabricaTest() {
	        Fabrica f = Fabrica.getInstancia();
	        iControladorUsuarios c1 = f.getControladorUsuario();
	        iControladorUsuarios c2 = f.getControladorUsuario();
	        assertNotNull(c1, "El controlador de usuarios no debe ser null.");
	        assertNotNull(c2, "El segundo controlador de usuarios no debe ser null.");
	        assertNotSame(c1, c2, "Cada llamada debe crear una instancia nueva del controlador de usuarios.");
	    }
	    
	    @Test
	    void getControladorEventosFabricaTest() {
	        Fabrica f = Fabrica.getInstancia();
	        iControladorEventos e1 = f.getControladorEventos();
	        iControladorEventos e2 = f.getControladorEventos();
	        assertNotNull(e1, "El controlador de eventos no debe ser null.");
	        assertNotNull(e2, "El segundo controlador de eventos no debe ser null.");
	        assertNotSame(e1, e2, "Cada llamada debe crear una instancia nueva del controlador de eventos.");
	    }
	    
}
