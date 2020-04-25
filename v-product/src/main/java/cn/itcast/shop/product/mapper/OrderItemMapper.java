package cn.itcast.shop.product.mapper;

import cn.itcast.shop.dto.OrderItemDto;
import cn.itcast.shop.pojo.Orderitem;

import java.util.List;

public interface OrderItemMapper {

    int addOrderItem(Orderitem orderitem);

    List<OrderItemDto> getOrderItemByOid(String oid);
}
