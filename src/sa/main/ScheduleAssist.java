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
        System.out.println("Please enter user password");
        String userPassword = scanner.next();
        //Check Database for User Login. This is done by DBSelector returning the information and checking for a match again the userLoginName
        boolean restartCheck = dataBaseQueried.userDBGet(userLoginName, userPassword);
            while (!restartCheck) {
            System.out.println("Please enter user login name");
            userLoginName = scanner.next();
            System.out.println("Please enter user password");
            userPassword = scanner.next();
            restartCheck = dataBaseQueried.userDBGet(userLoginName, userPassword);
            }
        
        
        
    }
    

   

}
