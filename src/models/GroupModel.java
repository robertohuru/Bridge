/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.GroupInfo;
import controllers.GroupMembers;
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
public class GroupModel {

    private PreparedStatement insertPreptsmt;
    private PreparedStatement selectPrepStmt;
    private PreparedStatement deletePrepStmt;
    private PreparedStatement createTable;
    private final Connection con;
    private GroupInfo group;
    private GroupMembers members;

    public GroupModel() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public int insertGroup(GroupInfo group) {
        int result = 0;
        this.group = group;
        String insertSQL = "INSERT INTO groups( name, description,table_name) VALUES (?,?,?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, group.getName());
            insertPreptsmt.setString(2, group.getDescription());
            insertPreptsmt.setString(3, group.getTableName());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Utils.Util.logError(GroupModel.class.getName(), ex.getMessage());

        }
        return result;

    }

    public void createGroupTable(String name) {
        int result = 0;
        String insertSQL = "CREATE TABLE " + name + " (contact VARCHAR(50)"
                + ",deleted SMALLINT NOT NULL DEFAULT 0)";
        try {
            createTable = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            result = createTable.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public int insert(GroupMembers members, String name) {
        int result = 0;
        this.members = members;
        String insertSQL = "INSERT INTO "+name+"( contact) VALUES (?)";
        try {

            insertPreptsmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPreptsmt.setString(1, members.getContact());
            result = insertPreptsmt.executeUpdate();

        } catch (SQLException ex) {
            Utils.Util.logError(GroupModel.class.getName(), ex.getMessage());

        }
        return result;

    }
    public int deleteMember(GroupMembers inbox, String table){
        int result = 0;
        String updateSQL = "DELETE from "+table+" WHERE  contact=? ";
        try {
            deletePrepStmt = con.prepareStatement(updateSQL);
            deletePrepStmt.setString(1, inbox.getContact());
            result = deletePrepStmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(GroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public GroupMembers[] getGroupMembers(GroupInfo name) {
        List<GroupMembers> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT contact, deleted FROM "+name.getTableName()+" WHERE deleted=0";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                members = null;
                members = new GroupMembers();
                members.setContact(rs.getString("contact"));
                members.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(members);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        GroupMembers personalInfoArr[] = new GroupMembers[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }

    public GroupInfo[] getGroups() {
        List<GroupInfo> allPersonalInfo = new ArrayList<>();
        String selectSQL = "SELECT name, description,table_name,deleted FROM groups WHERE deleted=0";
        try {
            selectPrepStmt = con.prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = selectPrepStmt.executeQuery();
            while (rs.next()) {
                group = null;
                group = new GroupInfo();
                group.setName(rs.getString("name"));
                group.setDescription(rs.getString("description"));
                group.setTableName(rs.getString("table_name"));
                group.setDeleted(rs.getBoolean("deleted"));
                allPersonalInfo.add(group);
            }

        } catch (SQLException ex) {
            Logger.getLogger(GroupModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        GroupInfo personalInfoArr[] = new GroupInfo[allPersonalInfo.size()];
        allPersonalInfo.toArray(personalInfoArr);
        return personalInfoArr;
    }

}
