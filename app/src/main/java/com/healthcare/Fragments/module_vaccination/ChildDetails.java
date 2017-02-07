package com.healthcare.Fragments.module_vaccination;

import java.util.ArrayList;

/**
 * Created by guptaanirudh100 on 2/5/2017.
 */

public class ChildDetails {
    public String name;
    public ArrayList<VaccineDetails> vaccineDetails;
    public String dob;
    public String gender;

    public ChildDetails(String name, ArrayList<VaccineDetails> vaccineDetails, String dob, String gender) {
        this.name = name;
        this.vaccineDetails = vaccineDetails;
        this.dob = dob;
        this.gender = gender;
    }
}
