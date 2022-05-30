package com.greenhouse.greenhouseapp.model;

public class Photo {

    private int _id;
    private String _name;
    private String _url;
    private static int autoIncrement = 1;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public Photo() {
    }

    public Photo(int _id, String _name, String _url) {
        this._id = _id;
        this._name = _name;
        this._url = _url;
    }

}
