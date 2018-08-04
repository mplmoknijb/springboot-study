package com.service.Impl;

import com.bo.UserBo;
import com.dao.UserDao;
import com.entity.UserEntity;
import com.service.UserService;
import com.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserBo> insert(UserVo userVo) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo,userEntity);
        return userDao.insert(userEntity);
    }

    @Override
    public List<UserBo> update(UserVo userVo) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo,userEntity);
        return userDao.update(userEntity);
    }

    @Override
    public UserBo findOne(String id) {
        return userDao.findOne(id);
    }

    @Override
    public List<UserBo> delete(UserVo userVo) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo,userEntity);
        return userDao.delete(userEntity);
    }

    @Override
    public List<UserBo> findAll(UserVo userVo) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo,userEntity);
        return userDao.findAll(userEntity);
    }
}
