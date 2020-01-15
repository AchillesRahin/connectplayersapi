package com.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.api.APIResponse;
import com.api.DuoMatch;
import com.constants.ChampList;
import com.db.DBUtils;
import com.dto.Match;
import com.dto.MatchSummoner;
import com.riotapi.RiotAPIController;
import com.updater.UpdateSummoner;


public class FindConnection {
	
	
	DBUtils dao;
	ChampList champs;
	RiotAPIController riotAPIController;
	public FindConnection() {
		dao = new DBUtils();
		champs = new ChampList();
		riotAPIController = new RiotAPIController();
	}
	
	public synchronized APIResponse searchMatches(String summonerOne, String summonerTwo, String region, int queue) {
		String accountID1 = null;
		String accountID2 = null;
		
		try {
			accountID1 = riotAPIController.convertSummonerToAccountID(summonerOne, region);
			accountID2 = riotAPIController.convertSummonerToAccountID(summonerTwo, region);
		}
		catch(Exception e) {
			return new APIResponse(e.getMessage());
		}
		UpdateSummoner us = new UpdateSummoner();
		us.updateSummoner(accountID1, region, queue);
		
		//for now just update one summoner, as all we really need is one summoner's match history.
		//having second is nice but just to speed up process
		//updateSummoner(accountID2, region, queue);
		
		Set<Long> matchIDList = dao.getMatchIDsByAccountID(accountID1, region, queue);
		
		
		List<Match> matchList = new ArrayList<>();
		
		for (long matchID: matchIDList) {
			Match currMatch = dao.getMatchByID(matchID);
			matchList.add(currMatch);
		}
		
		
		int togetherCount = 0;
		int againstCount = 0;
		int togetherWins = 0;
		int againstWins = 0;
		List<DuoMatch> duoMatches = new ArrayList<>();
		for (Match m: matchList) {
			Map<String, MatchSummoner> sums = m.getSummoners();
			if (m.getWinningTeam() == 0) continue;
			if (sums.containsKey(accountID2)) {
				DuoMatch dm = new DuoMatch();
				MatchSummoner accOne = sums.get(accountID1);
				MatchSummoner accTwo = sums.get(accountID2);
				if (champs.getChampList()[accOne.getChampID()] == null) {
					System.out.println(accOne.getChampID());
				}
				dm.setChampionOne(champs.getChampList()[accOne.getChampID()]);
				dm.setChampionTwo(champs.getChampList()[accTwo.getChampID()]);
				dm.setMatchURL(m.getUrl());
				dm.setSummonerOne(summonerOne);
				dm.setSummonerTwo(summonerTwo);
				Date date = new Date(m.getTimestamp());
				dm.setTimestamp(date.toString());
				dm.setWinner(m.getWinningTeam());
				duoMatches.add(dm);
				
				if (accOne.getTeamID() == accTwo.getTeamID()) {
					togetherCount++;
					if (accOne.getTeamID() == m.getWinningTeam()) {
						togetherWins++;
					}
				}
				else {
					againstCount++;
					if (accOne.getTeamID() == m.getWinningTeam()) {
						againstWins++;
					}
				}
				
				String standardUri = "https://matchhistory.na.leagueoflegends.com/en/#match-details/<REGION>/<MatchID>";
				standardUri = standardUri.replace("<REGION>", region);
				standardUri = standardUri.replace("<MatchID>", String.valueOf(m.getMatchID()));
				dm.setMatchURL(standardUri);
			}
		}
		
		
		APIResponse res = new APIResponse(200, duoMatches, togetherCount, againstCount, togetherWins, againstWins);
		return res;
	}
	
}
