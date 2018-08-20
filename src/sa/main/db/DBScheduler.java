/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sa.main.ScheduleAssist;
/**
 *
 * @author Mal
 */

    @FunctionalInterface
    interface Scheduler {
    public void schedule ();
}


// DBScheduler allows the creation and updating of Appointments in the SQL database. It does this through lambda expressions.
public class DBScheduler implements Scheduler {
    //Class Members
    private static DBConnector Connector;
    private static DBScheduler PrimeScheduler;
    //Class Methods
    @Override
    public void schedule () {
        
    };
    
    
    

}
