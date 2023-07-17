package com.linmour.common.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname PageResult
 * @Description 分页返回类型
 * @Date 2023/7/17 11:05
 * @Created by linmour
 */
@Data
public final class PageResult<T> implements Serializable {

    //数据
    private List<T> list;

    //总条数
    private Long total;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }

}
