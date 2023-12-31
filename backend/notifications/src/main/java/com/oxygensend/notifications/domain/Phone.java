package com.oxygensend.notifications.domain;

public record Phone(String number, PhoneCode code) {

    public String fullNumber() {
        return code.code + number;
    }
}
