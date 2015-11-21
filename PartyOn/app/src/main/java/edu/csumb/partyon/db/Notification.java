package edu.csumb.partyon.db;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Tobias on 21.11.2015.
 */
public class Notification extends RealmObject {

    private int type;
    @Required
    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
