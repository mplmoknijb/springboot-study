package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogUtil logUtil;

    @GetMapping("/log")
    public void getlog() {
        log.info("=========================================>Info");
        log.debug("=========================================>debug");
        log.error("=========================================>error");
        logUtil.createLog();
    }
}
