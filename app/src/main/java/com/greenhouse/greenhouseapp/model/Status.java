package com.greenhouse.greenhouseapp.model;

public class Status {

    private int _id;
    private String _name;
    private float _airTemperature;
    private float _airHumidity;
    private float _ambientLight;
    private float _waterQuantity;
    private float _airQuality;
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

    public float get_airTemperature() {
        return _airTemperature;
    }

    public void set_airTemperature(float _airTemperature) {
        this._airTemperature = _airTemperature;
    }

    public float get_airHumidity() {
        return _airHumidity;
    }

    public void set_airHumidity(float _airHumidity) {
        this._airHumidity = _airHumidity;
    }

    public float get_ambientLight() {
        return _ambientLight;
    }

    public void set_ambientLight(float _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public float get_waterQuantity() {
        return _waterQuantity;
    }

    public void set_waterQuantity(float _waterQuantity) {
        this._waterQuantity = _waterQuantity;
    }

    public float get_airQuality() {
        return _airQuality;
    }

    public void set_airQuality(float _airQuality) {
        this._airQuality = _airQuality;
    }

    public Status() {
    }

    //Current Sensors
    public Status(int _id, String _name, float _airTemperature, float _airHumidity) {
        this._id = autoIncrement;
        this._name = _name;
        this._airTemperature = _airTemperature;
        this._airHumidity = _airHumidity;
        autoIncrement++;
    }

    //All Sensors
    public Status(int _id, String _name, float _airTemperature, float _airHumidity, float _ambientLight, float _waterQuantity, float _airQuality) {
        this._id = autoIncrement;
        this._name = _name;
        this._airTemperature = _airTemperature;
        this._airHumidity = _airHumidity;
        this._ambientLight = _ambientLight;
        this._waterQuantity = _waterQuantity;
        this._airQuality = _airQuality;
        autoIncrement++;
    }

    @Override
    public String toString() {
        return "Status{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                ", _airTemperature=" + _airTemperature +
                ", _airHumidity=" + _airHumidity +
                ", _ambientLight=" + _ambientLight +
                ", _waterQuantity=" + _waterQuantity +
                ", _airQuality=" + _airQuality +
                '}';
    }
}
