/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.DeletedModel;

/**
 *
 * @author DELL
 */
public class DeletedInfo {

    private String message;
    private String sender;
    private String dateSent;
    private DeletedModel model;

    public DeletedInfo() {
        model = new DeletedModel();
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
    public int save(){
        return model.insert(this);
    }
    public int delete(){
        return model.delete(this);
    }

}
