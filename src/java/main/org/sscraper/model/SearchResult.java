/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */

package org.sscraper.model;

import org.sscraper.Status;

public class SearchResult {
    
    protected Long id;
    protected String title;
    protected String originalLanguage;
    protected String originalTitle;
    protected String overView;
    protected String releaseDate;
    protected String posterPath; // poster image url
    protected Double voteAverage;
    protected Long   voteCount;
    
    
    private Long getId() { return this.id; }
    
    private String getTitle() {return this.title; }
    
    private String getOriginalTitle() {return this.originalTitle; }
    
    private String getOriginalLanguage() {return this.originalLanguage; }
    
    private String getOverView() { return this.overView; }
    
    private String getReleaseDate() { return this.releaseDate; } 
    
    private String getPosterPath() { return this.posterPath; }
    
    private Double getVoteAverage() { return this.voteAverage; }
    
    public Long   getVoteCount() { return  this.voteCount; }   
    
    public  int parseJason(String jsonString) { return Status.OK; }
 }
    
