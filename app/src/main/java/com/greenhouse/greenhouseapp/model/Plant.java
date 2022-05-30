package com.greenhouse.greenhouseapp.model;

import java.util.ArrayList;
import java.util.List;

public class Plant {

    private int _id;
    private String _name;
    private String _type;
    private Country _origin;
    private List<Photo> _photoList;
    private Status _currentStatus;
    private Status _idealStatus;
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

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public Country get_origin() {
        return _origin;
    }

    public void set_origin(Country _origin) {
        this._origin = _origin;
    }

    public List<Photo> get_photoList() {
        return _photoList;
    }

    public void set_photoList(List<Photo> _photoList) {
        this._photoList = _photoList;
    }

    public Status get_currentStatus() {
        return _currentStatus;
    }

    public void set_currentStatus(Status _currentStatus) {
        this._currentStatus = _currentStatus;
    }

    public Status get_idealStatus() {
        return _idealStatus;
    }

    public void set_idealStatus(Status _idealStatus) {
        this._idealStatus = _idealStatus;
    }

    //Empty Constructor
    public Plant() {
    }

    //Test Constructor
    public Plant(int _id, String _name, String _type, Country _origin) {
        this._id = autoIncrement;
        this._name = _name;
        this._type = _type;
        this._origin = _origin;
        autoIncrement++;
    }

    //Complete Constructor
    public Plant(int _id, String _name, String _type, Country _origin, List<Photo> _photoList, Status _currentStatus, Status _idealStatus) {
        this._id = autoIncrement;
        this._name = _name;
        this._type = _type;
        this._origin = _origin;
        //New Instance?
        this._photoList = new ArrayList<>();
        this._currentStatus = _currentStatus;
        this._idealStatus = _idealStatus;
        autoIncrement++;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                ", _type='" + _type + '\'' +
                ", _origin=" + _origin +
                ", _photoList=" + _photoList +
                ", _currentStatus=" + _currentStatus +
                ", _idealStatus=" + _idealStatus +
                '}';
    }
}
