package com.kirtar.lab_7.commands;

import java.util.LinkedList;

/**
 * Invoker class, responsible for calling commands
 */

public class Invoker
{
    

    public void executeAllCommands(LinkedList<Command> commandList)
    {
        for (Command c: commandList)
        {
            c.execute();
        }
        commandList.clear();
    }

    public void executeFirstCommand(LinkedList<Command> commandList)
    {
        Command current = commandList.getFirst();
        commandList.removeFirst();
        current.execute();
    }

    public void executeLastCommand(LinkedList<Command> commandList)
    {
        commandList.getLast().execute();
        commandList.removeLast();
    }



}