/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;

/**
 *
 * @author DELL
 */
public interface DatabaseConnectionVariables {
    File file = new File("smsapp");
    
    //String host = "jdbc:derby://localhost:1527/C:\\Users\\DELL\\.netbeans-derby\\sms_db";
    String host = "jdbc:derby://localhost:1527/"+System.getProperty("user.home")+"\\.netbeans-derby\\sms_db";
    String database = "sms_db";
    String user = "roba";
    String password = "roba";
}
