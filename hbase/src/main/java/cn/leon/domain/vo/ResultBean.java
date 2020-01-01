package cn.leon.domain.vo;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Desc
 * @date 2019/6/13 9:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T> {
    private int code;
    private String message;
    private T data;

    public static ResultBean error(int code, String message) {
        return ResultBean.builder().code(code).message(message).build();
    }

    public static ResultBean success() {
        return ResultBean.builder().message("success").code(200).build();
    }

    public static <V> ResultBean<V> success(Collection<V> data) {
        ResultBean resultBean = ResultBean.builder().message("success").data(data).code(200).build();
        resultBean.setData(data);
        return resultBean;
    }
}
