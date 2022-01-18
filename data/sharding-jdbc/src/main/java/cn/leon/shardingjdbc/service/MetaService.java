package cn.leon.shardingjdbc.service;

import cn.leon.shardingjdbc.entity.Meta;
import cn.leon.shardingjdbc.mapper.master.MasterMetaMapper;
import cn.leon.shardingjdbc.mapper.slave.SlaveMetaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaService {

    @Autowired
    private MasterMetaMapper masterMetaMapper;

    @Autowired
    private SlaveMetaMapper slaveMetaMapper;

    public void add(String name) {
        Meta meta = new Meta();
        meta.setA(name);
        System.out.println("master save count " + masterMetaMapper.insert(meta));
        System.out.println("slave save count " + slaveMetaMapper.insert(meta));
    }
}
