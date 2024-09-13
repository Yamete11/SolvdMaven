package com.example.service;

import com.example.user.User;

public class NotificationService {
    public void notifyUser(User user) {
        System.out.println("Notifying user: " + user.getEmail());
    }
}
