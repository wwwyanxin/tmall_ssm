package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.Order;
import xin.wyan.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    void add(OrderItem orderItem);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> orders);

    void fill(Order order);

    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);

    List<OrderItem> listByOrder(int oid);
}
