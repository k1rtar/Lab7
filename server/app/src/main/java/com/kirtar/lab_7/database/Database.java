package com.kirtar.lab_7.database;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


import com.kirtar.lab_7.models.Flat;

public class Database {
    private static final String url = "jdbc:postgresql://localhost:5432/lab7prog1";
    private static final String user = "postgres";
    private static final String password = "7labaPoProge";

    
    private static Connection connection = null;

    static{
        try {
            Database.connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized static Connection getDataBaseConnection() {
        return connection;
    }
    
    
    
    

    public static synchronized Flat createFlat(Flat flat,String username) throws SQLException {
            if (connection == null) {
        throw new SQLException("Connection is null. Make sure it is initialized properly.");}
        String insertQuery = "INSERT INTO flats (name,coordinates_x,coordinates_y,creation_date,area,number_of_rooms,number_of_bathrooms,view,transport,house_name,house_year,house_number_of_lifts,username,user_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, flat.getName());
                statement.setFloat(2, flat.getCoordinates().getX());
                statement.setInt(3, flat.getCoordinates().getY());
                
                Timestamp creationTimestamp = Timestamp.from(flat.getCreationDate().toInstant());
                statement.setTimestamp(4, creationTimestamp);
       
                statement.setDouble(5, flat.getArea());
                statement.setInt(6, flat.getNumberOfRooms());
                statement.setLong(7, flat.getNumberOfBathrooms());
        
                if (flat.getView()==null) statement.setString(8, "");
                else statement.setString(8, flat.getView().name()); 
                if (flat.getTransport()==null) statement.setString(9, "");
                else statement.setString(9, flat.getTransport().name()); 
                if (flat.getHouse().getName()==null) statement.setString(10, "");
                else statement.setString(10, flat.getHouse().getName());
                statement.setLong(11, flat.getHouse().getYear());
                statement.setInt(12, flat.getHouse().getNumberOfLifts());
                statement.setString(13,flat.getUsername());
                int id = readUserIdByUsername(username);
                System.out.println(id);
                statement.setInt(14, id); 
                statement.executeUpdate();
                return flat;
            }
        
    }

     public static synchronized String createUser(String user) {
        try {
            System.out.println("createUser started");
            PreparedStatement statement = Database.getDataBaseConnection().prepareStatement("SELECT EXISTS (SELECT * FROM USERS WHERE username=?)");
            statement.setString(1, user.split(" ")[0]);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            
            if (!resultSet.getBoolean(1)) {
                String countQuery = "SELECT COUNT(*) FROM users";
                PreparedStatement countStatement = connection.prepareStatement(countQuery);
                ResultSet countResultSet = countStatement.executeQuery();
                countResultSet.next();
                int count = countResultSet.getInt(1);
                countResultSet.close();
                countStatement.close();
                count++; 

                PreparedStatement statementInsertUser = Database.getDataBaseConnection().prepareStatement("INSERT INTO USERS (id,username, password) VALUES (?, ?, ?)");//, Statement.RETURN_GENERATED_KEYS);
                
                statementInsertUser.setInt(1, count);
                statementInsertUser.setString(2, user.split(" ")[0]);
                String password = user.split(" ")[1];
                String pepper = "*11&jKqH(#";
                password = pepper + password;
                byte[] hashedBytes = MessageDigest.getInstance("SHA-224").digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashedBytes) {
                    String hex = Integer.toHexString(0xFF & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                        }
                        hexString.append(hex);
                    }
  
                
                statementInsertUser.setString(3, hexString.toString());
                statementInsertUser.executeUpdate();

                return user;

            } else {
                return null;
            }
        
        } catch (SQLException | NoSuchAlgorithmException exp) {
            exp.printStackTrace();
            return null;
        }
    }
 
        public static boolean readUser(String user)
        {
            try {
                PreparedStatement statement = Database.getDataBaseConnection().prepareStatement("SELECT EXISTS (SELECT * FROM USERS WHERE username=? AND password=?)");
                statement.setString(1, user.split(" ")[0]);
                String password = user.split(" ")[1];
                String pepper = "*11&jKqH(#";
                password = pepper + password;
                byte[] hashedBytes = MessageDigest.getInstance("SHA-224").digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashedBytes) {
                    String hex = Integer.toHexString(0xFF & b);
                    if (hex.length() == 1) {
                        hexString.append('0');
                        }
                        hexString.append(hex);
                    }
                statement.setString(2, hexString.toString());
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
            
            if (resultSet.getBoolean(1)) {
                return true;
            }
            else {
                return false;
            }}
            catch (SQLException | NoSuchAlgorithmException exc)
            {
                exc.printStackTrace();
                return false;
            }
        }
        public static int readUserIdByUsername(String username) {
        try {
            String query = "SELECT id FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            int userId = -1; 

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }


            System.out.println("return userId");
            return userId;
        } catch (SQLException e) {
            System.out.println("кач");
            e.printStackTrace();
             return -1;
        }

    }

    public static synchronized boolean deleteAllFlats()
    {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE flats RESTART IDENTITY");
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static synchronized boolean deleteFlatById(Long id, String user)
    {
        try{
            PreparedStatement statement = Database.getDataBaseConnection().prepareStatement("DELETE FROM flats WHERE id=? AND username=?");
            statement.setLong(1, id);
            statement.setString(2, user.split(" ")[0]);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static synchronized boolean updateFlat(String user,Flat flat)
    {
        try{
            PreparedStatement statement = Database.getDataBaseConnection().prepareStatement("UPDATE flats SET name=?,coordinates_x=?,coordinates_y=?,creation_date=?,area=?,number_of_rooms=?,number_of_bathrooms=?,view=?,transport=?,house_name=?,house_year=?,house_number_of_lifts=?,username=? WHERE username=? AND id=?");
                statement.setString(1, flat.getName());
                statement.setFloat(2, flat.getCoordinates().getX());
                statement.setInt(3, flat.getCoordinates().getY());
                Timestamp creationTimestamp = Timestamp.from(flat.getCreationDate().toInstant());
                statement.setTimestamp(4, creationTimestamp);
 
                statement.setDouble(5, flat.getArea());
                statement.setInt(6, flat.getNumberOfRooms());
                statement.setLong(7, flat.getNumberOfBathrooms());
       
                if (flat.getView()==null) statement.setString(8, "");
                else statement.setString(8, flat.getView().name()); 
                if (flat.getTransport()==null) statement.setString(9, "");
                else statement.setString(9, flat.getTransport().name()); 
                if (flat.getHouse().getName()==null) statement.setString(10, "");
                else statement.setString(10, flat.getHouse().getName());
                statement.setLong(11, flat.getHouse().getYear());
                statement.setInt(12, flat.getHouse().getNumberOfLifts());
                statement.setString(13,flat.getUsername());
                statement.setString(14,flat.getUsername());
                statement.setLong(15,flat.getId());
            statement.executeUpdate();
        
        return true;  }
        catch (SQLException exc)
        {
            exc.printStackTrace();
            return false;
        }
    

    }


}
