package malicious_app.servlets;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import malicious_app.DropboxService;

/**
 * Servlet implementation class DropboxReceiver
 */
@WebServlet("/DropboxReceiver")
public class DropboxReceiver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropboxReceiver() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Creating ServletContext object
		ServletContext servCon = getServletContext();
		//String redirectURI = "https://malicousapp-env.dwz5nv5yza.us-west-2.elasticbeanstalk.com/";
		String redirectURI = "http://localhost:8080/malicious_app";
		
		String auth_code = request.getParameter("code");
		DropboxService dropbox = new DropboxService();
		String result = dropbox.getAccessToken(auth_code);
		servCon.setAttribute("auth_code", request.getParameter("code"));
		servCon.setAttribute("access_token", result.split("\"")[3]);
		servCon.setAttribute("account_id", result.split("\"")[15]);
		response.sendRedirect(redirectURI);
	}

}
