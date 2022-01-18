package cn.leon.base;

import cn.leon.dao.RoleDao;
import cn.leon.entity.RoleUserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleUserService {

    @Resource
    private RoleDao roleDao;

    public List<RoleUserEntity> queryList() {
        return roleDao.selectAll();
    }
}