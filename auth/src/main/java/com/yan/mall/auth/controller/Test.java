package com.yan.mall.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class Test {

    @PreAuthorize("hasRole('User')")
    @GetMapping("oauthTest")
    public String fads(){return "cehngg";}

}
