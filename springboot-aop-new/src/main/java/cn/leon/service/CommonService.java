package cn.leon.service;

import cn.leon.annotation.leonBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@leonBean
public class CommonService {
    private static final Logger log = LoggerFactory.getLogger(CommonService.class);

    public int add(int i) {
        i = i + 1;
        log.info("CommonService 的处理结果为：" + i);
        return i;
    }
}
