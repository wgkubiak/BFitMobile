package com.patron.Controllers.ptr;

public class Protege {

    private String id;
    private String firstname;
    private String lastname;
    private String weight;
    private String glucose;
    private String pressure;

    public Protege(String id, String firstname, String lastname, String weight, String glucose, String pressure) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.weight = weight;
        this.glucose = glucose;
        this.pressure = pressure;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getWeight() { return weight; }

    public String getGlucose() { return glucose; }

    public String getPressure() { return pressure; }
}
