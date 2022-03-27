package com.hust.chartapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;
import java.util.Date;

@FirebaseDocumentPath("/Room_Environments")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomEnvironments implements Comparable<RoomEnvironments> {

    @FirebaseDocumentId
    private String firebaseId;

    private Date dateObj;

    private String date;

    private String time;

    @JsonProperty("roomTemp")
    private Double temperature;

    @JsonProperty("roomHum")
    private Double humidity;

    public RoomEnvironments() {}

    public RoomEnvironments(String firebaseId, Date dateObj, String date, String time, Double temperature, Double humidity) {
        this.firebaseId = firebaseId;
        this.dateObj = dateObj;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
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

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    @Override
    public int compareTo(RoomEnvironments o) {
        return o.dateObj.compareTo(this.dateObj);
    }
}
