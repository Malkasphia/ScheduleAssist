/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mal
 */
public class DBInserter {
    //DBInserter allows SQL insertion into customer records of database
    //Statement stmt = conn.createStatement();
//Class Members
    private static DBConnector Connector;


//Class Methods
    
    /* 
    customerInsert allows the user to insert new customers into the customer table. It takes
    a String for customerName, an int for addressId, and a short for addressID, and active status. It inserts data as 
    a new row and documents the change in the database by the user who logged in. It uses NOW() for the createDate.
    */
    
    //Method has not been constructed
    public void customerInsert (String customerName, int addressID, short active ) {
    //comment
    
    java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        String query = "INSERT INTO CUSTOMER "
                + "(customerName,addressId,active,createDate,createdBy,lastUpdateBy) VALUES"
                + "("+ customerName +"," + addressID + "," + active + "," + timestamp + ",Kyle" + ",Kyle" +")";
        
        
        try (Statement stmt = DBConnector.startConnecting().createStatement()) {
            int insertedRecordsCount = stmt.executeUpdate(query);
            System.out.println("Number of rows updated -" + insertedRecordsCount + "Inserted - "+ customerName +"," + addressID +"," + active + "," + timestamp);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
}