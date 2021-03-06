/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;

/**
 *
 * @author Kyle Nyce
 */
public class DBReports {
    
    
    
    
    // Get appointments to add to arraylist.
    public static void appointmentNumberByMonth () {
        System.out.println ("Please enter the number of the month you want to the report for. Please enter a 0 if there is no ten's place (ex - January is 01). May not contain a space or be blank.");
            String MonthOfAppointment = ScheduleAssist.getScanner().next();
                 while (DBExceptions.checkForSpacesAndEmpty(MonthOfAppointment)) {
                     MonthOfAppointment = ScheduleAssist.getScanner().next();
                 }
                            System.out.println ("Gathering report for number of appointment types by month for the month of " + MonthOfAppointment);
                            LocalDate currentDay = LocalDate.now();
                            int retrievedCount = 0;
                            int monthOfYearSQL = 0;
                                                     
      try {
                        
                            String selectSQL = "SELECT DISTINCT title, COUNT(title) as total FROM appointment WHERE start LIKE '2019-"+MonthOfAppointment+"%' GROUP BY title";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                String retrievedTitle = rs.getString("title");
                                retrievedCount = rs.getInt("total");
                                System.out.println ("Month " + MonthOfAppointment + " 2019 - Appointment Type " + retrievedTitle +  " Number of Appointments "+ retrievedCount);  
                                
                                }
                                
                                
                                    
                                
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            

        }
        catch (DateTimeException ex) {
            System.out.println(" DateTime Exception Found ");
   
        }
                 
                
}
    
        public static void appointmentDailyScheduleForConsultant () {
            LocalDate currentDay = LocalDate.now();
            System.out.println ("Gathering list of appointments today for all consultants" );
            String selectSQL = "SELECT title, start, createdBy FROM appointment WHERE start LIKE '"+currentDay+"%' ORDER BY createdBy";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                String retrievedTitle = rs.getString("title");
                                String retrievedCreatedBy = rs.getString("createdBy");
                                Timestamp retrievedStartDate = rs.getTimestamp("start");
                                
                                ZonedDateTime zdtUTC= ZonedDateTime.ofInstant(retrievedStartDate.toInstant(), ZoneId.of("Z"));
               
                                ZonedDateTime zdt= ZonedDateTime.ofInstant(retrievedStartDate.toInstant(), ZonedDateTime.now().getZone());
                                ZonedDateTime ConvertedZoneDateTimeUTC = zdtUTC.withZoneSameLocal(ZonedDateTime.now().getZone());
               
                                ZonedDateTime ConvertedZoneDateTime = zdt.withZoneSameLocal(ZonedDateTime.now().getZone());
                                LocalDateTime ldt = ConvertedZoneDateTime.toLocalDateTime();
                                System.out.println ("Appointment Type - " + retrievedTitle + "Consultant Scheduled - " + retrievedCreatedBy ); 
                                System.out.println(" Original Appointment Date - " + ldt);
                                System.out.println(" Appointment Start Date Modified by your Time Zone - " + ConvertedZoneDateTimeUTC);
                                 
                                
                                }
 
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
        
        
        }
        
                public static void allCustomersReport () {
            System.out.println ("Gathering list of all customers." );
            String selectSQL = "SELECT customer.customerName,address.address,address.address2,address.postalCode,address.phone,city.city,country.country FROM customer,address,city,country "
                    + " WHERE customer.customerId = address.addressId "
                    + " AND address.cityId = city.cityId"
                    + " AND city.countryId = country.countryId";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                String retrievedCustomerName = rs.getString("customer.customerName");
                                String retrievedAddress = rs.getString("address.address");
                                String retrievedAddress2 = rs.getString("address.address2");
                                String retrievedpostalCode = rs.getString("address.postalCode");
                                String retrievedCity = rs.getString("city.city");
                                String retrievedCountry = rs.getString("country.country");
                                String retrievedPhone = rs.getString("address.phone");
                                System.out.println ("Customer Name - " + retrievedCustomerName + " Phone Number - " + retrievedPhone + " Address - "+ retrievedAddress +" "+ retrievedAddress2 +" "+ retrievedpostalCode +" "+  retrievedCity +" "+ retrievedCountry );  
                                
                                }
   
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
        
        
        }
    
    
    
}
