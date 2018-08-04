package com.controller;

import com.UserAccess;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    public FirstController() {
    }

    @RequestMapping({"/first"})
    public Object first() {
        return "first controller";
    }

    @RequestMapping({"/doError"})
    public Object error() {
        return 1 / 0;
    }

    @RequestMapping({"/second"})
    @UserAccess(
            desc = "seond"
    )
    public Object second() {
        return "second controller";
    }
}
