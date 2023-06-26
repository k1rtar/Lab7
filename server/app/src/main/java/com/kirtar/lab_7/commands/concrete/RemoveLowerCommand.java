package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;
import com.kirtar.lab_7.models.Flat;
/**
 *  remove from the collection all elements smaller than the given one
 */

public class RemoveLowerCommand implements Command{
    private String user;
    private Receiver receiver;
    private Flat flat;
    public RemoveLowerCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }
    public RemoveLowerCommand(Receiver receiver,Flat flat)
    {
        this.receiver = receiver;
        this.flat = flat;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.removeLower(flat);
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
