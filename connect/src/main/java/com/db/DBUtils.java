package com.db;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dto.Match;
import com.dto.MatchSummoner;



public class DBUtils {
	String jdbcUrl;
	
    public DBUtils() {
    	String dbName = "summonermatch";
        String userName = "root";
        String password = "password1";
        String hostname = "localhost";
        String port = "3306";
    	jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password + "&useSSL=false";
	    
    }
	/*
	 * public void registerUser(User user) {
		user.setUsername(user.getUsername().toLowerCase());
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "INSERT INTO user_info (UserID, Username, Password, Email, Money) VALUES (null, ?, ?, ?, 100)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setString(3, user.getEmail());
			
			preparedStmt.execute();
			LOGGER.info("registered username " + user.getUsername());
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		
	}
	 */
    
    public long getLastLook(String accountID, String region) {
    	
    	Connection conn = null;
    	long ans = -1;
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
			return -1;
		}
	    try{
	    	conn = DriverManager.getConnection(jdbcUrl);
	    	Statement st = conn.createStatement();
	    	String query = "SELECT * FROM summonerlookup WHERE (accountid='" + accountID + "' AND region='" + region + "')";
			ResultSet rs = st.executeQuery(query);
			if (rs.next()){
				ans = rs.getLong("lastLookup");
			}
			conn.close();
	    } catch (Exception e){
	    	try {
				if (!conn.isClosed()) conn.close();
				return -1;
			} catch (SQLException e1) {
			}
	    }
	    return ans;
    }
    
    public void addLastLook(String accountID, String region, long lastLook) {
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "INSERT INTO summonerlookup (accountid, lastLookup, region) VALUES (?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, accountID);
			preparedStmt.setLong(2, lastLook);
			preparedStmt.setString(3, region);
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
		
	}

	public int getCurrentMatchCount(String accountID, int region, int seasonID, int matchtype) {

	    int ans = -1;
	    Connection conn = null;
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
			return -1;
		}
	    try{
	    	conn = DriverManager.getConnection(jdbcUrl);
	    	Statement st = conn.createStatement();
	    	String query = "SELECT COUNT(*) FROM acctomatch WHERE (accountID='" + accountID + "' AND region=" + region + " AND matchtype=" + matchtype + ")";
			ResultSet rs = st.executeQuery(query);
			if (rs.next()){
				ans = rs.getInt(1);
			}
			conn.close();
	    } catch (Exception e){
	    	try {
				if (!conn.isClosed()) conn.close();
				return -1;
			} catch (SQLException e1) {
			}
	    }
	    return ans;
	}
	

	public Match getMatchByID(long matchID){
		Match match = new Match();
		

	    Connection conn = null;
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
	    try{
	    	conn = DriverManager.getConnection(jdbcUrl);
	    	Statement st = conn.createStatement();
	    	String query = "SELECT * FROM matchdata WHERE MatchID='" + matchID + "'";
			ResultSet rs = st.executeQuery(query);
			if (rs.next()){
				match.setMatchID(matchID);
				match.setMatchType(rs.getInt("MatchType"));
				match.setWinningTeam(rs.getInt("WinningTeamID"));
				match.setTimestamp(rs.getLong("timestamp"));
				Map<String, MatchSummoner> matchSummoners = new HashMap<>();
				for (int i = 0; i < 10;i++){
					String accID = rs.getString("AccountID" + i);
					int teamID = rs.getInt("TeamID" + i);
					int champID = rs.getInt("ChampionID" + i);
					matchSummoners.put(accID, new MatchSummoner());
					matchSummoners.get(accID).setAccountID(accID);
					matchSummoners.get(accID).setChampID(champID);
					matchSummoners.get(accID).setTeamID(teamID);
				}
				match.setSummoners(matchSummoners);
				conn.close();
			}
			else
			{
				conn.close();
				return null;
			}
	    } catch (Exception e){
	    	try {
				if (!conn.isClosed()) conn.close();
			} catch (SQLException e1) {
			}
	    }
	    return match;
		
	}

	public Set<Long> getMatchIDsByAccountID(String accountID, String region, int matchtype) {
		Set<Long> dbMatchList = new HashSet<Long>();

	    Connection conn = null;
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
			return null;
		}
	    try{
	    	conn = DriverManager.getConnection(jdbcUrl);
	    	Statement st = conn.createStatement();
	    	String query = "SELECT * FROM acctomatch WHERE (accountID='" + accountID + "' AND region='" + region + "' AND matchtype=" + matchtype + ")";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()){
				dbMatchList.add(rs.getLong("matchID"));
			}
			conn.close();
	    } catch (Exception e){
	    	try {
				if (!conn.isClosed()) conn.close();
			} catch (SQLException e1) {
			}
	    }
	    return dbMatchList;
	}

	public void addMatchToDB(Match currMatchToAdd) {
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "INSERT INTO matchdata (MatchID, Matchtype, Region, Season, AccountID0, AccountID1, AccountID2, AccountID3, AccountID4, AccountID5, AccountID6, AccountID7, AccountID8, AccountID9, TeamID0, TeamID1, TeamID2, TeamID3, TeamID4, TeamID5, TeamID6, TeamID7, TeamID8, TeamID9, ChampionID0, ChampionID1, ChampionID2, ChampionID3, ChampionID4, ChampionID5, ChampionID6, ChampionID7, ChampionID8, ChampionID9, WinningTeamID, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, currMatchToAdd.getMatchID());
			preparedStmt.setInt(2, currMatchToAdd.getMatchType());
			preparedStmt.setString(3, currMatchToAdd.getRegion());
			preparedStmt.setInt(4, currMatchToAdd.getSeason());

			int index = 5;
			for (String summonerID: currMatchToAdd.getSummoners().keySet()) {
				MatchSummoner ms = currMatchToAdd.getSummoners().get(summonerID);
				preparedStmt.setString(index, ms.getAccountID());
				preparedStmt.setInt(index+10, ms.getTeamID());
				preparedStmt.setInt(index+20, ms.getChampID());
				index++;
			}
			preparedStmt.setInt(35, currMatchToAdd.getWinningTeam());
			preparedStmt.setLong(36, currMatchToAdd.getTimestamp());
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
		
	}
	
	public void clearDB() {
		clearAccToMatch();
		clearSummonerLookup();
		clearMatchData();
	}
	
	public void clearSummonerLookup() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "DELETE FROM summonerlookup";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
	}
	
	public void clearMatchData() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "DELETE FROM matchdata";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
	}
	
	public void clearAccToMatch() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "DELETE FROM acctomatch";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
	}

	public void addAccToMatchInDB(String accountID, Match currMatchToAdd) {

	    Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "INSERT INTO acctomatch (accountID, matchID, matchtype, region, season) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, accountID);
			preparedStmt.setLong(2, currMatchToAdd.getMatchID());
			preparedStmt.setInt(3, currMatchToAdd.getMatchType());
			preparedStmt.setString(4, currMatchToAdd.getRegion());
			preparedStmt.setInt(5, currMatchToAdd.getSeason());
			
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
		
	}

	public void updateLastLook(String accountID, String region, long currTime) {
	    Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e2) {
			System.out.println("driver missing");
			e2.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(jdbcUrl);
			String query = "UPDATE summonerlookup SET lastLookup=? WHERE accountID=?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, currTime);
			preparedStmt.setString(2, accountID);
			
			preparedStmt.execute();
			
			conn.close();
		} catch (SQLException e) {
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e1) {
				}
			}
		}
		
	}


}
