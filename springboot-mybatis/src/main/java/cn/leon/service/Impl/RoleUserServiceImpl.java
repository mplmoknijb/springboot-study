package cn.leon.service.Impl;

import cn.leon.bo.RoleUserBo;
import cn.leon.dao.RoleDao;
import cn.leon.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<RoleUserBo> queryList() {

        List<RoleUserBo> list = roleDao.queryList();

        return list;
    }
}