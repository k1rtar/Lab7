package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;

/**
 * display elements whose name field value starts with the given substring
 */

public class FilterNameCommand implements Command{
    private String user;
    private Receiver receiver;
    private String name;

    public FilterNameCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }
    public FilterNameCommand(Receiver receiver,String name)
    {
        this.receiver = receiver;
        this.name = name;
    }

    @Override
    public ServerResponse execute()
    {
        return receiver.filterStartsWithName(name);
    }


    public void setArg(Object name){
        this.name = (String) name;
    }
        public void setUser(String user){
        this.user = user;
    }
        public String getUser()
    {
        return user;
    }
}
