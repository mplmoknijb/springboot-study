package cn.leon.core.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class LockInfo implements Serializable {

    private static final long serialVersionUID = 5932322409616133348L;
    private String name;

    private long waitTime;

    private long leaseTime;
}
