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
public class GroupMembers {
    private String contact;
    private boolean deleted;
    private GroupModel model;
    public GroupMembers(){
        model = new GroupModel();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public int save(String table){
        return model.insert(this, table);
    }
    public int delete(String name){
        return model.deleteMember(this, name);
    }
    
    
}
