package sa.main.db;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import sa.main.ScheduleAssist;


public class DBLogin {
    //Class Members
    //Static DBConnector used to connect to database and provide Connection object for statements
    private static DBConnector Connector;
    //Class Methods
    //Connect to the Database, run 1st as it gets connection from Driver Manager
    //Select Users from Database to validate login
public boolean userDBGet (String userInputName, String userInputPassword) {
    
        
        try (Statement stmt = DBConnector.startConnecting().createStatement()) {
            boolean noMatchingFound = true;
            ResultSet rs = stmt.executeQuery("Select * from user");
            while (rs.next()){
                String userName = rs.getString("userName");
                if (!userName.equals(userInputName)) {
                    continue;
                }
                
                String userPassword = rs.getString("password");
                if (!userPassword.equals(userInputPassword)){
                    continue;
                }
                
                
                if (userName.equals(userInputName) & userPassword.equals(userInputPassword)) {
                                    if (Locale.getDefault().equals(ScheduleAssist.getAmericanEnglishLocale())){
                                        System.out.println("Welcome" + " " + userInputName);
                                        ScheduleAssist.changeUserLoggedIn(userInputName);
                                        DBLogger.recordUserLogin();
                                    }
                                    else {
                                   System.out.println("Bienvenido" + " " + userInputName); 
                                        ScheduleAssist.changeUserLoggedIn(userInputName);
                                        DBLogger.recordUserLogin();
                                    }
                    
                    
                    noMatchingFound = false;
                    break;
                }
                

                            }
                   if (noMatchingFound) {
                    try { 
                                if (Locale.getDefault().equals(ScheduleAssist.getAmericanEnglishLocale())) {
                                    throw new Exception("Username and Password did not match. Please re-enter Username and Password.");
                                    }
                                else {
                                       throw new Exception("El nombre de usuario y la contraseña no coinciden. Vuelva a introducir el nombre de usuario y la contraseña."); 
                                    }

                    }
                    catch (Exception ex){System.out.println(ex);}
                    return false;
                    
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
}
}




