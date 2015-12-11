/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.utils;

public class AppConstants {

    // Application Version 
    public final static String VERSION = "1.0.2";
    
    public final static boolean SERVER = true;
    
    // the movie data base 
    public final static String TMDB_API_KEY = "";
    public final static String TMDB_SEARCH_URL = "http://api.tmdb.org/3/search/movie?";
    public final static String TMDB_CONFIG_URL = "http://api.tmdb.org/3/configuration?";
    public final static String ZMDB_INFO_URL = "http://api.tmdb.org/3/movie/";
    
    // dou ban data base
    public final static String DOUBAN_SEARCH_URL = "http://api.douban.com/v2/movie/search?";
    
    // mysql server address
    public final static String MYSQL_SERVER = "192.168.11.239:3306";
    
    // image base url, use by poster, backdrop 
    public final static String IMAGE_BASE_URL = "http://192.168.11.127:8085/download/sscraper/images/"; 
    
    
    // Subtitle related
    public final static String SHOOTER_SEARCH_URL = "http://api.makedie.me/v1/sub/search?";
    public final static String SHOOTER_DETAIL_URL = "http://api.makedie.me/v1/sub/detail?";
    public final static String SHOOTER_TOKEN = "UyUaWLotvTmRKdCqu9C0GHq56B4xKMRE";
    public final static int SHOOTER_COUNT = 10; // how many results return when search
    public final static int SHOOTER_START = 0;  // page start position of search results
    
    public final static String QQMEDIA_SEARCH_URL = "http://s.video.qq.com/subtitle_search?";
    public final static String QQMEDIA_SEARCH_PREQID = "ytpd-BPIhGhovYGJlEjbuSqTl9cJUkC1jKoB7m8SwrukZKz26Xzrqw";
    public final static String QQMEDIA_SEARCH_PLATFORM = "pc";
}
