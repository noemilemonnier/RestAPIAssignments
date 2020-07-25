package server;
/**
 * @author noemilemonnier
 * @version 22.09.2019
 *
 * Servlet used to test the connection with the Jetty server. Does NOT call SOAP Web Service!
 * It will return the value of type 'str'
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;



@WebServlet("/GetString")
public class GetString extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetString() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log("POST");
		String searchString = request.getParameter("str").toString(); //gets the user input "str=value"
		//if the input is NOT empty or null
		if(searchString != null && !searchString.isEmpty()) {
			log(searchString);
			String ans = new StringBuilder(searchString).toString();
			log(ans);
			String myjson = "{The String given is : "+ans+"}";
			JSONObject jsonObj = new JSONObject(myjson);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObj.toString());
		}
		else {
	           Map<String, String> options = new LinkedHashMap<>();
	           options.put("message", "Bad Request: body of the request message is empty or incomplete...");
	           String json = new Gson().toJson(options);
	           
	               
	           response.setContentType("application/json");
	           response.setCharacterEncoding("UTF-8");
	           response.setStatus(400);
	           response.getWriter().write(json.toString());
	       }

	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log("POST");
	}



}
