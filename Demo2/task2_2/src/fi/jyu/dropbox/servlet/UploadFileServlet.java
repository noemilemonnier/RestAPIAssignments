package fi.jyu.dropbox.servlet;


import fi.jyu.dropbox.Dropbox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

@WebServlet("/dropbox/uploadFile")
public class UploadFileServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dropbox d = new Dropbox();

        PrintWriter out = resp.getWriter();

        try {
            String token = req.getParameter("token");
            out.write(d.uploadFile(token, "C:\\Users\\luca\\Daten\\Eigene Dokumente\\schule\\ZHAW\\Semester5\\SOA\\Demo2\\task2_2\\web\\picture.jpg"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        out.flush();
        out.close();
    }
}
