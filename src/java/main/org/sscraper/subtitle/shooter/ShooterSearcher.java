/**  
 *  Copyright (C) 2015-2016 dl@zidoo.tv
 */
package org.sscraper.subtitle.shooter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.sscraper.Status;
import org.sscraper.network.HttpUtils;
import org.sscraper.subtitle.SearcherInterface;
import org.sscraper.subtitle.SubTitleInfo;
import org.sscraper.subtitle.shooter.ShooterSearchResult.SubItem;
import org.sscraper.utils.AppConstants;
import org.sscraper.utils.Log;

public class ShooterSearcher implements SearcherInterface {
	private String NAME = "SHOOTER";
	
	@Override
	public List<SubTitleInfo> findSubTitle(String title, String language) {
        String nameUtf8 =  HttpUtils.decodeHttpParam(title, "UTF-8");
        Log.d(NAME, "find subtitle : " + nameUtf8);
        
        // encode query string on client point
		String encodeName = title;
        if (!AppConstants.SERVER)
        	encodeName = HttpUtils.encodeHttpParam(title, "UTF-8");        
        //Log.d(NAME, "encode name : " + encodeName);
        
        ShooterSearchResult search = searchSubTitle(encodeName, language);
        if (search == null || search.getSubContent() == null) {
            Log.d(NAME, "can not find information of <" + nameUtf8 + ">");
            return null;
        }
        
        List<SubTitleInfo> infos = null;
        List<SubItem> items = search.getSubContent().getSubs();
        for (int i = 0; i < items.size(); i++) {
	        ShooterDetailResult detail = getSubTitleDetail(items.get(i).getId());
	        if (detail != null && detail.getDetailContent() != null) {
	        	List<ShooterSubtitle> subtitles = detail.getDetailContent().getSubTitles();
	        	if (subtitles != null && subtitles.size() > 0) {
		        	ShooterSubtitle subtitle = subtitles.get(0);
		        	SubTitleInfo info = new SubTitleInfo();
		        	info.setTitle(title);
		        	info.setNativeName(subtitle.getNativename());
		        	info.setFilename(subtitle.getFilename());
		        	//info.setLanguage(language);
		        	info.setUrl(subtitle.getUrl());
		        	info.setType(subtitle.getSubtype());
		        	//info.setAuthor(author);
		        	info.setUploadTime(subtitle.getUploadtime());
		        	info.setSize(subtitle.getSize());
		        	info.setVoteScore(subtitle.getVotescore());
		        	info.setViewCount(subtitle.getViewcount());
		        	info.setDownloadCount(subtitle.getDowncount());
		        	
		        	info.setSource(NAME);
		        	
		        	if (infos == null) infos = new ArrayList<SubTitleInfo>();
		        	infos.add(info);
	        	} else {
	        		Log.d(NAME, "findSubTitle: get detail fail for (" + items.get(i).getId() + ")");
	        	}
	        }
        }
        
        return infos;
	}

	@Override
	public List<SubTitleInfo> findSubTitle(File movie, String language) {
		return null; // not implement
	}

	@Override
	public int downlaodSubtitle(SubTitleInfo subtitle, String location) {
		return -1; // not implement
	}

	@Override
	public int downloadSubTitle(SubTitleInfo subtitle, File file) {
		return -1; // not implement
	}

	private ShooterSearchResult searchSubTitle(String title, String language) {
		String url = AppConstants.SHOOTER_SEARCH_URL + "token=" + AppConstants.SHOOTER_TOKEN + "&q=" + title + "&cnt="
				+ AppConstants.SHOOTER_COUNT + "&pos=" + AppConstants.SHOOTER_START;

        String response = HttpUtils.httpGet(url);
        if (response != null) {
        	ShooterSearchResult search = new ShooterSearchResult();
            int ret = search.parseJson(response);
            if (ret == Status.OK) 
                return search;
            else {
                Log.d(NAME, "searchSubTitle : parserJson fail");
                return null;
            }            
        } else {
            Log.d(NAME, "searchSubTitle : HttpGet " + url + " fail!");
            return null;
        }
	}
	
	private ShooterDetailResult getSubTitleDetail(long id) {
        String url = AppConstants.SHOOTER_DETAIL_URL + "token=" + AppConstants.SHOOTER_TOKEN + "&id=" + id;
        String response = HttpUtils.httpGet(url);
        if (response != null) {
        	ShooterDetailResult subtitle = new ShooterDetailResult();
            if (subtitle.parseJson(response) == Status.OK) {
                return subtitle;
            } else {
                Log.d(NAME, "getSubTitleDetail : parse json fail");
            }
        } else {
            Log.d(NAME, "getSubTitleDetail : no response!");
        }
        
        return null;
    }
}
