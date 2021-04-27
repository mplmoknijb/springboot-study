package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogUtil {

    private static final Logger log = LoggerFactory.getLogger(LogUtil.class);

    public void createLog(){
            log.info("=========================================>Info");
            log.debug("=========================================>debug");
            log.error("=========================================>error");
    }
}
