package com.healthcare.Fragments.module_visitScheduler;

/**
 * Created by Ashish on 28-Jan-17.
 */
public class Element
{
    int _id;
    String _name,_date,_hour,_minute,_location,_description;

    public Element(){}

    public Element( String _name, String _date, String _hour, String _minute, String _location, String _description) {
        this._name = _name;
        this._date = _date;
        this._hour = _hour;
        this._minute = _minute;
        this._location = _location;
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_date() {
        return _date;
    }

    public String get_hour() {
        return _hour;
    }

    public String get_minute() {
        return _minute;
    }

    public String get_location() {
        return _location;
    }

    public String get_description() {
        return _description;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_hour(String _hour) {
        this._hour = _hour;
    }

    public void set_minute(String _minute) {
        this._minute = _minute;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
}
