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
import sa.main.db.DBInserter.*;
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
    
    private static String addressToBeEdited;
    private static String address2ToBeEdited;
    private static String postalCodeToBeEdited;
    private static String phoneToBeEdited;
    private static int cityToBeEdited;
    
    

    
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
                 while (DBExceptions.checkForSpacesAndEmpty(checkID)) {
                     checkID = ScheduleAssist.getScanner().next();
                     
                 }
                DBUpdater.idToBeUpdated = Integer.parseInt(checkID);
                //Code for selecting all customer info that the user can edit.
                getUpdateInformationRecords ();

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
                 while (DBExceptions.checkForSpacesAndEmpty(checkName)) {
                     checkName = ScheduleAssist.getScanner().next(); 
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
               System.out.println("3. City.");
               System.out.println("4. Postal Code.");
               System.out.println("5. Phone.");
               switch (Integer.parseInt(ScheduleAssist.getScanner().next())) { 
                   case 1: System.out.println ("Current Address is " + addressToBeEdited + " . Please enter the street address without apartment number. May not contain a space or be blank.");
                           String address = ScheduleAssist.getScanner().next();
                           while (DBExceptions.checkForSpacesAndEmpty(address)) {
                           address = ScheduleAssist.getScanner().next();
                           }
                           int option1 = 1;
                           StringAddressInsert(address, option1);
                       break;
                   case 2: System.out.println ("Current Apartment or Suite is " + address2ToBeEdited + " . Please enter the apartment or suite of the Customer. May not contain a space or be blank.");     
                           String address2 = ScheduleAssist.getScanner().next();
                           while (DBExceptions.checkForSpacesAndEmpty(address2)) {
                           address2 = ScheduleAssist.getScanner().next();
                           }
                           int option2 = 2;
                           StringAddressInsert(address2,option2);
                       break;
                  case 3: System.out.println ("Current City - "+ DBInserter.getCity(cityToBeEdited) + " . Please enter the number of the city the customer lives in. May not contain a space or be blank.");
                            System.out.println ("1. New York, United States");
                            System.out.println ("2. San Francisco, United States");
                            System.out.println ("3. Houston, United States");
                            System.out.println ("4. Madrid, Spain");     
                          int cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                          while(DBExceptions.checkForSpacesAndEmpty(Integer.toString(cityId))) {
                          cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                          }
                          IntAddressInsert(cityId);
                      break;
                  case 4: System.out.println ("Current Postal Code - " +postalCodeToBeEdited+" . Please enter the postal code of the Customer. May not contain a space or be blank.");     
                          String postalCode = ScheduleAssist.getScanner().next();
                          while (DBExceptions.checkForSpacesAndEmpty(postalCode)) {
                            postalCode = ScheduleAssist.getScanner().next();
                          }
                          int option4 = 4;
                          StringAddressInsert(postalCode,option4);
                          break;
                  case 5: System.out.println ("Current Phone Number - " + phoneToBeEdited +" . Please enter the phone number of the Customer's address.  May not contain non-numeric characters or spaces or be longer than 10 digits.");     
                          String phone = ScheduleAssist.getScanner().next();
                          while (DBExceptions.checkPhoneNumber(phone) || DBExceptions.checkForSpacesAndEmpty(phone)) {
                            phone = ScheduleAssist.getScanner().next();
                            
                            }
                          int option5 = 5;
                          StringAddressInsert(phone,option5);
                          
                      break;
                      
                      
                      //Get cities from a list and have user choose it, get ID of city and put that into addressID

                       
               }
               
               
               changeCustomerRecord();
               
               break;
           // Updates doesStatusNeedUpdated to true, updates customer table with new status ID, 0 for inactive, 1 for active, latest update time, and user who updated. Changes doesStatusNeedUpdated to false to reset.
           case 3:
               System.out.println("Please Enter the new Status ID for Customer " + DBUpdater.idToBeUpdated + ": 0 for inactive, 1 for active.");
               String checkStatusID = ScheduleAssist.getScanner().next();
                 while (DBExceptions.checkForSpacesAndEmpty(checkStatusID)) {
                     checkStatusID = ScheduleAssist.getScanner().next();
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
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Customer " + DBUpdater.idToBeUpdated + " Name Changed to " + DBUpdater.nameToBeUpdated +" " + DBInserter.getCurrentTimeStamp());
            
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
        
        public static int getCustomerID () {
            return idToBeUpdated;
        }
        
        private static void getUpdateInformationRecords () {
                            String selectSQLDisplayData = "SELECT address, address2, cityId, postalCode, phone FROM address WHERE addressId = '"+idToBeUpdated+"'";
                try (PreparedStatement stmtGatherDisplayData = DBConnector.startConnecting().prepareStatement(selectSQLDisplayData)) {
                ResultSet rsGatherDisplayData = stmtGatherDisplayData.executeQuery(selectSQLDisplayData);
                while (rsGatherDisplayData.next()) {

                 addressToBeEdited = rsGatherDisplayData.getString("address");
                 address2ToBeEdited = rsGatherDisplayData.getString("address2");
                 cityToBeEdited = rsGatherDisplayData.getInt("cityId");
                 postalCodeToBeEdited = rsGatherDisplayData.getString("postalCode");
                 phoneToBeEdited = rsGatherDisplayData.getString("phone");
                                }
                }
                catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        }
        
        
        
}
