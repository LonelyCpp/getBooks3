package com.example.ananthu.getbooks3.network;


/**
 * Callback for success or failed network calls
 */
public interface SuccessFailedCallback {

    /**
     * Called when a valid response is obtained from the network call
     * @param response response string
     */
    void success(String response);

    /**
     * Called when the network call fails
     */
    void failed();
}
