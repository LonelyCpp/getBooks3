package com.example.ananthu.getbooks3.search;

import android.util.Log;

import com.example.ananthu.getbooks3.InternalStorage;
import com.example.ananthu.getbooks3.model.Book;
import com.example.ananthu.getbooks3.model.BookBuilder;
import com.example.ananthu.getbooks3.network.GoodreadRequest;
import com.example.ananthu.getbooks3.network.SuccessFailedCallback;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

class SearchPresenter {
    private static final String TAG = SearchPresenter.class.getName();

    private final SearchView searchView;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
    }

    /**
     * Gets a list of Goodreads ids from the XML response of {@link GoodreadRequest#searchBook(String, SuccessFailedCallback)} API
     *
     * @param query query string
     * @param goodreadRequest request object
     * @param cache internal storage cache
     */
    public void searchQuery(String query, final GoodreadRequest goodreadRequest, final InternalStorage cache) {

        query = query.replaceAll(" ", "+");
        goodreadRequest.searchBook(query, new SuccessFailedCallback() {
            @Override
            public void success(String response) {
                List<Integer> bookIds = getBookIdsFromSearchResults(response);
                getEachBook(bookIds, goodreadRequest, cache);
            }

            @Override
            public void failed() {

            }
        });
    }

    /**
     * for each id in the search result check if it exists in cache. if not request for it
     *
     * param bookIds goodreads book id
     * @param goodreadRequest request object
     * @param cache internal storage cache
     */
    private void getEachBook(List<Integer> bookIds, final GoodreadRequest goodreadRequest, final InternalStorage cache){

        for (int i = 0; i < bookIds.size(); i++) {

            if (cache.getCachedBookById(bookIds.get(i)) == null) {
                goodreadRequest.getBook(bookIds.get(i), new SuccessFailedCallback() {
                    @Override
                    public void success(String response) {
                        Book book = BookBuilder.getBookFromXML(response);
                        cache.cacheBook(book);
                        searchView.showBookResult(book);
                    }

                    @Override
                    public void failed() {
                        searchView.showToast("some error occurred");
                    }
                });
            } else {
                searchView.showBookResult(cache.getCachedBookById(bookIds.get(i)));
            }

        }
    }

    /**
     * Parse the search result XML string to extract book ids
     *
     * @param xmlString XML string from {@link GoodreadRequest#searchBook(String, SuccessFailedCallback)} API
     * @return list of integer ids
     */
    private List<Integer> getBookIdsFromSearchResults(String xmlString) {
        Log.d(TAG, "getBookIdsFromSearchResults: entered");

        List<Integer> bookList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            StringReader in_s = new StringReader(xmlString);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s);

            int eventType = parser.getEventType();

            boolean inBook = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName;

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();

                        if (tagName.equals("best_book") || inBook) {
                            inBook = true;
                        } else {
                            eventType = parser.next();
                            continue;
                        }

                        Log.d(TAG, "getBookIdsFromSearchResults: parser - " + tagName);
                        Log.d(TAG, "getBookIdsFromSearchResults: parser - entered best_book");
                        if (tagName.equals("id")) {
                            Log.d(TAG, "getBookIdsFromSearchResults: parser - set id");
                            String id = parser.nextText();
                            bookList.add(Integer.parseInt(id));
                            inBook = false;
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

        Log.d(TAG, "getBookIdsFromSearchResults: exit");
        return bookList;
    }
}
