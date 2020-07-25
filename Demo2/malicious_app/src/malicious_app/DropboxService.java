package malicious_app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DropboxService {
	
	private static String appKey = "bcvmuyjdw7p2ugv";  // get from AppConsole when create the DropBox App
	private static String redirectURI = "http://localhost:8080/malicious_app/DropboxReceiver"; // any url to where you want to
																							   // redirect the user, has to be registered though
	//private static String redirectURI = "https://malicousapp-env.dwz5nv5yza.us-west-2.elasticbeanstalk.com/DropboxReceiver";
	private static String appSecret = "qrwammerm7tof5j";  // get from AppConsole when create the DropBox App

	public String getAuthCodeRequest(String str) throws URISyntaxException, IOException {
		// basically builds corresponding GET request that will be returned
		// so that the app can redirect to the dropbox access screen
		
		URI uri = new URI("https://www.dropbox.com/oauth2/authorize");
		StringBuilder requestUri = new StringBuilder(uri.toString());
		requestUri.append("?client_id=");
		requestUri.append(URLEncoder.encode(appKey, "UTF-8"));
		requestUri.append("&response_type=code");
		requestUri.append("&redirect_uri=" + redirectURI.toString());
		String queryResult = requestUri.toString();
		return queryResult;
	};

	public String getAccessToken(String auth_code) throws IOException {
		String code = "" + auth_code; // code get from previous step
		String queryResult = "";

		StringBuilder tokenUri = new StringBuilder("code=");
		tokenUri.append(URLEncoder.encode(code, "UTF-8"));
		tokenUri.append("&grant_type=");
		tokenUri.append(URLEncoder.encode("authorization_code", "UTF-8"));
		tokenUri.append("&client_id=");
		tokenUri.append(URLEncoder.encode(appKey, "UTF-8"));
		tokenUri.append("&client_secret=");
		tokenUri.append(URLEncoder.encode(appSecret, "UTF-8"));
		tokenUri.append("&redirect_uri=" + redirectURI.toString());
		URL url = new URL("https://api.dropbox.com/oauth2/token");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + tokenUri.toString().length());
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
			outputStreamWriter.write(tokenUri.toString());
			outputStreamWriter.flush();
			int status = connection.getResponseCode();
			Reader streamReader = null;
			if (status > 299) {
				streamReader = new InputStreamReader(connection.getErrorStream());
			} else {
				streamReader = new InputStreamReader(connection.getInputStream());
			}
			BufferedReader in = new BufferedReader(streamReader);
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			queryResult = response.toString();
		} finally {
			connection.disconnect();
		}
		return queryResult;
	};

	public String getAccountInfo(String tokenStr, String accountIDStr) throws URISyntaxException, IOException {
		String access_token = "" + tokenStr;
		String queryResult = "";
		String content = "{\"account_id\": \"" + accountIDStr + "\"}";
		URL url = new URL("https://api.dropboxapi.com/2/users/get_account");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", "" + content.length());
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
			outputStreamWriter.write(content);
			outputStreamWriter.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			queryResult = response.toString();
		} finally {
			connection.disconnect();
		}
		return queryResult;
	}

	public void UploadFile(String token, String path) throws URISyntaxException, IOException {
		String access_token = "" + token;
		String sourcePath = "" + path; // required file path on local file system
		Path pathFile = Paths.get(sourcePath);
		byte[] data = Files.readAllBytes(pathFile);
		String content = "{\"path\": \"/malicious_app_files/images/image_initial_uploaded.png\",\"mode\":\"add\",\"autorename\": true,\"mute\": false,\"strict_conflict\": false}";
		URL url = new URL("https://content.dropboxapi.com/2/files/upload");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestProperty("Dropbox-API-Arg", "" + content);
			connection.setRequestProperty("Content-Length", String.valueOf(data.length));
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(data);
			outputStream.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println("Upload response: " + response.toString());
		} finally {
			connection.disconnect();
		}
	}

	public boolean DownloadFile(String token) throws URISyntaxException, IOException {
		String access_token = "" + token;
		String content = "{\"path\": \"/malicious_app_files/images/image_initial_uploaded.png\"}";
		URL url = new URL("https://content.dropboxapi.com/2/files/download");
		boolean success = true;
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestProperty("Dropbox-API-Arg", "" + content);
			connection.setRequestProperty("Content-Length", "" + content.length());
			int status = connection.getResponseCode();
			Reader streamReader = null;
			if (status > 299) {
				streamReader = new InputStreamReader(connection.getErrorStream());
				BufferedReader in = new BufferedReader(streamReader);
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				System.out.println(response.toString());
			} else {
				streamReader = new InputStreamReader(connection.getInputStream());
				try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
					FileOutputStream fileOutputStream = new FileOutputStream("E:\\downloadedFile.png")) {
					byte dataBuffer[] = new byte[1024];
					int bytesRead;
					while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
						fileOutputStream.write(dataBuffer, 0, bytesRead);
					}
				} catch (IOException e) {
					System.out.println("error: " + e.getMessage());
					success = false;
				}
			}

		} finally {
			connection.disconnect();
		}
		return success;
	}

	public String getFileMetaData(String token) throws URISyntaxException, IOException {
		String access_token = "" + token;
		String queryResult = "";
		String content = "{\"path\": \"/malicious_app_files/images/image_initial_uploaded.png\",\"include_media_info\": false,\"include_deleted\": false,\"include_has_explicit_shared_members\": false}";
		URL url = new URL("https://api.dropboxapi.com/2/files/get_metadata");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", "" + content.length());
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
			outputStreamWriter.write(content);
			outputStreamWriter.flush();
			int status = connection.getResponseCode();
			Reader streamReader = null;
			if (status > 299) {
				streamReader = new InputStreamReader(connection.getErrorStream());
			} else {
				streamReader = new InputStreamReader(connection.getInputStream());
			}
			BufferedReader in = new BufferedReader(streamReader);
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			queryResult = response.toString();
		} finally {
			connection.disconnect();
		}
		return queryResult;
	}	
}
