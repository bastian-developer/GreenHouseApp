package com.greenhouse.greenhouseapp.model;

import java.util.ArrayList;
import java.util.List;

public class State {

    private int _id;
    private String _name;
    private List<City> _cityList;
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

    public List<City> get_cityList() {
        return _cityList;
    }

    public void set_cityList(List<City> _cityList) {
        this._cityList = _cityList;
    }

    public State() {
    }

    public State(int _id, String _name, List<City> _cityList) {
        this._id = autoIncrement;
        this._name = _name;
        //New Instance?
        this._cityList = new ArrayList<>();
        autoIncrement++;
    }

    @Override
    public String toString() {
        return "State{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                ", _cityList=" + _cityList +
                '}';
    }
}
