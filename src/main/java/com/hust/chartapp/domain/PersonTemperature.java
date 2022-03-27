package com.hust.chartapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;
import java.util.Date;

@FirebaseDocumentPath("/Person_Temp")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonTemperature implements Comparable<PersonTemperature> {

    @FirebaseDocumentId
    private String firebaseId;

    private Date dateObj;

    private String date;

    private String time;

    @JsonProperty("tempObject")
    private Double temperature;

    public PersonTemperature() {}

    public PersonTemperature(String firebaseId, Date dateObj, String date, String time, Double temperature) {
        this.firebaseId = firebaseId;
        this.dateObj = dateObj;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Date getDateObj() {
        return dateObj;
    }

    public void setDateObj(Date dateObj) {
        this.dateObj = dateObj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public int compareTo(PersonTemperature o) {
        return o.dateObj.compareTo(this.dateObj);
    }
}
