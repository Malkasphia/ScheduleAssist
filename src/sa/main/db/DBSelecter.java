package sa.main.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DBSelecter {
    //Class Members
    private static Connection conn = null;
    
    //Class Methods
    //Connect to the Database, run 1st as it gets connection from Driver Manager
public void startConnecting () {
String db = "U03lO7";
String url = "jdbc:mysql://52.206.157.109/" + db;
String user = "U03lO7";
String pass = "53688015239";
try {
DBSelecter.conn = DriverManager.getConnection(url,user,pass);
System.out.println("Connected to database : " + db);} catch (SQLException e) {
System.out.println("SQLException: "+e.getMessage());
System.out.println("SQLState: "+e.getSQLState());
System.out.println("VendorError: "+e.getErrorCode());
}
}

//Select Users from Database to validate login
public void userDBGet () {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user");
        } catch (SQLException ex) {
            Logger.getLogger(DBSelecter.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}




