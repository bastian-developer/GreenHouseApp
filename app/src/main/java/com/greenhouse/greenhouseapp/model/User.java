package com.greenhouse.greenhouseapp.model;

public class User {

    private int _id;
    private String _name;
    private String _email;
    private String _password;
    private String _address;

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

    public User(int _id, String _name, String _email, String _password, String _address) {
        this._id = _id;
        this._name = _name;
        this._email = _email;
        this._password = _password;
        this._address = _address;
    }

    public User(String _email, String _password) {
        this._email = _email;
        this._password = _password;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                "_name='" + _name + '\'' +
                ", _email='" + _email + '\'' +
                ", _password='" + _password + '\'' +
                ", _address='" + _address + '\'' +
                '}';
    }
}
