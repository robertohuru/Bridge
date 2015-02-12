/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.DraftModel;

/**
 *
 * @author DELL
 */
public class DraftInfo {
    private String message;
    private String reciever;
    private String dateSent;
    private DraftModel model;
    public DraftInfo(){
        model = new DraftModel();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }
    public int save(){
        return model.insert(this);        
    }
    public int delete(){
        return model.delete(this);
    }
    
    
    
}
