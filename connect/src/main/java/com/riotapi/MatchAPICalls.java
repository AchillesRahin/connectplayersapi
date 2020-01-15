package com.riotapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.constants.MillisecondTime;
import com.constants.RiotAPIKey;
import com.dto.Match;
import com.jsonparser.MatchListParser;


public class MatchAPICalls {

	public List<Long> getMatchList(String accountID, long lastLook, String region, long currTime, int queue){
		List<Long> matchList = new ArrayList<>();
		while (lastLook < currTime) {
			List<Long> currMatchList = getMatchListByTime(accountID, lastLook, Math.min(lastLook + MillisecondTime.ONE_WEEK, currTime), region, queue);
			if (currMatchList != null) {
				matchList.addAll(currMatchList);
			}
			else {
				System.out.println("null found");
			}

			lastLook += MillisecondTime.ONE_WEEK;
		}
		return matchList;
	}

	public List<Long> getMatchListByTime(String accountID, long time, long endTime, String region, int queue){
		try{
			String call = "https://<REGION>.api.riotgames.com/lol/match/v4/matchlists/by-account/<ACCOUNTID>?beginTime=<BEGINTIME>&endTime=<ENDTIME>";
			call = call.replace("<REGION>", region);
			call = call.replace("<ACCOUNTID>", accountID);
			call = call.replace("<BEGINTIME>", String.valueOf(time));
			call = call.replace("<ENDTIME>", String.valueOf(endTime));
			call += "&api_key=";
			call += RiotAPIKey.code;
			
			final String USER_AGENT = "Mozilla/5.0";
			URL obj = new URL(call);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == 403 || responseCode == 400) return new ArrayList<>();
			if (responseCode == 429) {
				Thread.sleep(10000);
				System.out.println("429 returned waiting...");
				return getMatchListByTime(accountID, time, endTime, region, queue);
			}
			if (responseCode == 404){
				return new ArrayList<>();
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
			JSONArray jsonMatchList = json.getJSONArray("matches");
			List<Long> matchList = MatchListParser.getMatchList(jsonMatchList, queue);
			return matchList;
		}catch(Exception e){
			return new ArrayList<>();
		}
	}

	public Match getMachByID(long matchID, String region) {
		Match m = new Match();
		try{
			//             https://na1.api.riotgames.com/lol/match/v4/matches/3147067513
			String call = "https://<REGION>.api.riotgames.com/lol/match/v4/matches/<MATCHID>";
			call = call.replace("<REGION>", region);
			call = call.replace("<MATCHID>", String.valueOf(matchID));
			call += "?api_key=";
			call += RiotAPIKey.code;
			
			final String USER_AGENT = "Mozilla/5.0";
			URL obj = new URL(call);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			int responseCode = con.getResponseCode();
			
			if (responseCode == 403 || responseCode == 400) return null;
			if (responseCode == 429) {
				Thread.sleep(10000);
				System.out.println("429 returned waiting...");
				return getMachByID(matchID, region);
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
			m = MatchListParser.getMatch(json, region);
			m.setRegion(region);
		}catch(Exception e){
			
			System.out.println(e.getMessage());
		}
		return m;
	}

}
