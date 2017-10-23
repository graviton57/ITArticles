package com.havryliuk.itarticles.data.remote.helper;

import java.util.HashMap;

import okhttp3.Headers;

/**
 * Created by Igor Havrylyuk on 21.10.2017
 */
public class HeaderHelper {

    private static final String CONTENT_TYPE_NAME_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_JSON_VALUE_HEADER = "application/json";
    private static final String OPEN_AUTH_NAME_HEADER = "Authorization";
    private static final String OPEN_AUTH_VALUE_HEADER = "Bearer %s";

    /**
     * Method for creating all the headers used in the app
     */
    public static Headers getAppHeaders() {
        HashMap<String, String> headersMap = new HashMap<>();
        // add all headers
        headersMap.put(CONTENT_TYPE_NAME_HEADER, CONTENT_TYPE_JSON_VALUE_HEADER);
       // headersMap.put("X-Requested-With", "XMLHttpRequest");
        return Headers.of(headersMap);
    }

}
