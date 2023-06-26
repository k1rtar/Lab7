package com.kirtar.lab_7.commands;

import java.util.Queue;
import java.util.LinkedList;


import com.kirtar.lab_7.Main;
import com.kirtar.lab_7.messages.*;
import com.kirtar.lab_7.models.Flat;
import com.kirtar.lab_7.models.View;
import com.kirtar.lab_7.database.Database;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet; 
import java.util.stream.Collectors;




/**
 * Implementation of all commands, executor class
 */
public class Receiver
{
    private Queue<Flat> collection;

    public Receiver(Queue<Flat> collection)
    {
        this.collection = collection;
    }

    public synchronized ServerResponse add(Flat element,String user)
    {
        Long id = 0L;
        for (Flat el:collection)
        {
            if (el.getId()>id)
            {
                id = el.getId();
            }
        }
        element.setId(++id);
        Flat createdFlat = null;
        try{ createdFlat= Database.createFlat(element,user.split(" ")[0]);}
        catch (SQLException e){System.out.println("Error adding item to database!");}
        if (createdFlat!=null){collection.add(element);return new ServerResponse(ResultStatus.OK, "ADD");}
        
        return new ServerResponse(ResultStatus.ERROR, "Failed to add object.");
    }

    public synchronized ServerResponse clear()
    {
        boolean cleared = Database.deleteAllFlats();
        if (cleared){collection.clear();return new ServerResponse(ResultStatus.OK,"CLEAR");}
        return new ServerResponse(ResultStatus.ERROR,"Error! Failed to clear collection!");
    }

    public synchronized ServerResponse help()
    {
    
        String message = "help : Display help on available commands\ninfo : Display information about the collection (type, initialization date, number of elements, etc.) on standard output\nshow : Display all elements of the collection in string representation on standard output } : add a new element to the collection\nupdate id {element} : update the value of the collection element whose id is equal to the given\nremove_by_id id : remove the element from the collection by its id\nclear : clear the collection\nsave : save the collection to a file\nexecute_script file_name : read and execute the script from the specified file. The script contains commands in the same form as the user enters them in interactive mode.\nexit : terminate the program (without saving to a file)\nremove_first : remove the first element from the collection\nremove_lower {element} : remove from the collection all elements less than than the given one\nhistory : print the last 11 commands (without their arguments)\ngroup_counting_by_area : group the elements of the collection by the area field value, display the number of elements in each group\nfilter_starts_with_name name : display the elements whose name field value starts with the given substring\nprint_unique_view : display unique values of the view field of all elements in the collection";
        return new ServerResponse(ResultStatus.OK,message);
    }

    public synchronized ServerResponse show()
    {
        String message = "Collection elements (number of elements): " + collection.size() + ": \n"
        + collection.stream().map(e -> e + "\n").collect(Collectors.joining());
        return new ServerResponse(ResultStatus.OK, message);
    }

    public synchronized ServerResponse history(LinkedList<String> lastCommands)
    {
        String message = "";
        message+="Last 11 commands: ";
        for(String command: lastCommands)
        {
            message+="\n"+command;
        }
        return new ServerResponse(ResultStatus.OK,message);

    }

    public synchronized ServerResponse removeFirst()
    {
        collection.poll();
        return new ServerResponse(ResultStatus.OK,"REMOVE_FIRST");
    }

    public synchronized ServerResponse update(Flat flat,String user)
    {
        boolean updated = Database.updateFlat(user, flat);
        if (updated){
        collection.stream()
        .filter(el -> el.getId().equals(flat.getId()) && el.getUsername().equals(flat.getUsername()))
        .forEach(el -> {
            el.setName(flat.getName());
            el.setCoordinates(flat.getCoordinates());
            el.setArea(flat.getArea());
            el.setNumberOfRooms(flat.getNumberOfRooms());
            el.setNumberOfBathrooms(flat.getNumberOfBathrooms());
            el.setView(flat.getView());
            el.setTransport(flat.getTransport());
            el.setHouse(flat.getHouse());     
        });
        return new ServerResponse(ResultStatus.OK,"UPDATE");}
        else {
            return new ServerResponse(ResultStatus.ERROR, "Error! Failed to update object!");
        }
    }
    public synchronized ServerResponse removeById(Long id,String user)
    {
        boolean removedById = Database.deleteFlatById(id, user);
        if (removedById){
        for (Flat el:collection)
        {
            if (el.getId().equals(id) && user.split(" ")[0].equals(el.getUsername()))
            {
                el.setId(-100000000L);
                collection.poll();
                break;
            }
        }
        return new ServerResponse(ResultStatus.OK,"REMOVE_BY_ID");}
        else {
            return new ServerResponse(ResultStatus.ERROR,"Error! Failed to delete element by id");
        }
    }


    public synchronized ServerResponse info()
    {
        String message = "---Collection Information---\n"+"Collection type: "+collection.getClass().toString()+'\n'+"Initialization date: "+Main.date+"\nAmount of elements: "+collection.size();
        return new ServerResponse(ResultStatus.OK,message);
        
    }

    public synchronized ServerResponse removeLower(Flat flat)
    {
        Iterator<Flat> it = collection.iterator();
        while (it.hasNext())
        {
            Flat nextFlat = it.next();
            if (flat.getNumberOfRooms()>nextFlat.getNumberOfRooms())
            {
                it.remove();
            }
        }
        return new ServerResponse(ResultStatus.OK,"REMOVE_LOWER");
        
    }

    public synchronized ServerResponse groupCountingByArea()
    {

        Map<Double,Integer> dictionary = new HashMap<Double,Integer>();
        for(Flat el: collection)
        {
            dictionary.put(el.getArea(),0);
        }
        for(Flat el: collection)
        {
            dictionary.put(el.getArea(),dictionary.get(el.getArea())+1);
        }
        String message = "";
        for(Map.Entry<Double, Integer> item : dictionary.entrySet()){
         
            message+=String.format("Area: %f  Amount of elements: %d \n",item.getKey(),item.getValue());
        }
        return new ServerResponse(ResultStatus.OK,message);
    }

    public synchronized ServerResponse filterStartsWithName(String name)
    {
        System.out.println(name);
        String message = collection.stream()
        .filter(el -> name.length() <= el.getName().length())
        .filter(el -> el.getName().substring(0, name.length()).equals(name))
        .map(el -> el + "\n")
        .collect(Collectors.joining());
        return new ServerResponse(ResultStatus.OK,message+"FILTER_STARTS_WITH_NAME");
    }

    public synchronized ServerResponse printUniqueView()
    {
        String message="";
        HashSet<View> viewSet = new HashSet<View>();
        for (Flat el:collection)
        {
            viewSet.add(el.getView());
        }
        for (View el:viewSet)
        {
            message+=(el.toString()+"\n");
        }
        message+="PRINT_UNIQUE_VIEW";
        return new ServerResponse(ResultStatus.OK, message);
    }
    public synchronized ServerResponse registerUser(String user)
    {
        if (Database.createUser(user)!=null){
            return new ServerResponse(ResultStatus.OK, user);
        }
        
        
        return new ServerResponse(ResultStatus.ERROR, "Registration error! Try again! A user with the same name already exists!");
    }
    public synchronized ServerResponse loginUser(String user)
    {
        if (Database.readUser(user)){
            return new ServerResponse(ResultStatus.OK, user);
        }
        return new ServerResponse(ResultStatus.ERROR, "User authorization error! Check if the username and password are correct.");
    }

}