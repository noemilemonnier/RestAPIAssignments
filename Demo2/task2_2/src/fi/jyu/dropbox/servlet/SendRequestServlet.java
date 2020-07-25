package fi.jyu.dropbox.servlet;

import fi.jyu.dropbox.Dropbox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/dropbox/sendRequest")
public class SendRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dropbox d = new Dropbox();

        try {
            d.sendRequest("hi");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
