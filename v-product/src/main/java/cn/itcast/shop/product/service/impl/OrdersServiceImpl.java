package cn.itcast.shop.product.service.impl;

import cn.itcast.shop.context.UserContext;
import cn.itcast.shop.dto.*;
import cn.itcast.shop.pojo.Orderitem;
import cn.itcast.shop.pojo.Orders;
import cn.itcast.shop.pojo.User;
import cn.itcast.shop.product.mapper.OrdersMapper;
import cn.itcast.shop.product.service.OrderItemService;
import cn.itcast.shop.product.service.OrdersService;
import cn.itcast.shop.product.service.ProductService;
import cn.itcast.shop.redis.UserKey;
import cn.itcast.shop.util.JsonUtils;
import cn.itcast.shop.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Transactional
    @Override
    public String putOrder(String pid, Integer num) {
        //生成订单
        ProductCategory product = productService.getProductById(pid);
        Integer total=product.getShopPrice()*num;
        String oid= UUIDUtil.getUUID();
        User user = UserContext.getUser();
        Orders orders=new Orders();
        orders.setOid(oid);
        orders.setUid(user.getUid());
        orders.setTotal(total);
        ordersMapper.addOrders(orders);
        //生成订单项
        Orderitem orderitem=new Orderitem();
        String itemId=UUIDUtil.getUUID();
        orderitem.setItemid(itemId);
        orderitem.setCount(num);
        orderitem.setSubtotal(total);
        orderitem.setPid(pid);
        orderitem.setOid(oid);
        orderItemService.addOrderItem(orderitem);
        return oid;
    }

    @Override
    public OrdersDto getOrdersByOid(String oid) {
        OrdersDto ordersDto = ordersMapper.getOrdersById(oid);
        List<OrderItemDto> orderItemDtoList = orderItemService.getOrderItemByOid(oid);
        ordersDto.setList(orderItemDtoList);
        return ordersDto;
    }

    @Transactional
    @Override
    public String putCartOrders() {
        User user = UserContext.getUser();
        String token= UserKey.USER_CART+":"+user.getUid();
        Boolean hasKey = redisTemplate.hasKey(token);
        if(hasKey){
            String json = redisTemplate.opsForValue().get(token);
            Cart cart = JsonUtils.jsonToPojo(json, Cart.class);
            //生成订单
            Orders orders=new Orders();
            String oid = UUIDUtil.getUUID();
            orders.setOid(oid);
            orders.setUid(user.getUid());
            orders.setTotal(cart.getTotalPrice());
            ordersMapper.addOrders(orders);
            //生成订单项
            cart.getList().stream().forEach(item->{
                Orderitem orderitem=new Orderitem();
                String itemId = UUIDUtil.getUUID();
                orderitem.setItemid(itemId);
                orderitem.setOid(oid);
                orderitem.setPid(item.getPid());
                orderitem.setCount(item.getNum());
                orderitem.setSubtotal(item.getShopPrice()*item.getNum());
                orderItemService.addOrderItem(orderitem);
            });
            redisTemplate.delete(token);
            return oid;
        }
        return null;
    }

    @Override
    public PageBean<OrdersDto> getOrdersByUid(Integer currentPage,Integer pageSize) {
        User user = UserContext.getUser();
        PageHelper.startPage(currentPage,pageSize);
        List<OrdersDto> ordersList = ordersMapper.getOrdersByUid(user.getUid());
        PageInfo<OrdersDto> info=new PageInfo<>(ordersList);
        PageBean<OrdersDto> pageBean=new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotal(info.getTotal());
        for (OrdersDto orders:info.getList()){
            List<OrderItemDto> itemList = orderItemService.getOrderItemByOid(orders.getOid());
            orders.setList(itemList);
        }
        pageBean.setData(info.getList());
        return pageBean;
    }

    @Transactional
    @Override
    public int updateOrdersByOid(Orders orders) {
        orders.setState(1);
        return  ordersMapper.updateOrdersByOid(orders);
    }
}
