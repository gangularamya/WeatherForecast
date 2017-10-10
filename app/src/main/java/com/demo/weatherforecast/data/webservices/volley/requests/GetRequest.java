package com.demo.weatherforecast.data.webservices.volley.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by ramya on 10/9/17.
 */

public class GetRequest extends StringRequest {

    public GetRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
