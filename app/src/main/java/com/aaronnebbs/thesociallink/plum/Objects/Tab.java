package com.aaronnebbs.thesociallink.plum.Objects;

import java.util.ArrayList;

public class Tab {

    private String tabName;
    private int userID;
    private int tabID;
    private ArrayList<Entity> entities;

    public Tab(String tabName, int userID, int tabID) {
        this.tabName = tabName;
        this.userID = userID;
        this.tabID = tabID;

        entities = new ArrayList<>();

    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTabID() {
        return tabID;
    }

    public void setTabID(int tabID) {
        this.tabID = tabID;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
