package com.MySOAP12;

/**
 * @version 21.9.2019
 * 
 * Calculator service interface
 */

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService
@SOAPBinding(style = Style.RPC)
public interface CalculatorService1 {
    
    @WebMethod
    int getNumber();
    @WebMethod
    int iterate();
    @WebMethod
    String setZero();
  

}
