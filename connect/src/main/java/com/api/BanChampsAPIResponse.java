package com.api;

import java.util.List;

public class BanChampsAPIResponse {

	int responseCode;
	String error;
	
	List<Champion> champList;
	
	public BanChampsAPIResponse(int code, List<Champion> champList) {
		this.responseCode = code;
		this.champList = champList;
	}
	
	public BanChampsAPIResponse(int code, String error) {
		this.responseCode = code;
		this.error = error;
	}
}
