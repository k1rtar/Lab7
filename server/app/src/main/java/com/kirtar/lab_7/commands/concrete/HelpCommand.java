package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;

/**
 * display help on available commands
 */

public class HelpCommand implements Command
{
    private String user;
    private Receiver receiver;
    public HelpCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.help();
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


