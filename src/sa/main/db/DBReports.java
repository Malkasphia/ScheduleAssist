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

/**
 *
 * @author Mal
 */
public class DBReports {
    
    
    
    
    // Get appointments to add to arraylist.
    public static void appointmentNumberByMonth () {

                            System.out.println ("Gathering report for number of appointment types by month.");
                            LocalDate currentDay = LocalDate.now();
                            ArrayList<String> appointmentTitles = new ArrayList<>();
      try {
            for (int intOfMonth = 1; intOfMonth <= 32; intOfMonth++)
                currentDay.withDayOfMonth(intOfMonth);                          
                            String selectSQL = "SELECT title FROM appointment WHERE start LIKE '%"+currentDay + "%'";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                appointmentTitles.add(rs.getString("title"));
                                }
                                
                                
                                    
                                
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            

        }
        catch (DateTimeException ex) {
            System.out.println(" No Appointments after the last date. ");
   
        }
        System.out.println ("Number of Appointments for the month of " + currentDay.getMonth() + " : " + appointmentTitles.size());           
}
    
    
    
}
