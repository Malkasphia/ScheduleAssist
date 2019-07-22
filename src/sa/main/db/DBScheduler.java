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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;

/**
 *
 * @author Kyle Nyce
 */

    @FunctionalInterface
    interface Scheduler {
    public void schedule ();
}

    @FunctionalInterface
    interface UpdateScheduler {
    public void updateSchedule ();
}


// DBScheduler allows the creation and updating of Appointments in the SQL database. It does this through lambda expressions.
public class DBScheduler {
    //Class Members
    private static Scheduler schedulerObj;
    private static UpdateScheduler updateSchedulerObj;
    private static DBConnector Connector;
    private static DBScheduler PrimeScheduler;
    private static int idEntered;
    private static String titleEntered;
    private static String descriptionEntered;
    private static String locationEntered;
    private static String contactEntered;
    private static String url = "www.defaultappointment.net";
    private static int year = 2019;
    private static int month;
    private static int dayOfMonth;
    private static int hour;
    private static int minute;
    private static ZoneId timezone;
    private static ZonedDateTime start;
    private static ZonedDateTime end;
    private static ZonedDateTime today;
    private static boolean isEndAppointment;
    private static boolean doesTitleNeedUpdated = false;
    private static boolean doesDescriptionIdNeedUpdated = false;
    private static boolean doesContactNeedUpdated = false;
    private static boolean doesDateNeedUpdated = false;

    
           
    // Lambda Expression allows other classes to call on a public facing method which in turn calls on a private function that hides members of DBScheduler. This makes program more
    //efficient by allowing redesign of private function that does not affect public function that is available to other classes.
    public void entrySchedule () {
     schedulerObj = () -> {
         scheduling();
     };
     schedulerObj.schedule();
    }
    

    
    
    
    private void scheduling ()  {
        
    
        String selectSQL = "SELECT customerId, customerName FROM customer";

        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                String retrievedCustomerId = rs.getString("customerId");
                String retrievedCustomerName = rs.getString("customerName");
                System.out.println("CustomerId - " + retrievedCustomerId + " Customer Name - " + retrievedCustomerName );

            }
            System.out.println("Please enter the Customer ID of the Customer you wish to schedule. May not contain spaces or be blank.");
            String checkID = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkID)) {
                     scheduling ();
                     return;
                 }
            DBScheduler.idEntered = Integer.parseInt(checkID);
            String insertSQL = "INSERT INTO appointment"
                            + "(customerId,title,description,location,contact,url,start,end,createDate,createdBy,lastUpdate,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            System.out.println("Please enter the title of the appointment for Customer. May not contain spaces or be blank."  );
            String checkTitle = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkTitle)) {
                     scheduling ();
                     return;
                 }
            DBScheduler.titleEntered = checkTitle;
            System.out.println("Please enter the description of the appointment for Customer. May not contain spaces or be blank. "  );
            String checkDescription = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkDescription)) {
                     scheduling ();
                     return;
                 }
            DBScheduler.descriptionEntered = checkDescription;
            System.out.println("Please enter the location of the appointment for Customer. May not contain spaces or be blank. "  );
            String checkLocation = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkLocation)) {
                     scheduling ();
                     return;
                 }
            DBScheduler.locationEntered = checkLocation;
            System.out.println("Please enter the contact phone number for Customer. May not contain spaces or be blank."  );
            String checkContact = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkContact)) {
                     scheduling ();
                     return;
                 }
            DBScheduler.contactEntered = checkContact;
            

            DBScheduler.start = getAppointmentDate();

            
            DBScheduler.isEndAppointment = true;
            

            DBScheduler.end = getAppointmentDate();

            
            DBScheduler.isEndAppointment = false;
            
            
            try (PreparedStatement stmtInsert = DBConnector.startConnecting().prepareStatement(insertSQL)) {
            stmtInsert.setInt(1, DBScheduler.idEntered);
            stmtInsert.setString(2, DBScheduler.titleEntered);
            stmtInsert.setString(3,DBScheduler.descriptionEntered);
            stmtInsert.setString(4,DBScheduler.locationEntered);
            stmtInsert.setString(5, DBScheduler.contactEntered);
            stmtInsert.setString(6, DBScheduler.url);
            stmtInsert.setTimestamp(7,toTimestamp(DBScheduler.start));
            stmtInsert.setTimestamp(8,toTimestamp(DBScheduler.end));
            stmtInsert.setTimestamp(9, toTimestamp(ZonedDateTime.now()));
            stmtInsert.setString(10, ScheduleAssist.getUserLoggedIn());
            stmtInsert.setTimestamp(11, toTimestamp(ZonedDateTime.now()));
            stmtInsert.setString(12, ScheduleAssist.getUserLoggedIn());
            
            int recordsEffected = stmtInsert.executeUpdate();
            System.out.println ("Number of Rows Effected " + recordsEffected + " Customer " + DBScheduler.idEntered + " Title " 
                    +  DBScheduler.titleEntered +  " Description " + DBScheduler.descriptionEntered + " Location " +  DBScheduler.locationEntered);
            System.out.println ("Contact " + DBScheduler.contactEntered + "URL " + DBScheduler.url + "Start Date " + toTimestamp(DBScheduler.start)
            + "End Date " + toTimestamp(DBScheduler.end) + "Created By " + ScheduleAssist.getUserLoggedIn() );
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            } 
        
            catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private  ZonedDateTime getAppointmentDate() {
        if (DBScheduler.isEndAppointment)
        {
        System.out.println("Please enter the ending month of the appointment, 1 through 12. May not contain a space or be blank." );
        String checkEndingMonth = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkEndingMonth)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.month = Integer.parseInt(checkEndingMonth);
        System.out.println("Please enter the ending number of the day of the month for the appointment (example: 1 through 31). May not contain a space or be blank. " );
        String checkEndingDay = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkEndingDay)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.dayOfMonth = Integer.parseInt(checkEndingDay);
        System.out.println("Please enter the ending number of the hour of the appointment (business hours are 8am to 5pm only), use military time, for example - 1pm is 13, 5pm is 17. May not contain a space or be blank. " );
        String checkEndingHour = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkEndingHour)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.hour = Integer.parseInt(checkEndingHour);
                while (DBScheduler.hour > 17 ) 
                {
            try {
                throw new Exception("Appointment cannot start later than 17:00 ( 5pm Military Time). Please Re-Enter ending number of the hour of the appointment. ");
            } catch (Exception ex) {
                System.out.println(ex);
                DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
                
            }
                }
            
            
            
                while (DBScheduler.hour < 8 ) 
                {
                
            try {
                throw new Exception("Appointment cannot start earlier than 8:00 ( 8am Military Time). Please Re-Enter ending number of the hour of the appointment");
            } catch (Exception ex) {
                System.out.println(ex);
                DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
            }
                }
            
        System.out.println("Please enter the ending number of the minute of the appointment (example: 15, 30 or 45). May not contain a space or be blank. " );
        String checkEndingMinute = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkEndingMinute)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.minute = Integer.parseInt(checkEndingMinute);
        int second = 0;
        int nanoOfSecond = 0;
        DBScheduler.timezone = timezone.systemDefault();
                    DBScheduler.timezone = timezone.systemDefault();
        ZonedDateTime returnZoneDateTime = ZonedDateTime.of(DBScheduler.year,DBScheduler.month,DBScheduler.dayOfMonth,DBScheduler.hour,DBScheduler.minute,second,
                nanoOfSecond, timezone);
        Timestamp comparedDay = toTimestamp(returnZoneDateTime);
                        // Check for overlapping appointments and throw an exception if there is one.
                        // Get all appointments of the day
                        // compare DBscheduler.hour with appointment considering the hour
                        // throw exception if they overlap
                            System.out.println ("Checking for overlapping appointments");
                            LocalDate currentDay = LocalDate.now(); 
                            String selectSQL = "SELECT start, end FROM appointment WHERE start LIKE '%"+currentDay + "%' ";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                Timestamp retrievedappointmentStart = rs.getTimestamp("start");
                                Timestamp retrievedappointmentEnd = rs.getTimestamp("end");
                                if (comparedDay.after(retrievedappointmentEnd) || comparedDay.before(retrievedappointmentStart) ) 
                                {
                                    
                                }
                                else 
                                {
                                    try {
                                      throw new Exception("Appointment overlaps with another appointment between the times" + " " + retrievedappointmentStart + " and " + retrievedappointmentEnd );
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                        DBScheduler.isEndAppointment = false;
                                        getAppointmentDate();
                                        
                                    }
                                }

                                }
                                System.out.println ("No overlapping appointments found");
                            
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
         return returnZoneDateTime;
        }
        
        else {
        System.out.println("Please enter the starting month of the appointment, 1 through 12. May not contain a space or be blank. " );
        String checkStartingMonth = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkStartingMonth)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.month = Integer.parseInt(checkStartingMonth);
        System.out.println("Please enter the starting number of the day of the month for the appointment (example: 1 through 31. May not contain a space or be blank. " );
        String checkStartingDay = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkStartingDay)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.dayOfMonth = Integer.parseInt(checkStartingDay);
        System.out.println("Please enter the starting number of the hour of the appointment (business hours are 8am to 5pm only), use military time, for example - 1pm is 13, 5pm is 17. May not contain a space or be blank.   " );
        String checkStartingHour = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkStartingHour)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.hour = Integer.parseInt(checkStartingHour);
               while (DBScheduler.hour >= 17 ) 
                {
                
            try {
                throw new Exception("Appointment cannot start later than 17:00 ( 5pm Military Time). Please Re-Enter starting number of the hour of the appointment.");
            } catch (Exception ex) {
                System.out.println(ex);
                DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
            }
                }
            
                while (DBScheduler.hour < 8 ) 
                {
                
            try {
                throw new Exception("Appointment cannot start earlier than 8:00 ( 8am Military Time). Please Re-Enter starting number of the hour of the appointment.");
            } catch (Exception ex) {
                System.out.println(ex);
                DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
            }
                
                }
                        
                        
            
        System.out.println("Please enter the starting number of the minute of the appointment (example: 15, 30 or 45). May not contain a space or be blank. " );
        String checkStartingMinute = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkStartingMinute)) {
                     scheduling ();
                     return null;
                 }
        DBScheduler.minute = Integer.parseInt(checkStartingMinute);
        int second = 0;
        int nanoOfSecond = 0;
        DBScheduler.timezone = timezone.systemDefault();
        ZonedDateTime returnZoneDateTime = ZonedDateTime.of(DBScheduler.year,DBScheduler.month,DBScheduler.dayOfMonth,DBScheduler.hour,DBScheduler.minute,second,
                nanoOfSecond, timezone);
        Timestamp comparedDay = toTimestamp(returnZoneDateTime);
                        // Check for overlapping appointments and throw an exception if there is one.
                        // Get all appointments of the day
                        // compare DBscheduler.hour with appointment considering the hour
                        // throw exception if they overlap
                            System.out.println ("Checking for overlapping appointments");
                            LocalDate currentDay = LocalDate.now(); 
                            String selectSQL = "SELECT start, end FROM appointment WHERE start LIKE '%"+currentDay + "%' ";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                Timestamp retrievedappointmentStart = rs.getTimestamp("start");
                                Timestamp retrievedappointmentEnd = rs.getTimestamp("end");
                                if (comparedDay.after(retrievedappointmentEnd) || comparedDay.before(retrievedappointmentStart) ) 
                                {
                                    
                                }
                                else 
                                {
                                    try {
                                      throw new Exception("Appointment overlaps with another appointment between the times" + " " + retrievedappointmentStart + " and " + retrievedappointmentEnd );
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                        DBScheduler.isEndAppointment = false;
                                        getAppointmentDate();
                                        
                                    }
                                }

                                }
                                System.out.println ("No overlapping appointments found");
                            
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
         return returnZoneDateTime;              
        }
        
    }
    
    
    // Used to convert ZoneDateTime to Timestamp
    public static Timestamp toTimestamp(ZonedDateTime dateTime) {
    return new Timestamp(dateTime.toInstant().getEpochSecond() * 1000L);
  }

        /* Make maintainScheduling method that uses code structure in DBUpdater customerUpdate method. Make public interface 
     so that it can be called as a lambda expression. */

    
    //Lambda operation for updating appointments.
    public void entryUpdate() {
     updateSchedulerObj = () -> {
         updateScheduling();
     };
     updateSchedulerObj.updateSchedule();
    }
    
    //private function for updating internal fields and appointments
    private void updateScheduling () {
        
        String selectSQL = "SELECT appointmentId, title FROM appointment";

        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                String retrievedappointmentId = rs.getString("appointmentId");
                String retrievedTitle = rs.getString("title");
                System.out.println("Appointment ID - " + retrievedappointmentId + " Appointment Title - " + retrievedTitle );

            }
            System.out.println("Please enter the Appointment ID of the Appointment you wish you update. May not contain a space or be blank.");
            String checkAppointmentID = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkAppointmentID)) {
                     updateScheduling ();
                     return;
                 }
            DBScheduler.idEntered = Integer.parseInt(checkAppointmentID);
            System.out.println("Please Enter the number of the option you wish to update for Appointment " + DBScheduler.idEntered);
                System.out.println("1. Appointment Title");
                System.out.println("2. Appointment Description");
                System.out.println("3. Appointment Contact.");
                System.out.println("4. Appointment Date.");
                System.out.println("5. Delete Appointment.");
                // Setup Switch statement to alter variables and update customer
                switch (Integer.parseInt(ScheduleAssist.getScanner().next())) {
                // Updates doesTitleNeedUpdated to true, updates appointment table with new appointment title ,latest update timestamp, and user who updated it. Changes doesTitleNeedUpdated to false to reset.
                    case 1:
                        System.out.println("Please Enter the new title for Appointment " + DBScheduler.idEntered + ", .May not contain spaces or be blank.");
                 String checkTitle = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkTitle)) {
                     updateScheduling ();
                     return;
                 }
                        DBScheduler.titleEntered = checkTitle;
                        DBScheduler.doesTitleNeedUpdated = true;
                        changeAppointmentRecord();
                        DBScheduler.doesTitleNeedUpdated = false;
                        break;
                // Updates doesDescriptionIdNeedUpdated to true, updates appointment table with new appointment title ,latest update timestamp, and user who updated it. Changes doesDescriptionIdNeedUpdated to false to reset.
                    case 2:
                        System.out.println("Please Enter the new description for Appointment " + DBScheduler.idEntered + ".May not contain spaces or be blank.");
                        String checkDescription = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkDescription)) {
                     updateScheduling ();
                     return;
                 }
                        DBScheduler.descriptionEntered = checkDescription;
                        DBScheduler.doesDescriptionIdNeedUpdated = true;
                        changeAppointmentRecord();
                        DBScheduler.doesDescriptionIdNeedUpdated = false;
                        break;
                // Updates doesContactNeedUpdated to true, updates appointment table with new appointment contact ,latest update timestamp, and user who updated it. Changes doesContactNeedUpdated to false to reset.
                    case 3:
                        System.out.println("Please Enter the new contact information for Appointment " + DBScheduler.idEntered + " May not contain spaces or be blank.");
                         String checkContact = ScheduleAssist.getScanner().next();
                 if (DBExceptions.checkForSpacesAndEmpty(checkContact)) {
                     updateScheduling ();
                     return;
                 }
                        DBScheduler.contactEntered = checkContact;
                        DBScheduler.doesContactNeedUpdated = true;
                        changeAppointmentRecord();
                        DBScheduler.doesContactNeedUpdated = false;
                        break;
                // Updates doesContactNeedUpdated to true, updates appointment table with new appointment date ,latest update timestamp, and user who updated it. Changes doesContactNeedUpdated to false to reset.
                    case 4:
                        DBScheduler.doesDateNeedUpdated = true;
                        changeAppointmentRecord();
                        DBScheduler.doesDateNeedUpdated = false;
                        break;
                    case 5:
                        deleteAppointment();
                        break;
                        
                        
                    
                    
                }
        
        
    } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
   public void databaseUpdate () {
            updateScheduling ();
        }
        
   
   // Uses doesTitleNeedUpdated, doesDescriptionIdNeedUpdated, doesContactNeedUpdated,  and doesDateNeedUpdated booleans to determine which SQL query to run and update to perform.
   private void changeAppointmentRecord () {
        if (DBScheduler.doesTitleNeedUpdated) {
            String updateSQLString = "UPDATE appointment SET title = ? , lastUpdate = ? , lastUpdateBy = ?  WHERE appointmentId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setString(1, DBScheduler.titleEntered);
            stmtUpdate.setTimestamp(2, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(3, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(4, DBScheduler.idEntered);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Appointment " + DBScheduler.idEntered + " " + DBScheduler.titleEntered +" " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if (DBScheduler.doesDescriptionIdNeedUpdated) {
            String updateSQLString = "UPDATE appointment SET description = ? , lastUpdate = ? , lastUpdateBy = ?  WHERE appointmentId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setString(1, DBScheduler.descriptionEntered);
            stmtUpdate.setTimestamp(2, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(3, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(4, DBScheduler.idEntered);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Appointment " + DBScheduler.idEntered + " " + DBScheduler.descriptionEntered +" " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if (DBScheduler.doesContactNeedUpdated) {
            String updateSQLString = "UPDATE appointment SET contact = ? , lastUpdate = ? , lastUpdateBy = ?  WHERE appointmentId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setString(1, DBScheduler.contactEntered);
            stmtUpdate.setTimestamp(2, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(3, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(4, DBScheduler.idEntered);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" +" " + recordsEffected +" Appointment " + DBScheduler.idEntered + " " + DBScheduler.contactEntered +" " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if (DBScheduler.doesDateNeedUpdated) {
            
            DBScheduler.start = getAppointmentDate();

            DBScheduler.isEndAppointment = true;
            
            DBScheduler.end = getAppointmentDate();

            DBScheduler.isEndAppointment = false;

            String updateSQLString = "UPDATE appointment SET start = ? , end = ?, lastUpdate = ? , lastUpdateBy = ?  WHERE appointmentId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setTimestamp(1,toTimestamp(DBScheduler.start));
            stmtUpdate.setTimestamp(2,toTimestamp(DBScheduler.end));
            stmtUpdate.setTimestamp(3, DBInserter.getCurrentTimeStamp());
            stmtUpdate.setString(4, ScheduleAssist.getUserLoggedIn());
            stmtUpdate.setInt(5, DBScheduler.idEntered);
            
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Number of Rows Effected" + " " + recordsEffected +" Appointment " + DBScheduler.idEntered + " Start Date " + toTimestamp(DBScheduler.start) + " End Date " + toTimestamp(DBScheduler.end) + " Updated " + DBInserter.getCurrentTimeStamp());
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
       
       
       
   }
   //Delete Appointment Record
     private void deleteAppointment () {
        try {
            String updateSQLString = "DELETE FROM appointment WHERE appointmentId = ? ";
            try (PreparedStatement stmtUpdate = DBConnector.startConnecting().prepareStatement(updateSQLString)) {
            stmtUpdate.setInt(1, DBScheduler.idEntered);
            int recordsEffected = stmtUpdate.executeUpdate();
            System.out.println ("Deleted Appointment" +" " + recordsEffected +" Appointment " + DBScheduler.idEntered +" " + DBInserter.getCurrentTimeStamp());
            }    
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
   
   // Provide the ability to view the calendar by month and by week.
   public void viewWeek () {
       provideViewWeek();
   }
   
   // Provides the view of current day and three previous days as well as three days into the future.
   private void provideViewWeek () {
   // Get current day
  /* today = ZonedDateTime.now();
   // Get previous three days
   ZonedDateTime firstPreviousDay = today.minusDays(1);
   ZonedDateTime secondPreviousDay = today.minusDays(2);
   ZonedDateTime thirdPreviousDay = today.minusDays(3);
   // Get future three days
   ZonedDateTime firstFutureDay = today.plusDays(1);
   ZonedDateTime secondFutureDay = today.plusDays(2);
   ZonedDateTime thirdFutureDay = today.plusDays(3);
   */
   // Using LocalDate instead of ZonedDateTime
   
   LocalDate todayToday = LocalDate.now();
   // Get previous three days
   LocalDate firstPreviousDay = todayToday.minusDays(1);
   LocalDate secondPreviousDay = todayToday.minusDays(2);
   LocalDate thirdPreviousDay = todayToday.minusDays(3);
   // Get future three days
   LocalDate firstFutureDay = todayToday.plusDays(1);
   LocalDate secondFutureDay = todayToday.plusDays(2);
   LocalDate thirdFutureDay = todayToday.plusDays(3);
   
   
   
   // Query Database for matching days algorithm
    // 1. Truncate ZoneDateTime Variables to Day Temporal Unit
   /* today = today.truncatedTo(ChronoUnit.DAYS);
   firstPreviousDay = firstPreviousDay.truncatedTo(ChronoUnit.DAYS);
   secondPreviousDay = secondPreviousDay.truncatedTo(ChronoUnit.DAYS);
   thirdPreviousDay = thirdPreviousDay.truncatedTo(ChronoUnit.DAYS);
   firstFutureDay = firstFutureDay.truncatedTo(ChronoUnit.DAYS);
   secondFutureDay = secondFutureDay.truncatedTo(ChronoUnit.DAYS);
   thirdFutureDay = thirdFutureDay.truncatedTo(ChronoUnit.DAYS); */
    // 2. Query Database to select all where appointment.start datetime (truncated to Days) is equal to all ZoneDateTime seven variables ( handled by printDayAppointments
    // and Print Week of Appointments
    System.out.println("Showing Appointments for the Week");
    printDayAppointments(thirdPreviousDay);
    printDayAppointments(secondPreviousDay);
    printDayAppointments(firstPreviousDay);
    printDayAppointments(todayToday);
    printDayAppointments(firstFutureDay);
    printDayAppointments(secondFutureDay);
    printDayAppointments(thirdFutureDay);
    System.out.println("End of Week of Appointments");
   
   }
      
   
  // Provides the view of week, 1st-30th or 31st, for all appointments by all consultants.
   public void printDayAppointments (LocalDate currentDay ) {
       System.out.println(currentDay);
        String selectSQL = "SELECT appointmentId, title, start FROM appointment WHERE start LIKE '%"+currentDay + "%'" ;
        
        try (PreparedStatement stmtSelect = DBConnector.startConnecting().prepareStatement(selectSQL)) {        
            ResultSet rs = stmtSelect.executeQuery(selectSQL);
            while (rs.next()) {
                String retrievedappointmentId = rs.getString("appointmentId");
                String retrievedTitle = rs.getString("title");
                Timestamp retrievedStartDate = rs.getTimestamp("start");
                System.out.println("Appointment ID - " + retrievedappointmentId + " Appointment Title - " + retrievedTitle + "Start Date - " + retrievedStartDate );

            }
            
            
            if(!rs.first()) 
                System.out.println(" No appointments found"); 
            
                
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   
   
   public void viewMonth () {
   // call provideViewMonth, is a public API for provideViewMonth
   provideViewMonth();
   }
   
   private void provideViewMonth () {
System.out.println("Showing Appointments for the Month");
// Iterate through 31 days using same code as viewWeek 
    //Setup Variable to call printDayAppointments   
   LocalDate todayToday = LocalDate.now();
   // Use LocalDate method withDayOfMonth to change date to the first day through the last day of the month, each time printing using printDayAppointments
        //using for loop to iterate through the possible 31 days of a month
        try {
            for (int intOfMonth = 1; intOfMonth <= 32; intOfMonth++)
                printDayAppointments(todayToday.withDayOfMonth(intOfMonth));
        }
        catch (DateTimeException ex) {
            System.out.println(" No Appointments after the last date. ");
   
        }
   }
        
        
        
    };
    
    
    


