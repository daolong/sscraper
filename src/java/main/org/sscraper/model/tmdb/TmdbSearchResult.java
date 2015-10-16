package org.sscraper.model.tmdb;

import org.sscraper.Status;
import org.sscraper.model.SearchResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TmdbSearchResult extends SearchResult {
    
    public  int parseJason(String jsonString) {
        JSONObject jb = JSONObject.fromObject(jsonString);
        int total = jb.getInt("total_results");
        if (total == 0) {
            return Status.NOT_FOUND;
        }
        
        // FIXME: we get the first result now       
        JSONArray ja = jb.getJSONArray("results");
        if (ja == null)
            return Status.NOT_FOUND;
        
        id = ja.getJSONObject(0).getLong("id");
        title = ja.getJSONObject(0).getString("title");
        originalLanguage = ja.getJSONObject(0).getString("original_language");
        originalTitle = ja.getJSONObject(0).getString("original_title");
        overView = ja.getJSONObject(0).getString("overview");
        releaseDate = ja.getJSONObject(0).getString("release_date");
        posterPath = ja.getJSONObject(0).getString("poster_path");
        voteAverage = ja.getJSONObject(0).getDouble("vote_average");
        voteCount = ja.getJSONObject(0).getLong("vote_count");
        
        return Status.OK;
    }
}
