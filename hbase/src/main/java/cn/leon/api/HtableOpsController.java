package cn.leon.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.base.HtableOpsService;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:13
 */
@RestController
@RequestMapping("/hbase")
public class HtableOpsController {

    @Autowired
    private HtableOpsService htableOpsService;

    @PostMapping("/create/table")
    public void insertColumn(@RequestBody HtableOpsForm form) {
        htableOpsService.createTable(form);
    }

    @PostMapping("/check/table")
    public boolean insertColumList(@RequestBody HtableOpsForm form) {
        return htableOpsService.tableExists(form.getTableName());
    }

}
