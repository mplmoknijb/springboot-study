package cn.leon.controller;

import cn.leon.dao.MongoTestDao;
import cn.leon.model.MongoDto;
import cn.leon.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@RestController
public class MongoController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private MongoTestDao mongoTestDao;

    @GetMapping(value = "/test1")
    public void saveTest() throws Exception {
        mongoTestDao.saveTest();
    }

    @PostMapping(value = "/test2")
    public List<MongoDto> findTestByName(@RequestBody MongoDto mongoDto) {
        List<MongoDto> mgtest = searchService.searchPageHelper(mongoDto, 1, 1000);
        System.out.println("mgtest is " + mgtest);
        return mgtest;
    }

    @GetMapping(value = "/test3")
    public void updateTest() {
        MongoDto mgtest = new MongoDto();
        mgtest.setAge(44);
        mgtest.setName("ceshi2");
        mongoTestDao.updateTest(mgtest);
    }

    @GetMapping(value = "/test4")
    public void deleteTestById() {
        mongoTestDao.deleteTestById(11);
    }
}