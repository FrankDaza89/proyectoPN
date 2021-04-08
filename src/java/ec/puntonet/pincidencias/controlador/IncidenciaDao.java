/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.controlador;

import ec.puntonet.pincidencias.global.HibernateUtil;
import ec.puntonet.pincidencias.modelo.Incidencia;
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
public class IncidenciaDao {

    public List<Incidencia> obtenerTodosIncidencias() throws Exception {
        List<Incidencia> lista = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Incidencia");
            lista = query.list();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(IncidenciaDao.class).log(Level.ERROR, "Error IncidenciaDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return lista;
    }

    public Incidencia obtenerIncidenciaPorId(int idIncidencia) throws Exception {
        Incidencia incidencia = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Incidencia where idIncidencia=:idIncidencia");
            query.setParameter("idIncidencia", idIncidencia);
            incidencia = (Incidencia) query.uniqueResult();
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
}
