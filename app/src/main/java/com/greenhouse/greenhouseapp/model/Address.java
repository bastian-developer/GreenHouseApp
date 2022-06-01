package com.greenhouse.greenhouseapp.model;

public class Address {

    private int _id;
    private String _country;
    private String _state;
    private String _city;
    private String _street;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public String get_state() {
        return _state;
    }

    public void set_state(String _state) {
        this._state = _state;
    }

    public String get_city() {
        return _city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public String get_street() {
        return _street;
    }

    public void set_street(String _street) {
        this._street = _street;
    }

    public Address() {
    }

    public Address(int _id, String _country, String _state, String _city, String _street) {
        this._id = _id;
        this._country = _country;
        this._state = _state;
        this._city = _city;
        this._street = _street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "_id=" + _id +
                ", _country=" + _country +
                ", _state=" + _state +
                ", _city=" + _city +
                ", _street='" + _street + '\'' +
                '}';
    }
}
