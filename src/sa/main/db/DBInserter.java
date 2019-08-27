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
            addressInsert();
            cityInsert();
            countryInsert();
            
        } catch (SQLException ex) {
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
                 if (DBExceptions.checkForSpacesAndEmpty(address)) {
                     return;
                 }
            System.out.println ("Please enter the apartment or suite of the Customer. May not contain a space or be blank.");     
            String address2 = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(address2)) {
                     return;
                 }
            System.out.println ("Please enter the city ID of the Customer, may only be a number. May not contain a space or be blank.");     
            int cityId = Integer.parseInt(ScheduleAssist.getScanner().next());
                 if (DBExceptions.checkForSpacesAndEmpty(Integer.toString(cityId))) {
                     return;
                 }
                 if (DBExceptions.checkIDNumber(Integer.toString(cityId))) {
                     return;
                 }
                 cityIDInserted = cityId;
            System.out.println ("Please enter the postal code of the Customer. May not contain a space or be blank.");     
            String postalCode = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(postalCode)) {
                     return;
                 }
            System.out.println ("Please enter the phone number of the Customer's address. May not contain non-numeric characters or spaces or be longer than 10 digits.");     
            String phone = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(phone)) {
                     return;
                 }
                 if (DBExceptions.checkPhoneNumber(phone)) {
                     addressInsert();
                     return;
                 }
                 else {
                     
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
            System.out.println ("Number of Rows Effected " + recordsEffected + " " + address + " " +  address2 +  " " + cityId + " " +  postalCode + " " + phone );
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void cityInsert () { 
        String insertSQL = "INSERT INTO city"
                            + "(cityId,city,countryId,createDate,createdBy,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?)";
        
        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL)) {
            System.out.println ("Please enter the city of the address. May not contain a space or be blank.");
            String city = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(city)) {
                     return;
                 }
            System.out.println ("Please enter the country ID of the city. May not contain a space or be blank.");     
            int countryId = Integer.parseInt(ScheduleAssist.getScanner().next());
                 if (DBExceptions.checkForSpacesAndEmpty(Integer.toString(countryId))) {
                     return;
                 }
                 if (DBExceptions.checkIDNumber(Integer.toString(countryId))) {
                     return;
                 }
                 countryIDInserted = countryId;

            stmt.setInt(1, cityIDInserted);
            stmt.setString(2, city);
            stmt.setInt(3,countryIDInserted);
            stmt.setTimestamp(4, toTimestamp(ZonedDateTime.now()));
            stmt.setString(5, ScheduleAssist.getUserLoggedIn());
            stmt.setString(6, ScheduleAssist.getUserLoggedIn());
            
            int recordsEffected = stmt.executeUpdate();
            System.out.println ("Number of Rows Effected " + recordsEffected + " Name of City " + city + " Country ID -  " +  countryIDInserted +  " CityID " + cityIDInserted + " " +  toTimestamp(ZonedDateTime.now()) );

    }
        catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void countryInsert () { 
        String insertSQL = "INSERT INTO country"
                            + "(countryId,country,createDate,createdBy,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?)";
        
        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL)) {
            System.out.println ("Please enter the country of the address. May not contain a space or be blank.");
            String country = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(country)) {
                     return;
                 }


            stmt.setInt(1, countryIDInserted);
            stmt.setString(2, country);
            stmt.setTimestamp(3, toTimestamp(ZonedDateTime.now()));
            stmt.setString(4, ScheduleAssist.getUserLoggedIn());
            stmt.setString(5, ScheduleAssist.getUserLoggedIn());
            
            int recordsEffected = stmt.executeUpdate();
            System.out.println ("Number of Rows Effected " + recordsEffected + " Name of Country " + country + " Country ID -  " +  countryIDInserted + " " + toTimestamp(ZonedDateTime.now()) );

    }
        catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     public static void StringAddressInsert (String stringAddressToBeInserted, int option) {
         switch (option) {
             case 1: String insertSQL1 = "UPDATE address SET address = ? , lastUpdate = ? , lastUpdateBy = ?";
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
             case 2: String insertSQL2 = "UPDATE address SET address2 = ? , lastUpdate = ? , lastUpdateBy = ?";
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
             case 4: String insertSQL4 = "UPDATE address SET postalCode = ? , lastUpdate = ? , lastUpdateBy = ?";
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
             case 5: String insertSQL5 = "UPDATE address SET phone = ? , lastUpdate = ? , lastUpdateBy = ?";
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
             case 6: String insertSQL6 = "UPDATE city SET city = ? , lastUpdate = ? , lastUpdateBy = ?";
                        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL6)) { 
                            stmt.setString(1, stringAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " City updated to  " + stringAddressToBeInserted);
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;
             case 7: String insertSQL7 = "UPDATE country SET country = ? , lastUpdate = ? , lastUpdateBy = ? ";
                        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL7)) { 
                            stmt.setString(1, stringAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " Country updated to " + stringAddressToBeInserted);
                            }
                        catch (SQLException ex) {
                        Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 break;
         }
         
     }
     
     public static void IntAddressInsert (int intAddressToBeInserted) {
         String insertSQL3 = "UPDATE address SET cityId = ? , lastUpdate = ? , lastUpdateBy = ?";
            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(insertSQL3)) { 
                            stmt.setInt(1, intAddressToBeInserted);
                            stmt.setTimestamp(2, toTimestamp(ZonedDateTime.now()));
                            stmt.setString(3, ScheduleAssist.getUserLoggedIn());
                            int recordsEffected = stmt.executeUpdate();
                            System.out.println ("Number of Rows Effected " + recordsEffected + " " + intAddressToBeInserted);
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