package cn.leon.service.Impl;

import cn.leon.bo.RoleUserBo;
import cn.leon.dao.RoleDao;
import cn.leon.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<HashMap<String, Object>> queryList() {
        List<HashMap<String, Object>> list = roleDao.queryList();
        list.stream().forEach(map -> {
            map.entrySet().stream().filter(entry -> entry.getKey().equals("a"))
                    .forEach(entry -> {
                        entry.setValue("test");
                    });
        });
        return list;
    }
}