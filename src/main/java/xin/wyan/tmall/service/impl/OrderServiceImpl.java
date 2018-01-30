package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.OrderMapper;
import xin.wyan.tmall.pojo.Order;
import xin.wyan.tmall.pojo.OrderExample;
import xin.wyan.tmall.pojo.OrderItem;
import xin.wyan.tmall.pojo.User;
import xin.wyan.tmall.service.OrderItemService;
import xin.wyan.tmall.service.OrderService;
import xin.wyan.tmall.service.UserService;
import xin.wyan.tmall.util.OrderStatus;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired(required = false)
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        add(order);
        /*
        模拟当增加订单后出现异常，观察事务管理是否预期发生。
        （需要把false修改为true才能观察到）
        */
        if (false) {
            throw new RuntimeException();
        }
        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        setOrderStatus(order);
        return order;
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);
        setUser(result);
        setOrderStatus(result);
        return result;
    }

    @Override
    public List<Order> list(int uid, OrderStatus excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus.name());
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    @Override
    public float getPriceTotal(int id) {
        float priceTotal=0;
        List<OrderItem> orderItems = orderItemService.listByOrder(id);
        for (OrderItem orderItem : orderItems) {
            priceTotal += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        return priceTotal;
    }

    private void setUser(List<Order> orders) {
        for (Order order : orders) {
            setUser(order);
        }
    }

    private void setUser(Order order) {
        Integer uid = order.getUid();
        User user = userService.get(order.getUid());
        order.setUser(user);
    }

    private void setOrderStatus(List<Order> orders) {
        for (Order order : orders) {
            setOrderStatus(order);
        }
    }

    private void setOrderStatus(Order order) {
        OrderStatus orderStatus = OrderStatus.valueOf(order.getStatus());
        order.setOrderStatus(orderStatus);
    }
}
