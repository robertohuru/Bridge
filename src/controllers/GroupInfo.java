/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.GroupModel;

/**
 *
 * @author DELL
 */
public class GroupInfo {
    private String name;
    private String description;
    private String tableName;
    private boolean deleted;
    private GroupModel model;
    public GroupInfo(){
        model = new GroupModel();
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public int save(){
        return model.insertGroup(this);
    }
    
}
