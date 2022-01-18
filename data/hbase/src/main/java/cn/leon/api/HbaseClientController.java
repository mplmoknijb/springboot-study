package cn.leon.api;

import cn.leon.base.HbaseReadServiceimpl;
import cn.leon.base.HbaseWriteServiceimpl;
import cn.leon.domain.form.BatchOpsForm;
import cn.leon.domain.form.HtableOpsForm;
import cn.leon.domain.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/4 18:13
 */
@RestController
@RequestMapping("/hbase")
public class HbaseClientController {
    @Autowired
    private HbaseWriteServiceimpl hbaseWriteServiceimpl;
    @Autowired
    private HbaseReadServiceimpl hbaseReadServiceimpl;

    @PostMapping("/writing/columns")
    public void insertColumList(@RequestBody HtableOpsForm form) {
        hbaseWriteServiceimpl.insertColums(form);
    }

    @PostMapping("/writing/row/list")
    public void insertRowList(@RequestBody BatchOpsForm form) {
        hbaseWriteServiceimpl.insertRowList(form);
    }

    @PostMapping("/reading/value")
    public String getValue(@RequestBody HtableOpsForm form) {
        return hbaseReadServiceimpl.getValue(form);
    }

    @PostMapping("/reading/row")
    public List<String> getRow(@RequestBody HtableOpsForm form) {
        return hbaseReadServiceimpl.getRow(form);
    }

    @PostMapping("/scan/table")
    public ResultBean<List<String>> getRowList(@RequestBody HtableOpsForm form) {
        ResultBean<List<String>> resultBean = new ResultBean<>();
        resultBean.setData(hbaseReadServiceimpl.getRowList(form));
        return resultBean;
    }
}
