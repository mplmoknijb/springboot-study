package cn.leon.base;

import cn.leon.domain.StorageForm;
import cn.leon.model.MongoDto;

import java.util.List;

public interface MongoOpsService {
    List<MongoDto> searchPageHelper(MongoDto mongoDto, Integer page, Integer size);

    void saveTest(StorageForm form);
}
