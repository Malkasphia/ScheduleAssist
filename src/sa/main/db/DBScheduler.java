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
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;
import static sa.main.db.DBInserter.getCurrentTimeStamp;
/**
 *
 * @author Mal
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
    
            
    //Class Methods
    
    
    // schedule calls scheduling. This is used for satisfying lambda requirement.
    // Try statement connecting to database and getting resultSet
    // Ask for customerId for appointment
    // Store customerId in DBScheduler field idEntered
    /*
    Insert statement that inserts customerId, asks for title, description, location,contact number, inserts default url, asks for 
    start datetime, end datetime. Inserts createDate datetime, createBy, lastUpdate timestamp, lastupdateBy automatically.
    */
    // Confirm appointment creation and print out values for appointment

    
    // Lambda operation for using entry of appointment into database.
    public void entrySchedule () {
     schedulerObj = () -> {
         scheduling();
     };
     schedulerObj.schedule();
    }
    

    
    
    
    private void scheduling () {
        
    
        String selectSQL = "SELECT customerId, customerName FROM customer";

        try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                String retrievedCustomerId = rs.getString("customerId");
                String retrievedCustomerName = rs.getString("customerName");
                System.out.println("CustomerId - " + retrievedCustomerId + " Customer Name - " + retrievedCustomerName );

            }
            System.out.println("Please enter the Customer ID of the Customer you wish to schedule.");
            DBScheduler.idEntered = Integer.parseInt(ScheduleAssist.getScanner().next());
            String insertSQL = "INSERT INTO appointment"
                            + "(customerId,title,description,location,contact,url,start,end,createDate,createdBy,lastUpdate,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            System.out.println("Please enter the title of the appointment for Customer, use _ or - for spaces. "  );
            DBScheduler.titleEntered = ScheduleAssist.getScanner().next();
            System.out.println("Please enter the description of the appointment for Customer, use _ or - for spaces. "  );
            DBScheduler.descriptionEntered = ScheduleAssist.getScanner().next();
            System.out.println("Please enter the location of the appointment for Customer, use _ or - for spaces. "  );
            DBScheduler.locationEntered = ScheduleAssist.getScanner().next();
            System.out.println("Please enter the contact phone number for Customer , use _ or - for spaces."  );
            DBScheduler.contactEntered = ScheduleAssist.getScanner().next();
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
        System.out.println("Please enter the ending month of the appointment, 1 through 12" );
        DBScheduler.month = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the ending number of the day of the month for the appointment (example: 1 through 31 " );
        DBScheduler.dayOfMonth = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the ending number of the hour of the appointment (business hours are 8am to 5pm only), use military time, for example - 1pm is 13, 5pm is 17 " );
        DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the ending number of the minute of the appointment (example: 15, 30 or 45) " );
        DBScheduler.minute = Integer.parseInt(ScheduleAssist.getScanner().next());
        int second = 0;
        int nanoOfSecond = 0;
        DBScheduler.timezone = timezone.systemDefault();
        return ZonedDateTime.of(DBScheduler.year,DBScheduler.month,DBScheduler.dayOfMonth,DBScheduler.hour,DBScheduler.minute,second,
                nanoOfSecond, timezone);
        }
        else {
            System.out.println("Please enter the starting month of the appointment, 1 through 12" );
        DBScheduler.month = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the starting number of the day of the month for the appointment (example: 1 through 31 " );
        DBScheduler.dayOfMonth = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the starting number of the hour of the appointment (business hours are 8am to 5pm only), use military time, for example - 1pm is 13, 5pm is 17  " );
        DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the starting number of the minute of the appointment (example: 15, 30 or 45) " );
        DBScheduler.minute = Integer.parseInt(ScheduleAssist.getScanner().next());
        int second = 0;
        int nanoOfSecond = 0;
        DBScheduler.timezone = timezone.systemDefault();
        return ZonedDateTime.of(DBScheduler.year,DBScheduler.month,DBScheduler.dayOfMonth,DBScheduler.hour,DBScheduler.minute,second,
                nanoOfSecond, timezone);
        }
    }
    
    
    // Used to convert ZoneDateTime to Timestamp
    private static Timestamp toTimestamp(ZonedDateTime dateTime) {
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
            System.out.println("Please enter the Appointment ID of the Appointment you wish you update.");
            DBScheduler.idEntered = Integer.parseInt(ScheduleAssist.getScanner().next());
            System.out.println("Please Enter the number of the option you wish to update for Appointment " + DBScheduler.idEntered);
                System.out.println("1. Appointment Title");
                System.out.println("2. Appointment Description");
                System.out.println("3. Appointment Contact.");
                System.out.println("4. Appointment Date.");
                // Setup Switch statement to alter variables and update customer
                switch (Integer.parseInt(ScheduleAssist.getScanner().next())) {
                // Updates doesTitleNeedUpdated to true, updates appointment table with new appointment title ,latest update timestamp, and user who updated it. Changes doesTitleNeedUpdated to false to reset.
                    case 1:
                        System.out.println("Please Enter the new title for Appointment " + DBScheduler.idEntered + ", limit is 40 characters, use _ or - for spaces.");
                        DBScheduler.titleEntered = ScheduleAssist.getScanner().next();
                        DBScheduler.doesTitleNeedUpdated = true;
                        changeAppointmentRecord();
                        DBScheduler.doesTitleNeedUpdated = false;
                        break;
                // Updates doesDescriptionIdNeedUpdated to true, updates appointment table with new appointment title ,latest update timestamp, and user who updated it. Changes doesDescriptionIdNeedUpdated to false to reset.
                    case 2:
                        System.out.println("Please Enter the new description for Appointment " + DBScheduler.idEntered + ", limit is 40 characters, use _ or - for spaces.");
                        DBScheduler.descriptionEntered = ScheduleAssist.getScanner().next();
                        DBScheduler.doesDescriptionIdNeedUpdated = true;
                        changeAppointmentRecord();
                        DBScheduler.doesDescriptionIdNeedUpdated = false;
                        break;
                // Updates doesContactNeedUpdated to true, updates appointment table with new appointment contact ,latest update timestamp, and user who updated it. Changes doesContactNeedUpdated to false to reset.
                    case 3:
                        System.out.println("Please Enter the new contact information for Appointment " + DBScheduler.idEntered + ", limit is 40 characters,, use _ or - for spaces.");
                        DBScheduler.contactEntered = ScheduleAssist.getScanner().next();
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
   private void printDayAppointments (LocalDate currentDay ) {
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
   // Query database for 1st of month through 31st
   // Display all appointments for month.
        }
   }
        
        
        
    };
    
    
    


