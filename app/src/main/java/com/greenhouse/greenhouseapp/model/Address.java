package com.greenhouse.greenhouseapp.model;

public class Address {

    private int _id;
    private Country _country;
    private State _state;
    private City _city;
    private String _street;
    private static int autoIncrement = 1;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Country get_country() {
        return _country;
    }

    public void set_country(Country _country) {
        this._country = _country;
    }

    public State get_state() {
        return _state;
    }

    public void set_state(State _state) {
        this._state = _state;
    }

    public City get_city() {
        return _city;
    }

    public void set_city(City _city) {
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

    public Address(int _id, Country _country, State _state, City _city, String _street) {
        this._id = autoIncrement;
        this._country = _country;
        this._state = _state;
        this._city = _city;
        this._street = _street;
        autoIncrement++;
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
