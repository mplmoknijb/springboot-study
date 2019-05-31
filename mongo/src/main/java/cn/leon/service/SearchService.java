package cn.leon.service;

import cn.leon.model.MongoDto;

import java.util.List;

public interface SearchService {
    List<MongoDto> searchPageHelper(MongoDto mongoDto, Integer page, Integer size);
}
