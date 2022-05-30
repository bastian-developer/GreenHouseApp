package com.greenhouse.greenhouseapp.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int _id;
    private String _name;
    private String _email;
    private String _password;
    private Address _address;
    private Photo _photo;
    private boolean _isBlocked;
    private List<Plant> _plantList;

    //CLASS attribute, NOT an OBJECT attribute
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

    public Address get_address() {
        return _address;
    }

    public void set_address(Address _address) {
        this._address = _address;
    }

    public Photo get_photo() {
        return _photo;
    }

    public void set_photo(Photo _photo) {
        this._photo = _photo;
    }

    public boolean is_isBlocked() {
        return _isBlocked;
    }

    public void set_isBlocked(boolean _isBlocked) {
        this._isBlocked = _isBlocked;
    }

    public List<Plant> get_plantList() {
        return _plantList;
    }

    public void set_plantList(List<Plant> _plantList) {
        this._plantList = _plantList;
    }


    //Complete Constructor
    public User(int _id, String _name, String _email, String _password, Address _address, Photo _photo, boolean _isBlocked, List<Plant> _plantList) {
        this._id = autoIncrement;
        this._name = _name;
        this._email = _email;
        this._password = _password;
        this._address = _address;
        this._photo = _photo;
        this._isBlocked = _isBlocked;
        //New Instance?
        this._plantList = new ArrayList<>();
        autoIncrement++;
    }

    //Register Constructor
    public User(int _id, String _name, String _email, String _password, Address _address, Photo _photo) {
        this._id = autoIncrement;
        this._name = _name;
        this._email = _email;
        this._password = _password;
        this._address = _address;
        this._photo = _photo;
        autoIncrement++;
    }

    //Test Constructor
    public User(int _id, String _name, String _email, String _password, Address _address) {
        this._id = autoIncrement;
        this._name = _name;
        this._email = _email;
        this._password = _password;
        this._address = _address;
        autoIncrement++;
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
                '}';
    }
}
