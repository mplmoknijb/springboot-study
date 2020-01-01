package cn.leon.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import cn.leon.domain.form.TaskForm;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 17:21
 */
@EnableBinding(DataStreamOutput.class)
public class TaskSchedulingApi {

    @Autowired
    private DataStreamOutput dataStreamOutput;

    public void queryTask(TaskForm form) {
        dataStreamOutput.queryTaskOutput().send(MessageBuilder.withPayload(form).build());
    }
}
