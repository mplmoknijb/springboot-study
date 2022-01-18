package cn.leon.api;

import cn.leon.base.MongoOpsService;
import cn.leon.domain.StorageForm;
import cn.leon.model.MongoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mujian
 * @date 2019-5-7 0007
 **/
@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private MongoOpsService mongoOpsService;

    @Autowired
    private cn.leon.dao.MongoOpsDao mongoOpsDao;

    @PostMapping(value = "/save")
    public void saveTest(@RequestBody StorageForm form) throws Exception {
        mongoOpsService.saveTest(form);
    }

    @PostMapping(value = "/test2")
    public List<MongoDto> findTestByName(@RequestBody MongoDto mongoDto) {
        List<MongoDto> mgtest = mongoOpsService.searchPageHelper(mongoDto, 1, 1000);
        System.out.println("mgtest is " + mgtest);
        return mgtest;
    }

    @PostMapping(value = "/get/one")
    public MongoDto findOneByField(@RequestBody MongoDto mongoDto) {
        MongoDto mgtest = mongoOpsDao.findOneByField(mongoDto);
        System.out.println("mgtest is " + mgtest);
        return mgtest;
    }

    @GetMapping(value = "/test3")
    public void updateTest() {
        MongoDto mgtest = new MongoDto();
        mongoOpsDao.updateTest(mgtest);
    }

    @GetMapping(value = "/test4")
    public void deleteTestById() {
        mongoOpsDao.deleteTestById(11);
    }
}