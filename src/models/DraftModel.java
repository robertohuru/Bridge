/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.DraftInfo;
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
public class DraftModel {
    private PreparedStatement insertPreptsmt;
    private PreparedStatement selectPrepStmt;
    private PreparedStatement deletePrepStmt;
    private final Connection con;
    private DraftInfo draft;
    public DraftModel(){
        con = DatabaseConnection.getInstance().getConnection();
    }
    public int delete(DraftInfo draft){
        int result = 0;
        String updateSQL = "DELETE from draft_table WHERE  date_sent=?";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            deletePrepStmt.setString(1, draft.getDateSent());
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DraftModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public int deleteAll(){
        int result = 0;
        String updateSQL = "DELETE from draft_table ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DraftModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public int insert(DraftInfo draft) {
        int result = 0;
        this.draft = draft;
        String insertSQL = "INSERT INTO draft_table( message,reciever, date_sent) VALUES (?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, draft.getMessage());
            insertPreptsmt.setString(2, draft.getReciever());
            insertPreptsmt.setString(3, draft.getDateSent());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DraftModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
    public DraftInfo[] getDrafts(){
        List<DraftInfo> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT reciever, message, date_sent FROM draft_table";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                draft = null;
                draft = new DraftInfo();
                draft.setMessage(rs.getString("message"));
                draft.setReciever(rs.getString("reciever"));
                draft.setDateSent(rs.getString("date_sent"));
                allPersonalInfo.add(draft);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InBoxModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        DraftInfo personalInfoArr[] = new DraftInfo[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }
    
}
