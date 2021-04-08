/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.controlador;

import ec.puntonet.pincidencias.global.HibernateUtil;
import ec.puntonet.pincidencias.modelo.Usuario;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Usuario
 */
public class UsuarioDao {
    
    /**
     * 
     * @param nombre
     * @param clave
     * @return
     * @throws Exception 
     */
    public Usuario obtenerUsuarioPorCredenciales(String nombre, String clave) throws Exception {
        Usuario usuario = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Usuario where nombreUsu=:nombre and claveUsu=:clave");
            query.setParameter("nombre", nombre);
            query.setParameter("clave", clave);
            usuario = (Usuario) query.uniqueResult();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(UsuarioDao.class).log(Level.ERROR, "Error UsuarioDao: ", ex);
        } finally {
            session.flush();
            session.close();
        }
        return usuario;
    }
    
}
