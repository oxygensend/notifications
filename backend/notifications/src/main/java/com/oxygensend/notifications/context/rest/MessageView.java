package com.oxygensend.notifications.context.rest;

record MessageView(String status) {

    public static MessageView ok() {
        return new MessageView("ok");
    }
}
