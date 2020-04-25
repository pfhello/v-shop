package cn.itcast.shop.product.service;

import cn.itcast.shop.dto.OrdersDto;
import cn.itcast.shop.dto.PageBean;
import cn.itcast.shop.pojo.Orders;


public interface OrdersService {

    String putOrder(String pid,Integer num);

    OrdersDto getOrdersByOid(String oid);

    String putCartOrders();

    PageBean<OrdersDto> getOrdersByUid(Integer currentPage, Integer pageSize);

    int updateOrdersByOid(Orders orders);
}
