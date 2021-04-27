package com.controller;

import com.bo.UserBo;
import com.service.UserService;
import com.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restful")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String queryList(UserVo userVo){
        return userService.findAll(userVo) + "hello";
    }

    @PostMapping("/new")
    public List<UserBo> insert(UserVo userVo){
        return userService.insert(userVo);
    }

    @PutMapping("/renew")
    public List<UserBo> update(UserVo userVo){
        return userService.update(userVo);
    }

    @GetMapping("/{id}")
    public UserBo findOne(@PathVariable String id){
        return userService.findOne(id);
    }

    @DeleteMapping("/next")
    public List<UserBo> delete(UserVo userVo){
        return userService.delete(userVo);
    }
}
