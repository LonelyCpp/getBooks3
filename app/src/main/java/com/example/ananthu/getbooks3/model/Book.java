package com.example.ananthu.getbooks3.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Book implements Serializable {
    private int id;
    private int isbn;
    private String title;
    private String imageUrl;
    private String smallImageUrl;
    private String description;
    private int totalPages;
    private List<Author> authors;
    private Map<String, String> buyLinks;
    private double avgRating = 0;
    private int reviewCount = 0;
    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Map<String, String> getBuyLinks() {
        return buyLinks;
    }

    public void setBuyLinks(Map<String, String> buyLinks) {
        this.buyLinks = buyLinks;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn=" + isbn +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", description='" + description + '\'' +
                ", totalPages=" + totalPages +
                ", authors=" + authors +
                ", avgRating=" + avgRating +
                ", reviewCount=" + reviewCount +
                ", url=" + url +
                '}';
    }

}
