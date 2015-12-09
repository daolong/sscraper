package org.sscraper.subtitle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.sscraper.subtitle.opensubtitlesapi.OpenSubtitlesAPI;
import org.sscraper.subtitle.opensubtitlesapi.OpenSubtitlesException;
import org.sscraper.utils.Log;

public class Subtitletest {
	static String TAG = "Scraper-Subtitletest";
	private static final String USER_NAME = "";
	private static final String PASS_WORD = "";
	private static final String USER_AGENT = "XBMC_Subtitles_v5.0.14";
	private static final String LANGUAGE = "zh";
	  
    public static void main(String[] args) throws IOException {
    	/*
    	if (args.length < 1) {
            Log.d(TAG, "usage: java org.sscraper.subtitle.Subtitletest file");
            return;
        }
    	*/
    	//String filename = args[0];
        File movie = new File("G:\\喋血江湖 (1990) NTSC DVD5 - VHS rip - Max Mok.iso");
        
        OpenSubtitlesAPI api = new OpenSubtitlesAPI();
        try {
			String token = api.login(USER_NAME, PASS_WORD, LANGUAGE, USER_AGENT);
			Log.d(TAG,  "token = " + token);
			if (token != null) {
				List<Map<String, Object>> results = api.search(token, movie, org.sscraper.subtitle.opensubtitlesapi.LANGUAGE.ZHO);
				if (results != null && results.size() > 0) {
					for (int i = 0; i < results.size(); i++) {
						Log.d(TAG, "MatchedBy : " + results.get(i).get("MatchedBy"));
						Log.d(TAG, "IDSubtitleFile : " + results.get(i).get("IDSubtitleFile"));
						Log.d(TAG, "SubFileName : " + results.get(i).get("SubFileName"));
						Log.d(TAG, "SubLastTS : " + results.get(i).get("SubLastTS"));
						Log.d(TAG, "SubEncoding : " + results.get(i).get("IDSubtitleFile"));
					}
				} else {
					Log.d(TAG, "No result");
				}
			}
		} catch (OpenSubtitlesException e) {
			e.printStackTrace();
		}
        
    } 
}