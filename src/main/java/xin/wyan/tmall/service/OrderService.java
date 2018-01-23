package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.Order;

import java.util.List;

public interface OrderService {
    void add(Order order);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    List<Order> list();
}
