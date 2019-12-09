package com.patron.Controllers.ptr;

public class Protege {

    private String id;
    private String age;
    private String firstname;
    private String lastname;
    private String phone;
    private String gender;


    public Protege(String id, String age, String firstname, String lastname, String phone, String gender) {
        this.id = id;
        this.age = age;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

}
