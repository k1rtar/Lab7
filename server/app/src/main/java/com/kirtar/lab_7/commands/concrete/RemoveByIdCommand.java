package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;

/**
 * remove element from collection by its id
 */

public class RemoveByIdCommand implements Command
{
    private String user;
    private Receiver receiver;
    private Long id;
    public RemoveByIdCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }
    public RemoveByIdCommand(Receiver receiver, Long id)
    {
        this.receiver = receiver;
        this.id = id;
    }
    @Override
    public ServerResponse execute()
    {
        return receiver.removeById(id,user);
    }

    public void setArg(Object id){
        this.id = (Long) id;
    }
    public void setUser(String user){
        this.user = user;
    }
        public String getUser()
    {
        return user;
    }
}
