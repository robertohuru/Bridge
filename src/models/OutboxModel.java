/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.OutboxInfo;
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
public class OutboxModel {

    private PreparedStatement insertPreptsmt;
    private PreparedStatement selectPrepStmt;
    private PreparedStatement deletePrepStmt;
    private final Connection con;
    private OutboxInfo outbox;

    public OutboxModel() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public int delete(OutboxInfo inbox) {
        int result = 0;
        String updateSQL = "Delete from outbox_table WHERE  id=? ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            deletePrepStmt.setLong(1, inbox.getId());
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OutboxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int deleteAll() {
        int result = 0;
        String updateSQL = "Delete from outbox_table ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OutboxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int insert(OutboxInfo outbox) {
        int result = 0;
        this.outbox = outbox;
        String insertSQL = "INSERT INTO outbox_table( message,sender, reciever,date_sent) VALUES (?,?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, outbox.getMessage());
            insertPreptsmt.setString(2, outbox.getSender());
            insertPreptsmt.setString(3, outbox.getReciever());
            insertPreptsmt.setString(4, outbox.getDateSent());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OutboxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public OutboxInfo[] getSentMessages() {
        List<OutboxInfo> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT id, sender, message, reciever, date_sent,deleted FROM outbox_table WHERE deleted=0";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                outbox = null;
                outbox = new OutboxInfo();
                outbox.setId(rs.getInt("id"));
                outbox.setMessage(rs.getString("message"));
                outbox.setSender(rs.getString("sender"));
                outbox.setReciever(rs.getString("reciever"));
                outbox.setDateSent(rs.getString("date_sent"));
                outbox.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(outbox);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OutboxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutboxInfo personalInfoArr[] = new OutboxInfo[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }

    public OutboxInfo[] getDeletedOutBox() {
        List<OutboxInfo> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT id, sender, message, reciever, date_sent,deleted FROM outbox_table WHERE deleted=1";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                outbox = null;
                outbox = new OutboxInfo();
                outbox.setId(rs.getInt("id"));
                outbox.setMessage(rs.getString("message"));
                outbox.setSender(rs.getString("sender"));
                outbox.setReciever(rs.getString("reciever"));
                outbox.setDateSent(rs.getString("date_sent"));
                outbox.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(outbox);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OutboxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutboxInfo personalInfoArr[] = new OutboxInfo[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }

}
