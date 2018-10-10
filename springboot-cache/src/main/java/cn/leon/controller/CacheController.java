package cn.leon.controller;

import cn.leon.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("concurrenmapcache.cacheController")
@RequestMapping(value = "/concurrenmapcache/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    /**
     * 查询方法
     */
    @GetMapping
    public String getByCache() {
        Long startTime = System.currentTimeMillis();
        long timestamp = this.cacheService.getByCache();
        Long endTime = System.currentTimeMillis();
        System.out.println("耗时: " + (endTime - startTime));
        return timestamp + "";
    }

    /**
     * 保存方法
     */
    @PostMapping
    public void save() {
        this.cacheService.save();
    }

    /**
     * 删除方法
     */
    @DeleteMapping
    public void delete() {
        this.cacheService.delete();
    }
}
