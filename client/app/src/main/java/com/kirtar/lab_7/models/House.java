package com.kirtar.lab_7.models;

import java.io.Serializable;



public class House implements Serializable{

    private String name; 
    private long year; 
    private Integer numberOfLifts; 

    public House()
    {
        super();
    }
    public House(String name,long year,Integer numberOfLifts)
    {
        this.name = name;
        this.year = year;
        this.numberOfLifts = numberOfLifts;
    }

    public String getName()
    {
        return name;
    }
    public Long getYear() 
    {
        return year; 
    }
    public Integer getNumberOfLifts()
    {
        return numberOfLifts;
    }

    @Override
    public String toString()
    {
        return String.format("House name = %s, year = %d,numberOfLifts = %d",name,year,numberOfLifts);
    }

}