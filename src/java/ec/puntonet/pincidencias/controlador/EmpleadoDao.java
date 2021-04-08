/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.controlador;

import ec.puntonet.pincidencias.global.HibernateUtil;
import ec.puntonet.pincidencias.modelo.Empleado;
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
public class EmpleadoDao {
    
     public List<Empleado> obtenerTodosEmpleados() throws Exception {
        List<Empleado> lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Empleado where eliminado=0");
            lista = query.list();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(EmpleadoDao.class).log(Level.ERROR, "Error EmpleadoDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return lista;
    }

    public Empleado obtenerEmpleadoPorId(int idEmpleado) throws Exception {
        Empleado empleado = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Empleado where idEmpleado=:idEmpleado");
            query.setParameter("idEmpleado", idEmpleado);
            empleado = (Empleado) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(EmpleadoDao.class).log(Level.ERROR, "Error EmpleadoDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return empleado;
    }

    public Empleado obtenerEmpleadoPorIdentificacion(String identificacion) throws Exception {
        Empleado empleado = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Empleado where identificacionEmp=:identificacion");
            query.setParameter("identificacion", identificacion);
            empleado = (Empleado) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(EmpleadoDao.class).log(Level.ERROR, "Error EmpleadoDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return empleado;
    }
}
