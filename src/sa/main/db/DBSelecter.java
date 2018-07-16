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


public class DBSelecter {
    //Class Members
    //private static DBConnector Connector = new DBConnector();
    private static Connection conn = null;
    
    
    //Class Methods
    //Connect to the Database, run 1st as it gets connection from Driver Manager
public Connection startConnecting () {
String db = "U03lO7";
String url = "jdbc:mysql://52.206.157.109/" + db;
String user = "U03lO7";
String pass = "53688015239";
DBSelecter.conn = null;
try {
DBSelecter.conn = DriverManager.getConnection(url,user,pass);
System.out.println("Connected to database : " + db);

}
catch (SQLException e) {
System.out.println("SQLException: "+e.getMessage());
System.out.println("SQLState: "+e.getSQLState());
System.out.println("VendorError: "+e.getErrorCode());

}
return DBSelecter.conn;
}

//Select Users from Database to validate login
public boolean userDBGet (String userInputName, String userInputPassword) {
    //comment
        startConnecting();
        try (Statement stmt = conn.createStatement()) {
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
            Logger.getLogger(DBSelecter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
}
}




