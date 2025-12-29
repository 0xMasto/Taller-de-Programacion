package logica.interfaces;


import logica.controllers.ControladorUsuarios;

import logica.model.ManejadorEventos;
import logica.model.ManejadorUsuarios;
import logica.controllers.ControladorEventos;

public class Fabrica {
    private static Fabrica instancia;
    private final ManejadorUsuarios manejadorUsuarios;
    private final ManejadorEventos manejadorEventos;
    
    private Fabrica() {
        this.manejadorUsuarios = ManejadorUsuarios.getInstance();
        this.manejadorEventos = ManejadorEventos.getInstance();
    }
    
    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }
    
    public iControladorUsuarios getControladorUsuario() {
        return new ControladorUsuarios(manejadorUsuarios, manejadorEventos);
    }
    
    public iControladorEventos getControladorEventos() {
        return new ControladorEventos(manejadorEventos, manejadorUsuarios);
    }
    
}
