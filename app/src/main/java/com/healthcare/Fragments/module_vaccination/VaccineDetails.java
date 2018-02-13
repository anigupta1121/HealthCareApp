package com.healthcare.Fragments.module_vaccination;

/**
 * Created by guptaanirudh100 on 2/5/2017.
 */

public class VaccineDetails {
    public String vaccine_name;
    public String date;
    public String type;
    public String given;

    public VaccineDetails(String vaccine_name, String date, String type,String given) {
        this.vaccine_name = vaccine_name;
        this.date = date;
        this.type = type;
        this.given=given;
    }
}
