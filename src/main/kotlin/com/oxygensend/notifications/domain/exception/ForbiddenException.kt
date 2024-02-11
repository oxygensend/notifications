package com.oxygensend.notifications.domain.exception

import com.oxygensend.commons_jdk.exception.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException(message: String) : ApiException(message) {
}