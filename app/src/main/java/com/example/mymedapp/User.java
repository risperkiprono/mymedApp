package com.example.mymedapp;

public class User {
    //declare variables
    String FirstName,LastName,Email,Password;
    //empty constructor user
    public User(){

    }
    //constructor for all variables

    public User(String firstName, String lastName,String email, String password) {
        FirstName=firstName;
        LastName=lastName;
       Email=email;
       Password=password;
    }

    public String getFirstName(){return FirstName;}
    public void setFirstName(String FirstName){this.FirstName=FirstName;}

    public String getLastName(){return LastName;}
    public void setLastName(String LastName){this.LastName=LastName;}

    public String getEmail(){return Email;}
    public void setEmail(String Email){this.Email=Email;}

    public String getPassword(){return Password;}
    public void setPassword(String Password){this.Password=Password;}
}
