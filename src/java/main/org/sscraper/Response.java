/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper;

import java.util.ArrayList;
import java.util.List;

import org.sscraper.model.MovieInfo;

public class Response {
    
    private int status;
    private List<MovieInfo> movies;
    
    public Response(int status) {
        this.status = status;
    }
    
    public void addMovie(MovieInfo movie) {
        if (movies == null) {
            movies = new ArrayList<MovieInfo>();
        }
        
        movies.add(movie);
    }
    
    public String toJsonString() {
        if (this.status == Status.OK) {
            if (movies.size() > 0) {
                // return the first one now
                return movies.get(0).toJasonString();
            } else {
                this.status = Status.NOT_FOUND;
            }
        } 
        
        String jason = "{\"status\":" + this.status + ", ";
        String decribtion;
        switch (this.status) {
        case Status.BAD_PARAM:
            decribtion = "bad request parameter";
            break;
        case Status.AUTH_FAIL:
            decribtion = "permission denied";
        case Status.OUT_OF_DATE:
            decribtion = "authorization out of date";
        default:
            decribtion = "can not find movie information";
        }
        
        jason += "\"decribtion\":\"" + decribtion + "\"";
        
        return jason;
        
    }
}
