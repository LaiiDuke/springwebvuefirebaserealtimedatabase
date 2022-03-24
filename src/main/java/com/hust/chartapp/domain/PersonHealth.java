package com.hust.chartapp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;

@FirebaseDocumentPath("/Person_Health")
public class PersonHealth {

    @FirebaseDocumentId
    private String firebaseId;

    private String time;
    private Double heartRate;

    @JsonProperty("SpO2")
    private Double spO2;

    public PersonHealth() {}

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public PersonHealth(String firebaseId, String time, Double heartRate, Double spO2) {
        this.firebaseId = firebaseId;
        this.time = time;
        this.heartRate = heartRate;
        this.spO2 = spO2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Double heartRate) {
        this.heartRate = heartRate;
    }

    public Double getSpO2() {
        return spO2;
    }

    public void setSpO2(Double spO2) {
        this.spO2 = spO2;
    }
}
