package cn.leon.shardingjdbc.mapper.slave;

import cn.leon.shardingjdbc.entity.Meta;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlaveMetaMapper {
    int insert(Meta meta);
}
