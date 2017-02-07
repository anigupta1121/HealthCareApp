package com.healthcare.Fragments.module_vaccination;

/**
 * Created by guptaanirudh100 on 2/5/2017.
 */

public class VaccineDetails {
    public String vaccine_name;
    public String date;
    public String year;

    public VaccineDetails(String vaccine_name, String date, String year) {
        this.vaccine_name = vaccine_name;
        this.date = date;
        this.year = year;
    }
}
