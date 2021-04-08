package ec.puntonet.pincidencias.global;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Inicializador implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger(Inicializador.class.getSimpleName()).log(Level.INFO, "El contexto ha sido destruido");
    }

    //este metodo se ejecuta cuando se inicia la aplicacion o bien el contexto.
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Global.init();
        Logger.getLogger(Inicializador.class.getName()).log(Level.INFO, "Contexto inicializado");

    }
}
