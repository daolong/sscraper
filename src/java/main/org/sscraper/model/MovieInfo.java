/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sun.net.www.http.PosterOutputStream;

public class MovieInfo {

    private Long zmdbId; //zidoo movie data base id
    
    private String originalSearchTitle; // original search string from client
    private String searchTitle;         // search title process by guess
    
    private String title;       // title from Internet
    private String otherTitle;  // alias if exist
    
    private String releaseDate;
    private Long duration;
    private String language;   // original language
    private String overView;
    
    private Double voteAverage;
    //private Long   voteCount;
    
    private String posterImageUrl;  //  url on the Internet
    private String posterImageName; //  zmdb poster image name
    
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
        
        posterImageName = "poster_image.jpg";
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
        this.posterImageUrl = url;
    }
    
    public String getPosterImageUrl() {
        return this.posterImageUrl;
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
                this.posterImageName + "\", \"poster_url\":\"" + this.posterImageUrl  + "\",\"overview\":\"" + this.overView + "\", ";  
        
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

    public static String getMysqlCreateTableCommand() {
        return "CREATE TABLE IF NOT EXISTS movies(id INT PRIMARY KEY AUTO_INCREMENT, " + 
               "original_search_title VARCHAR(512), " +
                "search_title VARCHAR(512), " + 
                "title VARCHAR(512), " + 
                "other_title VARCHAR(512), " + 
                "release_date VARCHAR(32), " + 
                "duration BIGINT, " +
                "language VARCHAR(10), " + 
                "overview VARCHAR(1024), " + 
                "vote_average DOUBLE, " + 
                "poster_image_url VARCHAR(512), " + 
                "poster_image_name VARCHAR(64), " +
                "directors VARCHAR(512), " + 
                "actors VARCHAR(512), " + 
                "script_writers VARCHAR(512), " + 
                "source VARCHAR(10))";        
    }
    
    public String getInsertSqlCmd() {
        String sql = "INSERT INTO movies (original_search_title,search_title,title,other_title," +
                "release_date,duration,language,overview,vote_average,poster_image_url,poster_image_name," + 
                "directors,actors,script_writers,source) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return sql;
    }
    
    public void setToStatement(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, originalSearchTitle);
        pstmt.setString(2, searchTitle);
        pstmt.setString(3, title);
        pstmt.setString(4, otherTitle);
        pstmt.setString(5, releaseDate);
        pstmt.setLong(6, duration);
        pstmt.setString(7, language);
        pstmt.setString(8, overView);
        pstmt.setDouble(9, voteAverage);
        pstmt.setString(10, posterImageUrl);
        pstmt.setString(11, posterImageName);
        if (directors.size() > 0) {
            String str = "";
            for (int i = 0; i < directors.size(); i++) {
                str += (String)directors.get(i);
                if (i != directors.size() - 1) {
                    str += ":"; // separate by ':'
                }
            }
            pstmt.setString(12, str);
        } else {
            pstmt.setString(12, "");
        }
        
        if (actors.size() > 0) {
            String str = "";
            for (int i = 0; i < actors.size(); i++) {
                str += actors.get(i).getName();
                if (i != actors.size() - 1) {
                    str += ":"; // separate by ':'
                }
            }
            pstmt.setString(13, str);
        } else {
            pstmt.setString(13, "");
        }
        
        if (scriptWriters.size() > 0) {
            String str = "";
            for (int i = 0; i < scriptWriters.size(); i++) {
                str += scriptWriters.get(i);
                if (i != scriptWriters.size() - 1) {
                    str += ":"; // separate by ':'
                }
            }
            pstmt.setString(14, str);
        } else {
            pstmt.setString(14, "");
        }
        
        pstmt.setString(15, source);
    }
}
