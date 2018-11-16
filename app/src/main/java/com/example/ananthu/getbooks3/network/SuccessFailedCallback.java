package com.example.ananthu.getbooks3.network;

/**
 * Created by Ananthu on 26-05-2018.
 */

public interface SuccessFailedCallback {
    void success(String response);
    void failed();
}
