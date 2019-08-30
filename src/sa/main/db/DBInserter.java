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
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;
import static sa.main.db.DBScheduler.toTimestamp;

/**
 *
 * @author Mal
 */
public class DBInserter {
    //DBInserter allows SQL insertion into customer records of database
    //Statement stmt = conn.createStatement();
//Class Members
    private static DBConnector Connector;
    private static int cityIDInserted;
    private static int countryIDInserted;
    
    private static String customerNameInserted;
    private static String addressInserted;
    private static String address2Inserted;
    private static String postalCodeInserted;
    private static String phoneInserted;


//Class Methods
    
    /* 
    customerInsert allows the user to insert new customers into the customer table. It takes
    a String for customerName, an int for addressId, and a short for addressID, and active status. It inserts data as 
    a new row and documents the change in the database by the user who logged in. It uses NOW() for the createDate.
    */
    
    public void customerInsert (String customerName, short active ) {
        customerNameInserted = customerName;
        String insertSQL = "INSERT INTO customer"
                            + "(customerName,addressId,active,createDate,createdBy,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?)";
        int addressIdInserted = 1;
        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL)) {
            stmt.setString(1, customerName);
            stmt.setInt(2, addressIdInserted);
            stmt.setShort(3,active);
            stmt.setTimestamp(4,getCurrentTimeStamp());
            stmt.setString(5, ScheduleAssist.getUserLoggedIn());
            stmt.setString(6, ScheduleAssist.getUserLoggedIn());
            int recordsEffected = stmt.executeUpdate();
            addressInsert();
            setAddressIDfromCustomerTable();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        
}
    private static void setAddressIDfromCustomerTable () {
            String updateSQL = " UPDATE customer SET addressId = customerId ";
            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(updateSQL)) { 
                            int recordsEffected = stmt.executeUpdate();
            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }

            
            
        }
    //method for inserting addresses into database.
    
    public static void addressInsert () {
        
        String insertSQL = "INSERT INTO address"
                            + "(address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?,?,?)";
        
        
        
        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL)) {
            System.out.println ("Please enter the street address without apartment number. May not contain a space or be blank.");
            String address = ScheduleAssist.getScanner().next();
                 while (DBExceptions.checkForSpacesAndEmpty(address)) {
                     address = ScheduleAssist.getScanner().next();
                 }
            System.out.println ("Please enter the apartment or suite of the Customer. May not contain a space or be blank.");     
            String address2 = ScheduleAssist.getScanner().next();
                 while (DBExceptions.checkForSpacesAndEmpty(address2)) {
                     address2 = ScheduleAssist.getScanner().next();
                 }
            System.out.println ("Please enter the number of the city the customer lives in. May not contain a space or be blank.");
            System.out.println ("1. New York, United States");
            System.out.println ("2. San Francisco, United States");
            System.out.println ("3. Houston, United States");
            System.out.println ("4. Madrid, Spain");
            int cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                 while (DBExceptions.checkForSpacesAndEmpty(Integer.toString(cityId))) {
                     cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                 }
                 while (DBExceptions.checkIDNumber(Integer.toString(cityId))) {
                     cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                 }
                 while (cityId > 4 || cityId < 0) {
                     System.out.println ("You have entered an invalid city selection. Please re-enter data.");
                     cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                 }
                 cityIDInserted = cityId;
            System.out.println ("Please enter the postal code of the Customer. May not contain a space or be blank.");     
            String postalCode = ScheduleAssist.getScanner().next();
                 while (DBExceptions.checkForSpacesAndEmpty(postalCode)) {
                     postalCode = ScheduleAssist.getScanner().next();
                 }
            System.out.println ("Please enter the phone number of the Customer's address. May not contain non-numeric characters or spaces or be longer than 10 digits.");     
            String phone = ScheduleAssist.getScanner().next();
                 while (DBExceptions.checkForSpacesAndEmpty(phone)) {
                     phone = ScheduleAssist.getScanner().next();
                 }
                 while (DBExceptions.checkPhoneNumber(phone)) {
                     
                     phone = ScheduleAssist.getScanner().next();
                 } 
            
            stmt.setString(1, address);
            stmt.setString(2, address2);
            stmt.setInt(3,cityId);
            stmt.setString(4,postalCode);
            stmt.setString(5, phone);
            stmt.setTimestamp(6, toTimestamp(ZonedDateTime.now()));
            stmt.setString(7, ScheduleAssist.getUserLoggedIn());
            stmt.setString(8, ScheduleAssist.getUserLoggedIn());
            
            int recordsEffected = stmt.executeUpdate();
            addressInserted = address;
            address2Inserted = address2;
            postalCodeInserted = postalCode;
            phoneInserted = phone;
            System.out.println ("Customer Created - " + customerNameInserted + " At Address " + addressInserted + " " + address2Inserted + " " + getCity(cityIDInserted) + " " + postalCodeInserted + " Phone Number - " + phoneInserted  );
            System.out.println ();
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void cityInsert () { 
        
        
    }
    
    public static void countryInsert () { 
        
    }
    
    public static String getCity (int city) {
       switch (city) { 
           case 1: String firstCity = "New York, United States";
                    return firstCity;
           case 2:String secondtCity = "San Francisco, United States";
                    return secondtCity;
           case 3:String thirdCity = "Houston, United States";
                    return thirdCity;
           case 4:String fourthCity = "Madrid, Spain";
                    return fourthCity;
       }
     String noCity = "No City Found";
     return noCity;
    }
    
     public static void StringAddressInsert (String stringAddressToBeInserted, int option) {
         switch (option) {
             case 1: String insertSQL1 = "UPDATE address SET address = ? , lastUpdate = ? , lastUpdateBy = ? WHERE addressId = '"+DBUpdater.getCustomerID()+"'";
                        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL1)) { 
                            stmt.setString(1, stringAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " Address updated to " + stringAddressToBeInserted);
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;
             case 2: String insertSQL2 = "UPDATE address SET address2 = ? , lastUpdate = ? , lastUpdateBy = ? WHERE addressId = '"+DBUpdater.getCustomerID()+"'";
                        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL2)) { 
                            stmt.setString(1, stringAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " Apartment or Suite updated to " + stringAddressToBeInserted);
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;
             case 4: String insertSQL4 = "UPDATE address SET postalCode = ? , lastUpdate = ? , lastUpdateBy = ? WHERE addressId = '"+DBUpdater.getCustomerID()+"'";
                        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL4)) { 
                            stmt.setString(1, stringAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " Postal Code updated to " + stringAddressToBeInserted);
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;
             case 5: String insertSQL5 = "UPDATE address SET phone = ? , lastUpdate = ? , lastUpdateBy = ? WHERE addressId = '"+DBUpdater.getCustomerID()+"'";
                        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL5)) { 
                            stmt.setString(1, stringAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " Phone number updated to " + stringAddressToBeInserted);
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;
         }
         
     }
     
     public static void IntAddressInsert (int intAddressToBeInserted) {
         
         
         String updateSQL = "UPDATE address SET cityId = ? , lastUpdate = ? , lastUpdateBy = ? WHERE address.addressId = (SELECT addressId FROM customer WHERE customerId = '" + DBUpdater.getCustomerID() + " ') ";
            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(updateSQL)) { 
                            stmt.setInt(1, intAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected );
                            System.out.println ("CityId changed to " + intAddressToBeInserted);
                            System.out.println ("City and Country Updated to " + getCity(intAddressToBeInserted));
                              
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }

            
     }
 

    
    public static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
    
}