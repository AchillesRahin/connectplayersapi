package com.riotapi;

import java.util.List;

import com.dto.Match;


public class RiotAPIController {

	SummonerConverter summonerConverter;
	MatchAPICalls matchCalls;
	
	public RiotAPIController() {
		summonerConverter = new SummonerConverter();
		matchCalls = new MatchAPICalls();
	}
	public String convertSummonerToAccountID(String summonerName, String region) throws Exception {
		return summonerConverter.convertNameToAccount(summonerName, region);
	}
	public String convertAccountIDToSummoner(String accountID, String region) {
		return summonerConverter.convertAccountToName(accountID, region);
	}
	public List<Long> getMatchList(String accountID, long lastLook, String region, long currTime, int queue) {
		return matchCalls.getMatchList(accountID, lastLook, region, currTime, queue);
	}
	public Match getMatchByMatchID(long matchID, String region) {
		return matchCalls.getMachByID(matchID, region);
	}
}
