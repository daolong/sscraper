package org.sscraper.launch;

import java.io.IOException;

import org.sscraper.Response;
import org.sscraper.ScraperProcess;
import org.sscraper.Status;
import org.sscraper.database.DatabaseHelper;
import org.sscraper.model.MovieInfo;
import org.sscraper.utils.Log;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.HttpResponse;
import org.httpkit.*;
import org.httpkit.server.HttpServer;
import org.httpkit.server.AsyncChannel;
import org.httpkit.server.Frame;
import org.httpkit.server.HttpRequest;
import org.httpkit.server.IHandler;
import org.httpkit.server.RespCallback;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.httpkit.HttpUtils.HttpEncode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class ServerHandler implements IHandler {
    private static final String TAG = "ServerHandler";
    public static final String body = "Hello!\nThis is a video information scraper server. \n\nCopyright (C) 2015 Zidoo(zidoo.tv).";
    public static final String quit_response = "Bye bye!";
    
    private final static String VALID_KEY = "57983e31fb435df4df77afb854740ea9";
    
    private ExecutorService exec;
    
    public ServerHandler() {
        int core = 6; // Runtime.getRuntime().availableProcessors();
        exec = Executors.newFixedThreadPool(core);
    }
    
    @Override
    public void handle(final HttpRequest request, final RespCallback cb) {
        exec.submit(new Runnable() {
            public void run() {
                Log.d(TAG, "request queryString : " + request.queryString);
                Log.d(TAG, "request url : " + request.uri);
                Log.d(TAG, "request method : " + request.method);       

                if (request.uri.equals("/command/quit")) {
                    // for test now
                    Log.d(TAG, "quit command");
                    HeaderMap map = new HeaderMap();
                    ByteBuffer[] bytes = HttpEncode(200, map, quit_response);
                    cb.run(bytes);
                    cb.requetStop();
                } else if (request.uri.equals("/search/movie")) {
                    String response = processQuery(request.queryString);
                    Log.d(TAG, "response to client : " + response);
                    HeaderMap map = new HeaderMap();
                    //map.put("Connection", "Keep-Alive");
                    //map.put("Content-Type", "text/json");
                    map.put("content-encoding", "UTF-8");
                    ByteBuffer[] bytes = HttpEncode(200, map, response);
                    cb.run(bytes);
                }
            }
        });
    }

    @Override
    public void handle(AsyncChannel channel, Frame frame) {
        
    }

    @Override
    public void clientClose(AsyncChannel channel, int status) {
        
    }

    @Override
    public void close(int timeoutMs) {
        
    }
    
    private String processQuery(String queryString) {
        String[] params = queryString.split("&");
        
        HashMap<String, String> paramMap = new HashMap<String, String>();
        
        for (String item : params) {
            if (item != null) {
                int i = item.indexOf('=');
                String key = item.substring(0, i);
                String value = item.substring(i + 1);
                Log.d(TAG, "key = " + key);
                Log.d(TAG, "value = " + value);
                paramMap.put(key, value);
            }
        }
        
        // check api key 
        String api_key = paramMap.get("api_key");
        if ( api_key == null || !api_key.equals(VALID_KEY) ) {
            Log.d(TAG, "key[" + api_key + "] is not valid");
            return (new Response(Status.AUTH_FAIL).toJsonString());
        }
        
        if (paramMap.get("query") != null) {
            return new ScraperProcess(paramMap.get("query"), paramMap.get("year")).findMovie();
        }
        
        
        return (new Response(Status.NOT_FOUND).toJsonString());
    }
    
}

public class Launcher {    
    static String TAG = "Scraper-Launcher";
    private final static int port = 8085;
    
    public static void main(String[] args) throws IOException {
        //testDatabase();
        testProcess();
        
        //startServer();  
    } 
    
    private static void startServer() throws IOException {
        HttpServer server = new HttpServer("0.0.0.0", port, new ServerHandler(), 20480,
                2048, 1024 * 1024 * 4);
        server.start();
        Log.d(TAG, "Server run on " + port);  
    }
    
    private static void testDatabase() {
        DatabaseHelper helper = new DatabaseHelper();        
        MovieInfo movie = helper.queryMovieByOriginalTitle("xialuotefannao");
    }
    
    private static void testProcess() {
        String response = new ScraperProcess("%E5%A4%8F%E6%B4%9B%E7%89%B9%E7%83%A6%E6%81%BC", "2015").findMovie();
        Log.d(TAG, "get response : " + response);
    }
}
