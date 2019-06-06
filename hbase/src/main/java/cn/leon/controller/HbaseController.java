package cn.leon.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.leon.service.HbaseTemplate;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:13
 */
@RestController
public class HbaseController {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    @GetMapping("/writing")
    public void write() throws IOException {
        hbaseTemplate.importData();
    }
}
