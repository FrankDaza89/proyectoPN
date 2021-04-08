/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.controlador;

import ec.puntonet.pincidencias.global.HibernateUtil;
import ec.puntonet.pincidencias.modelo.Ticket;
import ec.puntonet.pincidencias.objetos.ObjetoBusqueda;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Usuario
 */
public class TicketDao {

    public Ticket insertarTicket(Ticket ticket) throws Exception {
        Ticket tickets = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            tickets = (Ticket) session.merge(ticket);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(TicketDao.class).log(Level.ERROR, "Error TicketDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return tickets;
    }

    public List<Ticket> obtenerTodosTickets() throws Exception {
        List<Ticket> lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Ticket where eliminadoTic=0");
            lista = query.list();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(TicketDao.class).log(Level.ERROR, "Error Ticket: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return lista;
    }

    public String obtenerUltimoRegistroTicket() throws Exception {
        String incidencia = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("SELECT MAX(numero_tic) FROM mincidencias.ticket");
            incidencia = (String) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(IncidenciaDao.class).log(Level.ERROR, "Error IncidenciaDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return incidencia;
    }

    public List<Ticket> obtenerTicketBusqueda(ObjetoBusqueda parametros) throws Exception {
        List<Ticket> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteriaQuery = session.createCriteria(Ticket.class, "ticket");
        criteriaQuery.createAlias("ticket.cliente", "cliente");
        criteriaQuery.createAlias("ticket.incidencia", "incidencia");
        if (parametros != null) {
            if (parametros.getIdentificacionCliente() != null && !parametros.getIdentificacionCliente().equals("")) {
                criteriaQuery.add(Restrictions.eq("cliente.identificacionCli", parametros.getIdentificacionCliente()));
            }
            if (parametros.getNombreCliente() != null && !parametros.getNombreCliente().equals("")) {
                criteriaQuery.add(Restrictions.like("cliente.nombreCli", "%".concat(parametros.getNombreCliente()).concat("%")));
            }
            if (parametros.getNumeroTicket() != null && !parametros.getNumeroTicket().equals("")) {
                criteriaQuery.add(Restrictions.eq("numeroTic", parametros.getNumeroTicket()));
            }
            if (parametros.getEstado() != null && !parametros.getEstado().equals("")) {
                criteriaQuery.add(Restrictions.eq("estadoTic", Integer.parseInt(parametros.getEstado())));
            }
            if (parametros.getPrioridad() != null && !parametros.getPrioridad().equals("")) {
                if (parametros.getPrioridad().equals("0") ){
                    parametros.setPrioridad("BAJA");
                } else if (parametros.getPrioridad().equals("1")){
                   parametros.setPrioridad("MEDIA");
                }else if (parametros.getPrioridad().equals("2")){
                   parametros.setPrioridad("ALTA");
                }
                criteriaQuery.add(Restrictions.eq("incidencia.prioridadInc", parametros.getPrioridad()));
            }
        }
        lista = (List<Ticket>) criteriaQuery.list();
        session.flush();
        session.close();
        return lista;
    }
    
    
      public Ticket obtenerTicketPorId(int idTicket) throws Exception {
        Ticket ticket = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Ticket where idTicket=:idTicket");
            query.setParameter("idTicket", idTicket);
            ticket = (Ticket) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(ClienteDao.class).log(Level.ERROR, "Error TicketDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return ticket;
    }
      public Boolean actualizarTicket(Ticket ticket) throws Exception {
        Boolean bandera = false;      
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(ticket);
            session.getTransaction().commit();
            bandera = true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(TicketDao.class).log(Level.ERROR, "Error TicketDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return bandera;
    }
      
       public Boolean eliminarTicket(Ticket ticket) throws Exception {
        Boolean bandera = false;      
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(ticket);
            session.getTransaction().commit();
            bandera = true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(TicketDao.class).log(Level.ERROR, "Error TicketDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return bandera;
    }
}
