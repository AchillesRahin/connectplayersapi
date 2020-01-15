package com.findconnect.connect;

import com.api.APIResponse;
import com.db.DBUtils;
import com.lookup.FindConnection;

public class APITester {

	public static void main(String[] args) {
		FindConnection searcher = new FindConnection();
		String summonerOne = "achilles great";
		String summonerTwo = "DuncanhA";
		System.out.println("starting search between " + summonerOne + " and " + summonerTwo);
		APIResponse res = searcher.searchMatches(summonerOne, summonerTwo, "NA1", 420);
		System.out.println(res.toString());

	}
	
	public static void clearDB() {
		DBUtils dao = new DBUtils();
		dao.clearDB();
		System.out.println("matches deleted successfully");
	}

}
