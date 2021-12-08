package com.example.mymedapp.data;

public class Doctor {


    String name;
    String id;
    String speciality;
    String experience;
    String hospital;
    String fee;

    public Doctor(String name, String id) {
        this.name = name;
        this.id = id;
        this.speciality = speciality;
        this.experience = experience;
        this.hospital = hospital;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }


    }



