/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package sa.main.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;

/**
 *
 * @author Kyle Nyce
 */

public class DBLogger {
    //static File userLoggerDirectory = new File("C:\\ScheduleAssist");
    //static File userLoggerFile = new File (userLoggerDirectory,"userLoggerFile.txt");
    
    public static void recordUserLogin () {
    String userLogged = ScheduleAssist.getUserLoggedIn();
        Timestamp currentTimeStamp = DBInserter.getCurrentTimeStamp();
    String loggerFileAppendDate = currentTimeStamp.toString();
    File userLoginFile = new File ("userLoginFile.txt");
        try {    
            if(userLoginFile.createNewFile()){ 
            System.out.println("Log File " + userLoginFile + " created.");
            } 
            else {
            System.out.println("Log File " + userLoginFile + " already exists.");   
            }
            Files.write(Paths.get("userLoginFile.txt"),userLogged.getBytes(),StandardOpenOption.APPEND );
            Files.write(Paths.get("userLoginFile.txt"),loggerFileAppendDate.getBytes(),StandardOpenOption.APPEND );
        } catch (IOException ex) {
            Logger.getLogger(DBLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
}
