package xin.wyan.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.wyan.tmall.pojo.Order;
import xin.wyan.tmall.service.OrderItemService;
import xin.wyan.tmall.service.OrderService;
import xin.wyan.tmall.util.OrderStatus;
import xin.wyan.tmall.util.Page;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @RequestMapping("admin_order_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> os = orderService.list();
        int total = (int) new PageInfo<Order>(os).getTotal();
        page.setTotal(total);
        orderItemService.fill(os);
        model.addAttribute("os", os);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }

    @RequestMapping("admin_order_delivery")
    public String delivery(Order order) {
        order.setDeliveryDate(new Date());
        order.setStatus(OrderStatus.waitConfirm.toString());
        orderService.update(order);
        return "redirect:admin_order_list";
    }
}
