
package com.kirtar.lab_7.iomanagers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;  
import com.fasterxml.jackson.databind.JsonMappingException; 
import com.fasterxml.jackson.databind.*; 
import com.fasterxml.jackson.datatype.jsr310.*;
import com.kirtar.lab_7.models.Flat;
import com.kirtar.lab_7.models.IdFlat;

import java.io.FileNotFoundException;

/**
 * Parsing an XML file into a collection
 */

public class InputFileManager
{
    public static String path;
    public Queue<Flat> XMLtoFlat(String pathValue) 
    {
        Queue<Flat> collection = new PriorityQueue<Flat>();
        try {
            path = pathValue;
            FileInputStream inputFile = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFile));
            String responsetmp = new String();
            for (String line; (line = br.readLine()) != null; responsetmp += line);
            String response = responsetmp.replaceAll(" ","");
            br.close();
            System.out.println(response);    
            if (responsetmp.indexOf("creationDate")!=-1)
            {
                int start = 0;
                int end = response.indexOf("</Flat>")+7;
                while (end!=-1)
                {
                    String currentSubString = response.substring(start,end);
                    response = response.substring(end,response.length());
                    start = 0;
                    end = response.indexOf("</Flat>")+7;
                    ObjectMapper xmlmapper = new XmlMapper();
                    xmlmapper.registerModule(new JavaTimeModule());
                    xmlmapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    Flat value = xmlmapper.readValue(currentSubString,Flat.class);
                    value.setId(IdFlat.lastId); 
                    collection.add(value);
    
                } 
            }
            
            int start = 0;
            int end = response.indexOf("</Flat>")+7;
            while (end!=-1)
            {
                String currentSubString = response.substring(start,end);
                response = response.substring(end,response.length());
                start = 0;
                end = response.indexOf("</Flat>")+7;
                ObjectMapper xmlmapper = new XmlMapper();
                Flat value = xmlmapper.readValue(currentSubString,Flat.class);
                value.setId(IdFlat.lastId); 
                collection.add(value);

            } 
            
        }
        catch (StringIndexOutOfBoundsException e)
        {
        }
        catch (InvalidFormatException e)
        {
            System.out.println("Edit the file, y in coordinates must be integer");   
        }

        catch (JsonMappingException e) {  
            e.printStackTrace();
            System.out.println("Error loading to collection. Check if the data in the file is correct");
        } catch (JsonProcessingException e) { 
            e.printStackTrace();   
            System.out.println("Error loading to collection. Check if the data in the file is correct");
        }   
        catch (FileNotFoundException e) {
            System.out.println("Error! File to load data into collection not found");
            
        }
        catch (Exception e) {
            System.out.println("Error loading to collection. Check if the data in the file is correct");
            
        }
        System.out.println(collection.size());
        return collection;
    }


        
       
        
    
}