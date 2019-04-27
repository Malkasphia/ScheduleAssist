/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;

/**
 *
 * @author Mal
 */

public class DBExceptions {
    
    static String invalidData = "You have entered invalid data. Please try again.";
    static String spaceDetected = "You have entered a space. Please re-enter data without a space.";
    
    public static String isInvalidData () 
    {return invalidData;}
    
    public static String isSpaceDetected () 
    {return spaceDetected;} 
            
    
    
    
}

