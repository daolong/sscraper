package org.sscraper.scraper;

import org.sscraper.model.MovieInfo;

public interface ScraperInterface {

    public MovieInfo findMovie(String name, String year);

    
}
