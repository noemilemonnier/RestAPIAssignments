package server;
/**
 *  @author noemilemonnier
 *  @version 21.09.2019
 *  
 *  NumberConvertor servlet that will take user input of type number and 
 *  convert it to a written number or any type and convert it to a price.
 *  
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import soapClient.NumberConvertor;


/**
 * Servlet implementation class MyConvertor_Servlet
 */
@WebServlet("/Convert")
public class MyConvertor_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyConvertor_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NumberConvertor client = new NumberConvertor();
		
		String value = request.getParameter("value").toString(); //gets the value of the input in URL
		String type = request.getParameter("type").toString(); //gets the type of the input of url
		PrintWriter out = response.getWriter();
		
		if(value.equals("") || value == null && value.isEmpty()){ //if input isn't valid
			out.write("Error: Please, provide a value!");  
		}
		else{
			String result = "";			
			if(type.equals("number")) { 
				result = client.convert2word(value);
			}
			else{
				result = client.convert2dollars(value);
			}
			out.write(result); 			
		}
					 
		out.flush();
	    out.close();

	}

}
