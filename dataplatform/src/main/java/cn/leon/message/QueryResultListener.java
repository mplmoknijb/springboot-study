package cn.leon.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import cn.leon.core.DataPlatFormOps;
import cn.leon.domain.form.StorageForm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mujian
 * @Desc
 * @date 2019/7/9 18:01
 */
@EnableBinding(DataStreamInput.class)
@Slf4j
public class QueryResultListener {

    @Autowired
    private DataPlatFormOps dataPlatFormOps;

    @StreamListener(DataStreamInput.QUERY_TASK_INPUT)
    public void resultListener(Message<String> message) {
        log.info("=================={}=====================", message.getPayload());
        dataPlatFormOps.saveData(StorageForm.builder().build());
    }
}
