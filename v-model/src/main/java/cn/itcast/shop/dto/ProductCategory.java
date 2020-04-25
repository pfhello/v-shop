package cn.itcast.shop.dto;

import cn.itcast.shop.pojo.Product;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCategory extends Product implements Serializable{
    private String cname;
}
