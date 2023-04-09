package com.capnews.auth.backend.resources.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    @GetMapping("/messages")
    public String getMessages() {
        return "the protected messages";
    }
}
