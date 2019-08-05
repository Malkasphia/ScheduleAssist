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
import static sa.main.db.DBInserter.IntAddressInsert;
import static sa.main.db.DBInserter.StringAddressInsert;

/**
 *
 * @author Kyle Nyce
 */


public class DBUpdater {
    // DBUpdater allows the updating of customer records.
    //Class Members
    private static DBConnector Connector;
    private static int idToBeUpdated;
    private static int addressIdToBeUpdated;
    private static String nameToBeUpdated;
    private static short statusToBeUpdated;
    private static boolean doesNameNeedUpdated = false;
    private static boolean doesAddressIdNeedUpdated = false;
    private static boolean doesStatusNeedUpdated = false;
    
    

    
    //Class Methods
        private void customerUpdate () {

        String selectSQL = "SELECT customerId, customerName FROM customer";

        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                String retrievedCustomerId = rs.getString("customerId");
                String retrievedCustomerName = rs.getString("customerName");
                System.out.println("CustomerId - " + retrievedCustomerId + " Customer Name - " + retrievedCustomerName );

            }
                System.out.println("Please enter the Customer ID of the Customer you wish you update. May not contain spaces or be blank.");
                String checkID = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkID)) {
                     customerUpdate ();
                     return;
                 }
                DBUpdater.idToBeUpdated = Integer.parseInt(checkID);
                
                
                System.out.println("Please Enter the number of the option you wish to update for Customer " + DBUpdater.idToBeUpdated);
                System.out.println("1. Customer Name");
                System.out.println("2. Address or Phone");
                System.out.println("3. Active Status (0 is not active, 1 is active.)");
                System.out.println("4. Delete Customer");
                // Setup Switch statement to alter variables and update customer
                switch (Integer.parseInt(ScheduleAssist.getScanner().next())) {
           // Updates doesNameNeedUpdated to true, updates customer table with new customer name and latest update and user who updated it. Changes doesNameNeedUpdated to false to reset.
           case 1: 
               System.out.println("Please Enter the new name for Customer " + DBUpdater.idToBeUpdated + ", limit is 40 characters. May not contain spaces or be blank.");
               String checkName = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkName)) {
                     customerUpdate ();
                     return;
                 }
               DBUpdater.nameToBeUpdated = checkName;
               DBUpdater.doesNameNeedUpdated = true;
               changeCustomerRecord();
               DBUpdater.doesNameNeedUpdated = false;
               break;
           // Updates doesAddressIdNeedUpdated to true, updates customer table with new Address ID, latest update time, and user who updated. Changes doesAddressIdNeedUpdated to false to reset.
           case 2:
               System.out.println("Enter the number of the option you would like to update.");
               System.out.println("1. Street Address");
               System.out.println("2. Apartment Number or Suite.");
               System.out.println("3. City ID.");
               System.out.println("4. Postal Code.");
               System.out.println("5. Phone.");
               switch (Integer.parseInt(ScheduleAssist.getScanner().next())) { 
                   case 1: System.out.println ("Please enter the street address without apartment number. May not contain a space or be blank.");
                           String address = ScheduleAssist.getScanner().next();
                           if (DBExceptions.checkForSpacesAndEmpty(address)) {
                           return;
                           }
                           int option1 = 1;
                           StringAddressInsert(address, option1);
                       return;
                   case 2: System.out.println ("Please enter the apartment or suite of the Customer. May not contain a space or be blank.");     
                           String address2 = ScheduleAssist.getScanner().next();
                           if (DBExceptions.checkForSpacesAndEmpty(address2)) {
                           return;
                           }
                           int option2 = 2;
                           StringAddressInsert(address2,option2);
                       return;
                  case 3: System.out.println ("Please enter the city ID of the Customer. May not contain a space or be blank.");     
                          int cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                          if (DBExceptions.checkForSpacesAndEmpty(Integer.toString(cityId))) {
                          return;
                          }
                          IntAddressInsert(cityId);
                      return;
                  case 4: System.out.println ("Please enter the postal code of the Customer. May not contain a space or be blank.");     
                          String postalCode = ScheduleAssist.getScanner().next();
                          if (DBExceptions.checkForSpacesAndEmpty(postalCode)) {
                          return;  
                          }
                          int option4 = 4;
                          StringAddressInsert(postalCode,option4);
                          return;
                  case 5: System.out.println ("Please enter the phone number of the Customer's address.  May not contain non-numeric characters or spaces or be longer than 10 digits.");     
                          String phone = ScheduleAssist.getScanner().next();
                          while (DBExceptions.checkPhoneNumber(phone) || DBExceptions.checkForSpacesAndEmpty(phone)) {
                            phone = ScheduleAssist.getScanner().next();
                            
                            }
                          int option5 = 5;
                          StringAddressInsert(phone,option5);
                          
                      return;
                       
               }
               String checkAddressID = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkAddressID)) {
                     customerUpdate ();
                     return;
                 }
               DBUpdater.addressIdToBeUpdated = 1;
               DBUpdater.addressIdToBeUpdated = Integer.parseInt(checkAddressID);
               DBUpdater.doesAddressIdNeedUpdated = true;
               changeCustomerRecord();
               DBUpdater.doesAddressIdNeedUpdated = false;
               break;
           // Updates doesStatusNeedUpdated to true, updates customer table with new status ID, 0 for inactive, 1 for active, latest update time, and user who updated. Changes doesStatusNeedUpdated to false to reset.
           case 3:
               System.out.println("Please Enter the new Status ID for Customer " + DBUpdater.idToBeUpdated + ": 0 for inactive, 1 for active.");
               String checkStatusID = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkStatusID)) {
                     customerUpdate ();
                     return;
                 }
               DBUpdater.statusToBeUpdated = Short.parseShort(checkStatusID);
               DBUpdater.doesStatusNeedUpdated = true;
               changeCustomerRecord();
               DBUpdater.doesStatusNeedUpdated = false;
               break;
               case 4:
                   deleteCustomer();
                }
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
}
             private void deleteCustomer () {
        try {
            String updateSQLString = "DELETE FROM customer WHERE customerId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setInt(1, DBUpdater.idToBeUpdated);
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Deleted Customer" +" " + recordsEffected +" Customer " + DBUpdater.idToBeUpdated+" " + DBInserter.getCurrentTimeStamp());
            }    
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        
        
        // Uses doesNameNeedUpdated, doesAddressIdNeedUpdated, and doesStatusNeedUpdated booleans to determine which SQL query to run and update to perform.
        private void changeCustomerRecord () {
        if (DBUpdater.doesNameNeedUpdated) {
            String updateSQLString = "UPDATE customer SET customerName = ? , lastUpdate = ? , lastUpdateBy = ?  WHERE customerId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setString(1, DBUpdater.nameToBeUpdated);
            stmtUpdate.setTimestamp(2, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(3, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(4, DBUpdater.idToBeUpdated);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Customer " + DBUpdater.idToBeUpdated + " " + DBUpdater.nameToBeUpdated +" " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if (DBUpdater.doesAddressIdNeedUpdated) {
        String updateSQLString = "UPDATE customer SET addressId = ? , lastUpdate = ? , lastUpdateBy = ?  WHERE customerId = ? ";
        try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setInt(1, DBUpdater.addressIdToBeUpdated);
            stmtUpdate.setTimestamp(2, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(3, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(4, DBUpdater.idToBeUpdated);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Customer " + DBUpdater.idToBeUpdated + " Updated Address to " + DBUpdater.addressIdToBeUpdated +" " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        if (DBUpdater.doesStatusNeedUpdated) {
        String updateSQLString = "UPDATE customer SET active = ? , lastUpdate = ? , lastUpdateBy = ?  WHERE customerId = ? ";   
        try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setShort(1, DBUpdater.statusToBeUpdated);
            stmtUpdate.setTimestamp(2, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(3, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(4, DBUpdater.idToBeUpdated);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Customer " + DBUpdater.idToBeUpdated + " Updated Status to " + DBUpdater.statusToBeUpdated +" " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        }
        
        public void databaseUpdate () {
            customerUpdate ();
        }
        
        
        
        
}
