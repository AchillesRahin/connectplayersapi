package com.riotapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import com.constants.RiotAPIKey;



public class SummonerConverter {
	
	public String convertAccountToName(String accountID, String region){
		try{
			String call = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-account/";
			call += accountID;
			call += "?api_key=";
			call += RiotAPIKey.code;
			
			final String USER_AGENT = "Mozilla/5.0";
			URL obj = new URL(call);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == 403 || responseCode == 400) return "malformed exception";
			if (responseCode == 429) {
				Thread.sleep(10000);
				System.out.println("429 returned waiting...");
				return convertNameToAccount(accountID, region);
			}
			if (responseCode == 404){
				return null;
			}
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			StringBuilder sb = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				sb.append(inputLine);
			}
			in.close();
			
			JSONObject json = new JSONObject(sb.toString());
			String name = json.getString("name");
			return name;
		}catch(Exception e){
			return null;
		}
	}

	public String convertNameToAccount(String name, String region) throws Exception{
			name = name.replaceAll(" ", "");
			String call = "https://" + region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/";
			call += URLEncoder.encode(name, "UTF-8");
			call += "?api_key=";
			call += RiotAPIKey.code;
			
			final String USER_AGENT = "Mozilla/5.0";
			URL obj = new URL(call);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == 403) throw new Exception("Not allowed to make this call(maybe api code timeout");
			if (responseCode == 400) throw new Exception("malformed exception");
			if (responseCode == 429) {
				Thread.sleep(10000);
				System.out.println("429 returned waiting...");
				return convertNameToAccount(name, region);
			}
			if (responseCode == 404){
				return null;
			}
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			StringBuilder sb = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				sb.append(inputLine);
			}
			in.close();
			
			JSONObject json = new JSONObject(sb.toString());
			String accountID = json.getString("accountId");
			return accountID;
	}
}
