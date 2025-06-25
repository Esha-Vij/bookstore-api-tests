package com.bookstore.payloads;

public class BookPayload {
    private String name;
    private String author;
    private int published_year;
   // private String book_summary;

    public BookPayload(String name, String author, int publishedYear, String summary) {
        this.name = name;
        this.author = author;
        this.published_year = publishedYear;
        //this.book_summary = summary;
    }

    
    // Getters and Setters
    public String getName() { 
    	return name; 
    	}
    public String getAuthor() { 
    	return author; 
    }
    public int getYear() { 
    	return published_year; 
    }

    public void setName(String name) { 
    	this.name = name; 
    	}
    public void setAuthor(String author) { 
    	this.author = author; 
    	}
    public void setYear(int year) { 
    	this.published_year = year; 
    	}

    @Override
    public String toString() {
        return String.format("{\"title\":\"%s\", \"author\":\"%s\", \"year\":%d}", name, author, published_year);
    }
}
