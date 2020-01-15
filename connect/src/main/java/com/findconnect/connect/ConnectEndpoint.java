package com.findconnect.connect;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.APIResponse;
import com.lookup.FindConnection;

@RestController
public class ConnectEndpoint {
	
	@RequestMapping("/findConnection")
	public APIResponse findConnections(@RequestParam Map<String,String> allParams) {
		String player1 = allParams.get("player1");
		String player2 = allParams.get("player2");
		String region = allParams.get("region");
		String queue = allParams.get("queue");
		
		FindConnection sr = new FindConnection();
		return sr.searchMatches(player1, player2, region, Integer.parseInt(queue));
	}

}
