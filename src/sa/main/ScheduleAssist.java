/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main;

import java.util.Locale;
import java.util.Scanner;
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
        DBSelecter connect = new DBSelecter();
        //Username Login Process
        System.out.println("Welcome to Schedule Assist V 0.1");
        
        ScheduleAssist.startLogin(connect);
      


        
        
    }
    

    
    private static void startLogin (DBSelecter dataBaseQueried) {
        //User enters Username
        Scanner scanner = new Scanner (System.in);
        System.out.println("Please enter user login name");
        String userLoginName = scanner.nextLine();
        //Check Database for User Login. This is done by DBSelector returning the information and checking for a match again the userLoginName
        dataBaseQueried.userDBGet(userLoginName);
        
        
        
    }
    

    
}
