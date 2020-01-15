package com.findconnect.connect;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.DuoqPartnerAPIResponse;
import com.lookup.FindDuoqPartners;

@RestController
public class DuoqEndpoint {

	
	@RequestMapping("/findDuoqPartners")
	public DuoqPartnerAPIResponse findConnections(@RequestParam Map<String,String> allParams) {
		String player1 = allParams.get("player1");
		String region = allParams.get("region");
		String queue = allParams.get("queue");
		
		FindDuoqPartners dp = new FindDuoqPartners();
		return dp.search(player1, region, Integer.parseInt(queue));
	}
}
