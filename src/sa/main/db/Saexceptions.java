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

/*Construct methods that check if data entered by user is what is needed. Then build exceptions to each situation that print out the appropriate error message. 
Make each error callable as a Lambda. For example, first case is in scheduleassist.java - upon entering a customer's name, it should not contain numbers as instructed. */
    @FunctionalInterface
    interface LambdaAlert {
    public String poplambdaAlert (Saexceptions a);
}
    

public class Saexceptions {
    String alert = "hello" ; 
    
    public Saexceptions (String alertmessage) {
        this.alert = alertmessage ;
    }

    @Override
    public String toString () {return alert;}
    
    public void invalidData () {
        alert = " The data you have entered is invalid. Please try again.";
    } 

 
    
}
