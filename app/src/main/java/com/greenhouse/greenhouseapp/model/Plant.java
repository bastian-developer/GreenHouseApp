package com.greenhouse.greenhouseapp.model;

import java.util.ArrayList;
import java.util.List;

public class Plant {

    private int _id;
    private int _idUser;
    private String _name;
    private String _type;
    private String _origin;
    private String _image;
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

    public int get_idUser() {
        return _idUser;
    }

    public void set_idUser(int _idUser) {
        this._idUser = _idUser;
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

    public String get_origin() {
        return _origin;
    }

    public void set_origin(String _origin) {
        this._origin = _origin;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
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
    public Plant(int _id, int _idUser,String _name, String _type, String _origin, String _image) {
        this._id = _id;
        this._idUser = _idUser;
        this._name = _name;
        this._type = _type;
        this._origin = _origin;
        this._image = _image;
    }

    //Complete Constructor
    public Plant(int _id, int _idUser, String _name, String _type, String _origin, List<Photo> _photoList, Status _currentStatus, Status _idealStatus) {
        this._id = autoIncrement;
        this._idUser = _idUser;
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
                ", _idUser='" + _idUser + '\'' +
                ", _name='" + _name + '\'' +
                ", _type='" + _type + '\'' +
                ", _origin=" + _origin +
                ", _photoList=" + _photoList +
                ", _currentStatus=" + _currentStatus +
                ", _idealStatus=" + _idealStatus +
                '}';
    }
}
