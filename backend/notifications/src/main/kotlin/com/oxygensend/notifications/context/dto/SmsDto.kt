package com.oxygensend.notifications.context.dto

import com.oxygensend.notifications.domain.Phone
import jakarta.validation.constraints.NotEmpty

data class SmsDto(val body: @NotEmpty String, val phoneNumbers: Set<Phone>)
