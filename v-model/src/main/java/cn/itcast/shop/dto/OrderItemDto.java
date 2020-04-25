package cn.itcast.shop.dto;

import cn.itcast.shop.pojo.Product;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemDto extends Product implements Serializable {

    private Integer count;

    private Integer subtotal;
}
