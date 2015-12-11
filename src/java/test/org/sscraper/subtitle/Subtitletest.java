package org.sscraper.subtitle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.sscraper.network.HttpUtils;
import org.sscraper.subtitle.opensubtitlesapi.LANGUAGE;
import org.sscraper.subtitle.opensubtitlesapi.OpenSubtitlesAPI;
import org.sscraper.subtitle.opensubtitlesapi.OpenSubtitlesException;
import org.sscraper.utils.Log;

public class Subtitletest {
	static String TAG = "Scraper-Subtitletest";
	private static final String USER_NAME = "";
	private static final String PASS_WORD = "";
	private static final String USER_AGENT = "XBMC_Subtitles_v5.0.14";
	  
    public static void main(String[] args) throws IOException {
    	/*
    	if (args.length < 1) {
            Log.d(TAG, "usage: java org.sscraper.subtitle.Subtitletest file");
            return;
        }
    	*/
    	//String filename = args[0];
    	
    	//testShooter();
    	testQqMedia();
    } 
    
    
    private static void testOpenSubtitles() {
    	LANGUAGE[] languages = {LANGUAGE.ZHO, LANGUAGE.ZH};
        
        OpenSubtitlesAPI api = new OpenSubtitlesAPI();
        try {
			String token = api.login(USER_NAME, PASS_WORD, "zh", USER_AGENT);
			Log.d(TAG,  "token = " + token);
			if (token != null) {
				List<Map<String, Object>> results = api.search(token, "The Amazing Spider", languages);
				if (results != null && results.size() > 0) {
					for (int i = 0; i < results.size(); i++) {
						Log.d(TAG, "=================== " + i + "=====================");
						Log.d(TAG, "MatchedBy : " + results.get(i).get("MatchedBy"));
						Log.d(TAG, "IDSubtitleFile : " + results.get(i).get("IDSubtitleFile"));
						Log.d(TAG, "SubFileName : " + results.get(i).get("SubFileName"));
						Log.d(TAG, "SubLastTS : " + results.get(i).get("SubLastTS"));
						Log.d(TAG, "SubEncoding : " + results.get(i).get("IDSubtitleFile"));
					}
				} else {
					Log.d(TAG, "No result");
				}
				
				api.logout(token);
			}
		} catch (OpenSubtitlesException e) {
			e.printStackTrace();
		}
    }
    
    private static void testShooter() {
    	List<SubTitleInfo> subs = SubTitleProcess.findSubTitle("阿凡达", "zh");
    	if (subs != null && subs.size() > 0) {
    		for (int i = 0; i < subs.size(); i++) {
    			Log.d(TAG, "Subtitle : " + subs.get(i).toJsonString());
    			HttpUtils.download2Location(subs.get(i).getUrl(), "E:\\temp\\subtitles\\" + subs.get(i).getFilename());
    		}
    	}
    }
    
    private static void testQqMedia() {
    	List<SubTitleInfo> subs = SubTitleProcess.findSubTitle("%E9%98%BF%E5%87%A1%E8%BE%BE", "zh");
    	if (subs != null && subs.size() > 0) {
    		for (int i = 0; i < subs.size(); i++) {
    			Log.d(TAG, "Subtitle : " + subs.get(i).toJsonString());
    			HttpUtils.download2Location(subs.get(i).getUrl(), "E:\\temp\\subtitles\\" + subs.get(i).getFilename());
    		}
    	}
    }
}