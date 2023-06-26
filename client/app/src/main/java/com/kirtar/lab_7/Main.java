package com.kirtar.lab_7;


import java.util.Scanner;

import com.kirtar.lab_7.iomanagers.*;
import com.kirtar.lab_7.network.ServerConnection;
import com.kirtar.lab_7.parsers.*;


/**
 * The main class through which the entire program is launched
 */

public class Main
{
    public static InputConsoleManager runner;
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        CommandParser commandParser = new CommandParser();
        ExecuteScriptManager executeManager = new ExecuteScriptManager();
        ServerConnection connection = new ServerConnection(8888);
        System.out.println("Client started on port 8888");
        runner = new InputConsoleManager(commandParser,connection,executeManager);
        ExecuteScriptCommand.runner = runner;
        runner.run(scanner);
        
       

    }
}