package com.api;

import java.util.List;


public class APIResponse {

	int responseCode;
	List<DuoMatch> list;
	int togetherCount;
	int againstCount;
	int togetherWins;
	int againstWins;
	String error;
	
	public APIResponse(String error) {
		this.error = error;
		responseCode = 500;
	}
	
	public APIResponse(int code, List<DuoMatch> list, int togetherCount, int againstCount, int togetherWins, int againstWins) {
		this.list = list;
		responseCode = code;
		this.togetherCount = togetherCount;
		this.againstCount = againstCount;
		this.togetherWins = togetherWins;
		this.againstWins = againstWins;
	}
	
	@Override
	public String toString() {
		return "responseCode: " + responseCode + "\n" + 
				"togetherCount: " + togetherCount + "\n" + 
				"againstCount: " + againstCount + "\n" + 
				"togetherWins: " + togetherWins + "\n" + 
				"againstWins:"  + againstWins + "\n";
	}

	public int getResponseCode() {
		return responseCode;
	}
	
	public String getError() {
		return this.error;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public List<DuoMatch> getList() {
		return list;
	}

	public void setList(List<DuoMatch> list) {
		this.list = list;
	}

	public int getTogetherCount() {
		return togetherCount;
	}

	public void setTogetherCount(int togetherCount) {
		this.togetherCount = togetherCount;
	}

	public int getAgainstCount() {
		return againstCount;
	}

	public void setAgainstCount(int againstCount) {
		this.againstCount = againstCount;
	}

	public int getTogetherWins() {
		return togetherWins;
	}

	public void setTogetherWins(int togetherWins) {
		this.togetherWins = togetherWins;
	}

	public int getAgainstWins() {
		return againstWins;
	}

	public void setAgainstWins(int againstWins) {
		this.againstWins = againstWins;
	}
	
	
}
