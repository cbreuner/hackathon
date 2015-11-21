package edu.csumb.partyon.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Tobias on 21.11.2015.
 */
public class APIClient {
    private static final String BASE_URL = "";
    private static final String GPS_ENDPOINT = "/party";
    private static final String INVITE_ENDPOINT = "/invites";
    private static final String NOTIFICATION_ENDPOINT = "/notifications";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void logGPS(RequestParams requestParams, AsyncHttpResponseHandler handler){
        client.post(BASE_URL + GPS_ENDPOINT, requestParams, handler);
    }

    public static void checkInvites(String userid, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams("userid", userid);
        client.get(BASE_URL + INVITE_ENDPOINT, params, handler);
    }

    public static void checkNotifications(String userid, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams("userid", userid);
        client.get(BASE_URL + INVITE_ENDPOINT, params, handler);
    }
}
