package com.oxygensend.notifications.domain.recipient

import java.util.*

enum class PhoneCode(val code: String, val country: String) {
    POLAND("+48", "PL"),
    GERMANY("+49", "DE"),
    FRANCE("+33", "FR"),
    UNITED_KINGDOM("+44", "GB"),
    UNITED_STATES("+1", "US");

    companion object {
        fun fromCountry(country: String?): PhoneCode {
            return Arrays.stream(entries.toTypedArray())
                .filter { phoneCode: PhoneCode -> phoneCode.country == country }
                .findFirst()
                .orElseThrow()
        }
    }
}
