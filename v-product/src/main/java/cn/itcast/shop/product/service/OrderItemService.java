package cn.itcast.shop.product.service;

import cn.itcast.shop.dto.OrderItemDto;
import cn.itcast.shop.pojo.Orderitem;

import java.util.List;

public interface OrderItemService {

    int addOrderItem(Orderitem orderitem);

    List<OrderItemDto> getOrderItemByOid(String oid);
}
