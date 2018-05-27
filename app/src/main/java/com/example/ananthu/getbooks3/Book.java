package com.example.ananthu.getbooks3;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static android.R.attr.country;
import static android.R.attr.tunerCount;

/**
 * Created by Ananthu on 26-05-2018.
 */

public class Book implements Serializable{
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

    public Book(){}

    public Book(String xmlString){
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            StringReader in_s = new StringReader(xmlString);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s);

            int eventType = parser.getEventType();
            boolean idSet = false;
            boolean titleSet = false;
            boolean isbnSet = false;
            boolean imgSet = false;
            boolean smallImgSet = false;
            boolean despSet = false;
            boolean tpSet = false;
            boolean buySet = false;
            boolean ratingSet = false;
            boolean revCountSet = false;

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if(name.equals("id") && !idSet){
                            String id = parser.nextText();
                            this.id = Integer.parseInt(id);
                            idSet = true;
                        }
                        else if(name.equals("isbn") && !isbnSet){
                            String isbn = parser.nextText();
                            try {
                                this.isbn = Integer.parseInt(isbn);
                            }catch (NumberFormatException e){
                                this.isbn = 0;
                            }
                            isbnSet = true;
                        }
                        else if(name.equals("title") && !titleSet){
                            this.title = parser.nextText().trim();;
                            titleSet = true;
                        }
                        else if(name.equals("image_url") && !imgSet){
                            this.imageUrl = parser.nextText();
                            imgSet = true;
                        }
                        else if(name.equals("small_image_url") && !smallImgSet){
                            this.smallImageUrl = parser.nextText();
                            smallImgSet = true;
                        }
                        else if(name.equals("description") && !despSet){
                            this.description = parser.nextText();
                            despSet = true;
                        }
                        else if(name.equals("num_pages") && !tpSet){
                            try {
                                this.totalPages = Integer.parseInt(parser.nextText());
                            }catch (NumberFormatException e){
                                this.totalPages = 0;
                            }
                            tpSet = true;
                        }
                        else if(name.equals("average_rating") && !ratingSet){
                            try {
                                this.avgRating = Double.parseDouble(parser.nextText());
                            }catch (NumberFormatException e){
                                this.avgRating = 0;
                            }
                            ratingSet = true;
                        }
                        else if(name.equals("text_reviews_count") && !revCountSet){
                            try {
                                this.reviewCount = Integer.parseInt(parser.nextText());
                            }catch (NumberFormatException e){
                                this.reviewCount = 0;
                            }
                            revCountSet = true;
                        }

                        else if(idSet && isbnSet && titleSet && imgSet && smallImgSet
                                && despSet && tpSet && ratingSet && revCountSet){
                            return;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("book")){
                            return;
                        }
                        break;
                }
                eventType = parser.next();
            }



        } catch (XmlPullParserException e) {

            e.printStackTrace();
            Log.e("xmlPare", e.getMessage());
        } catch (IOException e) {

            e.printStackTrace();
            Log.e("xmlPare", e.getMessage());
        } finally {
            authors = Author.getAuthors(xmlString);
        }

    }


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
                //", buyLinks=" + buyLinks +
                ", avgRating=" + avgRating +
                ", reviewCount=" + reviewCount +
                '}';
    }
}
