import com.cdyne.ws.IP2Geo;
import com.dataaccess.webservicesserver.NumberConversion;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

@WebServlet("/ip")
public class MyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IP2Geo ipClient = new IP2Geo();
        NumberConversion numberClient = new NumberConversion();

        String ip = request.getParameter("ip");

        PrintWriter out = response.getWriter();

        if(ip.equals("") || ip == null) {
            out.write("Parameter ip is required!");
        } else {
            String[] ipbytes = ip.split("\\.");
            String[] ipword = new String[ipbytes.length];

            for(int i = 0; i < ipbytes.length; i++){
                ipword[i]  = numberClient.getNumberConversionSoap().numberToWords(new BigInteger(ipbytes[i]));
            }

            String ipResult = String.join("\ndot\n", ipword);
            String countryResult = ipClient.getIP2GeoSoap().resolveIP(ip, "1234").getCountry();

            out.write("The submitted IP-Adress is: \n\n" + ipResult + "\n\nThis IP is in: \n" + countryResult);
        }

        out.flush();
        out.close();

    }
}
