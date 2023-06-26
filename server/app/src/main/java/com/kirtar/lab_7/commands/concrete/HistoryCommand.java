package com.kirtar.lab_7.commands.concrete;

import java.util.LinkedList;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;

/**
 * print the last 11 commands (without their arguments)
 */

public class HistoryCommand implements Command{
    private String user;
    private Receiver receiver;

    private LinkedList<String> lastCommands;

    public HistoryCommand(Receiver receiver,LinkedList<String> lastCommands)
    {
        this.receiver = receiver;
        this.lastCommands = lastCommands;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.history(lastCommands);
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
