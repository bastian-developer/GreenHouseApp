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

    //Stats
    private int _waterSpent;
    private int _temperature;
    private int _humidity;
    private int _water;
    private int _light;

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



    //Stats

    public int get_waterSpent() {
        return _waterSpent;
    }

    public void set_waterSpent(int _waterSpent) {
        this._waterSpent = _waterSpent;
    }

    public int get_temperature() {
        return _temperature;
    }

    public void set_temperature(int _temperature) {
        this._temperature = _temperature;
    }

    public int get_humidity() {
        return _humidity;
    }

    public void set_humidity(int _humidity) {
        this._humidity = _humidity;
    }

    public int get_water() {
        return _water;
    }

    public void set_water(int _water) {
        this._water = _water;
    }

    public int get_light() {
        return _light;
    }

    public void set_light(int _light) {
        this._light = _light;
    }





    //Empty Constructor
    public Plant() {
    }

    //Test Constructor
    public Plant(int _id, int _idUser,String _name, String _type, String _origin, String _image, int _waterSpent, int _temperature, int _humidity, int _water, int _light) {
        this._id = _id;
        this._idUser = _idUser;
        this._name = _name;
        this._type = _type;
        this._origin = _origin;
        this._image = _image;
        this._waterSpent = _waterSpent;
        this._temperature = _temperature;
        this._humidity = _humidity;
        this._water = _water;
        this._light = _light;
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
                ", _waterSpent=" + _waterSpent +
                ", _airTemperature=" + _temperature +
                ", _airHumidity=" + _humidity +
                ", _waterQuantity=" + _water +
                ", _lightQuantity=" + _light +
                '}';
    }
}
