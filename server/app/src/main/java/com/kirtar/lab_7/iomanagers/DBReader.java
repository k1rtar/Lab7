package com.kirtar.lab_7.iomanagers;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.Queue;

import java.sql.Statement;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;


import com.kirtar.lab_7.models.Coordinates;
import com.kirtar.lab_7.models.Flat;
import com.kirtar.lab_7.models.House;
import com.kirtar.lab_7.models.Transport;
import com.kirtar.lab_7.models.View;

public class DBReader
{
    public static Queue<Flat> loadDataFromDBtoCollection(Connection dbConnection)
    {
        Queue<Flat> collection = new PriorityQueue<Flat>();
        try
        {
            String selectQuery = "SELECT * FROM flats";
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Float x = resultSet.getFloat("coordinates_x");
                int y = resultSet.getInt("coordinates_y");
                LocalDateTime localDateTime = resultSet.getTimestamp("creation_date").toLocalDateTime();
                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime creationDate = ZonedDateTime.of(localDateTime, zoneId);
                double area = (double) resultSet.getFloat("area");
                int numberOfRooms = resultSet.getInt("number_of_rooms");
                Long numberOfBathrooms = resultSet.getLong("number_of_bathrooms");
                View view = null;
                if (resultSet.getString("view").length()>0) {view = View.valueOf(resultSet.getString("view"));}
                Transport transport = null;
                if (resultSet.getString("transport").length()>0){transport = Transport.valueOf(resultSet.getString("transport"));}
                String houseName = resultSet.getString("house_name");
                long houseYear = resultSet.getLong("house_year");
                Integer houseNumberOfLifts = resultSet.getInt("house_number_of_lifts");
                String username = resultSet.getString("username");
                
                

                Flat flat = new Flat();
                flat.setId(id);
                flat.setName(name);
                flat.setCoordinates(new Coordinates(x, y));
                flat.setCreationDate(creationDate);
                flat.setArea(area);
                flat.setNumberOfRooms(numberOfRooms);
                flat.setNumberOfBathrooms(numberOfBathrooms);
                flat.setView(view);
                flat.setTransport(transport);
                flat.setHouse(new House(houseName, houseYear, houseNumberOfLifts));
                flat.setUsername(username);

                collection.add(flat);

            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        for (Flat el : collection) {
            System.out.println(el);
        }
        return collection;
    }
}