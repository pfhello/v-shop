package cn.itcast.shop.product.mapper;

import cn.itcast.shop.dto.OrdersDto;
import cn.itcast.shop.pojo.Orders;

import java.util.List;

public interface OrdersMapper {

    int addOrders(Orders orders);

    OrdersDto getOrdersById(String oid);

    List<OrdersDto> getOrdersByUid(String uid);

    int updateOrdersByOid(Orders orders);
}
