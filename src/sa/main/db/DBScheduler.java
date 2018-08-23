/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;
/**
 *
 * @author Mal
 */

    @FunctionalInterface
    interface Scheduler {
    public void schedule (DBScheduler dbs);
}


// DBScheduler allows the creation and updating of Appointments in the SQL database. It does this through lambda expressions.
public class DBScheduler implements Scheduler {
    //Class Members
    private static DBConnector Connector;
    private static DBScheduler PrimeScheduler;
    private static int idEntered;
    private static String titleEntered;
    private static String descriptionEntered;
    private static String locationEntered;
    private static String contactEntered;
    private static String url = "www.defaultappointment.net";
    private static int year = 2018;
    private static int month;
    private static int dayOfMonth;
    private static int hour;
    private static int minute;
    private static ZoneId timezone;
    
            
    //Class Methods
    
    
    
    // Try statement connecting to database and getting resultSet
    // Ask for customerId for appointment
    // Store customerId in DBScheduler field idEntered
    /*
    Insert statement that inserts customerId, asks for title, description, location,contact number, inserts default url, asks for 
    start datetime, end datetime. Inserts createDate datetime, createBy, lastUpdate timestamp, lastupdateBy automatically.
    */
    // Confirm appointment creation and print out values for appointment
    @Override
    public void schedule (DBScheduler dbs) {
        
    
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
                            + "(customerId,title,description,location,contact,url,start,end,createBy,lastUpdate,lastUpdateBy)VALUES"
                            + "(?,?,?,?,?,?,?,?,?,?,?)";
            System.out.println("Please enter the title of the appointment for Customer " + DBScheduler.idEntered );
            DBScheduler.titleEntered = ScheduleAssist.getScanner().next();
            System.out.println("Please enter the description of the appointment for Customer " + DBScheduler.idEntered );
            DBScheduler.descriptionEntered = ScheduleAssist.getScanner().next();
            System.out.println("Please enter the location of the appointment for Customer " + DBScheduler.idEntered );
            DBScheduler.locationEntered = ScheduleAssist.getScanner().next();
            System.out.println("Please enter the contact phone number for Customer " + DBScheduler.idEntered );
            DBScheduler.contactEntered = ScheduleAssist.getScanner().next();
            
            
            } 
        
            catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private  ZonedDateTime getAppointmentDate () {
        System.out.println("Please enter the month of the appointment" );
        DBScheduler.month = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the number of the day of the month for the appointment (example: 1 through 31 " );
        DBScheduler.dayOfMonth = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the number of the hour of the appointment (business hours are 8am to 5pm only) " );
        DBScheduler.hour = Integer.parseInt(ScheduleAssist.getScanner().next());
        System.out.println("Please enter the number of the minute of the appointment (example: 15, 30 or 45) " );
        DBScheduler.minute = Integer.parseInt(ScheduleAssist.getScanner().next());
        int second = 0;
        int nanoOfSecond = 0;
        DBScheduler.timezone = timezone.systemDefault();
        return ZonedDateTime.of(DBScheduler.year,DBScheduler.month,DBScheduler.dayOfMonth,DBScheduler.hour,DBScheduler.minute,second,
                nanoOfSecond, timezone);
    }

        
        
        
        
        
        
        
    };
    
    
    


