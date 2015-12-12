/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */

package org.sscraper.subtitle;

public class SubTitleInfo {
	
	private String title;
	private String nativeName;
	private String filename;
	private String language;
	private String url;
	private String type;
	private String author;
	private String uploadTime;
	private int size; 
	private int voteScore;
	private long viewCount;
	private long downloadCount;
	private String source; // where from
	
	public SubTitleInfo() {
		title = "";
		nativeName = "";
		filename = "";
		language = "";
		url = "";
		type = "";
		author = "";
		uploadTime = "";
		size = -1; // invalid
		voteScore = -1; // invalid
		viewCount = -1L; // invalid
		downloadCount = -1L; // invalid
		source = "UNKNOWN";
	}
	
	public String toJsonString() {
		String json = "{\"title\":\"" + title + "\"," + 
	                   "\"native_name\":\"" + nativeName + "\"," + 
				       "\"filename\":\"" + filename + "\"," + 
				       "\"language\":\"" + language + "\"," + 
				       "\"url\":\"" + url + "\"," +
				       "\"type\":\"" + type + "\"," +
				       "\"author\":\"" + author + "\"," +
				       "\"uploadTime\":\"" + uploadTime + "\"," +
				       "\"vote_score\":" + voteScore + "," +
				       "\"view_count\":" + viewCount + "," +
				       "\"down_count\":" + downloadCount + "," +
				       "\"source\":\"" + source + "\"}";
		
		return json;
	}
	
	public void setTitle(String title) { this.title = title; }	
	public String getTitle() { return title; }
	
	public void setNativeName(String name) { nativeName = name; }	
	public String getNativeName() { return nativeName; }
	
	public void setFilename(String name) { filename = name; }
	public String getFilename() { return filename; }
	
	public void setLanguage(String language) { this.language = language; }
	public String getLanguage() { return language; }
	
	public void setUrl(String url) { this.url = url; }
	public String getUrl() { return url; }
	
	public void setType(String type) { this.type = type; }
	public String getType() { return type; }
	
	public void setAuthor(String author) { this.author = author; }
	public String getAuthor() { return this.author; }
	
	public void setUploadTime(String time) { uploadTime = time; }
	public String getUploadTime() { return uploadTime; }
	
	public void setSize(int size) { this.size = size; }
	public int getSize() { return size; }
	
	public void setVoteScore(int score) { voteScore = score; }
	public int getVoteScore() { return voteScore; }
	
	public void setViewCount(long count) { viewCount = count; }
	public long getViewCount() { return viewCount; }
	
	public void setDownloadCount(long count) { downloadCount = count; }
	public long getDownloadCount() { return downloadCount; }
	
	public void setSource(String source) { this.source = source; }
	public String getSource() { return source; }
	
}