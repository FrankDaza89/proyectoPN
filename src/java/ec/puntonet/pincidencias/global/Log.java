/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.global;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author Francisco Daza
 */
public class Log {

    public static String LOG_RUTA = "";
    public static String LOG_NOMBRE = "";

    /**
     * Configura el log de la lib log4j
     *
     * @return
     */
    public static boolean configurarLog() {
        boolean success = false;
        try {
            String pathLog = LOG_RUTA + LOG_NOMBRE;
            // creates pattern layout
            PatternLayout layout = new PatternLayout();
            String conversionPattern = "[%p] %d %L %M - %c - %m%n";
            layout.setConversionPattern(conversionPattern);

            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(layout);
            consoleAppender.activateOptions();

            // creates daily rolling file appender
            DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
            rollingAppender.setFile(pathLog);
            rollingAppender.setDatePattern("'.'yyyy-MM-dd");
            rollingAppender.setLayout(layout);
            rollingAppender.activateOptions();

            // configures the root logger
            org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
            rootLogger.setLevel(org.apache.log4j.Level.INFO);
            rootLogger.addAppender(rollingAppender);
            rootLogger.addAppender(consoleAppender);
            success = true;
        } catch (Exception ex) {
            Logger.getLogger(Propiedades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
}
