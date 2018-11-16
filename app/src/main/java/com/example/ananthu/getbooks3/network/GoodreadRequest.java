package com.example.ananthu.getbooks3.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ananthu on 26-05-2018.
 */

public class GoodreadRequest {
    private RequestQueue requestQueue;
    private String key;


    public GoodreadRequest(String key, Context context) {
        this.key = key;
        requestQueue = Volley.newRequestQueue(context);
    }

    private void request(String url, final SuccessFailedCallback callback) {
        Log.d("request", "request : " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("request", "response : " + response);
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

    public void getBook(Integer id, final SuccessFailedCallback callback) {

        String url = "https://www.goodreads.com/book/show/" + id + ".xml?key=" + key;
        Log.d("request", "getBook " + url);
        request(url, callback);
    }

    public void getAuthor(Integer id, final SuccessFailedCallback callback) {

        String url = "https://www.goodreads.com/author/show/" + id + "?format=xml&key=" + key;
        Log.d("request", "getAuthor " + url);
        request(url, callback);
    }

    public void searchBook(String query, final SuccessFailedCallback callback) {

        String url = "https://www.goodreads.com/search/index.xml?q=" + query + "&key=" + key;
        Log.d("request", "searchBook " + url);
        request(url, callback);
    }
}
