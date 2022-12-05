package com.greenhouse.greenhouseapp.model;

public class Status {

    private int _id;
    private int _idPlant;
    private String _name;
    private float _airTemperature;
    private float _airHumidity;
    private float _soilHumidity;
    private float _waterQuantity;
    private float _lightQuantity;
    //private static int autoIncrement = 1;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_idPlant() {
        return _idPlant;
    }

    public void set_idPlant(int _idPlant) {
        this._idPlant = _idPlant;
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

    public float get_waterQuantity() {
        return _waterQuantity;
    }

    public void set_waterQuantity(float _waterQuantity) {
        this._waterQuantity = _waterQuantity;
    }

    public float get_soilHumidity() {
        return _soilHumidity;
    }

    public void set_soilHumidity(float _soilHumidity) {
        this._soilHumidity = _soilHumidity;
    }

    public float get_lightQuantity() {
        return _lightQuantity;
    }

    public void set__lightQuantity(float _lightQuantity) {
        this._lightQuantity = _lightQuantity;
    }

    public Status() {
    }

    //Current Sensors
    public Status(int _id, int _idPlant, String _name, float _airTemperature, float _airHumidity) {
        //this._id = autoIncrement;
        this._id = _idPlant;
        this._name = _name;
        this._airTemperature = _airTemperature;
        this._airHumidity = _airHumidity;
        //autoIncrement++;
    }

    //All Sensors
    public Status(int _id, String _name, float _airTemperature, float _airHumidity, float _soilHumidity, float _waterQuantity, float _lightQuantity) {
        //this._id = autoIncrement;
        this._id = _id;
        this._name = _name;
        this._airTemperature = _airTemperature;
        this._airHumidity = _airHumidity;
        this._soilHumidity = _soilHumidity;
        this._waterQuantity = _waterQuantity;
        this._lightQuantity = _lightQuantity;
        //autoIncrement++;
    }

    @Override
    public String toString() {
        return "Status{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                ", _airTemperature=" + _airTemperature +
                ", _airHumidity=" + _airHumidity +
                ", _soilHumidity=" + _soilHumidity +
                ", _waterQuantity=" + _waterQuantity +
                ", _lightQuantity=" + _lightQuantity +
                '}';
    }
}
