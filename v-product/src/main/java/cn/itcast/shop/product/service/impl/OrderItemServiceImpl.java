package cn.itcast.shop.product.service.impl;

import cn.itcast.shop.dto.OrderItemDto;
import cn.itcast.shop.pojo.Orderitem;
import cn.itcast.shop.product.mapper.OrderItemMapper;
import cn.itcast.shop.product.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public int addOrderItem(Orderitem orderitem) {
        return orderItemMapper.addOrderItem(orderitem);
    }

    @Override
    public List<OrderItemDto> getOrderItemByOid(String oid) {
        return orderItemMapper.getOrderItemByOid(oid);
    }
}
