/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.model.douban;

import java.util.ArrayList;

import org.sscraper.Status;
import org.sscraper.utils.Log;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class DoubanMovie {
    private static String TAG = "DoubanMovie";
    
    public static class NameItem {
        private String name;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }        
    }
    
    private ArrayList<NameItem> directors = new ArrayList<NameItem>();
    private ArrayList<NameItem> script_writers = new ArrayList<NameItem>();
    private ArrayList<NameItem> actors = new ArrayList<NameItem>();
    private ArrayList<NameItem> genres = new ArrayList<NameItem>();
    
    private String area;
    private String spoken_language;
    private String release_date;
    private Long  duration;
    private String other_title;
    private String imdb_link;
    private String imdb_id;
    
    public ArrayList<NameItem> getDirectors() { return this.directors; }
    public void addDirector(NameItem director) { this.directors.add(director); }
    
    public ArrayList<NameItem> getScriptWriter() { return this.script_writers; }
    public void addScriptWriter(NameItem script_writer) { this.script_writers.add(script_writer); }
    
    public ArrayList<NameItem> getActors() { return this.actors; }
    public void addActor(NameItem actor) { this.script_writers.add(actor); }
    
    public ArrayList<NameItem> getGenres() { return this.genres; }
    public void addGenre(NameItem genre) { this.genres.add(genre); }
    
    public void setArea(String area) { this.area = area; }
    public String getArea() { return this.area; }
    
    public void setSpokenLanguage(String spoken) { this.spoken_language = spoken; }
    public String getSpokenLanguage() { return this.spoken_language; }
    
    public void setReleaseDate(String release_date) { this.release_date = release_date; }
    public String getReleaseDate() { return this.release_date; }
    
    public void setDuration(Long duration) { this.duration = duration; }
    public Long getDuration() { return this.duration; }
    
    public void setOtherTitle(String other_title) { this.other_title = other_title; }
    public String getOtherTitle() { return this.other_title; }
    
    public void setImdbLink(String imdb_link) { this.imdb_link = imdb_link; }
    public String getImdbLink() { return this.imdb_link; }
    
    public void setImdbId(String imdb_id) { this.imdb_id = imdb_id; }
    public String getImdbId() { return this.imdb_id; }
    
    public int parseHtml(String htmlString) {
        
        Document doc = Jsoup.parse(htmlString);
        if (doc == null) {
            return Status.FORMAT_ERROR;
        }
        
        // information
        Element infoElement = doc.getElementById("info");
        if (infoElement != null) {
            Elements spans = infoElement.getElementsByTag("span");
            for (Element e : spans) {
                String className = e.attr("class");
                Log.d(TAG, "class = " + className);
                if (className != null && !className.isEmpty() && className.equals("pl")) {
                    System.out.println("text : " + e.text());
                    System.out.println("nextSibling : " + e.nextSibling());
                    if (e.text().startsWith("类型")) {
                        Elements es = e.nextElementSibling();
                    }
                    if (e.text().startsWith("IMDb")) {
                        System.out.println("match imdb info");
                        Element imdbInfo = e.nextElementSibling();
                        if (imdbInfo != null) {
                            System.out.println("imdb url = " + imdbInfo.attr("href"));
                            System.out.println("imdb id = " + imdbInfo.text());
                        } else {
                            System.out.println("can not find imdb info");
                        }
                    }
                } 
                
            }
            
        } else {
            System.out.println("No info id");
        }
        
        return Status.OK;
    }
    
}
