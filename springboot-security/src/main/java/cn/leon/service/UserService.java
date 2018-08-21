package cn.leon.service;

import cn.leon.entity.UserEntity;

public interface UserService {

    boolean insert(UserEntity userEntity);

    UserEntity getByUsername(String username);

}
