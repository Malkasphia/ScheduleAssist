/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;

/**
 *
 * @author Mal
 */
public class DBUpdater {
    // DBUpdater allows the updating of database records.
    //Class Members
    private static DBConnector Connector;
    
            //Display all Customers in Database - Done
            //Ask to enter which Customer to alter by ID number - Done
            //Ask what change is needed (customerName, addressId, active status)
            //Ask for change details appropriate to the field.
            //Update Customer
    
    //Class Methods
        public void customerUpdate () {

        String selectSQL = "SELECT customerId, customerName FROM customer";

        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                String retrievedCustomerId = rs.getString("customerId");
                String retrievedCustomerName = rs.getString("customerName");
                System.out.println("CustomerId - " + retrievedCustomerId + " Customer Name - " + retrievedCustomerName );
                
                
            }
                System.out.println("Please enter the Customer ID of the Customer you wish you update.");
                String userInputCustomerID = ScheduleAssist.getScanner().next();
                System.out.println("Please Enter the number of the option you wish to update for Customer " + userInputCustomerID);
                System.out.println("1. Customer Name");
                System.out.println("1. Address ID");
                System.out.println("1. Active Status (0 is not active, 1 is active.)");
                // Setup Switch statement to alter variables and update customer
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
}
        
        
        
}
