/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.qqmedia;

import java.io.File;
import java.io.IOException;
import java.util.List;

import nu.xom.*;

import org.sscraper.network.HttpUtils;
import org.sscraper.subtitle.SearcherInterface;
import org.sscraper.subtitle.SubTitleInfo;
import org.sscraper.subtitle.qqmedia.QqMediaSearchResult.SearchItem;
import org.sscraper.utils.AppConstants;
import org.sscraper.utils.Log;

public class QqMediaSearcher implements SearcherInterface {
	private final static String NAME = "QQMEDIA";
	
	@Override
	public List<SubTitleInfo> findSubTitle(String title, String language) {
		String nameUtf8 =  HttpUtils.decodeHttpParam(title, "UTF-8");
        Log.d(NAME, "find subtitle : " + nameUtf8);
        
        // encode query string on client point
		String encodeName = title;
        if (!AppConstants.SERVER)
        	encodeName = HttpUtils.encodeHttpParam(title, "UTF-8");        
        //Log.d(NAME, "encode name : " + encodeName);
        
        QqMediaSearchResult result = searchSubTitle(encodeName, language);
        if (result != null) {
        	Log.d(NAME, "result = " + result.toJsonString());
        }
        
		return null;
	}

	@Override
	public List<SubTitleInfo> findSubTitle(File movie, String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int downlaodSubtitle(SubTitleInfo subtitle, String location) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int downloadSubTitle(SubTitleInfo subtitle, File file) {
		// TODO Auto-generated method stub
		return -1;
	}

	private QqMediaSearchResult searchSubTitle(String title, String language) {
		String url = AppConstants.QQMEDIA_SEARCH_URL + "query=" + title + "&lang=1&preqid=" + 
				AppConstants.QQMEDIA_SEARCH_PREQID + "&platform=" + AppConstants.QQMEDIA_SEARCH_PLATFORM;
		
		Document doc;
		Builder builder = new Builder(true);
		try {
		     doc = builder.build(url);
		}
		catch (ValidityException ex) {
			Log.d(NAME, "Search Validity exception");
			doc = ex.getDocument();
		}
		catch (ParsingException ex) {
			Log.d(NAME, "Search parse exception");
			return null;
		}
		catch (IOException ex) {
			Log.d(NAME, "Search io exception");
			return null;
		}
		
	     Element root = doc.getRootElement();
	     Element head = root.getFirstChildElement("head");
	     if (head == null) {
	    	 // HACK : No head !!? maybe format change
	    	 return null;
	     }
	     Element num = head.getFirstChildElement("num");
	     if (num == null) {
	    	 // HACK: No num !!? maybe format change
	    	 return null;
	     }
	     
	     Log.d(NAME, "total number = " + num.getValue());
	     int count = 0;
	     try {
	    	 count = Integer.parseInt(num.getValue());
	     } catch (NumberFormatException e) {}
	     
	     if (count == 0)
	    	 return null;
	     
	     Elements lists = root.getChildElements("list");
	     if (lists == null || lists.size() == 0) {
	    	 Log.d(NAME, "Can not find list element");
	    	 return null;
	     }
	     
	     Log.d(NAME, "list count = " + lists.size());
	     QqMediaSearchResult result = new QqMediaSearchResult();
	     for (int i = 0; i < lists.size(); i++) {
	    	 SearchItem item = new SearchItem();
	    	 Element e = lists.get(i);
	    	 Element ag = e.getFirstChildElement("AG");
	    	 if (ag != null) item.parseAg(ag.getValue());
	    	 Element aw = e.getFirstChildElement("AW");
	    	 if (aw != null) item.setRarUrl(aw.getValue());
	    	 Element tf = e.getFirstChildElement("TF");
	    	 if (tf != null) item.setTitle(tf.getValue());
	    	 Element at = e.getFirstChildElement("AT");
	    	 if (at != null) item.setUploadTime(at.getValue());
	    	 Element id = e.getFirstChildElement("ID");
	    	 if (id != null) {
	    		 long value = -1;
	    		 try {
	    			 value = Long.parseLong(id.getValue());
	    		 } catch(Exception ex) {}
	    		 item.setId(value); 
	    	 }
	    	 
	    	 Element ti = e.getFirstChildElement("TI");
	    	 if (ti != null) item.setRarFilename(ti.getValue());
	    	 
	    	 result.addSearchItem(item);
	     }
	     
		return result;
	}
}