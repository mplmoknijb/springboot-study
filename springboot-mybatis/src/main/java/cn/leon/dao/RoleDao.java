package cn.leon.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface RoleDao {

    List<HashMap<String, Object>> queryList();
}
