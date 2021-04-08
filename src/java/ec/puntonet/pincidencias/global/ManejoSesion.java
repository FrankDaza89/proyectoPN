/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.global;

import ec.puntonet.pincidencias.modelo.Empleado;
import ec.puntonet.pincidencias.modelo.Usuario;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Francisco Daza
 */
public class ManejoSesion implements Serializable {

    private HttpSession sesion;

    public ManejoSesion() {
        sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public ManejoSesion(HttpSession _sesion) {
        sesion = _sesion;
    }

    public HttpSession getSesion() {
        return sesion;
    }

    public void setSesion(HttpSession sesion) {
        this.sesion = sesion;
    }

public Usuario getUsuario() {
        return (Usuario) sesion.getAttribute("usuario");
    }

    public void setUsuario(Usuario usuario) {
        sesion.setAttribute("usuario", usuario);
    }

    public Empleado getEmpleado() {
        return (Empleado) sesion.getAttribute("empleado");
    }

    public void setEmpleado(Empleado empleado) {
        sesion.setAttribute("empleado", empleado);
    }


    public void iniciarSesion(Empleado empleado, Usuario usuario) {
        setEmpleado(empleado);
        setUsuario(usuario);
    }

    public void cerrarSesion() {
        sesion.removeAttribute("empleado");
         sesion.removeAttribute("usuario");

    }
}
