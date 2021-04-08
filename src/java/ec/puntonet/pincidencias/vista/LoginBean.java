/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.vista;

import ec.puntonet.pincidencias.controlador.IncidenciasControlador;
import ec.puntonet.pincidencias.global.AES256;
import ec.puntonet.pincidencias.global.ManejoSesion;
import ec.puntonet.pincidencias.global.UtilidadesVista;
import ec.puntonet.pincidencias.modelo.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Francisco Daza
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String nombreUsuario;
    private String clave;
    private IncidenciasControlador incidenciasControlador;
     private ManejoSesion manejoSesion;
    private Usuario usuarioLogeado;

    @PostConstruct
    public void init() {
        incidenciasControlador = new IncidenciasControlador();
        manejoSesion = new ManejoSesion();
        try {
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    public void login() {
        String titulo = "";
        String mensaje = "";
        try {
            String password = AES256.cifrar(clave);
          
            usuarioLogeado = incidenciasControlador.obtenerUsuarioPorCredenciales(nombreUsuario, password);
            if (usuarioLogeado != null) {
                manejoSesion.iniciarSesion(null,usuarioLogeado);
                UtilidadesVista.direccionar("privado/ticket.xhtml");
            } else {
                titulo = "Usuario no Encontrado";
                mensaje = "Nombre de usuario o clave incorrectos";
                UtilidadesVista.mostrarMensaje(titulo, mensaje);
            }
        } catch (Exception e) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.ERROR, null, e);
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

  
}