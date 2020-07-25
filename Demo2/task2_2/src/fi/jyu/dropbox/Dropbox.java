package fi.jyu.dropbox;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Dropbox {
    private String appKey = "o03i3jtulm1iwb0";
    private String appSecret = "dgrdv06l2be81sj";
    private String redirectURI = "http://localhost:8080/";

    public void sendRequest(String str) throws URISyntaxException, IOException {
        // basically builds corresponding GET request that will be returnd to the front-end…
        URI uri=new URI("https://www.dropbox.com/oauth2/authorize");
        StringBuilder requestUri=new StringBuilder(uri.toString());
        requestUri.append("?client_id=");
        requestUri.append(URLEncoder.encode(appKey,"UTF-8"));
        requestUri.append("&response_type=code");
        requestUri.append("&redirect_uri="+redirectURI.toString());
        String queryResult = requestUri.toString();
    }

    public String accessToken(String codeStr) throws URISyntaxException, IOException {
        String result = "";

        String code = "" + codeStr; //code get from previous step
        StringBuilder tokenUri = new StringBuilder("code=");
        tokenUri.append(URLEncoder.encode(code, "UTF-8"));
        tokenUri.append("&grant_type=");
        tokenUri.append(URLEncoder.encode("authorization_code", "UTF-8"));
        tokenUri.append("&client_id=");
        tokenUri.append(URLEncoder.encode(appKey, "UTF-8"));
        tokenUri.append("&client_secret=");
        tokenUri.append(URLEncoder.encode(appSecret, "UTF-8"));
        tokenUri.append("&redirect_uri=" + redirectURI);
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
            result = response.toString();
        } finally {
            connection.disconnect();
        }

        return result;
    }

    public String getAccountInfo(String tokenStr, String accountIDStr) throws URISyntaxException, IOException {
        String queryResult = "";
        String access_token = "" + tokenStr;
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

    public String uploadFile(String token, String path) throws URISyntaxException, IOException {
        String queryResult = "";
        String access_token = "" + token;
        String sourcePath = "" + path; //required file path on local file system
        Path pathFile = Paths.get(sourcePath);
        byte[] data = Files.readAllBytes(pathFile);
        String content = "{\"path\": \"/MyDBoxClient_App01_files/images/image_initial_uploaded.png\",\"mode\":\"add\",\"autorename\": true,\"mute\": false,\"strict_conflict\": false}";
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
            queryResult = response.toString();
        } finally {
            connection.disconnect();
        }

        return queryResult;
    }

    public String deleteFile(String token) throws URISyntaxException, IOException {
        String queryResult = "";
        String access_token = "" + token;
        String content = "{\"entries\": [{ \"path\": \"/MyDBoxClient_App01_files\" }]}";
        URL url = new URL("https://api.dropboxapi.com/2/files/delete_batch");
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
}
