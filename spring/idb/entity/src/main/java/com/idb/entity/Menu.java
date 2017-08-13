package com.idb.entity;

public class Menu extends TreeModel{

    /**链接*/
    private String url;

    public Menu(String id, String path, String label, String url) {
        this.setPath(path);
        this.setId(id);
        this.setLabel(label);
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}