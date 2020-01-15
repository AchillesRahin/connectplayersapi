package com.jsonparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dto.Match;
import com.dto.MatchSummoner;



public class MatchListParser {

	
	public static List<Long> getMatchList(JSONArray jsonMatchList, int queue) throws JSONException{
		List<Long> matchList = new ArrayList<>();
		
		for (int i = 0; i < jsonMatchList.length();i++) {
			JSONObject curr = jsonMatchList.getJSONObject(i);
			long currMatchID = curr.getLong("gameId");
			matchList.add(currMatchID);
		}
		return matchList;
	}

	public static Match getMatch(JSONObject json, String region) throws JSONException {
		Match m = new Match();
		m.setMatchID(json.getLong("gameId"));
		m.setMatchType(json.getInt("queueId"));
		m.setSeason(json.getInt("seasonId"));
		m.setTimestamp(json.getLong("gameCreation"));
		
		JSONArray teams = json.getJSONArray("teams");
		
		for (int i = 0; i < teams.length();i++) {
			JSONObject team = teams.getJSONObject(i);
			String win = null;
			try {
				win = team.getString("win");
			}
			catch(Exception e) {
				win = "false";
			}
			if (win.equals("Win")) m.setWinningTeam(team.getInt("teamId"));
		}
		m.setUrl("https://matchhistory.na.leagueoflegends.com/en/#match-details/" + region + "/" + m.getMatchID() + "?tab=overview");
		
		Map<Integer, MatchSummoner> map = new HashMap<>();
		
		JSONArray participantIdentityList = json.getJSONArray("participantIdentities");
		for (int i = 0; i < participantIdentityList.length();i++) {
			JSONObject participantIdentity = participantIdentityList.getJSONObject(i);
			int participantID = participantIdentity.getInt("participantId");
			map.put(participantID, new MatchSummoner());
			JSONObject playerdto = participantIdentity.getJSONObject("player");
			String accountID = playerdto.getString("currentAccountId");
			map.get(participantID).setAccountID(accountID);
		}
		
		JSONArray participantList = json.getJSONArray("participants");
		
		for (int i = 0; i < participantList.length();i++) {
			JSONObject participantdto = participantList.getJSONObject(i);
			int participantId = participantdto.getInt("participantId");
			map.get(participantId).setChampID(participantdto.getInt("championId"));
			map.get(participantId).setTeamID(participantdto.getInt("teamId"));
		}
		
		
		Map<String, MatchSummoner> summoners = new HashMap<>();
		
		for (int partID: map.keySet()) {
			MatchSummoner ms = map.get(partID);
			summoners.put(ms.getAccountID(), ms);
		}

		m.setSummoners(summoners);
		return m;
	}
}
