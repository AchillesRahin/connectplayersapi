package com.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.api.APIResponse;
import com.api.DuoqPartner;
import com.api.DuoqPartnerAPIResponse;
import com.constants.ChampList;
import com.db.DBUtils;
import com.dto.Match;
import com.dto.MatchSummoner;
import com.riotapi.RiotAPIController;
import com.updater.UpdateSummoner;

public class FindDuoqPartners {
	
	DBUtils dao;
	ChampList champs;
	RiotAPIController riotAPIController;
	
	public FindDuoqPartners() {
		dao = new DBUtils();
		champs = new ChampList();
		riotAPIController = new RiotAPIController();
	}

	public DuoqPartnerAPIResponse search(String summonerOne, String region, int queue) {
		String accountID1 = null;
		
		try {
			accountID1 = riotAPIController.convertSummonerToAccountID(summonerOne, region);
		}
		catch(Exception e) {
			return new DuoqPartnerAPIResponse(500, e.getMessage());
		}
		UpdateSummoner us = new UpdateSummoner();
		us.updateSummoner(accountID1, region, queue);
		
		Set<Long> matchIDList = dao.getMatchIDsByAccountID(accountID1, region, queue);
		List<Match> matchList = new ArrayList<>();
		for (long id: matchIDList) {
			Match curr = dao.getMatchByID(id);
			if (curr != null) {
				matchList.add(curr);
			}
		}
		Map<String, Integer> teammateCount = new HashMap<>();
		for (Match m: matchList) {
			Map<String, MatchSummoner> summonerMap = m.getSummoners();
			
			MatchSummoner msUser = summonerMap.get(accountID1);
			
			if (msUser == null) {
				System.out.println("wtf why even here line 55 class duoqpartners.java");
			}
			
			int teamID = msUser.getTeamID();
			
			for (String summoner: summonerMap.keySet()) {
				if (summoner.equals(accountID1)) continue;
				
				MatchSummoner ms = summonerMap.get(summoner);
				if (ms.getTeamID() == teamID) {
					teammateCount.put(summoner, teammateCount.getOrDefault(summoner, 0) + 1);
				}
			}
		}
		List<String> removeList = new ArrayList<>();
		for (String s: teammateCount.keySet()) {
			if (teammateCount.get(s) <= 2) removeList.add(s);
		}
		
		for (String s: removeList) {
			teammateCount.remove(s);
		}
		
		List<String> summonerAccountList = new ArrayList<>();
		summonerAccountList.addAll(teammateCount.keySet());
		
		Collections.sort(summonerAccountList, new Comparator<String>() {
			public int compare(String a, String b) {
				return teammateCount.get(b) - teammateCount.get(a);
			}
		});
		
		int minReturned = Math.min(10, summonerAccountList.size());
		List<DuoqPartner> duoqAccounts = new ArrayList<>();
		for (int i = 0; i < minReturned;i++) {
			String accID = summonerAccountList.get(i);
			String ign = riotAPIController.convertAccountIDToSummoner(accID, region);
			if (ign != null) {
				duoqAccounts.add(new DuoqPartner(ign, teammateCount.get(accID)));
			}
			else {
				System.out.println("wtf what now. convertaccid to summoner for " + accountID1 + " returned null");
			}
		}
		return new DuoqPartnerAPIResponse(200, duoqAccounts);
	}

}
