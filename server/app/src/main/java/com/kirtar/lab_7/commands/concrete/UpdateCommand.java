package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;
import com.kirtar.lab_7.models.Flat;

/**
 * update the value of the collection element whose id is equal to the given one
 */

public class UpdateCommand implements Command
{
    private String user;
    private Receiver receiver;
    private Flat flat;
    public UpdateCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }
    public UpdateCommand(Receiver receiver, Flat flat)
    {
        this.receiver = receiver;
        this.flat = flat;
    }
    @Override
    public ServerResponse execute()
    {
        return receiver.update(flat,user);
    }

    public void setArg(Object flat){
        this.flat = (Flat) flat;
    }
    public void setUser(String user){
        this.user = user;
    }
        public String getUser()
    {
        return user;
    }

}
