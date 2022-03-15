package com.example.registrationform;

public class Candidates {
    private String name;
    private String email;
    private String password;
    private String gender;
    private String country;

    public Candidates(String name, String email, String password, String gender, String country) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.country = country;
    }

    public String getDetails(){
        return "Candidates Details :" +
                "\n\tName : "+ this.name
                +"\n\tEmail "+ this.email
                +"\n\tGender "+ this.gender
                +"\n\tCountry "+ this.country+"\n";

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
