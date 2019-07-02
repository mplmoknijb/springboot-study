package cn.leon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.leon.domain.form.HtableOpsForm;
import cn.leon.domain.vo.ResultBean;
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

    @PostMapping("/writing/column")
    public void insertColumn(@RequestBody HtableOpsForm form) {
        hbaseTemplate.insertColumn(form);
    }

    @PostMapping("/writing/columns")
    public void insertColumList(@RequestBody HtableOpsForm form) {
        hbaseTemplate.insertColumList(form);
    }

    @PostMapping("/writing/row/list")
    public void insertRowList(@RequestBody HtableOpsForm form) {
        hbaseTemplate.insertRowList(form);
    }

    @PostMapping("/reading/value")
    public String getValue(@RequestBody HtableOpsForm form) {
        return hbaseTemplate.getValue(form);
    }

    @PostMapping("/reading/row")
    public List<String> getRow(@RequestBody HtableOpsForm form) {
        return hbaseTemplate.getRow(form);
    }

    @PostMapping("/scan/table")
    public ResultBean<List<String>> getRowList(@RequestBody HtableOpsForm form) {
        ResultBean<List<String>> resultBean = new ResultBean<>();
        resultBean.setData(hbaseTemplate.getRowList(form));
        return resultBean;
    }
}
