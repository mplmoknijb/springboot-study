package com.dao;

import com.bo.UserBo;
import com.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

//持久层操作的bean
@Repository
public class UserDao {
    //线程安全的map
    public static Map<String, UserEntity> map = Collections.synchronizedMap(new HashMap<>());

    /**
     * 类加载顺序
     * 1.父类静态代码块；
     * 2.子类静态代码块；
     * 3.父类非静态代码块；
     * 4.父类构造函数；
     * 5.子类非静态代码块；
     * 6.子类构造函数；
     */
    //静态代码块，JVM加载类时会执行这些静态的代码块
    static {
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(i + "");
            userEntity.setName("leon" + i);
            userEntity.setMsg("i am " + i);
            map.put(i + "", userEntity);
        }
    }

    public List<UserBo> getInfo(Map<String, UserEntity> map) {
        Set<String> set = map.keySet();
        List<UserBo> list = new ArrayList<>();
        set.stream().map(key -> {
            UserEntity userEntity = map.get(key);
            return userEntity;
        })
                .map(userEntity -> {
                    UserBo userBo = new UserBo();
                    BeanUtils.copyProperties(userEntity, userBo);
                    return userBo;
                }).forEach(userBo -> list.add(userBo));
        return list;
    }

    public List<UserBo> insert(UserEntity userEntity) {
        map.put(userEntity.getId(), userEntity);
        List<UserBo> list = getInfo(map);
        return list;
    }

    public List<UserBo> delete(UserEntity userEntity) {
        map.remove(userEntity.getId());
        List<UserBo> list = getInfo(map);
        return list;
    }

    public List<UserBo> update(UserEntity userEntity) {
        map.replace(userEntity.getId(), userEntity);
        List<UserBo> list = getInfo(map);
        return list;
    }

    public UserBo findOne(String id) {
        UserEntity userEntity = map.get(id);
        UserBo userBo = new UserBo();
        BeanUtils.copyProperties(userEntity, userBo);
        return userBo;
    }

    public List<UserBo> findAll(UserEntity userEntity){
        List<UserBo> list = getInfo(map);
        return list;
    }
}
