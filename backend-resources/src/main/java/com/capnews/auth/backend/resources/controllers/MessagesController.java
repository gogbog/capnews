package com.capnews.auth.backend.resources.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {


    @GetMapping("/messages")
    public String getMessages(@AuthenticationPrincipal Jwt jwt) {
    String username = jwt.getClaimAsString("preferred_username");
    String email = jwt.getClaimAsString("email");
    String userId = jwt.getSubject();

    // Do something with the user information...

    return username;
}
}
