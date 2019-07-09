package cn.leon.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 17:19
 */
public interface DataStreamOutput {

    String QUERY_TASK_OUTPUT = "query_task_output";


    @Output(QUERY_TASK_OUTPUT)
    MessageChannel queryTaskOutput();
}
