/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.vista;

import com.google.gson.Gson;
import ec.puntonet.pincidencias.controlador.IncidenciasControlador;
import ec.puntonet.pincidencias.global.UtilidadesVista;
import ec.puntonet.pincidencias.modelo.Cliente;
import ec.puntonet.pincidencias.modelo.Empleado;
import ec.puntonet.pincidencias.modelo.Incidencia;
import ec.puntonet.pincidencias.modelo.Ticket;
import ec.puntonet.pincidencias.modelo.Usuario;
import ec.puntonet.pincidencias.objetos.ObjetoBusqueda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Francisco Daza
 */
@ManagedBean(name = "ticketBean")
@ViewScoped
public class TicketBean implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private IncidenciasControlador incidenciasControlador;
    private List<Ticket> tickets;
    private List<Incidencia> incidencias;
    private String contactoComunicacion;
    private String medioComunicacion;
    private String observacion;

    private String incidenciaSeleccionada;
    private String empleadoSeleccionado;
    private String clienteSeleccionado;
    private String banderaModo;
    private String busqueda;
    private String idTicketSeleccionado;
    private Usuario usuario;
    private List<Cliente> clientes;
    private String cambioEstadoSeleccionado;

    @PostConstruct
    public void init() {
        try {
            usuario = loginBean.getUsuarioLogeado();
            incidenciasControlador = new IncidenciasControlador();
            tickets = incidenciasControlador.obtenerTodosTickets();
            incidencias = incidenciasControlador.obtenerTodosIncidencias();
            clientes = incidenciasControlador.obtenerTodosClientes();
            RequestContext.getCurrentInstance().execute("mostrarTabla()");

        } catch (Exception ex) {
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    public void registrarTicket() {
        int estado = 0;
        int eliminado = 0;

        try {
            Ticket ticket = new Ticket();
            String numeroT = incidenciasControlador.obtenerUltimoRegistroTicket();
            int i = Integer.parseInt(numeroT);
            int n1 = i + 1;
            String nuevoNumTicket = String.valueOf(n1);
            ticket.setNumeroTic(nuevoNumTicket);
            ticket.setEstadoTic(estado);
            ticket.setFechaRegistroTic(new Date());
            ticket.setContactoComunicacionTic(contactoComunicacion);
            ticket.setMedioComunicacionTic(medioComunicacion);
            ticket.setObservacionTic(observacion);
            ticket.setEliminadoTic(eliminado);

            Cliente cliente = incidenciasControlador.obtenerClientePorId(Integer.parseInt(clienteSeleccionado));

            if (cliente != null) {
                ticket.setCliente(cliente);
            } else {
                ticket.setCliente(null);
            }
            Incidencia incidencia = incidenciasControlador.obtenerIncidenciaPorId(Integer.parseInt(incidenciaSeleccionada));
            if (incidencia != null) {
                ticket.setIncidencia(incidencia);
            } else {
                ticket.setIncidencia(null);
            }

            Empleado empleado = incidenciasControlador.obtenerEmpleadoPorId(usuario.getEmpleado().getIdEmpleado());

            if (empleado != null) {
                ticket.setEmpleado(empleado);
            } else {
                ticket.setEmpleado(null);
            }

            ticket = incidenciasControlador.insertarTicket(ticket);
            tickets = incidenciasControlador.obtenerTodosTickets();
            UtilidadesVista.mostrarMensajeRecargar("Insertar", "El registro se insertó correctamente");

        } catch (Exception ex) {
            UtilidadesVista.mostrarMensajeRecargar("Error en Insertar", "Error al insertar el registro");
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
        limpiar();
        RequestContext.getCurrentInstance().execute("mostrarTabla()");
    }

    public void buscar() {
        Gson gson = new Gson();
        ObjetoBusqueda objB = gson.fromJson(busqueda, ObjetoBusqueda.class);
        objB.setIdentificacionCliente(objB.getIdentificacionCliente());
        objB.setNombreCliente(objB.getNombreCliente());
        objB.setNumeroTicket(objB.getNumeroTicket());
        objB.setEstado(objB.getEstado());
        objB.setPrioridad(objB.getPrioridad());
        try {
            tickets = incidenciasControlador.obtenerTicketBusqueda(objB);
        } catch (Exception ex) {
            UtilidadesVista.mostrarMensajeRecargar("Error", "Ha ocurrido un error al consultar los Tickets.");
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, "Error al consultar los Tickets: ", ex);
        }
        RequestContext.getCurrentInstance().execute("mostrarTabla()");

    }

    public void obtenerTicket() {
        try {
            this.banderaModo = "1";
            Ticket ticket = incidenciasControlador.obtenerTicketPorId(Integer.parseInt(idTicketSeleccionado));
            this.clienteSeleccionado = ticket.getCliente().getIdCliente().toString();
            RequestContext.getCurrentInstance().execute("$(\"#clienteS-ticket option[id='" + clienteSeleccionado + "']\").attr('selected', true)");
            this.contactoComunicacion = ticket.getContactoComunicacionTic();
            this.observacion = ticket.getObservacionTic();
            this.medioComunicacion = ticket.getMedioComunicacionTic();
            RequestContext.getCurrentInstance().execute("$(\"#medio-comuni-ticket option[id='" + medioComunicacion + "']\").attr('selected', true)");
            this.incidenciaSeleccionada = ticket.getIncidencia().getIdIncidencia().toString();
            RequestContext.getCurrentInstance().execute("$(\"#incidencia-ticket option[id='" + incidenciaSeleccionada + "']\").attr('selected', true)");

        } catch (Exception ex) {
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
    }
    
    public void obtenerEstado() {
        try {
            
            Ticket ticket = incidenciasControlador.obtenerTicketPorId(Integer.parseInt(idTicketSeleccionado));
            this.cambioEstadoSeleccionado = ticket.getEstadoTic().toString();
            RequestContext.getCurrentInstance().execute("$(\"#cambio-es-ticket option[id='" + cambioEstadoSeleccionado + "']\").attr('selected', true)");
        } catch (Exception ex) {
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    public void actualizarTicket() {
        Boolean bandera = false;
        try {
            Ticket ticket = incidenciasControlador.obtenerTicketPorId(Integer.parseInt(idTicketSeleccionado));
            ticket.setContactoComunicacionTic(contactoComunicacion);
            ticket.setMedioComunicacionTic(medioComunicacion);
            ticket.setObservacionTic(observacion);

            Cliente cliente = incidenciasControlador.obtenerClientePorId(Integer.parseInt(clienteSeleccionado));

            if (cliente != null) {
                ticket.setCliente(cliente);
            } else {
                ticket.setCliente(null);
            }
            Incidencia incidencia = incidenciasControlador.obtenerIncidenciaPorId(Integer.parseInt(incidenciaSeleccionada));
            if (incidencia != null) {
                ticket.setIncidencia(incidencia);
            } else {
                ticket.setIncidencia(null);
            }

            bandera = incidenciasControlador.actualizarTicket(ticket);
            if (bandera) {
                tickets = incidenciasControlador.obtenerTodosTickets();
                UtilidadesVista.mostrarMensajeRecargar("Actualizar", "El registro se actualizó correctamente");
            } else {
                UtilidadesVista.mostrarMensajeRecargar("Error en Actualización", "Error al actualizar el registro");
            }

        } catch (Exception ex) {
            UtilidadesVista.mostrarMensajeRecargar("Error en Insertar", "Error al actualizar el registro");
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
        limpiar();
        RequestContext.getCurrentInstance().execute("mostrarTabla()");
        this.banderaModo = "0";
    }
    
    
    public void cambioEstado() {
        Boolean bandera = false;      
        try {
            Ticket ticket = incidenciasControlador.obtenerTicketPorId(Integer.parseInt(idTicketSeleccionado));
            ticket.setEstadoTic(Integer.parseInt(cambioEstadoSeleccionado));
            bandera = incidenciasControlador.eliminarTicket(ticket);
            if (bandera) {
                tickets = incidenciasControlador.obtenerTodosTickets();
                UtilidadesVista.mostrarMensajeRecargar("CambioEstado", "El cambio de estado se realizó correctamente");
            } else {
                UtilidadesVista.mostrarMensajeRecargar("Error Cambio Estado", "Error al cambiar estado");
            }
        } catch (Exception ex) {
            UtilidadesVista.mostrarMensajeRecargar("Error Cambio Estado", "Error al cambiar estado");
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
        limpiar();
        RequestContext.getCurrentInstance().execute("mostrarTabla()");
        this.banderaModo = "0";

    }

    public void removerAsignacion() {
        Boolean bandera = false;
        int eliminado = 1;
        try {
            Ticket ticket = incidenciasControlador.obtenerTicketPorId(Integer.parseInt(idTicketSeleccionado));
            ticket.setEliminadoTic(eliminado);
            bandera = incidenciasControlador.eliminarTicket(ticket);
            if (bandera) {
                tickets = incidenciasControlador.obtenerTodosTickets();
                UtilidadesVista.mostrarMensajeRecargar("Eliminar", "El registro se eliminó  correctamente");
            } else {
                UtilidadesVista.mostrarMensajeRecargar("Error en Eliminar", "Error al eliminar el registro");
            }
        } catch (Exception ex) {
            UtilidadesVista.mostrarMensajeRecargar("Error en Eliminar", "Error al eliminar el registro");
            Logger.getLogger(TicketBean.class.getName()).log(Level.ERROR, null, ex);
        }
        limpiar();
        RequestContext.getCurrentInstance().execute("mostrarTabla()");
        this.banderaModo = "0";

    }

    public void limpiar() {
        contactoComunicacion = null;
        medioComunicacion = null;
        observacion = null;
        banderaModo = "";
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public String getContactoComunicacion() {
        return contactoComunicacion;
    }

    public void setContactoComunicacion(String contactoComunicacion) {
        this.contactoComunicacion = contactoComunicacion;
    }

    public String getMedioComunicacion() {
        return medioComunicacion;
    }

    public void setMedioComunicacion(String medioComunicacion) {
        this.medioComunicacion = medioComunicacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIncidenciaSeleccionada() {
        return incidenciaSeleccionada;
    }

    public void setIncidenciaSeleccionada(String incidenciaSeleccionada) {
        this.incidenciaSeleccionada = incidenciaSeleccionada;
    }

    public String getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(String empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public String getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(String clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public String getBanderaModo() {
        return banderaModo;
    }

    public void setBanderaModo(String banderaModo) {
        this.banderaModo = banderaModo;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getIdTicketSeleccionado() {
        return idTicketSeleccionado;
    }

    public void setIdTicketSeleccionado(String idTicketSeleccionado) {
        this.idTicketSeleccionado = idTicketSeleccionado;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public String getCambioEstadoSeleccionado() {
        return cambioEstadoSeleccionado;
    }

    public void setCambioEstadoSeleccionado(String cambioEstadoSeleccionado) {
        this.cambioEstadoSeleccionado = cambioEstadoSeleccionado;
    }
}
