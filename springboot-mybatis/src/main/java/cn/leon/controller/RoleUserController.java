package cn.leon.controller;

import cn.leon.bo.RoleUserBo;
import cn.leon.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @GetMapping("/getlist")
    public List<HashMap<String, Object>> queryList() {
        return roleUserService.queryList();
    }
}