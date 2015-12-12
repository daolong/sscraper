/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.shooter;

import java.util.ArrayList;
import java.util.List;

import org.sscraper.Status;

import net.sf.json.JSONObject;

public class ShooterSubtitle {
	private String filename;
	private String native_name;
	private int size;
	private long id;
	private List<String> filelist = new ArrayList<String>();
	private String subtype;
	private String videoname;
	private long view_count;
	private String upload_time;
	private int vote_score;
	private String url;
	private long down_count;
	private String title;
	
	
	public String getFilename() { return filename; }
	public String getNativename() { return native_name; }
	public int getSize() { return size; }
	public long getId() { return id; }
	public String getSubtype() { return subtype; }
	public String getVideoname() { return videoname; }
	public long getViewcount() { return view_count; }
	public String getUploadtime() { return upload_time; }
	public int getVotescore() { return vote_score; }
	public String getUrl() { return url; }
	public long getDowncount() { return down_count; }
	public String getTitle() { return title; }
	
	public int fromJsonObject(JSONObject jb) {
		try  {
			filename = jb.getString("filename");
			native_name = jb.getString("native_name");
			size = jb.getInt("size");
			id = jb.getLong("id");
			// TODO: get filelist
			subtype = jb.getString("subtype");
			videoname = jb.getString("videoname");
			view_count = jb.getLong("view_count");
			upload_time = jb.getString("upload_time");
			vote_score = jb.getInt("vote_score");
			url = jb.getString("url");
			down_count = jb.getLong("down_count");
			title = jb.getString("title");
		} catch (Exception e) {
			return Status.FORMAT_ERROR;
		}
		
		return Status.OK;
	}
}
