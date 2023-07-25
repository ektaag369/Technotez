package com.example.technotez.model;

public class TeacherDetails {

    String Email;
    String Password;

    public TeacherDetails() {
    }

    public TeacherDetails(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
