package ec.puntonet.pincidencias.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Propiedades {

    public static String ARCHIVO_PROPIEDADES_AMBIENTE = "ambiente.properties";
    private String ambiente;

    /**
     * Carga propiedades del archivo ambiente.properties
     *
     * @return
     */
    public boolean cargarPropiedadesAmbiente() {
        boolean success = false;
        InputStream ambientePropiedades = null;
        try {
            ambientePropiedades = getClass().getClassLoader().getResourceAsStream(ARCHIVO_PROPIEDADES_AMBIENTE);
            Properties propertiesAmbiente = new Properties();
            if (ambientePropiedades != null) {
                propertiesAmbiente.load(ambientePropiedades);
                setAmbiente(limpiar(propertiesAmbiente.getProperty("ambiente")));
                Global.AMBIENTE_PROYECTO = ambiente;
            }
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.ERROR, null, ex);
        } finally {
            try {
                ambientePropiedades.close();
            } catch (IOException ex) {
                Logger.getLogger(Propiedades.class.getName()).log(Level.ERROR, null, ex);
            }
        }
        return success;
    }

    /**
     *
     * @return
     */
    public boolean cargarPropiedades() {
        boolean success = false;
        InputStream proyectoPropiedades = null;
        try {
            proyectoPropiedades = getClass().getClassLoader().getResourceAsStream("incidencias." + ambiente + ".properties");
            Properties properties = new Properties();
            if (proyectoPropiedades != null) {
                properties.load(proyectoPropiedades);
                Global.DOMINIO = limpiar(properties.getProperty("dominio"));
                Global.CONTEXTO = limpiar(properties.getProperty("contexto"));
                 Global.RUTA_TEMPORAL = limpiar(properties.getProperty("temporal_ruta"));
                Log.LOG_RUTA = limpiar(properties.getProperty("log_ruta"));
                Log.LOG_NOMBRE = limpiar(properties.getProperty("log_nombre"));
            }
        } catch (IOException ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.ERROR, null, ex);
        } finally {
            try {
                proyectoPropiedades.close();
            } catch (IOException ex) {
                Logger.getLogger(Propiedades.class.getName()).log(Level.ERROR, null, ex);
            }
        }
        return success;
    }

    private String limpiar(String propiedad) {
        return propiedad.trim();
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

}
