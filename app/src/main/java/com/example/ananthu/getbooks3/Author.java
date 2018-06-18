package com.example.ananthu.getbooks3;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Ananthu on 26-05-2018.
 */

public class Author implements Serializable{

    private String name;
    private int id;
    private String img;
    private String url;
    private String averageRating;
    private String textReviewsCount;

    public Author(){}

    public Author(String name, int id, String img, String url, String averageRating, String textReviewsCount) {
        this.name = name;
        this.id = id;
        this.img = img;
        this.url = url;
        this.averageRating = averageRating;
        this.textReviewsCount = textReviewsCount;
    }


    public static List<Author> getAuthors(String xmlString){

        Log.d("method", "entered getAuthors");
        List<Author> authorList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            StringReader in_s = new StringReader(xmlString);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s);

            int eventType = parser.getEventType();
            boolean idSet = false;
            boolean nameSet = false;
            boolean imgSet = false;
            boolean urlSet = false;
            boolean avgRatingSet = false;
            boolean txtReviewsSet = false;

            boolean inAuthors = false;

            Author auth = new Author();
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName;

                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if(tagName.equals("authors") || inAuthors){
                            inAuthors = true;
                        }
                        else {
                            eventType = parser.next();
                            continue;
                        }

                        Log.d("parser", tagName);
                        Log.d("parser", "entered author");
                        if(tagName.equals("id") && !idSet){
                            Log.d("parser", "set id");
                            String id = parser.nextText();
                            auth.setId(Integer.parseInt(id));
                            idSet = true;
                        }
                        else if(tagName.equals("name") && !nameSet){
                            Log.d("parser", "set name");
                            auth.setName(parser.nextText());
                            nameSet = true;
                        }
                        else if(tagName.equals("link") && !urlSet){
                            Log.d("parser", "set url");
                            auth.setUrl(parser.nextText().trim());
                            urlSet = true;
                        }

                        else if(tagName.equals("image_url") && !imgSet){
                            Log.d("parser", "set img");
                            auth.setImg(parser.nextText().trim());
                            imgSet = true;
                        }

                        else if(tagName.equals("average_rating") && !avgRatingSet){
                            Log.d("parser", "set img");
                            auth.setAverageRating(parser.nextText());
                            avgRatingSet = true;
                        }

                        else if(tagName.equals("text_reviews_count") && !txtReviewsSet){
                            Log.d("parser", "set img");
                            auth.setTextReviewsCount(parser.nextText());
                            txtReviewsSet = true;
                        }

                        else if(auth.allSet()){

                            authorList.add(auth);
                            auth = new Author();
                            idSet = nameSet = imgSet = avgRatingSet = txtReviewsSet = urlSet = false;
                        }
                        break;

                    //parser stops as author end tag is encountered
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("authors")){
                            return authorList;
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            Log.d("method", "exit getAuthors");
            return authorList;

        }


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getTextReviewsCount() {
        return textReviewsCount;
    }

    public void setTextReviewsCount(String textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", averageRating='" + averageRating + '\'' +
                ", textReviewsCount='" + textReviewsCount + '\'' +
                '}';
    }

    public boolean allSet(){
        if(     id == 0
            ||  url == null
            ||  img == null
            ||  averageRating == null
            ||  textReviewsCount == null)
            return false;

        return true;
    }
}
