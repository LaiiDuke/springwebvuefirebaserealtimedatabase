package com.hust.chartapp.domain;

import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentId;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseDocumentPath;
import com.github.alperkurtul.firebaserealtimedatabase.annotation.FirebaseUserAuthKey;
import java.math.BigDecimal;

@FirebaseDocumentPath("/product")
public class Product {

    @FirebaseDocumentId
    private String firebaseId;

    private String name;
    private BigDecimal price;

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
