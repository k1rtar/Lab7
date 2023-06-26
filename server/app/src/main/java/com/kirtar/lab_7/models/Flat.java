package com.kirtar.lab_7.models;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
  



public class Flat implements Comparable<Flat>,Serializable
{
    
    private Long id = ++IdFlat.lastId; 
    private String name; 
    private Coordinates coordinates; 
    LocalDateTime myLocalDateTime = LocalDateTime.of(2023, Month.MARCH, 1, 20, 55, 30);
    ZoneId vnZoneId = ZoneId.of("Europe/Moscow");
    private ZonedDateTime creationDate =  ZonedDateTime.of(myLocalDateTime, vnZoneId); 
    private double area; 
    private int numberOfRooms; 
    private long numberOfBathrooms; 
    private View view; 
    private Transport transport; 
    private House house; 
    private String username;

    public Flat()
    {
        super();
    }
    public Flat(String name, Coordinates coordinates,
    double area, int numberOfRooms, long numberOfBathrooms, View view, Transport transport, House house,ZonedDateTime creationDate)
    {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.view = view;
        this.transport = transport;
        this.house = house;
        this.creationDate = creationDate;
    }

    public Flat(String name, Coordinates coordinates,
    double area, int numberOfRooms, long numberOfBathrooms, View view, Transport transport, House house)
    {
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }
    public Long getId() 
    {
        return id; 
    }
    public String getName()
    {
        return name;
    }
    public Coordinates getCoordinates() 
    {
        return coordinates;
    }
    public ZonedDateTime getCreationDate()
    {
        return creationDate;
    }

    public double getArea()
    {
        return area;
    }

    public int getNumberOfRooms()
    {
        return numberOfRooms;
    }

    public long getNumberOfBathrooms()
    {
        return numberOfBathrooms;
    }
    public View getView()
    {
        return view;
    }
    
    public Transport getTransport()
    {
        return transport;
    }

    public House getHouse()
    {
        return house;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) 
    {
        this.coordinates = coordinates;
    }
    public void setCreationDate(ZonedDateTime creationDate)
    {
        this.creationDate = creationDate;
    }

    public void setArea(double area)
    {
        this.area = area;
    }

    public void setNumberOfRooms(int numberOfRooms)
    {
        this.numberOfRooms = numberOfRooms;
    }

    public void setNumberOfBathrooms(long numberOfBathrooms)
    {
        this.numberOfBathrooms = numberOfBathrooms;
    }
    public void setView(View view)
    {
        this.view = view;
    }
    
    public void setTransport(Transport transport)
    {
        this.transport = transport;
    }

    public void setHouse(House house)
    {
        this.house = house;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getUsername()
    {
        return username;
    }

    @Override
    public int compareTo(Flat flat)
    {
        return id.compareTo(flat.getId());
    }

    @Override 
    public String toString()
    {
        return String.format("Flat(id=%d,name=%s,coordinates=%s,area=%f,numberOfRooms=%d,numberOfBathrooms=%d,view=%s,transport=%s,house=(%s),creationDate = %s,username = %s)",
        id,name,coordinates.toString(),area,numberOfRooms,numberOfBathrooms,view,transport,house.toString(),creationDate,username);
    }


}
