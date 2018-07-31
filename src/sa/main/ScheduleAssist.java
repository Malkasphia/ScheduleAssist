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
    public static void main(String[] args) {
        
        //Test DataBase Connection
        DBLogin connect = new DBLogin();
        //Username Login Process
        System.out.println("Welcome to Schedule Assist V 0.1");
        
        ScheduleAssist.startLogin(connect);
      


        
        
    }
    

    
    private static void startLogin (DBLogin dataBaseQueried) {
        //User enters Username
        Scanner scanner = new Scanner (System.in);
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
        
        /*
        Using a Switch statement, allow user to select an action to "View" appointments or customers, "Create" appointments or customers,
         "Delete" appointments or customers, or exit the program. The functions called are Lambda expressions. 
        */
        //Insert Code for this
        
        // Once Switch is completed, the Switch is called again unless exit the program was selected, in which case a confirm action is asked.
        //Insert Code for this.
            
        
        
        
        
    }
    

   

}
