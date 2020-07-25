
package com.mysoap12;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Calculator1Service", targetNamespace = "http://MySOAP12.com/", wsdlLocation = "http://localhost:8081/Calc?wsdl")
public class Calculator1Service
    extends Service
{

    private final static URL CALCULATOR1SERVICE_WSDL_LOCATION;
    private final static WebServiceException CALCULATOR1SERVICE_EXCEPTION;
    private final static QName CALCULATOR1SERVICE_QNAME = new QName("http://MySOAP12.com/", "Calculator1Service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8081/Calc?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CALCULATOR1SERVICE_WSDL_LOCATION = url;
        CALCULATOR1SERVICE_EXCEPTION = e;
    }

    public Calculator1Service() {
        super(__getWsdlLocation(), CALCULATOR1SERVICE_QNAME);
    }

    public Calculator1Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), CALCULATOR1SERVICE_QNAME, features);
    }

    public Calculator1Service(URL wsdlLocation) {
        super(wsdlLocation, CALCULATOR1SERVICE_QNAME);
    }

    public Calculator1Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CALCULATOR1SERVICE_QNAME, features);
    }

    public Calculator1Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Calculator1Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CalculatorService1
     */
    @WebEndpoint(name = "Calculator1Port")
    public CalculatorService1 getCalculator1Port() {
        return super.getPort(new QName("http://MySOAP12.com/", "Calculator1Port"), CalculatorService1.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CalculatorService1
     */
    @WebEndpoint(name = "Calculator1Port")
    public CalculatorService1 getCalculator1Port(WebServiceFeature... features) {
        return super.getPort(new QName("http://MySOAP12.com/", "Calculator1Port"), CalculatorService1.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CALCULATOR1SERVICE_EXCEPTION!= null) {
            throw CALCULATOR1SERVICE_EXCEPTION;
        }
        return CALCULATOR1SERVICE_WSDL_LOCATION;
    }

}
