package org.sscraper.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Log {
    private static String COMMON_TAG = "SSCRAPER";
    
    public static void d(String tag, String msg) {
    	if (COMMON_TAG != null)
    		tag = COMMON_TAG + "." + tag;
    	
    	if (!AppConstants.SERVER) {
    		android.util.Log.d(tag, msg);
    		return;
    	}
    	
        String error = String.format("%s D/%s (%d): %s", new Date(), tag,  Thread.currentThread().getId(),  msg);
        StringWriter str = new StringWriter();
        PrintWriter pw = new PrintWriter(str, false);
        pw.println(error);
        System.out.print(str.getBuffer().toString());        
    }

    public static void w(String tag, String msg) {
    	if (COMMON_TAG != null)
    		tag = COMMON_TAG + "." + tag;
    	
    	if (!AppConstants.SERVER) {
    		android.util.Log.w(tag, msg);
    		return;
    	}
    	
        String error = String.format("%s W/%s (%d): %s", new Date(), tag,  Thread.currentThread().getId(),  msg);
        StringWriter str = new StringWriter();
        PrintWriter pw = new PrintWriter(str, false);
        pw.println(error);
        System.out.print(str.getBuffer().toString());        
    }
    
    public static void e(String tag, String errMsg) {
    	if (COMMON_TAG != null)
    		tag = COMMON_TAG + "." + tag;
    	
    	if (!AppConstants.SERVER) {
    		android.util.Log.d(tag, errMsg);
    		return;
    	}
    	
        String error = String.format("%s E/%s (%d): %s", new Date(), tag,  Thread.currentThread().getId(),  errMsg);
        StringWriter str = new StringWriter();
        PrintWriter pw = new PrintWriter(str, false);
        pw.println(error);
        System.err.print(str.getBuffer().toString());          
    }
    
    public static void printStackTrace(Throwable t) {    	
        StringWriter str = new StringWriter();
        PrintWriter pw = new PrintWriter(str, false);
        t.printStackTrace(pw);
    	
        if (!AppConstants.SERVER) {
    		android.util.Log.e(COMMON_TAG + ".EXCETION", str.getBuffer().toString());
    		return;
    	}
        
        System.err.print(str.getBuffer().toString());        
    }
}
