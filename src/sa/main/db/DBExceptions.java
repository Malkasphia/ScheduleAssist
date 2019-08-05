/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sa.main.db;

/**
 *
 * @author Kyle Nyce
 */

public class DBExceptions {
    
    static String invalidData = "You have entered invalid data. Please try again.";
    static String spaceDetected = "You have entered a space. Please re-enter data without a space.";
    static String isEmpty = "You have not entered any data. Please re-enter data.";
    static String isNotNumeric = "You have entered a non-numeric character for the phone number. Please re-enter data.";
    static String isBiggerThanTenDigits = "You have entered a phone number that is larger than 10 digits. Please re-enter data.";
    
    public static String isInvalidData () 
    {return invalidData;}
    
    public static String isSpaceDetected () 
    {return spaceDetected;} 
    
    public static String isEmptyDetected () 
    {return isEmpty;} 
    
    public static String isNotNumericDetected () 
    {return isNotNumeric;}
    
    public static String isBiggerThanTenDigitsDetected () 
    {return isBiggerThanTenDigits;}
    
    // Lambda Expression allows for code to be compact by storing exception error messages to be reused where needed in the whole program.
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
    //Checks that phone number only contains numbers. Then checks that the phone number is 10 characters long.
    public static boolean checkPhoneNumber (String phoneNumber) {
        if (!phoneNumber.matches("^[0-9]+$")) 
        {
                    try 
                        {
                            
                            LambdaException LEisNotNumericDetected = () -> DBExceptions.isNotNumericDetected();
                            String showMessage = LEisNotNumericDetected.errorMessage();
                            throw new Exception(showMessage);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            return true;
                        }
        }
        
        if (phoneNumber.length() > 10)  
        {
            try 
                        {
                            
                            LambdaException LEisBiggerThanTenDigitsDetected = () -> DBExceptions.isBiggerThanTenDigitsDetected();
                            String showMessage = LEisBiggerThanTenDigitsDetected.errorMessage();
                            throw new Exception(showMessage);
                        }
                        catch (Exception ex) 
                        {
                            System.out.println (ex);
                            return true;
                        }
        }
        return false;
    }
            
    
    
    
}

