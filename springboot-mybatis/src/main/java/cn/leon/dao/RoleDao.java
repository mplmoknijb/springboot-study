package cn.leon.dao;

import cn.leon.bo.RoleUserBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao {

    List<RoleUserBo> queryList();
}
