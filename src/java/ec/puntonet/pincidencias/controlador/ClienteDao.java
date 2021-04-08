/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.controlador;

import ec.puntonet.pincidencias.global.HibernateUtil;
import ec.puntonet.pincidencias.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Usuario
 */
public class ClienteDao {
    
    public List<Cliente> obtenerTodosClientes() throws Exception {
        List<Cliente> lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Cliente where eliminadoCli=0");
            lista = query.list();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(ClienteDao.class).log(Level.ERROR, "Error ClienteDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return lista;
    }

    public Cliente obtenerClientePorId(int idCliente) throws Exception {
        Cliente cliente = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Cliente where idCliente=:idCliente");
            query.setParameter("idCliente", idCliente);
            cliente = (Cliente) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(ClienteDao.class).log(Level.ERROR, "Error ClienteDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return cliente;
    }

    public Cliente obtenerClientePorIdentificacion(String identificacion) throws Exception {
        Cliente cliente = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Cliente where identificacionCli=:identificacion");
            query.setParameter("identificacion", identificacion);
            cliente = (Cliente) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(ClienteDao.class).log(Level.ERROR, "Error ClienteDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return cliente;
    }
    
}
