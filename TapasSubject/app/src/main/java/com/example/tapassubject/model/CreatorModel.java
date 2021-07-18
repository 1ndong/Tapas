package com.example.tapassubject.model;

import java.io.Serializable;

public class CreatorModel implements Serializable {
    private int id;
    private String uname;
    private String display_name;
    private String profile_pic_url;
    private boolean joined_creator_tips;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public boolean isJoined_creator_tips() {
        return joined_creator_tips;
    }

    public void setJoined_creator_tips(boolean joined_creator_tips) {
        this.joined_creator_tips = joined_creator_tips;
    }
}
