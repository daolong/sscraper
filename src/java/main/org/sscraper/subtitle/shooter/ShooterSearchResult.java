/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.shooter;

import java.util.ArrayList;
import java.util.List;

import org.sscraper.Status;
import org.sscraper.utils.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShooterSearchResult {
	private final static String TAG  = "ShooterSearchResult";
	
	private int status;
	private String errmsg;
	private SubContent sub;
	
	public int getStatus() {
		return status;
	}
	
	public String getErrMsg() { return errmsg; }
	
	public SubContent getSubContent() { return sub; }
	
	public int parseJson(String jsonString) {
		Log.d(TAG, "parseJson : "  + jsonString);
        if (jsonString == null) return Status.BAD_PARAM;
        
        JSONObject jb = null;
        try {
            jb = JSONObject.fromObject(jsonString);
        } catch (Exception e) {
            Log.printStackTrace(e);
        }
        
        if (jb == null)
            return Status.FORMAT_ERROR;
        
        status = jb.getInt("status");
        if (status != 0) {
        	Log.d(TAG, "parseJson: result fail, code(" + status + "), errMsg(" + jb.getString("errmsg") + ")");
        	return Status.NOT_FOUND;
        }
        
        JSONObject jsub = jb.getJSONObject("sub");
        if (jsub == null) {
        	return Status.NOT_FOUND;
        }
        
        sub = new SubContent();
        return sub.fromJsonObject(jsub);
	}
	
	public static class SubContent {
		private String result;
		private String action;
		private String keyword;
		
		List<SubItem> subs = new ArrayList<SubItem>();
		
		public void addSub(SubItem sub) {
			subs.add(sub);
		}
		
		public List<SubItem> getSubs() { return subs; }
		
		public int fromJsonObject(JSONObject jb) {
			JSONArray jsubs = jb.getJSONArray("subs");
			if (jsubs == null) {
				return Status.FORMAT_ERROR;
			}

			for (int i = 0; i < jsubs.size(); i++) {
				SubItem sub = new SubItem();
				if (sub.fromJsonObject((JSONObject)jsubs.get(i)) == Status.OK) {
					subs.add(sub);
				}
			}
			
			return Status.OK;
		}
	}
	
	
	public static class SubItem {
		private String native_name;
		private String videoname;
		private String revision;
		private String subtype;
		private String upload_time;
		private int vote_score;
		private long id;
		
		public void setId(long id) { this.id = id; }
		public long getId() { return this.id; }
		
		public int fromJsonObject(JSONObject jb) {
			try  {
				native_name = jb.getString("native_name");
				videoname = jb.getString("videoname");
				revision = jb.getString("revision");
				subtype = jb.getString("subtype");
				upload_time = jb.getString("upload_time");
				vote_score = jb.getInt("vote_score");
				id = jb.getLong("id");
			} catch (Exception e) {
				Log.printStackTrace(e);
				Log.d(TAG, "parse fail");
				return Status.FORMAT_ERROR;
			}
			
			return Status.OK;
		}
		
	}
}
