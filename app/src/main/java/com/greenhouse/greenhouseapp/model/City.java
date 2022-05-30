package com.greenhouse.greenhouseapp.model;

public class City {

    private int _id;
    private String _name;
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

    public City() {
    }

    public City(int _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    @Override
    public String toString() {
        return "City{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                '}';
    }

}
