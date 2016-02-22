package edu.csumb.partyon.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by Tobias on 21.11.2015.
 */
public class APIClient {
    private static final String BASE_URL = "   ";
    private static final String INVITE_ENDPOINT = "/createInvite.php";
    private static final String NOTIFICATION_ENDPOINT = "/createNotification.php";
    private static final String ANSWER_ENDPOINT = "/setAnswer.php";
    private static final String GET_INVITE_ENDPOINT = "/getInvite.php";
    private static final String SHOW_INVITE_ENDPOINT = "/showInvites.php";

    private static SyncHttpClient client = new SyncHttpClient();

    public static void setAnswer(String notificationId, int answer, String data, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("notificationId", notificationId);
        params.add("data", data);
        params.add("answer", Integer.toString(answer));
        client.get(BASE_URL + ANSWER_ENDPOINT, params, handler);
    }

    public static void createInvite(String FBID, String event, String startDate, String data, int active, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("FBID", FBID);
        params.add("event", event);
        params.add("startDate", startDate);
        params.add("data", data);
        params.add("active", Integer.toString(active));
        client.get(BASE_URL + INVITE_ENDPOINT, params, handler);
    }

    public static void createNotification(String FBID, int inviteId, String type, String startDate, String data, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("FBID", FBID);
        params.add("inviteId", Integer.toString(inviteId));
        params.add("type", type);
        params.add("startDate", startDate);
        params.add("data", data);
        client.get(BASE_URL + NOTIFICATION_ENDPOINT, params, handler);
    }

    public static void showInvites(String FBID, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.add("FBID", FBID);
        client.get(BASE_URL + SHOW_INVITE_ENDPOINT, params, handler);
    }

    public static void getInvites(String FBID, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.add("FBID", FBID);
        client.get(BASE_URL + GET_INVITE_ENDPOINT, params, handler);
    }

}