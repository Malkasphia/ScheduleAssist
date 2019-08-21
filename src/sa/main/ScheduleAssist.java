

package sa.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.db.*;
import static sa.main.db.DBExceptions.isInvalidData;

/**
 *
 * @author Kyle Nyce
 */

public class ScheduleAssist {

    static Scanner scanner = new Scanner(System.in);
    static String userLoggedIn = "Not Logged In";
    static DBLogin connect = new DBLogin();
    static DBInserter inserter = new DBInserter();
    static DBUpdater updater = new DBUpdater();
    static DBScheduler scheduler = new DBScheduler();
    static DBExceptions exception = new DBExceptions();
    static DBReports reporter = new DBReports();
    static Locale spain = new Locale("es", "es-es");
    static Locale americanEnglish = new Locale("en", "en-us");

    public static void main(String[] args) {
        //Initialize objects for logging into database, insertion, updating, scheduler, and scanner to use new lines as delimiter.
        scanner.useDelimiter("\\n");

        /* Set Locale Object Here - Languages supported are English US and Spain Spanish. Default is American English. 
        Comment US Locale and uncomment Spain Spanish Locale to get Spanish localization. */
        //American English Localization Below
        Locale.setDefault(new Locale("en", "en-us"));
        //Spain Spanish Localization Below
        //Locale.setDefault(new Locale("es", "es-es"));

        
        
        

        //Username Login Process

        if (Locale.getDefault().equals(americanEnglish)) {
           System.out.println("Welcome to Schedule Assist Version 1.0"); 
        }
        else {
           System.out.println("Bienvenido a Schedule Assist Versión 1.0"); 
        }
        

        System.out.println("Welcome to Schedule Assist V 0.1");
        

        ScheduleAssist.startLogin(connect);
        while ( 1 == 1) {
            try {
        System.out.println("Do you wish to continue using this program? Enter 1 for yes. Enter 2 to exit. ");
        int userChoiceInputContinue = Integer.parseInt(scanner.next()); 
        switch (userChoiceInputContinue) {
            case 1:
                    appointmentReminder();
                    printChoicesforUserInterface();
                    int userChoiceInput = Integer.parseInt(scanner.next());
                    ScheduleAssist.userInterface(userChoiceInput,inserter,updater,scheduler);
                    break;
            case 2: System.exit(0);
            break;
        }
            }
            
            
        catch (Exception ex) {
            System.out.println("You did not enter any of the correct options. Please re-enter a valid option. ");
            
        }

        }
    }
    

    
    private static void startLogin (DBLogin dataBaseQueried) {
        //User enters Username
        if (Locale.getDefault().equals(americanEnglish)) {
           System.out.println("Please enter user login name."); 
        }
        else {
           System.out.println("Por favor ingrese el nombre de usuario"); 
        }
        String userLoginName = scanner.nextLine();
        //User enters Password
        if (Locale.getDefault().equals(americanEnglish)) {
           System.out.println("Please enter user password."); 
        }
        else {
           System.out.println("Por favor ingrese la contraseña de usuario."); 
        }
        String userPassword = scanner.next();
        
        /*Check Database for User Login. This is done by DBLogin returning the information and checking for a match against the 
        userLoginName and userPassword. If login is incorrect, restartCheck calls the function again.*/
        
        boolean restartCheck = dataBaseQueried.userDBGet(userLoginName, userPassword);
            while (!restartCheck) {
                
            if (Locale.getDefault().equals(americanEnglish)) {
           System.out.println("Please enter user login name."); 
            }
            else {
           System.out.println("Por favor ingrese el nombre de usuario"); 
            }
            userLoginName = scanner.next();
            if (Locale.getDefault().equals(americanEnglish)) {
           System.out.println("Please enter user password."); 
            }
           else {
           System.out.println("Por favor ingrese la contraseña de usuario."); 
            }
            userPassword = scanner.next();
            restartCheck = dataBaseQueried.userDBGet(userLoginName, userPassword);
            }
     
    }
    
    /*
        Using a Switch statement, allow user to select an action to "View" appointments or customers, "Create" appointments or customers,
         "Delete" appointments or customers. The functions called are Lambda expressions. 
        */
        //Insert Code for this
        
        /* Once Switch is completed, the restart Switch function  is called again unless exit the program was selected, 
          in which case a confirm action is asked. */
        //Insert Code for this.
    

    private static void userInterface (int userChoice, DBInserter DBInserterObject, DBUpdater DBUpdaterObject, DBScheduler DBSchedulerObject ) {
    
        
           
        
       switch (userChoice) {
           
           case 1: 
               System.out.println("Please enter the Customer Name. May not contain spaces or be blank.");
               String inputCustomerName = null;
               inputCustomerName = scanner.next();
               //check customer name for containing a space   
               checkForSpacesAndEmptyForUI(inputCustomerName);
               System.out.println("Please enter the Address ID. May not contain spaces or be blank.");
               int inputAddressID = Integer.parseInt(scanner.next());
               String inputAddressIDString = Integer.toString(inputAddressID);
               checkForSpacesAndEmptyForUI(inputAddressIDString); 
               System.out.println("Please enter the active state. Choose 1 for active. 0 for inactive. May not contain spaces or be blank.");
               short inputActive = Short.parseShort(scanner.next());
               String inputActiveString = Short.toString(inputActive);
               checkForSpacesAndEmptyForUI(inputActiveString);
               DBInserterObject.customerInsert(inputCustomerName, inputAddressID, inputActive);
               break;
           case 2:
               DBUpdaterObject.databaseUpdate();
               break;
           case 3:
               DBSchedulerObject.entrySchedule();
               break;
           case 4:
               DBSchedulerObject.entryUpdate();
               break;
           case 5:
               DBSchedulerObject.viewWeek();
               break;
           case 6:
               DBSchedulerObject.viewMonth();
               break;
           case 7:
               DBReports.appointmentNumberByMonth();
               break;
           case 8:
               DBReports.appointmentDailyScheduleForConsultant();
               break;
           case 9:
               DBReports.allCustomersReport();
               break;
           case 10:
               System.out.println("Thank you for using Schedule Assist, " + userLoggedIn);
               System.exit(0);
               break;
               
       }
        
    }
    
    
public static void changeUserLoggedIn (String user) {
    userLoggedIn = user;
    
} 

public static String getUserLoggedIn () {
    return userLoggedIn;
    
} 

public static Scanner getScanner () {
    return scanner;
}

public static Locale getAmericanEnglishLocale () {
    return americanEnglish;
}

public static void printChoicesforUserInterface () {
    System.out.println(userLoggedIn + ", please enter a number to the corresponding action you wish to complete.");
    System.out.println("1. Create New Customer Record ");
    System.out.println("2. Update a Customer Record ");
    System.out.println("3. Schedule an appointment ");
    System.out.println("4. Update an appointment ");
    System.out.println("5. View Weekly Schedule");
    System.out.println("6. View Monthly Schedule");
    System.out.println("7. View report for number of appointment types by month.");
    System.out.println("8. View report for today's appointments for each Consultant.");
    System.out.println("9. View report of all customers.");
    System.out.println("10. Exit Program");
}

public static void checkForSpacesAndEmptyForUI (String stringToCheck) {
                        if (stringToCheck.contains(" "))
                    {
                        try 
                        {
                            
                            
                            LambdaException LESpaceDetected = () -> DBExceptions.isSpaceDetected();
                            String showMessage = LESpaceDetected.errorMessage();
                            throw new Exception(showMessage);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            printChoicesforUserInterface();
                            int userChoiceInput = Integer.parseInt(scanner.next());
                            userInterface (userChoiceInput,inserter,updater,scheduler);
                            return;
                        }
                    }
                    //check customer name for being empty
                    if (stringToCheck.isEmpty()) 
                    {
                        
                        try 
                        {
                            
                            LambdaException LEisEmptyDetected = () -> DBExceptions.isEmptyDetected();
                            String showMessage = LEisEmptyDetected.errorMessage();
                            throw new Exception(showMessage);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            printChoicesforUserInterface();
                            int userChoiceInput = Integer.parseInt(scanner.next());
                            userInterface (userChoiceInput,inserter,updater,scheduler);
                            
                        }
                    
                        
                    }
}

// Work in progress for checking if appointment is within 15 minutes or less - used after loggin in
private static void appointmentReminder () {
    LocalDate todayToday = LocalDate.now();
    Timestamp currentDay = new Timestamp(System.currentTimeMillis());
    LocalDateTime comparedDaySetup = currentDay.toLocalDateTime();
    comparedDaySetup = comparedDaySetup.plusMinutes(15);
    Timestamp appointmentFifteenMinutesInFutureComparison = Timestamp.valueOf(comparedDaySetup);
    
    
                        // Check for overlapping appointments and throw an exception if there is one.
                        // Get all appointments of the day
                        // compare DBscheduler.hour with appointment considering the hour
                        // throw exception if they overlap
                            System.out.println ("Checking for appointments within 15 minutes");
                            
                            String selectSQL = "SELECT title, start FROM appointment WHERE start LIKE '%"+todayToday + "%' ";
                            try (PreparedStatement stmt = DBConnector.startConnecting().prepareStatement(selectSQL)) {
                                ResultSet rs = stmt.executeQuery(selectSQL);
                                while (rs.next()) {
                                String retrievedTitle = rs.getString("title");
                                Timestamp retrievedappointmentStart = rs.getTimestamp("start");
                                if (retrievedappointmentStart.before(appointmentFifteenMinutesInFutureComparison) && retrievedappointmentStart.after(currentDay)) 
                                {
                                 System.out.println("You have an appointment coming up within the next 15 minutes - " + "Appointment Title - " + retrievedTitle + "Appointment Date -" + retrievedappointmentStart );   
                                }
                                else {
                                     System.out.println ("No upcoming appointments found.");
                                }
                                }
                            }
                            catch (SQLException ex) {
                                    Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
}
    
    
    


}
