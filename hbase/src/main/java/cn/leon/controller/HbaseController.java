package cn.leon.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.service.HbaseTemplate;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:13
 */
@RestController
public class HbaseController {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    @PostMapping("/writing")
    public void write(@RequestBody HtableOpsForm form) throws IOException {
        hbaseTemplate.insertOne(form);
    }

    @PostMapping("/writing/batch")
    public void writeAll(@RequestBody HtableOpsForm form) throws IOException {
        hbaseTemplate.batchSyncData(form);
    }

    @PostMapping("/value")
    public String getValue(@RequestBody HtableOpsForm form) {
        return hbaseTemplate.getValue(form);
    }

    @PostMapping("/one/row")
    public String getOneRow(@RequestBody HtableOpsForm form) throws IOException {
        return hbaseTemplate.getOneRow(form);
    }
}
