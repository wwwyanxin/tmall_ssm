package xin.wyan.tmall.controller;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import xin.wyan.tmall.pojo.*;
import xin.wyan.tmall.service.*;
import xin.wyan.tmall.util.ImageType;
import xin.wyan.tmall.util.ProductComparator;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        model.addAttribute("cs", categories);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if (exist) {
            String msg = "用户名已经被使用，不能使用";
            model.addAttribute("msg", msg);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:/registerSuccessPage";
    }

    @RequestMapping("forelogin")
    public String login(Model model, HttpSession session, String name, String password) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);
        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product(Model model, int pid) {
        Product product = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(product.getId(), ImageType.single);
        List<ProductImage> productDetailImages = productImageService.list(product.getId(), ImageType.detail);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<PropertyValue> propertyValues = propertyValueService.list(product.getId());
        List<Review> reviews = reviewService.list(product.getId());
        productService.setSaleAndReviewNumber(product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", product);
        model.addAttribute("pvs", propertyValues);
        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);
        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    @RequestMapping("forecategory")
    public String category(Model model, int cid, String sort) {
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());

        if (null != sort) {
            Comparator<Product> comparator = ProductComparator.valueOf(sort);
            Collections.sort(category.getProducts(), comparator);
        }
        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(Model model, String keyword) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        productService.setSaleAndReviewNumber(products);
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        int oiId = 0;

        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId().equals(product.getId())) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                oiId = orderItem.getId();
                found = true;
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItemService.add(orderItem);
            oiId = orderItem.getId();
        }

        return "redirect:forebuy?oiid=" + oiId;
    }

    @RequestMapping("forebuy")
    public String buy(Model model, @RequestParam(value = "oiid") String[] oiIds, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strId : oiIds) {
            int oiId = Integer.parseInt(strId);
            OrderItem orderItem = orderItemService.get(oiId);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        session.setAttribute("ois", orderItems);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(Model model, HttpSession session, int pid, int num) {
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : ois) {
            if (orderItem.getProduct().getId().equals(product.getId())) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                break;
            }
        }

        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItemService.add(orderItem);
        }

        return "success";
    }

    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Model model, HttpSession session, int pid, int number) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem orderItem : ois) {
            if (orderItem.getProduct().getId().equals(pid)) {
                orderItem.setNumber(number);
                orderItemService.update(orderItem);
                break;
            }
        }

        return "success";
    }

    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(Model model, HttpSession session, @RequestParam(value = "oiid") int oiId) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return "fail";
        }
        orderItemService.delete(oiId);
        return "success";
    }
}
