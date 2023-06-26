package com.kirtar.lab_7.iomanagers;

import java.util.Queue;
import java.util.Scanner;

import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.commands.concrete.SaveCommand;
import com.kirtar.lab_7.models.Flat;

public class ServerInput extends Thread{
    private Receiver receiver;
    private Queue<Flat> collection;
    private String path = InputFileManager.path;;
    public ServerInput(Receiver receiver,Queue<Flat> collection)
    {
        this.receiver = receiver;
        this.collection = collection;
    }
    public void run()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String input = scanner.nextLine();

        if ("exit".equals(input)) {
                System.out.println("EXIT");
                SaveCommand saveCommand = new SaveCommand(receiver, collection,path);
                saveCommand.execute();
                scanner.close();
                System.exit(0);}
            else
            {
                System.out.println("Invalid command. Enter save to save the collection to a file");
            }
        }
    }
    
}

