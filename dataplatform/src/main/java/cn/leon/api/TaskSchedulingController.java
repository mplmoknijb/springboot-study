package cn.leon.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.leon.domain.form.TaskForm;
import cn.leon.message.TaskSchedulingApi;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 15:34
 */
@RestController
public class TaskSchedulingController {
    @Autowired
    private TaskSchedulingApi taskSchedulingApi;

    @PostMapping("/query/task/scheduling")
    public void query(@RequestBody TaskForm form) {
        taskSchedulingApi.queryTask(form);
    }
}
