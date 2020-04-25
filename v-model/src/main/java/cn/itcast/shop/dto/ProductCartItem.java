package cn.itcast.shop.dto;

import cn.itcast.shop.pojo.Product;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCartItem extends Product implements Serializable {
    private Integer num;
}
