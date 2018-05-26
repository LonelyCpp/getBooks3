package com.example.ananthu.getbooks3;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.Key;

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

    public void getBook(Integer id, final SuccessFailedCallback callback){
        String url = "https://www.goodreads.com/book/show/" + id +".xml?key=" + key;

        Log.d("request", "request : " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("request", "response : " + response);
                        if(callback != null){
                            callback.success(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(callback != null){
                    callback.failed();
                }
            }
        });

        requestQueue.add(stringRequest);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
