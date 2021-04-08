/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.global;

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Francisco Daza
 */
public class UtilidadesVista {
    
      /**
     * Método que muestra un mensaje en un diálogo por pantalla.
     *
     * @param titulo el texto que aparece en la cabecera del diálogo.
     * @param mensaje el texto que va en el cuerpo del diálogo.
     */
    public static void mostrarMensaje(String titulo, String mensaje) {
        String call = "mostrarMensaje('".concat(titulo).concat("','").concat(mensaje.replaceAll("'", "")).concat("')");
        RequestContext.getCurrentInstance().execute(call);
    }

    /**
     * Método que muestra un mensaje en un diálogo por pantalla y que recarga la
     * página al cerrarse.
     *
     * @param titulo el texto que aparece en la cabecera del diálogo.
     * @param mensaje el texto que va en el cuerpo del diálogo.
     */
    public static void mostrarMensajeRecargar(String titulo, String mensaje) {
        String call = "mostrarMensajeRecargar('".concat(titulo).concat("','").concat(mensaje.replaceAll("'", "")).concat("')");
        RequestContext.getCurrentInstance().execute(call);
    }
    
    /**
     * Método que realiza el redireccionamiento hacia una página web.
     *
     * @param direccion indica la ruta de la página web a la que se desea
     * redireccionar.
     */
    public static void direccionar(String direccion) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) context.getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf(Global.CONTEXTO + "/")
                    + Global.CONTEXTO.length()) + "/" + direccion);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesVista.class.getName()).log(Level.ERROR, "Error al redireccinar: ", ex);
        }
    }
    
}
