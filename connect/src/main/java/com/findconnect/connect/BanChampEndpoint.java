package com.findconnect.connect;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.BanChampsAPIResponse;
import com.lookup.FindChampionData;

@RestController
public class BanChampEndpoint {

	@RequestMapping("/findBanChamps")
	public BanChampsAPIResponse findConnections(@RequestParam Map<String,String> allParams) {
		String player1 = allParams.get("player1");
		String region = allParams.get("region");
		String champion = allParams.get("champion");
		String queue = allParams.get("queue");
		
		FindChampionData fd = new FindChampionData();
		return fd.find(player1, region, champion, Integer.parseInt(queue));
	}
}
