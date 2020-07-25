package com.MySOAP12;

/**
 * @version 21.9.2019
 *
 **Calculator service that has three methods
 */

import javax.jws.WebService;
import com.MySOAP12.CalculatorService1;

//Service Implementation

@WebService(endpointInterface = "com.MySOAP12.CalculatorService1")
public class Calculator1 implements CalculatorService1{
    
    private int number;
    
    @Override
    public int iterate() {
        this.number= this.number+1;
        return this.number;
    }
    
    @Override
    public String setZero() {
        this.number = 0;
        return "Now 0 again!";
    }
    @Override
    public int getNumber() {
        return this.number;
    }

}
