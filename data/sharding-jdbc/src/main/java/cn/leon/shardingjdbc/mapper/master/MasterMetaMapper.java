package cn.leon.shardingjdbc.mapper.master;


import cn.leon.shardingjdbc.entity.Meta;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterMetaMapper {

    int insert(Meta meta);
}
