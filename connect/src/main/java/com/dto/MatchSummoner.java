package com.dto;

public class MatchSummoner {
	private String accountID;
	private int teamID;
	private int champID;
	
	public MatchSummoner(String accountID, int teamID, int champID){
		this.accountID = accountID;
		this.teamID = teamID;
		this.champID = champID;
	}

	public MatchSummoner() {
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public int getChampID() {
		return champID;
	}

	public void setChampID(int champID) {
		this.champID = champID;
	}
	
	
}
