package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.OrderItemMapper;
import xin.wyan.tmall.pojo.Order;
import xin.wyan.tmall.pojo.OrderItem;
import xin.wyan.tmall.pojo.OrderItemExample;
import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.service.OrderItemService;
import xin.wyan.tmall.service.ProductService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired(required = false)
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;

    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem result = orderItemMapper.selectByPrimaryKey(id);
        setProduct(result);
        return result;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        //setProduct(orderItems);
        return orderItems;
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total=0;
        int totalNumber=0;
        for (OrderItem orderItem : ois) {
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            totalNumber += orderItem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(ois);
    }

    public void setProduct(List<OrderItem> ois) {
        for (OrderItem orderItem : ois) {
            setProduct(orderItem);
        }
    }

    public void setProduct(OrderItem orderItem) {
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }
}
