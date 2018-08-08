package cn.leon.service.order.Impl;

import cn.leon.annotation.leonBean;
import cn.leon.service.order.OrderInfoService;
import cn.leon.service.user.Impl.UserInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@leonBean
public class OrderInfoServiceImpl implements OrderInfoService {
    private final static Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Override
    public String addUserInfo(String name) {

        log.info("My name is : " + name);
        return "Order : " + name;
    }
}
