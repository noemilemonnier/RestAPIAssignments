package server;
/**
 * @author Wiljam
 * @version 21.9.2019
 * 
 * Servlet connected to our SOAP WS that sets the number to 0.
 * 
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import soapClient.ClientCalc;


@WebServlet("/SetZero")
public class SetZero extends HttpServlet {
    
   /**
	 * @see HttpServlet#HttpServlet()
	 */
    private static final long serialVersionUID = 1L;

    public SetZero() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       ClientCalc calc = new ClientCalc();
       String value = calc.setZero();
       log("GET");
       String myjson = "{The number is now : "+value+"}";
       JSONObject jsonObj = new JSONObject(myjson);
       
       response.setContentType("application/json");
       response.setCharacterEncoding("UTF-8");
       response.getWriter().write(jsonObj.toString());
        
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        log("POST");
        
    }


}
