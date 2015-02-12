/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.DeletedInfo;
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
public class DeletedModel {
    private PreparedStatement insertPreptsmt;
    private PreparedStatement selectPrepStmt;
    private PreparedStatement deletePrepStmt;
    private final Connection con;
    private DeletedInfo deleted;
    public DeletedModel(){
        con = DatabaseConnection.getInstance().getConnection();
    }
    public int delete(DeletedInfo deleted){
        int result = 0;
        String updateSQL = "DELETE from deleted_table WHERE  date_sent=? ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            deletePrepStmt.setString(1, deleted.getDateSent());
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DeletedModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public int deleteAll(){
        int result = 0;
        String updateSQL = "DELETE from deleted_table ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DeletedModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int insert(DeletedInfo deleted) {
        int result = 0;
        this.deleted= deleted;
        String insertSQL = "INSERT INTO deleted_table( message,sender, date_sent) VALUES (?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, deleted.getMessage());
            insertPreptsmt.setString(2, deleted.getSender());
            insertPreptsmt.setString(3, deleted.getDateSent());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DeletedModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
    public DeletedInfo[] getSentMessages(){
        List<DeletedInfo> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT sender, message, date_sent FROM deleted_table";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                deleted = null;
                deleted = new DeletedInfo();
                deleted.setMessage(rs.getString("message"));
                deleted.setSender(rs.getString("sender"));
                deleted.setDateSent(rs.getString("date_sent"));
                allPersonalInfo.add(deleted);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DeletedModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        DeletedInfo personalInfoArr[] = new DeletedInfo[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }
    
}
