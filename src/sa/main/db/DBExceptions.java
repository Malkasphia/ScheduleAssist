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
    static String isEmpty = "You have not entered any data. Please re-enter data.";
    
    public static String isInvalidData () 
    {return invalidData;}
    
    public static String isSpaceDetected () 
    {return spaceDetected;} 
    
    public static String isEmptyDetected () 
    {return isEmpty;} 
    
    public static boolean checkForSpacesAndEmpty (String stringToCheck) {
                        if (stringToCheck.contains(" "))
                    {
                        try 
                        {
                            LambdaException LESpaceDetected = () -> DBExceptions.isSpaceDetected();
                            String showMessage = LESpaceDetected.errorMessage();
                            throw new Exception(showMessage);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            return true;
                        }
                    }
                    //check customer name for being empty
                    if (stringToCheck.isEmpty()) 
                    {
                        
                        try 
                        {
                            
                            LambdaException LEisEmptyDetected = () -> DBExceptions.isEmptyDetected();
                            String showMessage = LEisEmptyDetected.errorMessage();
                            throw new Exception(showMessage);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            return true;
                            
                        }
                        
                        
                    }
                    else {
                        return false;
                    }
                   
}
            
    
    
    
}

