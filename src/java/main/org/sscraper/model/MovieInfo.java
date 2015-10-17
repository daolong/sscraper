/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.model;

import java.util.ArrayList;
import java.util.List;

public class MovieInfo {

    private Long zmdbId; // zidoo movie data base id
    
    private String originalSearchTitle; // original search string from client
    private String searchTitle;         // search title process by guess
    
    private String title;       // title from internet
    private String otherTitle;  // alias if exist
    
    private String releaseDate;
    private Long duration;
    private String language;   // original language
    private String overView;
    
    private Double voteAverage;
    //private Long   voteCount;
    
    private String postorImageUrl;  //  url on the Internet
    private String postorImageName; //  zmdb poster image name
    
    private List<String> directors;
    private List<Actor>  actors;
    private List<String> scriptWriters;    

    private String source; // tmdb, m1905, douban etc.
    
    public MovieInfo(String searchTitle) {
        this.searchTitle = searchTitle;
        
        zmdbId = -1L; // meaning not add to data base
        directors = new ArrayList<String>();
        actors = new ArrayList<Actor>();
        scriptWriters = new ArrayList<String>();
        
        postorImageName = "poster_image.jpg";
    }
    
    public void setZmdbId(Long zmdbId) {
        this.zmdbId = zmdbId;
    }
    
    public Long getZmdb() {
        return this.zmdbId;
    }
    
    public void setOriginalSearchTitle(String originalSearchTitle) { this.originalSearchTitle = originalSearchTitle; }
    
    public String getOriginalSearChTitle() { return this.originalSearchTitle; }
    
    public void setSearchTitle(String searchTitle) { this.searchTitle = searchTitle; }
    
    public String getSearChTitle() { return this.searchTitle; }
    
    public void setTitle(String title) {
        this.title = title;        
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setOtherTitle(String title) {
        this.otherTitle = title;        
    }
    
    public String getOtherTitle() {
        return this.otherTitle;
    }
    
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public String getReleaseDate() {
        return this.releaseDate;
    }
    
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public Long getDuration()  {
        return this.duration;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void setOverView(String overView) {
        this.overView = overView;
    }
    
    public String getOverView() {
        return this.overView;
    }
    
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }
    
    public Double getVoteAverage() { return this.voteAverage; }
    
    public void addDirector(String director) {
        this.directors.add(director);
    }
    
    public List<String> getDirectors() {
        return this.directors;
    }
    
    public void addActor(Actor actor) {
        this.actors.add(actor);
    }
    
    public List<Actor> getActors() {
        return this.actors;
    }
    
    public void addSriptWriter(String scriptWriter) {
        this.scriptWriters.add(scriptWriter);
    }
    
    
    public void setPostorImageUrl(String url) {
        this.postorImageUrl = url;
    }
    
    public String getPosterImageUrl() {
        return this.postorImageUrl;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public String toJsonString() {
        String json = "{\"id\":" + this.zmdbId + ", \"title\":\"" + this.title + "\", \"other_title\":\"" + this.otherTitle + "\", \"release_date\":\"" + 
                this.releaseDate + "\", \"duration\":" + this.duration + ", \"original_language\":\"" + this.language + "\", \"poster_name\":" + 
                this.postorImageName + "\", \"poster_url\":\"" + this.postorImageUrl  + "\",\"overview\":\"" + this.overView + "\", ";  
        
        json += "\"directors\":[";
        int i = 0;
        int size = directors.size();
        for (i = 0; i < size; i++) {
            if (i == size - 1) {
                json += "{\"name\":\"" + directors.get(i) + "\"}";
            } else {
                json += "{\"name\":\"" + directors.get(i) + "\"},";
            }
        }
        json += "],";
        
        json += "\"actor\":[";
        size = actors.size();
        Actor actor;
        for (i = 0; i < size; i++) {
            actor = actors.get(i);
            if (i == size - 1) {
                json += "{\"name\":\"" + actor.getName() + "\", \"role\":\"" + actor.getRole() + "\"}";
            } else {
                json += "{\"name\":\"" + actor.getName() + "\", \"role\":\"" + actor.getRole() + "\"},";
            }
        }
        json += "]}";
        
        return json;
    }
}
