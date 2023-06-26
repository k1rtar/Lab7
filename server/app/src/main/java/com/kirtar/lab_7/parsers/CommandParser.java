package com.kirtar.lab_7.parsers;


import java.util.Scanner;

import com.kirtar.lab_7.models.*;

/**
 * Parser for commands with Flat type parameter
 */

public class CommandParser
{

    public Flat ParseFlatObject(Scanner scanner)
    {
        String input;
        String name="";float x=0; int y=0;

        boolean flag = true;
        while (flag){
            System.out.println("Enter name: ");
            input = scanner.nextLine();
            try{
                name = input;
                flag=false;
            } 
            catch (Exception e){System.out.println("Error! The value cannot be loaded into the name field!"); flag=true;}
            if (name==null || name.trim().isEmpty()) {System.out.println("Error! The name field cannot be null!");flag=true;}
        } 
        System.out.println("Input coordinates");
        flag = true;
        while(flag){
            System.out.println("Enter x: ");
            input = scanner.nextLine();
            try{
                x = Float.parseFloat(input);
                flag=false;
            } 
            catch(Exception e){System.out.println("Error! Value cannot be loaded into field x!"); flag=true;}
            if (x<=-232){System.out.println("Error! The x field value must be greater than -232"); flag=true;}
        }

        flag = true;
        
        while(flag){
            System.out.println("Enter y:");
            input = scanner.nextLine();
            try{
                y = Integer.parseInt(input);
                flag=false;} 
            catch(Exception e) {System.out.println("Error! Value cannot be loaded into field y!"); flag=true;}
            if (y<=-150){System.out.println("Error! The value of the y field must be greater than -150"); flag=true;}
        }
        double area=0; int numberOfRooms=0; long numberOfBathrooms=0;
        flag = true;
        System.out.println("Enter area: ");
        while(flag){
            input = scanner.nextLine();
            try{
                area = Double.parseDouble(input);
                flag=false;} 
            catch(Exception e){System.out.println("Error! Value cannot be loaded into area field!"); flag=true;}
            if (area<=0){System.out.println("Error! The value of the area field must be greater than 0"); flag=true;}
        }
        flag = true;
        
        while (flag){
            System.out.println("Enter numberOfRooms: ");
            input = scanner.nextLine();
            try{
                numberOfRooms= Integer.parseInt(input);
                flag=false;} 
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the numberOfRooms field!"); flag=true;}
            if (numberOfRooms<=0){System.out.println("Error! The value of the numberOfRooms field must be greater than 0"); flag=true;}
        }
        flag = true;
        
        while (flag){
            System.out.println("Enter numberOfBathrooms: ");
            input = scanner.nextLine();
            try{
                numberOfBathrooms= Long.parseLong(input);
                flag=false;} 
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the numberOfBathrooms field!"); flag=true;}
            if (numberOfBathrooms<=0){System.out.println("Error! The value of the numberOfBathrooms field must be greater than 0"); flag=true;}
        }
        flag = true;
        View view=View.NORMAL; Transport transport=Transport.NORMAL;
        
        while (flag){
            System.out.println("Enter view(STREET,YARD,BAD,NORMAL,GOOD): ");
            input = scanner.nextLine();
            if (view==null || input.trim().isEmpty()){view = null;break;}
            if (input.isEmpty()){view=null;}
            else{
            try{view= View.valueOf(input);
                flag=false;}
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the view field!"); flag=true;}}
            }
        
        flag = true;
        while (flag)
        {
            System.out.println("Enter transport(FEW,NONE,LITTLE,NORMAL,ENOUGH): ");
            input = scanner.nextLine();
            if (transport==null || input.trim().isEmpty()){transport = null;break;}
            try{
                transport= Transport.valueOf(input);flag=false;} 
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the transport field!"); flag=true;}
        }
        flag = true;
        String houseName="";long year=0;Integer numberOfLifts=0;
        System.out.println("Input house");
        
        while (flag)
        {
            System.out.println("Enter name: ");
            input = scanner.nextLine();
            if (input==null || input.trim().isEmpty()){name = null;break;}
            if (input.isEmpty()){houseName = null;}
            else{
            try{houseName = input;flag=false;} 
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the name(House) field!"); flag=true;}}
        }
        flag = true;
        
        while(flag)
        {
            System.out.println("Enter year: ");
            input = scanner.nextLine();
            try{year = Long.parseLong(input);flag=false;}
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the year field!"); flag=true;}
            if (year<=0){System.out.println("Error! The value of the year field must be greater than 0");flag=true;}
        }
        flag = true;
        while(flag)
        {
            System.out.println("Enter numberOfLifts: ");
            input = scanner.nextLine();
            try{numberOfLifts = Integer.parseInt(input);flag=false;}
            catch(Exception e){System.out.println("Error! The value cannot be loaded into the numberOfLifts field!"); flag=true;}
            if (numberOfLifts<=0){System.out.println("Error! The value of the numberOfLifts field must be greater than 0"); flag=true;}
        }
        Flat flat = new Flat(name,new Coordinates(x,y), area, numberOfRooms,numberOfBathrooms,view,transport,new House(houseName,year,numberOfLifts));
        System.out.println(flat);
        return flat;
    }
}