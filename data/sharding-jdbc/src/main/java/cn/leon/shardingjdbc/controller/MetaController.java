package cn.leon.shardingjdbc.controller;

import cn.leon.shardingjdbc.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping("add")
    public void add(String name) {
        metaService.add(name);
    }

}
