/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.ContactInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class ContactModel {
    private PreparedStatement insertPreptsmt;
    private PreparedStatement selectPrepStmt;
    private PreparedStatement deletePrepStmt;
    private final Connection con;
    private ContactInfo contact;
    public ContactModel(){
        con = DatabaseConnection.getInstance().getConnection();
    }
    public int insertContact(ContactInfo contact) {
        int result = 0;
        this.contact = contact;
        String insertSQL = "INSERT INTO contact_table( first_name,last_name,contact) VALUES (?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, contact.getFirstName());
            insertPreptsmt.setString(2, contact.getLastName());
            insertPreptsmt.setString(3, contact.getMobNumber());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Utils.Util.logError(ContactModel.class.getName(), ex.getMessage());
            
        }
        return result;

    }
    
    public ContactInfo[] getContacts(){
        List<ContactInfo> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT id, first_name, last_name, contact,deleted FROM contact_table WHERE deleted=0";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                contact = null;
                contact = new ContactInfo();
                contact.setId(rs.getInt("id"));
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setMobNumber(rs.getString("contact"));
                contact.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(contact);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContactModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ContactInfo personalInfoArr[] = new ContactInfo[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }
    
     public ContactInfo getPersonsContact(int id){
        String selectSQL = "SELECT id, first_name, last_name, contact,deleted FROM contact_table WHERE deleted=0 AND id='"+id+"'";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                contact = null;
                contact = new ContactInfo();
                contact.setId(rs.getInt("id"));
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
                contact.setMobNumber(rs.getString("contact"));
                contact.setDeleted(rs.getBoolean("deleted"));
            }

        } catch (SQLException ex) {
            Utils.Util.logError(ContactModel.class.getName(), ex.getMessage());
            Logger.getLogger(ContactModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contact;
    }
     
     public ContactInfo getPerson(String mobNum){
        String selectSQL = "SELECT first_name, last_name FROM contact_table WHERE deleted=0 AND contact = '"+mobNum+"'";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                contact = null;
                contact = new ContactInfo();
                contact.setFirstName(rs.getString("first_name"));
                contact.setLastName(rs.getString("last_name"));
            }

        } catch (SQLException ex) {
            Utils.Util.logError(ContactModel.class.getName(), ex.getMessage());
            Logger.getLogger(ContactModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contact;
    }
    
    
}
