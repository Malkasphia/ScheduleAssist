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
    public void poplambdaAlert (saexceptions alerter);
}
    

public class saexceptions extends Exception implements LambdaAlert {
    private static LambdaAlert LambdaAlertObj;
    String alert;
    
    public saexceptions(String alertMessage) {
    this.alert = alertMessage;
    }
    
   /*     
    public void generatelambdaAlert() {
     LambdaAlertObj = () -> {
         new saexceptions();
     };
     LambdaAlertObj.poplambdaAlert();
    }
*/

    /**
     *
     * @param alerter
     */

    // Learn Lambda Expressions Tomorrow    
    @Override
        public void poplambdaAlert (saexceptions alerter) {
            
        }
        
    
    
    
}
