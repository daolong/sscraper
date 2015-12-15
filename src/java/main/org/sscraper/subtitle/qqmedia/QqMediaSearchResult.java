/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.qqmedia;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.sscraper.Status;
import org.sscraper.utils.Log;

public class QqMediaSearchResult {
	private static final String TAG = "QqMediaSearchResult";
	
	public static class SearchItem {
		private List<String> naLists = new ArrayList<String>();
		private List<String> chsLists = new ArrayList<String>();
		private List<String> engLists = new ArrayList<String>();
		private List<String> dualLists = new ArrayList<String>();
		private String uploadTime; // Tag AT
		private String rarUrl;  //Tag AW 
		private String title; // Tag TF
		private String rarFilename; // Tag TI
		private long id;
		
		public void addNaItem(String item) { naLists.add(item); }
		public List<String> getNaLists() { return naLists; }
		
		public void addChsItem(String item) { chsLists.add(item); }
		public List<String> getChsList() { return chsLists; }
		
		public void addEngItem(String item) { engLists.add(item); }
		public List<String> getEngLists() { return engLists; }
		
		public void addDualItem(String item) { dualLists.add(item); }
		public List<String> getDualLists() { return dualLists; }
		
		public void setUploadTime(String time) { uploadTime = time; }
		public String getUploadTime() { return uploadTime; }
		
		public void setRarUrl(String url) { rarUrl = url; }
		public String getRarUrl() { return rarUrl; }
		
		public void setTitle(String title) { this.title = title; }
		public String getTitle() { return title; }
		
		public void setRarFilename(String filename) { rarFilename = filename; }
		public String getRarFilename() { return rarFilename; }
		
		public void setId(long id) { this.id = id; }
		public long getId() { return id; }
		
		public void parseAg(String jsonString) {
			//Log.d(TAG, "parseAg : "  + jsonString);
	        if (jsonString == null) return;
	        
	        JSONObject jb = null;
	        try {
	            jb = JSONObject.fromObject(jsonString);
	        } catch (Exception e) {
	            Log.printStackTrace(e);
	        }
	        
	        if (jb == null) return;
	        
	        getValue(jb, "NA", naLists);
	        getValue(jb, "CHS", chsLists);
	        getValue(jb, "ENG", engLists);
	        getValue(jb, "DUAL", dualLists);
		}
		
		void getValue(JSONObject jb, String key, List<String> list) {
			JSONArray jna = jb.getJSONArray(key);
			if (jna != null && jna.size() > 0) {
				for (int i = 0; i < jna.size(); i++) {
					try {
						list.add(jna.getString(i));
					} catch (Exception e) {}
				}
			}
		}
		
		public String toJsonString() {
			String json = "{";
			json += listToJsonString("NA", naLists) + ",";
			json += listToJsonString("CHS", chsLists) + ",";
			json += listToJsonString("ENG", engLists) + ",";
			json += listToJsonString("DUAL", dualLists) + ",";
			json += "\"upload_titem\":\"" + uploadTime + "\",";
			json += "\"url\":\"" + rarUrl + "\",";
			json += "\"id\":" + id + ",";
			json += "\"title\":\"" + title + "\",";
			json += "\"rar_filename\":\"" + rarFilename + "\"";
			json += "}";
			return json;
		}
		
		String listToJsonString(String name, List<String> list) {
			String json = "\"" + name + "\":[";
			for (int i = 0; i < list.size(); i++) {
				json += "\"" + list.get(i) + "\"";
				if (i < list.size() - 1) {
					json += ",";
				} 
			}
			json += "]";
			return json;
		}
	}
	
	private List<SearchItem> searchItems = new  ArrayList<SearchItem>();
	
	public void addSearchItem(SearchItem item) { searchItems.add(item); }
	public List<SearchItem> getSearchItems() { return searchItems; }
	
	public String toJsonString() {
		String json = "{";
		int count = searchItems.size();
		for (int i = 0; i < count; i++) {
			json = json + searchItems.get(i).toJsonString();
			if (i < count -1) {
				json = json  + ",";
			} 
		}
		json = json + "}";
		return json;
	}
}
