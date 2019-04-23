/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sa.main.db.*;

/**
 *
 * @author Mal
 */

public class ScheduleAssist {

    /**
     * @param args the command line arguments
     */
    
    
    static Scanner scanner = new Scanner(System.in);
    static String userLoggedIn = "Not Logged In";
    static DBLogin connect = new DBLogin();
    static DBInserter inserter = new DBInserter();
    static DBUpdater updater = new DBUpdater();
    static DBScheduler scheduler = new DBScheduler();
    
    
    public static void main(String[] args) {
        //Initialize objects for logging into database, insertion, updating, scheduler, and scanner to use new lines as delimiter.
        scanner.useDelimiter("\\n");
        
        
        
        //Username Login Process
        System.out.println("Welcome to Schedule Assist V 0.1");
        ScheduleAssist.startLogin(connect);
        System.out.println(userLoggedIn + ", please enter a number to the corresponding action you wish to complete.");
        System.out.println("1. Create New Customer Record ");
        System.out.println("2. Update a Customer Record ");
        System.out.println("3. Schedule an appointment ");
        System.out.println("4. Update an appointment ");
        System.out.println("5. View Weekly Schedule");
        System.out.println("6. View Monthly Schedule");
        
        int userChoiceInput = Integer.parseInt(scanner.next());
        
            
        
        ScheduleAssist.userInterface(userChoiceInput,inserter,updater,scheduler);
 
    }
    

    
    private static void startLogin (DBLogin dataBaseQueried) {
        //User enters Username
        
        System.out.println("Please enter user login name");
        String userLoginName = scanner.nextLine();
        //User enters Password
        System.out.println("Please enter user password");
        String userPassword = scanner.next();
        
        /*Check Database for User Login. This is done by DBLogin returning the information and checking for a match against the 
        userLoginName and userPassword. If login is incorrect, restartCheck calls the function again.*/
        
        boolean restartCheck = dataBaseQueried.userDBGet(userLoginName, userPassword);
            while (!restartCheck) {
            System.out.println("Please enter user login name");
            userLoginName = scanner.next();
            System.out.println("Please enter user password");
            userPassword = scanner.next();
            restartCheck = dataBaseQueried.userDBGet(userLoginName, userPassword);
            }
            
            
        
        
           
    }
    
    /*
        Using a Switch statement, allow user to select an action to "View" appointments or customers, "Create" appointments or customers,
         "Delete" appointments or customers, or exit the program. The functions called are Lambda expressions. 
        */
        //Insert Code for this
        
        /* Once Switch is completed, the restart Switch function  is called again unless exit the program was selected, 
          in which case a confirm action is asked. */
        //Insert Code for this.
    
    //REWRITE THIS METHOD AND MAKE IT EASIER TO USE AND ISOLATED
    private static void userInterface (int userChoice, DBInserter DBInserterObject, DBUpdater DBUpdaterObject, DBScheduler DBSchedulerObject ) {
        
       switch (userChoice) {
           
           case 1: 
               System.out.println("Please enter the Customer Name. Name limit is 20 characters. May not contain spaces.");
               String inputCustomerName = null;
               
               
               inputCustomerName = scanner.next();
               
               System.out.println ("Input of " + inputCustomerName);
               
                    if (inputCustomerName.contains(" "))
                    {
                        try 
                        {
                            
                            throw new Exception(" Space detected in customer name, please try again. ");
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            System.out.println(userLoggedIn + ", please enter a number to the corresponding action you wish to complete.");
                            System.out.println("1. Create New Customer Record ");
                            System.out.println("2. Update a Customer Record ");
                            System.out.println("3. Schedule an appointment ");
                            System.out.println("4. Update an appointment ");
                            System.out.println("5. View Weekly Schedule");
                            System.out.println("6. View Monthly Schedule");
                            int userChoiceInput = Integer.parseInt(scanner.next());
                            userInterface (userChoiceInput,inserter,updater,scheduler);
                            return;
                        }
                    }
               
               System.out.println("Please enter the Address ID. Limit 10 numbers.");
               int inputAddressID = Integer.parseInt(scanner.next());
               System.out.println("Please enter the active state. Choose 1 for active. 0 for inactive.");
               short inputActive = Short.parseShort(scanner.next());
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
    
    
    


}
