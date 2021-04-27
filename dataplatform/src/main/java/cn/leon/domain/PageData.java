package cn.leon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {
    private int size;
    private int num;
    private List list;
    private int page;
    private long count;

}
