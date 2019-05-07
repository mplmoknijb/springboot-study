package cn.leon.controller;

import cn.leon.dao.MongoTestDao;
import cn.leon.model.MongoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@RestController
public class MongoController {

    @Autowired
    private MongoTestDao mtdao;

    @GetMapping(value = "/test1")
    public void saveTest() throws Exception {
        MongoDto mgtest = new MongoDto();
        mgtest.setId(11);
        mgtest.setAge(33);
        mgtest.setName("ceshi");
        mtdao.saveTest(mgtest);
    }

    @GetMapping(value = "/test2")
    public MongoDto findTestByName() {
        MongoDto mgtest = mtdao.findTestByName("ceshi");
        System.out.println("mgtest is " + mgtest);
        return mgtest;
    }

    @GetMapping(value = "/test3")
    public void updateTest() {
        MongoDto mgtest = new MongoDto();
        mgtest.setId(11);
        mgtest.setAge(44);
        mgtest.setName("ceshi2");
        mtdao.updateTest(mgtest);
    }

    @GetMapping(value = "/test4")
    public void deleteTestById() {
        mtdao.deleteTestById(11);
    }

}