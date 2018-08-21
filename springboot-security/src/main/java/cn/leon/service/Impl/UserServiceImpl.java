package cn.leon.service.Impl;

import cn.leon.dao.UserMapper;
import cn.leon.entity.UserEntity;
import cn.leon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean insert(UserEntity userEntity) {
        String username = userEntity.getUsername();

        if (exist(username)) {
            return false;
        }

        encryptPassword(userEntity);
        userEntity.setRoles("ROLE_USER");

        int num = userMapper.insert(userEntity);
        return num == 1;

    }

    @Override
    public UserEntity getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    private boolean exist(String username) {

        UserEntity userEntity = userMapper.selectByUsername(username);

        return (userEntity != null);
    }

    private void encryptPassword(UserEntity userEntity) {

        String password = userEntity.getPassword();

        password = new BCryptPasswordEncoder().encode(password);

        userEntity.setPassword(password);
    }
}
