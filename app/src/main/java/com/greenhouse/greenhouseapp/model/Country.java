package com.greenhouse.greenhouseapp.model;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private int _id;
    private String _name;
    private List<State> _stateList;
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

    public List<State> get_stateList() {
        return _stateList;
    }

    public void set_stateList(List<State> _stateList) {
        this._stateList = _stateList;
    }

    public Country() {
    }

    public Country(int _id, String _name, List<State> _stateList) {
        this._id = autoIncrement;
        this._name = _name;
        this._stateList = new ArrayList<>();
        autoIncrement++;
    }

    @Override
    public String toString() {
        return "Country{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                ", _stateList=" + _stateList +
                '}';
    }

}
