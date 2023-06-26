package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;

/**
 * print to standard output all elements of the collection in string representation
 */

public class ShowCommand implements Command
{
    private String user;
    private Receiver receiver;
    public ShowCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.show();
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