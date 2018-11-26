package com.example.ananthu.getbooks3.model;

import android.util.Log;

import com.example.ananthu.getbooks3.network.SuccessFailedCallback;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class BookBuilder {
    private static final String TAG = BookBuilder.class.getName();

    /**
     * Parse info from the XML string containing book details
     *
     * @param xmlString XML response from {@link com.example.ananthu.getbooks3.network.GoodreadRequest#getBook(Integer, SuccessFailedCallback)} API
     * @return A {@link Book} Object
     */
    public static Book getBookFromXML(String xmlString) {

        XmlPullParserFactory pullParserFactory;

        Book book = new Book();
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
            boolean urlSet = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if (name.equals("id") && !idSet) {
                            String id = parser.nextText();
                            book.setId(Integer.parseInt(id));
                            idSet = true;
                        } else if (name.equals("isbn") && !isbnSet) {
                            String isbn = parser.nextText();
                            try {
                                book.setIsbn(Integer.parseInt(isbn));
                            } catch (NumberFormatException e) {
                                book.setIsbn(0);
                            }
                            isbnSet = true;
                        } else if (name.equals("title") && !titleSet) {
                            book.setTitle(parser.nextText().trim());
                            titleSet = true;
                        } else if (name.equals("image_url") && !imgSet) {
                            book.setImageUrl(parser.nextText());
                            imgSet = true;
                        } else if (name.equals("small_image_url") && !smallImgSet) {
                            book.setSmallImageUrl(parser.nextText());
                            smallImgSet = true;
                        } else if (name.equals("url") && !urlSet) {
                            book.setUrl(parser.nextText());
                            urlSet = true;
                        } else if (name.equals("description") && !despSet) {
                            book.setDescription(parser.nextText());
                            despSet = true;
                        } else if (name.equals("num_pages") && !tpSet) {
                            try {
                                book.setTotalPages(Integer.parseInt(parser.nextText()));
                            } catch (NumberFormatException e) {
                                book.setTotalPages(0);
                            }
                            tpSet = true;
                        } else if (name.equals("average_rating") && !ratingSet) {
                            try {
                                book.setAvgRating(Double.parseDouble(parser.nextText()));
                            } catch (NumberFormatException e) {
                                book.setAvgRating(0);
                            }
                            ratingSet = true;
                        } else if (name.equals("text_reviews_count") && !revCountSet) {
                            try {
                                book.setReviewCount(Integer.parseInt(parser.nextText()));
                            } catch (NumberFormatException e) {
                                book.setReviewCount(0);
                            }
                            revCountSet = true;
                        } else if (idSet && isbnSet && titleSet && imgSet && smallImgSet
                                && despSet && tpSet && ratingSet && revCountSet && urlSet) {
                            return book;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("book")) {
                            return book;
                        }
                        break;
                }
                eventType = parser.next();
            }


        } catch (XmlPullParserException e) {

            e.printStackTrace();
            Log.e(TAG, "getBookFromXML: ", e);
        } catch (IOException e) {

            e.printStackTrace();
            Log.e(TAG, "getBookFromXML: ", e);
        } finally {
            book.setAuthors(AuthorBuilder.getAuthorsUsingBookAPI(xmlString));
        }
        return book;
    }

}
