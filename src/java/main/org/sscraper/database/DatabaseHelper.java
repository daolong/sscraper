/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.database;

import java.util.List;

import org.sscraper.model.MovieInfo;

public interface DatabaseHelper {
    
    /**
     * Insert movie into data base
     * 
     * @param movie The movie information data structure
     * @return The data base id if success, -1 if fail
     */
    public long insertMovie(MovieInfo movie);
    
    
    /**
     * Query movie by original search title from client/application
     * 
     * @param originalTitle Then Original search title
     * @param year Release year of the movie, maybe null 
     * @return The movie if success, null if not found
     */
    public MovieInfo queryMovieByOriginalTitle(String originalTitle, String year);
    
    
    /**
     * Query all movie in database
     * @return The list of movie if exists, null if no movie in database.
     */
    public List<MovieInfo> queryMovies();
    
    
    /**
     * Query movies by genre (类型)
     * @param genre The name of genre
     * @return The list of movie which belong to the genre, null if no movie found
     */
    public List<MovieInfo> queryMoviesByGenre(String genre);
    
    /**
     * Query movies by release year 
     * @param year The year when movie release
     * @return The list of movie which release at the year, null if no movie found
     */
    public List<MovieInfo> queryMoviesByYear(String year);
    
    /**
     * Query movies by Director  (导演)
     * @param director 
     * @return The list of movie if found, null if not found
     */
    public List<MovieInfo> queryMoviesByDirector(String director);
    
    /**
     * Query movies by Actor (演员)
     * @param actor
     * @return The list of movie if found, null if not found
     */
    public List<MovieInfo> queryMoviesByActor(String actor);
    
    /**
     * Query movies by customer condition (用户自定义查询语句)
     * @param sqlString The sql command string (Sqlite for android, Mysql for server) 
     * @return The list of movie if found, null if not found
     */
    public List<MovieInfo> queryMoviesByCustomer(String sqlString);
    
    /**
     * Update movie information to data base by zmdb id
     * @param movie
     */
    public void updateMovie(MovieInfo movie);
}
