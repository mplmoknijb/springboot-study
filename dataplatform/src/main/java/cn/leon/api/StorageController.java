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
import cn.leon.domain.vo.ResultBean;
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
    public ResultBean<List<SearchVo>> getData(@RequestBody SearchForm form) {
        ResultBean<List<SearchVo>> resultBean = new ResultBean();
        resultBean.setMessage("查询成功");
        resultBean.setCode(200);
        resultBean.setData(dataOps.getData(form));
        return resultBean;
    }

    @PostMapping("/search/ranking")
    public ResultBean<List<SearchVo>> checkTarget(@RequestBody SearchForm form) {
        ResultBean<List<SearchVo>> resultBean = new ResultBean();
        resultBean.setMessage("查询成功");
        resultBean.setCode(200);
        resultBean.setData(dataOps.getData(form));
        return resultBean;
    }

    @PostMapping("/search/detail")
    public ResultBean<SearchVo> searchDetail(@RequestBody SearchForm form) {
        ResultBean<SearchVo> resultBean = new ResultBean();
        resultBean.setMessage("查询成功");
        resultBean.setCode(200);
        resultBean.setData(dataOps.getDeatilById(form));
        return resultBean;
    }
}
