package cn.leon.base.user.Impl;

import cn.leon.annotation.leonMethod;
import cn.leon.base.user.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final static Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);


    @Override
    @leonMethod
    public String addUserInfo(String name) {

        log.info("My name is : " + name);
        return "User : " + name;
    }
}
