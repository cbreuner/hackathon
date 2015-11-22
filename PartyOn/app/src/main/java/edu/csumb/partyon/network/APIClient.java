package edu.csumb.partyon.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Tobias on 21.11.2015.
 */
public class APIClient {
    private static final String BASE_URL = "http://107.170.231.224";
    private static final String INVITE_ENDPOINT = "/createInvite.php";
    private static final String NOTIFICATION_ENDPOINT = "/createNotification.php";
    private static final String ANSWER_ENDPOINT = "/setAnswer.php";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void setAnswer(String notificationId, Int Answer, String data, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("notificationId", notificationId);
        params.add("data", data);
        params.add("answer", answer);
        client.get(BASE_URL + ANSWER_ENDPOINT, params, handler);
    }

    public static void createInvite(String FBID, String event, String startDate, String data, Int active, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("FBID", FBID);
        params.add("event", event);
        params.add("startDate", startDate);
        params.add("data", data);
        params.add("active", active);
        client.get(BASE_URL + INVITE_ENDPOINT, params, handler);
    }

    public static void createNotification(String FBID, Int inviteId, String type, String startDate, String data, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("FBID", FBID);
        params.add("inviteId", inviteId);
        params.add("type", type);
        params.add("startDate", startDate);
        params.add("data", data);
        client.get(BASE_URL + NOTIFICATION_ENDPOINT, params, handler);
    }

}