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
                System.out.println("Please enter the Customer ID of the Customer you wish you update.");
                DBUpdater.idToBeUpdated = Integer.parseInt(ScheduleAssist.getScanner().next());
                System.out.println("Please Enter the number of the option you wish to update for Customer " + DBUpdater.idToBeUpdated);
                System.out.println("1. Customer Name");
                System.out.println("2. Address ID");
                System.out.println("3. Active Status (0 is not active, 1 is active.)");
                // Setup Switch statement to alter variables and update customer
                switch (Integer.parseInt(ScheduleAssist.getScanner().next())) {
           // Updates doesNameNeedUpdated to true, updates customer table with new customer name and latest update and user who updated it. Changes doesNameNeedUpdated to false to reset.
           case 1: 
               System.out.println("Please Enter the new name for Customer " + DBUpdater.idToBeUpdated + ", limit is 40 characters");
               DBUpdater.nameToBeUpdated = ScheduleAssist.getScanner().next();
               DBUpdater.doesNameNeedUpdated = true;
               changeCustomerRecord();
               DBUpdater.doesNameNeedUpdated = false;
               break;
           // Updates doesAddressIdNeedUpdated to true, updates customer table with new Address ID, latest update time, and user who updated. Changes doesAddressIdNeedUpdated to false to reset.
           case 2:
               System.out.println("Please Enter the new Address ID for Customer " + DBUpdater.idToBeUpdated + ", limit is 10 numbers");
               DBUpdater.addressIdToBeUpdated = Integer.parseInt(ScheduleAssist.getScanner().next());
               DBUpdater.doesAddressIdNeedUpdated = true;
               changeCustomerRecord();
               DBUpdater.doesAddressIdNeedUpdated = false;
               break;
           // Updates doesStatusNeedUpdated to true, updates customer table with new status ID, 0 for inactive, 1 for active, latest update time, and user who updated. Changes doesStatusNeedUpdated to false to reset.
           case 3:
               System.out.println("Please Enter the new Status ID for Customer " + DBUpdater.idToBeUpdated + ": 0 for inactive, 1 for active.");
               DBUpdater.statusToBeUpdated = Short.parseShort(ScheduleAssist.getScanner().next());
               DBUpdater.doesStatusNeedUpdated = true;
               changeCustomerRecord();
               DBUpdater.doesStatusNeedUpdated = false;
               break;   
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
