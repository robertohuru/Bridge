/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.InBoxModel;
import org.apache.derby.client.am.DateTime;

/**
 *
 * @author DELL
 */
public class InboxController {
    private int id;
    private String message;
    private String sender;
    private String dateSent;
    private boolean deleted;
    private InBoxModel model;
    public InboxController(){
        this.model = new InBoxModel();
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public int save(){
        return model.insert(this);
    }
    public int delete(){
        return model.delete(this);
    }
    
}
