package com.crud.crud_project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private StringProperty username = null;
    private StringProperty id = null;
    private StringProperty StartDate=null;
    private StringProperty gender = null;
    private StringProperty course=null;
    private StringProperty MobileNumber=null;
    public Student() {
        username = new SimpleStringProperty(this,"username");
        id = new SimpleStringProperty(this,"id");;
        StartDate = new SimpleStringProperty(this,"StartDate");
        gender = new SimpleStringProperty(this,"gender");
        course = new SimpleStringProperty(this,"course");
        MobileNumber=new SimpleStringProperty(this,"MobileNumber");
    }

    public String getMobileNumber() {
        return MobileNumber.get();
    }

    public StringProperty mobileNumberProperty() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.MobileNumber.set(mobileNumber);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getStartDate() {
        return StartDate.get();
    }

    public StringProperty startDateProperty() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        this.StartDate.set(startDate);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getCourse() {
        return course.get();
    }

    public StringProperty courseProperty() {
        return course;
    }

    public void setCourse(String course) {
        this.course.set(course);
    }
}
