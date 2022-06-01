package com.greenhouse.greenhouseapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int _id;
    private String _name;
    private String _email;
    private String _password;
    private String _address;
    private String _photo;
    private String _isBlocked;

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

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_photo() {
        return _photo;
    }

    public void set_photo(String _photo) {
        this._photo = _photo;
    }

    public String is_isBlocked() {
        return _isBlocked;
    }

    public void set_isBlocked(String _isBlocked) {
        this._isBlocked = _isBlocked;
    }

    //Complete Constructor
    public User(int _id, String _name, String _email, String _password, String _address, String _photo, String _isBlocked) {
        this._id = _id;
        this._name = _name;
        this._email = _email;
        this._password = _password;
        this._address = _address;
        this._photo = _photo;
        this._isBlocked = _isBlocked;
    }

    //Login Constructor
    public User(String _email, String _password) {
        this._email = _email;
        this._password = _password;
    }

    //Empty Constructor
    public User() {

    }

    //Test toString
    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                "_name='" + _name + '\'' +
                ", _email='" + _email + '\'' +
                ", _password='" + _password + '\'' +
                ", _address='" + _address + '\'' +
                ", _photo='" + _photo + '\'' +
                ", _isBlocked='" + _isBlocked + '\'' +
                '}';
    }
}
