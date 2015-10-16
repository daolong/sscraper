package org.sscraper.scraper;

import org.sscraper.model.MovieInfo;


public class DoubanScraper extends ScraperBase {
    private final static String NAME = "DOUBAN";

    public DoubanScraper() {
        super(NAME);
    }
    
    public MovieInfo findMovie(String name, String year) {
        return null;
    }

}