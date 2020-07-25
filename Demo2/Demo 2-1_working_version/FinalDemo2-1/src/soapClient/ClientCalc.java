package soapClient;

/**
 * @author Wiljam
 * @version 21.9.2019
 *
 * This is the SOAP client that calls our created SOAP.
 * Remember that WSDL has to be active in localhost that code can run
 * Run SOAP2->com.MYSOAP12->EndPoint BEFORE this file to used to generate the wsdl file
 * 
 */

import com.mysoap12.CalculatorService1;
import com.mysoap12.Calculator1Service;



public class ClientCalc {
    
    public ClientCalc() {}
    
    
    /**
     * Method that returns the number that is in our SOAP Calculator
     */
    public int getNumber() {
        
        Calculator1Service calc = new Calculator1Service();
        CalculatorService1 soap = calc.getCalculator1Port();
        int result = soap.getNumber();
        System.out.println(result);
        return result;
    }
    
    /**
     * Method that returns the new value of the number in our SOAP Calculator
     */
    public int iterate() {
        Calculator1Service calc = new Calculator1Service();
        CalculatorService1 soap = calc.getCalculator1Port();
        int result = soap.iterate();
        System.out.println(result);
        return result;
        
    }
    
    /**
     * Method that sets the value of the number in our SOAP Calculator to zero
     */
    public String setZero() {
        Calculator1Service calc = new Calculator1Service();
        CalculatorService1 soap = calc.getCalculator1Port();
        String result = soap.setZero();
        System.out.println(result);
        return result;
    }
    
    
   

}
