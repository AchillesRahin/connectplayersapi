package com.dto;

import java.util.Map;

public class Match {
	private long matchID;
	private int matchType;
	private Map<String, MatchSummoner> summoners;
	private int winningTeam;
	private long timestamp;
	private String region;
	private int season;
	private String url;
	
	public Match(long matchID, int matchType, int winningTeam){
		this.matchID = matchID;
		this.matchType = matchType;
		this.winningTeam = winningTeam;
	}

	public Match() {
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getMatchID() {
		return matchID;
	}

	public void setMatchID(long matchID) {
		this.matchID = matchID;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

	public Map<String, MatchSummoner> getSummoners() {
		return summoners;
	}

	public void setSummoners(Map<String, MatchSummoner> summoners) {
		this.summoners = summoners;
	}

	public int getWinningTeam() {
		return winningTeam;
	}

	public void setWinningTeam(int winningTeam) {
		this.winningTeam = winningTeam;
	}
	
	
}
