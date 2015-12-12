/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.shooter;

import java.util.ArrayList;
import java.util.List;

import org.sscraper.Status;
import org.sscraper.subtitle.shooter.ShooterSearchResult.SubContent;
import org.sscraper.utils.Log;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ShooterDetailResult {
	private final static String TAG  = "ShooterDetailResult";
	
	private int status;
	private DetailContent sub;
	
	public int getStatus() { return status; }
	public DetailContent getDetailContent() { return sub; }
	
	public int parseJson(String jsonString) {
		jsonString = jsonString.replace("\\/\\/", "//");
        jsonString = jsonString.replace("\\/", "/");
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
        
        sub = new DetailContent();
        return sub.fromJsonObject(jsub);
        
	}
	
	
	public static class DetailContent {
		private String result;
		private String action;
		
		List<ShooterSubtitle> subs = new ArrayList<ShooterSubtitle>();
		
		public List<ShooterSubtitle> getSubTitles() { return subs; }
		
		public int fromJsonObject(JSONObject jb) {
			JSONArray jsubs = jb.getJSONArray("subs");
			if (jsubs == null) {
				return Status.FORMAT_ERROR;
			}
			
			for (int i = 0; i < jsubs.size(); i++) {
				ShooterSubtitle sub = new ShooterSubtitle();
				if (sub.fromJsonObject((JSONObject)jsubs.get(i)) == Status.OK) {
					subs.add(sub);
				}
			}
			
			return Status.OK;
		}
	}
}
