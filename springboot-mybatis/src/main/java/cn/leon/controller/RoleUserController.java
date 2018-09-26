package cn.leon.controller;

import cn.leon.entity.RoleUserEntity;
import cn.leon.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Validated
@RestController
@RequestMapping("roleUser")
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @GetMapping
    public List<RoleUserEntity> query() {
        return roleUserService.queryList();
    }
}