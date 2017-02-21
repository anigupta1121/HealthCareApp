package com.healthcare.Fragments.module_family_record;

/**
 * Created by Ashish on 28-Jan-17.
 */
public class Element
{
    int _id;
    String _fmember,_date,_doctor,_place,_prescription,_disease,_tests;


    public Element(){}

    public Element(String _tests, String _fmember, String _date, String _doctor, String _place, String _prescription, String _disease) {
        this._tests = _tests;
        this._fmember = _fmember;
        this._date = _date;
        this._doctor = _doctor;
        this._place = _place;
        this._prescription = _prescription;
        this._disease = _disease;
    }

    public String get_fmember() {
        return _fmember;
    }

    public String get_date() {
        return _date;
    }

    public String get_doctor() {
        return _doctor;
    }

    public String get_place() {
        return _place;
    }

    public String get_prescription() {
        return _prescription;
    }

    public String get_disease() {
        return _disease;
    }

    public String get_tests() {
        return _tests;
    }

    public void set_fmember(String _fmember) {
        this._fmember = _fmember;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_doctor(String _doctor) {
        this._doctor = _doctor;
    }

    public void set_place(String _place) {
        this._place = _place;
    }

    public void set_prescription(String _prescription) {
        this._prescription = _prescription;
    }

    public void set_disease(String _disease) {
        this._disease = _disease;
    }

    public void set_tests(String _tests) {
        this._tests = _tests;
    }
}
