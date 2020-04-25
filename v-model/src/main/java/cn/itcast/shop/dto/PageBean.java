package cn.itcast.shop.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageBean<T> implements Serializable {

    private Integer currentPage;

    private Long total;

    private Integer pageSize;

    private List<T> data;

}
