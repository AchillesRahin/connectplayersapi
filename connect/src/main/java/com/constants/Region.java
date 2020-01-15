package com.constants;

public class Region {
	public static int NA = 1;
	public static int convertRegionToInt(String region){
		if (region.equalsIgnoreCase("na1") || region.equalsIgnoreCase("na")) return 1;
		else if (region.equalsIgnoreCase("br1")) return 2;
		else if (region.equalsIgnoreCase("eun1")) return 3;
		else if (region.equalsIgnoreCase("euw1")) return 4;
		else if (region.equalsIgnoreCase("jp1")) return 5;
		else if (region.equalsIgnoreCase("kr"))  return 6;
		else if (region.equalsIgnoreCase("la1")) return 7;
		else if (region.equalsIgnoreCase("la2")) return 8;
		else if (region.equalsIgnoreCase("oc1")) return 9;
		else if (region.equalsIgnoreCase("tr1")) return 10;
		else if (region.equalsIgnoreCase("ru")) return 11;
		return -1;
	}
	
	public static String convertIntToPlatform(int region){
		if (region == 1) return "NA1";
		else if (region == 2) return "BR1";
		else if (region == 3) return "EUN1";
		else if (region == 4) return "EUW1";
		else if (region == 5) return "JP1";
		else if (region == 6) return "KR";
		else if (region == 7) return "LA1";
		else if (region == 8) return "LA2";
		else if (region == 9) return "OC1";
		else if (region == 10) return "TR1";
		else if (region == 11) return "RU";
		else return "";
	}
	
	public static long getRegionShift(int region){
		if (region == 1) return 10800000;
		else if (region == 2) return -3600000;
		else if (region == 3) return -21600000;
		else if (region == 4) return -10800000;
		else if (region == 5) return -43200000;
		else if (region == 6) return -39600000;
		else if (region == 7) return 7200000;
		else if (region == 8) return 0;
		else if (region == 9) return -46800000;
		else if (region == 10) return -18000000;
		else if (region == 11) return -28800000;
		return 0;
	}
}
