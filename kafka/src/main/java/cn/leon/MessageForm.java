package cn.leon;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author mujian
 * @date 2019-4-25 0025
 **/
@Data
public class MessageForm {
    private JSONObject content;
    private String topic;
}
