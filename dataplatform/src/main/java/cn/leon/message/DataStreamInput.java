package cn.leon.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 17:19
 */
public interface DataStreamInput {

    String QUERY_TASK_INPUT = "query_task_input";


    @Input(QUERY_TASK_INPUT)
    MessageChannel queryTaskInput();
}
