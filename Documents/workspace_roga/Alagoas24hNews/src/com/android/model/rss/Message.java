package com.android.model.rss;

import java.net.MalformedURLException;
import java.net.URL;

public class Message implements Comparable<Message>{
//    static SimpleDateFormat FORMATTER = 
//        new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
    private String title;
    private URL link;
    private String description;
    private String date;

      // getters and setters omitted for brevity
    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    

    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getTitle() {
    	return this.title;
    }

    public void setDate(String date) {
        // pad the date if necessary
//        while (!date.endsWith("00")){
//            date += "0";
//        }
//        try {
            this.date = date;//FORMATTER.parse(date.toString());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
    }
    
    public String getDate() {
//      return FORMATTER.format(this.date);
    	return this.date;
    }

    public void setDescription(String description) {
    	this.description = description;
    }
    
    public String getDescription() {
    	return this.description;
    }
    
    @Override
    public String toString() {
             // return a specific string
    	return "Title: " + title + " // Date: " + date + " // Description: " + description + " // Link: " + link;
    }

//    @Override
//    public int hashCode() {
//            // omitted for brevity
//    }
//    
//    @Override
//    public boolean equals(Object obj) {
//            // omitted for brevity
//    }
      // sort by date
    public int compareTo(Message another) {
        if (another == null) return 1;
        // sort descending, most recent first
        return another.date.compareTo(date);
    }
}