/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.qqmedia;

import java.util.ArrayList;
import java.util.List;

public class QqMediaSearchResult {
	
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
	}
	
	private List<SearchItem> searchItems = new  ArrayList<SearchItem>();
	
	public void addSearchItem(SearchItem item) { searchItems.add(item); }
	public List<SearchItem> getSearchItems() { return searchItems; }
}
