package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;
/**
 * print information about the collection to the standard output stream (type, initialization date, number of elements, etc.)
 */

public class InfoCommand implements Command
{
    private String user;
    private Receiver receiver;
    public InfoCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.info();
    }
    public void setArg(Object element){}
    public void setUser(String user){
        this.user = user;
    }
        public String getUser()
    {
        return user;
    }
}


