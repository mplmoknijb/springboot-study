package cn.leon.repository;

import cn.leon.bo.RoleUserBo;
import cn.leon.command.RoleUserCommand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.leon.dao.RoleUserMapper;
import cn.leon.entity.RoleUser;
import java.util.List;

@Repository
@Mapper
public interface RoleUserRepository extends RoleUserMapper{

    List<RoleUserBo> queryList(RoleUserCommand command);

    int insertList (List<RoleUser> roleUsers);
}