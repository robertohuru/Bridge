/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.ChatController;
import controllers.InboxController;
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
public class InBoxModel {

    private PreparedStatement insertPreptsmt;
    private PreparedStatement selectPrepStmt;
    private PreparedStatement deletePrepStmt;
    private final Connection con;
    private InboxController inbox;
    private ChatController chat;

    public InBoxModel() {
        con = DatabaseConnection.getInstance().getConnection();
    }
   
    public int delete(InboxController inbox){
        int result = 0;
        String updateSQL = "DELETE from inbox_table WHERE  id=? ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            deletePrepStmt.setLong(1, inbox.getId());
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public int deleteAll(){
        int result = 0;
        String updateSQL = "DELETE FROM inbox_table  "
                + "WHERE  deleted=1 ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int deleteChats(ChatController chat){
        int result = 0;
        String updateSQL = "UPDATE chat_tbl SET "
                + "deleted=1 WHERE  sender=? AND owner=?";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            deletePrepStmt.setString(1, chat.getSender());
            deletePrepStmt.setString(2, chat.getOwner());
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int insert(InboxController inbox) {
        int result = 0;
        this.inbox = inbox;
        String insertSQL = "INSERT INTO inbox_table( message,sender,date_sent) VALUES (?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, inbox.getMessage());
            insertPreptsmt.setString(2, inbox.getSender());
            insertPreptsmt.setString(3, inbox.getDateSent());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
    public int insertChat(ChatController chat) {
        int result = 0;
        this.chat = chat;
        String insertSQL = "INSERT INTO chat_tbl( message,sender,sent,owner) VALUES (?,?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, chat.getMessage());
            insertPreptsmt.setString(2, chat.getSender());
            insertPreptsmt.setBoolean(3, chat.isSent());
            insertPreptsmt.setString(4, chat.getOwner());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
    
    
    public InboxController[] getMessages(){
        List<InboxController> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT id, sender, message, date_sent,deleted FROM inbox_table WHERE deleted=0";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                inbox = null;
                inbox = new InboxController();
                inbox.setId(rs.getInt("id"));
                inbox.setMessage(rs.getString("message"));
                inbox.setSender(rs.getString("sender"));
                inbox.setDateSent(rs.getString("date_sent"));
                inbox.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(inbox);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        InboxController personalInfoArr[] = new InboxController[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }
    
    public InboxController[] getDeletedMessages(){
        List<InboxController> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT id, sender, message, date_sent,deleted FROM inbox_table WHERE deleted=1";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                inbox = null;
                inbox = new InboxController();
                inbox.setId(rs.getInt("id"));
                inbox.setMessage(rs.getString("message"));
                inbox.setSender(rs.getString("sender"));
                inbox.setDateSent(rs.getString("date_sent"));
                inbox.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(inbox);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        InboxController personalInfoArr[] = new InboxController[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }
    
    public ChatController[] getAllChats(String owner, String sender){
        List<ChatController> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT  sender, message, date_sent,sent,owner, deleted FROM chat_tbl WHERE deleted=0 AND"
                + " sender='"+sender+"' AND owner='"+owner+"'";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                chat = null;
                chat = new ChatController();
                chat.setMessage(rs.getString("message"));
                chat.setSender(rs.getString("sender"));
                chat.setDateSent(rs.getString("date_sent"));
                chat.setDeleted(rs.getBoolean("deleted"));
                chat.setOwner(rs.getString("owner"));
                chat.setSent(rs.getBoolean("sent"));
                allPersonalInfo.add(chat);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ChatController personalInfoArr[] = new ChatController[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }
   

}
