/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.database.android;

import org.sscraper.database.DatabaseHelper;
import org.sscraper.model.MovieInfo;

public class AndroidHelper implements DatabaseHelper {

    @Override
    public synchronized long insertMovie(MovieInfo movie) {
        return 0;
    }

    @Override
    public MovieInfo queryMovieByOriginalTitle(String originalTitle, String year) {
        return null;
    }

}
