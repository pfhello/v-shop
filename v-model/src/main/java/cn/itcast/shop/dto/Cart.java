package cn.itcast.shop.dto;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    private Integer totalPrice;

    private List<ProductCartItem> list=new ArrayList<>();
}
