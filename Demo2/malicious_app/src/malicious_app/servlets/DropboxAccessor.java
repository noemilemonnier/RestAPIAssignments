package malicious_app.servlets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import malicious_app.DropboxService;

/**
 * Servlet implementation class DropboxAccessor
 */
@WebServlet("/DropboxAccessor") // specifies the path, so we don't need to specify it in web.xml anymore
public class DropboxAccessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropboxAccessor() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DropboxService db_service = new DropboxService();
		try {
			String redirect_url = db_service.getAuthCodeRequest("");
			response.sendRedirect(redirect_url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
