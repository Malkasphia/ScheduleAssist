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
                 if (DBExceptions.checkForSpacesAndEmpty(MonthOfAppointment)) {
                     return;
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
                                System.out.println ("Number of Appointments for " + MonthOfAppointment + " 2019 - " + retrievedTitle +  " "+ retrievedCount);  
                                
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
            String selectSQL = "SELECT title, createdBy FROM appointment WHERE start LIKE '"+currentDay+"%' ORDER BY createdBy";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                String retrievedTitle = rs.getString("title");
                                String retrievedCreatedBy = rs.getString("createdBy");
                                System.out.println ("Appointment Title - " + retrievedTitle + "Consultant Scheduled - " + retrievedCreatedBy );  
                                
                                }
 
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
        
        
        }
        
                public static void allCustomersReport () {
            System.out.println ("Gathering list of all customers." );
            String selectSQL = "SELECT customerName FROM customer ";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                String retrievedCustomerName = rs.getString("customerName");
                                System.out.println ("Customer Name - " + retrievedCustomerName );  
                                
                                }
   
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
        
        
        }
    
    
    
}
