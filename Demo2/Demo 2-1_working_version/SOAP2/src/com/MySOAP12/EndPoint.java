package com.MySOAP12;
import javax.xml.ws.Endpoint;
import com.MySOAP12.Calculator1;

public class EndPoint {
    
   
        
        public static void main(String[] args) {
           Endpoint.publish("http://localhost:8081/Calc", new Calculator1());
        }

    }


