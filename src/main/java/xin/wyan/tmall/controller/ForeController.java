package xin.wyan.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;
import xin.wyan.tmall.pojo.*;
import xin.wyan.tmall.service.*;
import xin.wyan.tmall.util.ImageType;

import javax.servlet.http.HttpSession;
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
        boolean exist=userService.isExist(name);
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
        model.addAttribute("ptvs", propertyValues);
        return "fore/product";
    }
}
