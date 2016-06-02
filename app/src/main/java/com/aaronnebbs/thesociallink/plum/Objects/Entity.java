package com.aaronnebbs.thesociallink.plum.Objects;

public class Entity {

    private String entName;
    private String data;
    private int tabID;
    private int entityID;

    public Entity(String entName, String data, int tabID, int entityID) {
        this.entName = entName;
        this.data = data;
        this.tabID = tabID;
        this.entityID = entityID;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTabID() {
        return tabID;
    }

    public void setTabID(int tabID) {
        this.tabID = tabID;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }
}
