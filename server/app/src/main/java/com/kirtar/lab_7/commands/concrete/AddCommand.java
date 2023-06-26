package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;
import com.kirtar.lab_7.models.Flat;

/**
 * add a new element to the collection
 */

public class AddCommand implements Command{

    private Receiver receiver;
    private Flat element;
    private String user;

    public AddCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }
    public AddCommand(Receiver receiver, Flat element)
    {
        this.receiver = receiver;
        this.element = element;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.add(element,user);
    }

    public void setArg(Object element){
        this.element = (Flat) element;
    }
    public void setUser(String user){
        this.user = user;
    }
    public String getUser()
    {
        return user;
    }
    
}
