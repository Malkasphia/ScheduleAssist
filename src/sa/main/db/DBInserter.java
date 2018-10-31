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
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;

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
    
    public void customerInsert (String customerName, int addressId, short active ) {

        String insertSQL = "INSERT INTO customer"
                            + "(customerName,addressId,active,createDate,createdBy,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?)";
        
        
        
        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL)) {
            stmt.setString(1, customerName);
            stmt.setInt(2, addressId);
            stmt.setShort(3,active);
            stmt.setTimestamp(4,getCurrentTimeStamp());
            stmt.setString(5, ScheduleAssist.getUserLoggedIn());
            stmt.setString(6, ScheduleAssist.getUserLoggedIn());
            
            int recordsEffected = stmt.executeUpdate();
            System.out.println ("Number of Rows Effected " + recordsEffected + " " + customerName + " " +  addressId +  " " + active + " " +  getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        
}
    //method for inserting appointments into database. Uses Scheduler interface for lambda expressions.
    public void appointmentInsert () {
        
    }
 

    
    public static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
    
}