package com.api;

public class Champion {

	String name;
	int id;
	int wins;
	int losses;
	
	public Champion(String name, int id, int wins, int losses) {
		this.name = name;
		this.id = id;
		this.wins = wins;
		this.losses = losses;
	}
	
	public void addLoss() {
		losses++;
	}
	
	public void addWin() {
		wins++;
	}
	
	public int getTotal() {
		return wins + losses;
	}
}
