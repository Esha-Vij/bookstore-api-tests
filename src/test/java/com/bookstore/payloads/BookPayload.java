package com.bookstore.payloads;

public class BookPayload {

    private String name;
    private String author;
    private int published_year;
    private String book_summary;

    public BookPayload() {
    }

    public BookPayload(String name, String author, int published_year, String book_summary) {
        this.name = name;
        this.author = author;
        this.published_year = published_year;
        this.book_summary = book_summary;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublished_year() {
        return published_year;
    }

    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    public String getBook_summary() {
        return book_summary;
    }

    public void setBook_summary(String book_summary) {
        this.book_summary = book_summary;
    }

    @Override
    public String toString() {
        return String.format("BookPayload{name='%s', author='%s', published_year=%d, book_summary='%s'}",
                name, author, published_year, book_summary);
    }
}
