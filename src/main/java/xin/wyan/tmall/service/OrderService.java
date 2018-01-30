package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.Order;
import xin.wyan.tmall.pojo.OrderItem;
import xin.wyan.tmall.util.OrderStatus;

import java.util.List;

public interface OrderService {
    void add(Order order);

    float add(Order order, List<OrderItem> orderItems);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    List<Order> list();

    /**除了某状态的订单，例如排除已经删除的订单*/
    List<Order> list(int uid, OrderStatus excludedStatus);

    float getPriceTotal(int id);
}
