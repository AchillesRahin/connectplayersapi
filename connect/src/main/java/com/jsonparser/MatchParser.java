package com.jsonparser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class MatchParser {
	
	public static List<Long> convertJSONMatchArrayToListLong(JSONArray jsonArr, int queue) throws JSONException{
		List<Long> ans = new ArrayList<Long>();
		for (int i = 0; i < jsonArr.length();i++){
			JSONObject currMatch = jsonArr.getJSONObject(i);
			long matchID = currMatch.getLong("gameId");
			if (currMatch.getInt("queue") == queue) ans.add(matchID);
		}
		return ans;
	}


}
