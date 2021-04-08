
import ec.puntonet.pincidencias.controlador.TicketDao;
import ec.puntonet.pincidencias.global.Global;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Global.init();
            TicketDao dao = new TicketDao();
         String numero =  dao.obtenerUltimoRegistroTicket();
         
        int i=Integer.parseInt(numero);   
        int n1= i+1;
        String s=String.valueOf(n1);  
            System.out.println(s);
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
