package cn.itcast.shop.product.controller;

import cn.itcast.shop.dto.OrdersDto;
import cn.itcast.shop.dto.PageBean;
import cn.itcast.shop.pojo.Orders;
import cn.itcast.shop.product.service.OrdersService;
import cn.itcast.shop.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/putOrder")
    public Result putOrder(@RequestParam("pid") String pid, @RequestParam("num") Integer num){
        String oid = ordersService.putOrder(pid, num);
        return Result.success(oid);
    }

    @GetMapping("/getOrders")
    public Result getOrders(@RequestParam("oid") String oid){
        OrdersDto ordersDto = ordersService.getOrdersByOid(oid);
        return Result.success(ordersDto);
    }

    @PostMapping("/confirmOrders")
    public Result confirmOrders(Orders orders){
        ordersService.updateOrdersByOid(orders);
        return Result.success();
    }

    @PostMapping("/putCartOrders")
    public Result putCartOrders(){
        String oid = ordersService.putCartOrders();
        return Result.success(oid);
    }

    @GetMapping("getOrdersList")
    public Result getOrdersList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                @RequestParam(value = "pageSize",defaultValue = "12") Integer pageSize){
        PageBean<OrdersDto> pageBean = ordersService.getOrdersByUid(currentPage, pageSize);
        return Result.success(pageBean);
    }
}
