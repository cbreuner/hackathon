package edu.csumb.partyon.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Tobias on 22.11.2015.
 */
public class PartyMember extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String name;
    private String imageUrl;

    public PartyMember(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}