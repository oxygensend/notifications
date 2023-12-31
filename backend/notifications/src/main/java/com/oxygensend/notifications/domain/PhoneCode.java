package com.oxygensend.notifications.domain;

import java.util.Arrays;
import java.util.Objects;

public enum PhoneCode {

    POLAND("+48", "PL"),
    GERMANY("+49", "DE"),
    FRANCE("+33", "FR"),
    UNITED_KINGDOM("+44", "GB"),
    UNITED_STATES("+1", "US");

    public final String code;
    public final String country;

    PhoneCode(String code, String country) {
        this.code = code;
        this.country = country;
    }

    public static PhoneCode fromCountry(String country) {
        return Arrays.stream(PhoneCode.values())
                     .filter(phoneCode -> Objects.equals(phoneCode.country, country))
                     .findFirst()
                     .orElseThrow();
    }

}
