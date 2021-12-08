package com.example.mymedapp.data;

public class Book {
    String time,date,email;

    public Book(String email, String time, String date) {

        this.time = time;
        this.date = date;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
