package cn.leon.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.leon.core.DataOps;
import cn.leon.domain.form.SearchForm;
import cn.leon.domain.form.StorageForm;
import cn.leon.domain.vo.SearchVo;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/4 10:29
 */
@RestController
    @RequestMapping("/platform")
public class StorageController {

    @Autowired
    private DataOps dataOps;

    @PostMapping("/storage")
    public void storage(@RequestBody StorageForm form) {
        dataOps.saveData(form);
    }

    @PostMapping("/search")
    public List<SearchVo> getData(@RequestBody SearchForm form) {
        return dataOps.getData(form);
    }
}
