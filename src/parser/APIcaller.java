package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIcaller {
	
	private final String USER_AGENT = "Mozilla/5.0";
	private String apiHTML = "https://github.com/Naehyung/HARMs";
	private String apiURL = "http://localhost:8181/";
	
	public void get() {
		
		try {
			
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//con.setRequestProperty("Authorization", "token ffba027931ff739c1c5e8382190fc35273909de8 ");
			
			con.setDoOutput(false);
			StringBuilder sb = new StringBuilder();
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(),"utf-8"));
				String line;
				while((line=br.readLine()) !=null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			
			} else {
				
				System.out.println(con.getResponseMessage());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void post(String jsonMessage) {
		
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(jsonMessage);
			wr.flush();
			
			StringBuilder sb = new StringBuilder();
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while((line=br.readLine())!=null) {
					
					sb.append(line).append("\n");
					
				}
				br.close();
				System.out.println(""+sb.toString());
				
			} else {
				
				System.out.println(con.getResponseMessage());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
