package com.oxygensend.notifications.context;


public record MessageCommand<T>(T content,
                                String login,
                                String serviceID,
                                String createdAt) {


}
