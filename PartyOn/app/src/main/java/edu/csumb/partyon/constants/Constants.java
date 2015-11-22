package edu.csumb.partyon.constants;

/**
 * Created by Tobias on 21.11.2015.
 */
public class Constants {

    public static final String USER_INFO = "USER_INFO";
    public static final String COMPACT_KEY = "compact";
    public static final String FRIEND_ID = "friend_id";
    public static final String INVITE_INTENT = "invitation_response";
    public static final int INVITE_INTENT_ACCEPT = 1;
    public static final int INVITE_INTENT_REJECT = 2;

    //TODO: More types??
    public enum INVITE_TYPE {
        PARTY
    }

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    

    public enum NOTIFICATION_TYPE {
        INFO, WARNING, CRITICAL, EMERGENCY
    }
}
