package malicious_app.servlets;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import malicious_app.DropboxService;

/**
 * Servlet implementation class DownloadFile
 */
@WebServlet("/DownloadFile")
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadFile() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DropboxService service = new DropboxService();
		ServletContext servCon = getServletContext();
		Object access_token = servCon.getAttribute("access_token");
		
		if(access_token != null) {
		try {
			boolean success = service.DownloadFile(access_token.toString());
			if(success) {
				response.getWriter().append("File has been downloaded to E:\\downloadedFile.png");
			}else {
				response.getWriter().append("An Error occurred. Check server log");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		}else {
			response.getWriter().append("No access token. Go back and request access to Dropbox first.");
		}
	}

}
