package com.example.ananthu.getbooks3.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class GoodreadRequest {
    private static final String TAG = GoodreadRequest.class.getName();

    private final RequestQueue requestQueue;

    /**
     * Goodreads API Key
     */
    private final String key;

    public GoodreadRequest(String key, Context context) {
        this.key = key;
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Generates a volley request for the given URL
     * @param url URL of the API to request
     * @param callback {@link SuccessFailedCallback} reference
     */
    private void request(String url, final SuccessFailedCallback callback) {
        Log.d(TAG, "request: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d(TAG, "response : " + response);
                    if (callback != null) {
                        callback.success(response);
                    }
                }, error -> {
            if (callback != null) {
                callback.failed();
            }
        });

        requestQueue.add(stringRequest);
    }

    /**
     * Constructs a show-book API URL
     * @param id Goodreads id of the book
     * @param callback @param callback {@link SuccessFailedCallback} reference
     */
    public void getBook(Integer id, final SuccessFailedCallback callback) {

        String url = "https://www.goodreads.com/book/show/" + id + ".xml?key=" + key;
        request(url, callback);
    }

    /**
     * Constructs a show-author API URL
     * @param id Goodreads id of the Author
     * @param callback @param callback {@link SuccessFailedCallback} reference
     */
    public void getAuthor(Integer id, final SuccessFailedCallback callback) {

        String url = "https://www.goodreads.com/author/show/" + id + "?format=xml&key=" + key;
        request(url, callback);
    }

    /**
     * Constructs a search query API URL
     * @param query query string to search
     * @param callback @param callback {@link SuccessFailedCallback} reference
     */
    public void searchBook(String query, final SuccessFailedCallback callback) {

        String url = "https://www.goodreads.com/search/index.xml?q=" + query + "&key=" + key;
        request(url, callback);
    }
}
