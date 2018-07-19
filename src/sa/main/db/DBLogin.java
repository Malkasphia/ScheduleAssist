package sa.main.db;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;


public class DBLogin {
    //Class Members
    //Static DBConnector used to connect to database and provide Connection object for statements
    private static DBConnector Connector;
    //Class Methods
    //Connect to the Database, run 1st as it gets connection from Driver Manager
    //Select Users from Database to validate login
public boolean userDBGet (String userInputName, String userInputPassword) {
    //comment
        
        try (Statement stmt = DBConnector.startConnecting().createStatement()) {
            boolean noMatchingFound = false;
            ResultSet rs = stmt.executeQuery("Select * from user");
            while (rs.next()){
                String userName = rs.getString("userName");
                String userPassword = rs.getString("password");
                
                if (userName.equals(userInputName) && userPassword.equals(userInputPassword)) {
                    System.out.println("Welcome" + " " + userInputName);
                    break;
                }
                else {
                    noMatchingFound = true;
                    }

                            }
                   if (noMatchingFound) {
                    System.out.println("Username and Password did not match. Please re-enter Username and Password.");
                    
                    
                    return false;
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
}
}




