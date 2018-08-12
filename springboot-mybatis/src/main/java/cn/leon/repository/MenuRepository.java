package cn.leon.repository;

import cn.leon.bo.MenuBo;
import cn.leon.command.MenuCommand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.leon.dao.MenuMapper;
import cn.leon.entity.Menu;
import java.util.List;

@Repository
@Mapper
public interface MenuRepository extends MenuMapper{

    List<MenuBo> queryList(MenuCommand command);

    int insertList (List<Menu> menus);
}