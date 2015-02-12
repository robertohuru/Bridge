
import views.Login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author EED Advisory
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Login(null, true).setVisible(true);
        Utils.SystemProperties.createSMSAppFolders();
        // TODO code application logic here
    }
    
}
