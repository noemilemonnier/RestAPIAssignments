package soapClient;

/**
 * @author noemilemonnier
 * @version 21.09.2019
 * 
 * NumberConvertor SOAP Client
 * Used Existing Web Service Code
 * Added error handling method for comma, dot and not a number 
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import javax.servlet.http.HttpServlet;

import com.dataaccess.webservicesserver.NumberConversion;
import com.dataaccess.webservicesserver.NumberConversionSoapType;

public class NumberConvertor {

	public NumberConvertor() {}

	public String convert2word(String inputStr){
		String result;

		//modified it to only accept full numbers
		//it is a number
		if(isNumericNumber(inputStr) == true) {
			BigInteger input_N = new BigInteger(inputStr);
			NumberConversion NC_service = new NumberConversion(); //created service object
			NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
			result = NC_serviceSOAP.numberToWords(input_N);
		}

		//if it isn't a number
		else {
			BigInteger input_N = new BigInteger("0");
			NumberConversion NC_service = new NumberConversion(); //created service object
			NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
			result = NC_serviceSOAP.numberToWords(input_N);
		}
		return result;
	}

	public String convert2dollars(String inputStr){
		String result;		
		
		//modified it to accept dot and comma for price
		if(isNumericPrice(inputStr) == true) {
			inputStr = inputStr.replace(',', '.'); //replace comma by dot so BigDecimal accepts it
			BigDecimal input_D = new BigDecimal(inputStr);
			
			if((input_D).scale() > 2) { //round up the number entered if it exceeds 2 decimals
				input_D = input_D.setScale(2, RoundingMode.CEILING);
			}
			
			NumberConversion NC_service = new NumberConversion(); //created service object
			NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
			result = NC_serviceSOAP.numberToDollars(input_D); 
		}
		
		//if it isn't a price
		else {
			BigDecimal input_D = new BigDecimal("0");
			NumberConversion NC_service = new NumberConversion(); //created service object
			NumberConversionSoapType NC_serviceSOAP = NC_service.getNumberConversionSoap(); //create SOAP object (a port of the service)
			result = NC_serviceSOAP.numberToDollars(input_D); 
		}
		return result;
	}

	public static boolean isNumericPrice(String strNum) {
		if(strNum.matches("^\\d+[,]+\\d+$")){ //if it contains a comma, replace it with a dot
			strNum = strNum.replace(',', '.');
		}
		return strNum.matches("-?\\d+(\\.\\d+)?");
	}

	public static boolean isNumericNumber(String strNum) {
		if(strNum.matches("^\\d+[,.]+\\d+$")){ //if it contains a comma or a point
			return false;
		}
		return strNum.matches("^\\d+$");
	}

}
