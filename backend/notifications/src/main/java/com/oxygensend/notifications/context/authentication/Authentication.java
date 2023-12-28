package com.oxygensend.notifications.context.authentication;

import java.util.Map;

public interface Authentication {
    void authenticate(Map<String, String> params) throws AuthException;

}