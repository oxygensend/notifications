package com.oxygensend.notifications.application.config

import com.oxygensend.notifications.application.authentication.HashAlgorithm


interface AuthProperties {
    val enabled: Boolean
    val secret: String
    val hashAlgorithm: HashAlgorithm
}