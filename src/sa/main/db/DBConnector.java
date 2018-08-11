/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mal
 */
// Allows the connection of all DB modules to an SQL database. Its only method startConnecting returns a conn for use in JDBC interactions.
public class DBConnector {
    private static Connection conn = null;
    
    public static Connection startConnecting () {
        String db = "U03lO7";
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = "U03lO7";
        String pass = "53688015239";
        conn = null;
    try {
        conn = DriverManager.getConnection(url,user,pass);
        System.out.println("Connected to database : " + db);

}
    catch (SQLException e) {
        System.out.println("SQLException: "+e.getMessage());
        System.out.println("SQLState: "+e.getSQLState());
        System.out.println("VendorError: "+e.getErrorCode());

}
    return conn;
}
}

