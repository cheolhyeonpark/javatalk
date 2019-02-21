package javatalk.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class NaverSearching {
	
	String responseJson;

	public void search(String keyword) {
		String clientId = "0vZmgcpKYstunAInxONV";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "hJHbaYUKQG";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/encyc?display=1&query="+ text;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            responseJson = response.toString();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public String getLink() {
		int linkStart = responseJson.indexOf("\"link\"");
		int descriptionStart = responseJson.indexOf("\"description\"");
		return responseJson.substring(linkStart+9, descriptionStart-2);
	}
	
	public String getDescription() {
		int descriptionStart = responseJson.indexOf("\"description\"");
		int thumbnailStart = responseJson.indexOf("\"thumbnail\"");
		return responseJson.substring(descriptionStart+16, thumbnailStart-2);
	}
}
