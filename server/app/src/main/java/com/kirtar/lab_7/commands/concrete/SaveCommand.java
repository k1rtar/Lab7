package com.kirtar.lab_7.commands.concrete;


import java.util.Queue;

import com.kirtar.lab_7.commands.Receiver;
import com.kirtar.lab_7.models.Flat;

/**
 * save collection to file
 */

public class SaveCommand 
{
    private Queue<Flat> collection;
    private Receiver receiver;
    private String path;
    public SaveCommand(Receiver receiver, Queue<Flat> collection,String path)
    {
        this.receiver = receiver;
        this.collection = collection;
        this.path = path;
    }
    
    public void execute()
    {
        //receiver.save(collection,path);
    }
}
