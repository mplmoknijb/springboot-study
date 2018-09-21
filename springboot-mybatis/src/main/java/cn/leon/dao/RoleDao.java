package cn.leon.dao;

import cn.leon.entity.RoleUserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao extends MyMapper<RoleUserEntity> {

}
