package cn.itcast.shop.dto;

import cn.itcast.shop.pojo.Orders;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersDto extends Orders implements Serializable {

    private List<OrderItemDto> list=new ArrayList<>();
}
