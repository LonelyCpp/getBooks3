package com.example.ananthu.getbooks3.model;

import android.util.Log;

import com.example.ananthu.getbooks3.network.SuccessFailedCallback;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class AuthorBuilder {
    private static final String TAG = AuthorBuilder.class.getName();

    /**
     * Get a list of authors of a book.
     *
     * @param xmlString XML response string of a book from {@link com.example.ananthu.getbooks3.network.GoodreadRequest#getBook(Integer, SuccessFailedCallback)} API
     * @return A list of the book's authors
     */
    public static List<Author> getAuthorsUsingBookAPI(String xmlString) {
        Log.d(TAG, "getAuthorsUsingBookAPI: entered");

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

            boolean inAuthors = false;

            Author auth = new Author();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if (tagName.equals("authors") || inAuthors) {
                            inAuthors = true;
                        } else {
                            eventType = parser.next();
                            continue;
                        }

                        Log.d(TAG, "getAuthorsUsingBookAPI: " + parser);
                        Log.d(TAG, "getAuthorsUsingBookAPI: " + "entered author");
                        if (tagName.equals("id") && !idSet) {
                            Log.d(TAG, "getAuthorsUsingBookAPI: " + "set id");
                            String id = parser.nextText();
                            auth.setId(Integer.parseInt(id));
                            idSet = true;
                        } else if (tagName.equals("name") && !nameSet) {
                            Log.d(TAG, "getAuthorsUsingBookAPI: " + "set name");
                            auth.setName(parser.nextText());
                            nameSet = true;
                        } else if (tagName.equals("image_url") && !imgSet) {
                            Log.d(TAG, "getAuthorsUsingBookAPI: " + "set img");
                            auth.setImg(parser.nextText().trim());
                            imgSet = true;
                        } else if (idSet && nameSet && imgSet) {

                            authorList.add(auth);
                            auth = new Author();
                            idSet = nameSet = imgSet = false;
                        }
                        break;

                    //parser stops as author end tag is encountered
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("authors")) {
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
        }

        Log.d(TAG, "getAuthorsUsingBookAPI: exit");
        return authorList;
    }

    /**
     * Used to get some additional details about an author.
     * (Author objects built from book XML will not have author summary)
     *
     * @param xmlString XML string from the {@link com.example.ananthu.getbooks3.network.GoodreadRequest#getAuthor(Integer, SuccessFailedCallback)} API
     * @param author an existing {@link Author} object to store the details in.
     * @return Updated Author object
     */
    public static Author getAboutDetails(String xmlString, Author author) {
        Log.d(TAG, "getAboutDetails: entered");

        XmlPullParserFactory pullParserFactory;
        author.setBookIds(new ArrayList<Integer>());

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            StringReader in_s = new StringReader(xmlString);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s);

            int eventType = parser.getEventType();
            boolean decSet = false;
            boolean inBooks = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if (tagName.equals("books") || inBooks) {
                            inBooks = true;
                        } else if (tagName.equals("about")) {
                            Log.d(TAG, "getAboutDetails: set about");
                            author.setAbout(parser.nextText());
                        } else {
                            eventType = parser.next();
                            continue;
                        }

                        Log.d(TAG, "getAboutDetails: " + tagName);
                        Log.d(TAG, "getAboutDetails: entered books");
                        if (tagName.equals("id")) {
                            Log.d(TAG, "getAboutDetails: set id");
                            String id = parser.nextText();
                            author.getBookIds().add(Integer.parseInt(id));
                        }
                        break;

                    //parser stops as author end tag is encountered
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("books")) {
                            return author;
                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.d(TAG, "getAboutDetails: exit");
        return author;
    }

}
