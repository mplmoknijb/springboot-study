package com.service;

import com.bo.UserBo;
import com.vo.UserVo;

import java.util.List;

public interface UserService {

    List<UserBo> insert(UserVo userVo);

    List<UserBo> update(UserVo userVo);

    UserBo findOne(String id);

    List<UserBo> delete(UserVo userVo);

    List<UserBo> findAll(UserVo userVo);

}
