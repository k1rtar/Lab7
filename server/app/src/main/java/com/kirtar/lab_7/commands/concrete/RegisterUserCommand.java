package com.kirtar.lab_7.commands.concrete;

import com.kirtar.lab_7.commands.Command;
import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.messages.ServerResponse;

public class RegisterUserCommand implements Command{
    private String user;
    private Receiver receiver;
    public RegisterUserCommand(Receiver receiver)
    {
        this.receiver = receiver;
    }
    @Override
    public ServerResponse execute()
    {
        return receiver.registerUser(user);
    }
    public void setArg(Object user)
    {
        this.user = (String) user;
    }
    public void setUser(String user){
        this.user = user;
    }
        public String getUser()
    {
        return user;
    }
    
}
