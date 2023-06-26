package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;
/**
 * group the elements of the collection by the value of the area field, display the number of elements in each group
 */

public class GroupCountingCommand implements Command
{
    private String user;
    private Receiver receiver;
    public GroupCountingCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.groupCountingByArea();
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


