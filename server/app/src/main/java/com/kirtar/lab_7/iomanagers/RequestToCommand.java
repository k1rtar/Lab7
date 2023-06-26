package com.kirtar.lab_7.iomanagers;
import java.util.HashMap;
import java.util.Map;

import com.kirtar.lab_7.commands.*;
import com.kirtar.lab_7.commands.concrete.AddCommand;
import com.kirtar.lab_7.commands.concrete.ClearCommand;
import com.kirtar.lab_7.commands.concrete.FilterNameCommand;
import com.kirtar.lab_7.commands.concrete.GroupCountingCommand;
import com.kirtar.lab_7.commands.concrete.HelpCommand;
import com.kirtar.lab_7.commands.concrete.InfoCommand;
import com.kirtar.lab_7.commands.concrete.LoginUserCommand;
import com.kirtar.lab_7.commands.concrete.PrintUniqueViewCommand;
import com.kirtar.lab_7.commands.concrete.RegisterUserCommand;
import com.kirtar.lab_7.commands.concrete.RemoveByIdCommand;
import com.kirtar.lab_7.commands.concrete.RemoveFirstCommand;
import com.kirtar.lab_7.commands.concrete.RemoveLowerCommand;
import com.kirtar.lab_7.commands.concrete.ShowCommand;
import com.kirtar.lab_7.commands.concrete.UpdateCommand;

import com.kirtar.lab_7.messages.*;



public class RequestToCommand {
    private Receiver receiver;
    public Map<String,Command> commands = new HashMap<String,Command>();
    public RequestToCommand(Receiver receiver)
    {
        this.receiver = receiver;
        registerRequest(new ClientRequest<String>("help","",""),new HelpCommand(this.receiver));
        registerRequest(new ClientRequest<String>("info","",""), new InfoCommand(this.receiver));
        registerRequest(new ClientRequest<String>("clear","",""), new ClearCommand(this.receiver));
        registerRequest(new ClientRequest<String>("show","",""), new ShowCommand(this.receiver));
        registerRequest(new ClientRequest<String>("remove_first","",""),new RemoveFirstCommand(this.receiver));
        registerRequest(new ClientRequest<String>("group_counting_by_area","",""), new GroupCountingCommand(this.receiver));
        registerRequest(new ClientRequest<String>("print_unique_view","",""),new PrintUniqueViewCommand(this.receiver));
        
        registerRequest(new ClientRequest<String>("add","",""),new AddCommand(this.receiver));
        registerRequest(new ClientRequest<String>("filter_starts_with_name","",""),new FilterNameCommand(this.receiver));
        registerRequest(new ClientRequest<String>("remove_by_id","",""),new RemoveByIdCommand(this.receiver));
        registerRequest(new ClientRequest<String>("remove_lower","",""),new RemoveLowerCommand(this.receiver));
        registerRequest(new ClientRequest<String>("update","",""),new UpdateCommand(this.receiver));
        registerRequest(new ClientRequest<String>("registerUser","",""), new RegisterUserCommand(this.receiver));
        registerRequest(new ClientRequest<String>("loginUser","",""), new LoginUserCommand(this.receiver));
    }
    public Command requestToCommand(ClientRequest<?> clientRequest)
    {

        Command command = commands.get(clientRequest.getCommandType());
        try{
            command.setArg(clientRequest.getCommandArgument());
            command.setUser(clientRequest.getUserInfo());
        }
        catch (Exception e){
        }
        return command;

    }
    public void registerRequest (ClientRequest<?> newRequest, Command cmd)
    {
        if(commands.containsKey(newRequest.getCommandType())) return;
            //throw new IllegalArgumentException("A command with the same name already exists!");
        commands.put(newRequest.getCommandType(), cmd);
    }
}