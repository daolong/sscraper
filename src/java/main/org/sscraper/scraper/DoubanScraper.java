/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */

package org.sscraper.scraper;

import java.util.ArrayList;

import org.sscraper.Status;
import org.sscraper.model.MovieInfo;
import org.sscraper.model.douban.DoubanSearchResult;
import org.sscraper.model.douban.DoubanSearchResult.Subject;
import org.sscraper.network.HttpUtils;
import org.sscraper.utils.AppConstants;
import org.sscraper.utils.Log;


public class DoubanScraper extends ScraperBase {
    private final static String NAME = "DOUBAN";

    public DoubanScraper() {
        super(NAME);
    }
    
    public MovieInfo findMovie(String name, String year) {
        String nameUtf8 =  HttpUtils.decodeHttpParam(name, "UTF-8");
        Log.d(NAME, "find movie : " + nameUtf8);
        
        DoubanSearchResult search = searchMovie(name, year);
        Subject finalSubject = null;
        
        if (search != null) {
            ArrayList<Subject> subjects = search.getSubjects();
            if (subjects != null && subjects.size() > 0) {
                ArrayList<Subject> matchNames = new ArrayList<Subject>();  
               
                
                for (int i = 0; i < subjects.size(); i++) {
                    Subject s = subjects.get(i);
                    // exact match name only
                    if (nameUtf8.equals(s.getTitle())) {
                        matchNames.add(s);
                    }
                }
                
                if (matchNames.size() > 0) {
                    if (year != null) {
                        for (int i = 0; i < matchNames.size(); i++) {
                            if (year.equals(matchNames.get(i).getYear())) {
                                finalSubject = matchNames.get(i);
                                break;
                            }
                        }                        
                    } else {
                        finalSubject = matchNames.get(0); // get the first one
                    }
                }
            }
        }
        
        
        if (finalSubject != null) {
            Subject s = finalSubject;
            MovieInfo info = new MovieInfo(nameUtf8);
            
            info.setTitle(s.getTitle());
            info.setOriginalSearchTitle(s.getOriginalTitle());
        }
        
        return null;
    }

    private DoubanSearchResult searchMovie(String name, String year) {
        String url = AppConstants.DOUBAN_SEARCH_URL + "q=" + name;
        String response = HttpUtils.httpGet(url);
        if (response != null) {
            DoubanSearchResult search = new DoubanSearchResult();
            int ret = search.parseJson(response);
            if (ret == Status.OK) 
                return search;
            else {
                Log.d(NAME, "searchMovie : parserJson fail or no result");
                return null;
            }            
        } else {
            Log.d(NAME, "searchMovie : HttpGet " + url + " fail!");
            return null;
        }
    }

}