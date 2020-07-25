package server;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import javax.servlet.ServletException;

/**
 * @version 19.9.2019
 * Java version is SE 8
 * Run SOAP2->com.MYSOAP12->EndPoint BEFORE this file to used to generate the wsdl file
 * Using jetty-all.jar that should have all the jetty libraries 
 */

public class JettyServ {

    /** Main method to run our jetty server
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(returnReverse.class, "/Reverse");   //http://localhost:82/Reverse?str=car  -> will return json {ans : your string reversed}
        handler.addServletWithMapping(GetString.class, "/GetString");    //http://localhost:82/GetString?str=car  -> will return your string as json {ans : your string}

        ///Our SOAP
        handler.addServletWithMapping(ReturnNumb.class, "/Return_Numb"); //http://localhost:82/Return_Numb -> value ogf calc
        handler.addServletWithMapping(Iterate.class, "/Iterate");  // http://localhost:82/Iterate -> value ++
        handler.addServletWithMapping(SetZero.class, "/SetZero");  // http://localhost:82/SetZero -> value is 0 again
        
        //Another Web service SOAP
        handler.addServletWithMapping(MyConvertor_Servlet.class, "/Convert");    //http://localhost:82/Convert?value=12&type=number OR http://localhost:82/Convert?value=12&type=cash

        Server server = new Server(82);  //port selected    
        server.setHandler(handler); //adding the servlet to the jetty server
        server.start();
        server.dumpStdErr();
        server.join();
        
    }

}
