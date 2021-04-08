/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.controlador;

import ec.puntonet.pincidencias.modelo.Cliente;
import ec.puntonet.pincidencias.modelo.Empleado;
import ec.puntonet.pincidencias.modelo.Incidencia;
import ec.puntonet.pincidencias.modelo.Ticket;
import ec.puntonet.pincidencias.modelo.Usuario;
import ec.puntonet.pincidencias.objetos.ObjetoBusqueda;
import java.util.List;

/**
 *
 * @author Francisco Daza
 */
public class IncidenciasControlador {

    /* Controlador Usuario */
    public Usuario obtenerUsuarioPorCredenciales(String nombre, String clave) throws Exception {
        UsuarioDao dao = new UsuarioDao();
        return dao.obtenerUsuarioPorCredenciales(nombre, clave);
    }

    /* Controlador Cliente */
    public List<Cliente> obtenerTodosClientes() throws Exception {
        ClienteDao dao = new ClienteDao();
        return dao.obtenerTodosClientes();
    }

    public Cliente obtenerClientePorId(int idCliente) throws Exception {
        ClienteDao dao = new ClienteDao();
        return dao.obtenerClientePorId(idCliente);
    }

    public Cliente obtenerClientePorIdentificacion(String identificacion) throws Exception {
        ClienteDao dao = new ClienteDao();
        return dao.obtenerClientePorIdentificacion(identificacion);
    }

    /* Controlador Empleado */
    public List<Empleado> obtenerTodosEmpleados() throws Exception {
        EmpleadoDao dao = new EmpleadoDao();
        return dao.obtenerTodosEmpleados();
    }

    public Empleado obtenerEmpleadoPorId(int idEmpleado) throws Exception {
        EmpleadoDao dao = new EmpleadoDao();
        return dao.obtenerEmpleadoPorId(idEmpleado);
    }

    public Empleado obtenerEmpleadoPorIdentificacion(String identificacion) throws Exception {
        EmpleadoDao dao = new EmpleadoDao();
        return dao.obtenerEmpleadoPorIdentificacion(identificacion);
    }

    /* Controlador Ticket */
    public Ticket insertarTicket(Ticket ticket) throws Exception {
        TicketDao dao = new TicketDao();
        return dao.insertarTicket(ticket);
    }

    public List<Ticket> obtenerTodosTickets() throws Exception {
        TicketDao dao = new TicketDao();
        return dao.obtenerTodosTickets();
    }

    public String obtenerUltimoRegistroTicket() throws Exception {
        TicketDao dao = new TicketDao();
        return dao.obtenerUltimoRegistroTicket();
    }

    public List<Ticket> obtenerTicketBusqueda(ObjetoBusqueda parametros) throws Exception {
        TicketDao dao = new TicketDao();
        return dao.obtenerTicketBusqueda(parametros);
    }

    public Ticket obtenerTicketPorId(int idTicket) throws Exception {
        TicketDao dao = new TicketDao();
        return dao.obtenerTicketPorId(idTicket);
    }

    public Boolean actualizarTicket(Ticket ticket) throws Exception {
        TicketDao dao = new TicketDao();
        return dao.actualizarTicket(ticket);
    }

    public Boolean eliminarTicket(Ticket ticket) throws Exception {
        TicketDao dao = new TicketDao();
        return dao.eliminarTicket(ticket);
    }

    /*Controlador Incidencia */
    public List<Incidencia> obtenerTodosIncidencias() throws Exception {
        IncidenciaDao dao = new IncidenciaDao();
        return dao.obtenerTodosIncidencias();
    }

    public Incidencia obtenerIncidenciaPorId(int idIncidencia) throws Exception {
        IncidenciaDao dao = new IncidenciaDao();
        return dao.obtenerIncidenciaPorId(idIncidencia);
    }

}
