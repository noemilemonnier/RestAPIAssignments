package malicious_app.servlets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import malicious_app.DropboxService;

/**
 * Servlet implementation class UploadFile
 */
@WebServlet("/UploadFile")
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFile() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext servCon = getServletContext();
		Object access_token = servCon.getAttribute("access_token");
		DropboxService service = new DropboxService();
		URL url = getClass().getResource("dontTakeMyLeaf.jpg");
		String path_to_file = url.getPath().substring(1);
		if (access_token != null) {
			try {
				service.UploadFile(access_token.toString(), path_to_file);
				response.getWriter().append("Upload successful or Item already existed and has not been modified.");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
		} else {
			response.getWriter().append("No access token. Go back and request access to Dropbox first.");
		}
		
	}

}
