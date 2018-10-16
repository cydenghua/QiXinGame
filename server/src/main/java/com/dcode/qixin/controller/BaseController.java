package com.dcode.qixin.controller;

import com.dcode.qixin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    protected UserService userService;

    protected boolean tokenIsExists(String token) {
        if(null != userService.getUserByToken(token)) {
            return true;
        }
        return false;
    }
}
