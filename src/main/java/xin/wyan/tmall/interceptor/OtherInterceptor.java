package xin.wyan.tmall.interceptor;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.OrderItem;
import xin.wyan.tmall.pojo.User;
import xin.wyan.tmall.service.CategoryService;
import xin.wyan.tmall.service.OrderItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //cs将用于每个页面搜索框下的推荐，4个就够了
        List<Category> cs = (List<Category>) request.getAttribute("cs");
        if (null == cs) {
            PageHelper.offsetPage(0, 4);
            cs = categoryService.list();
            request.setAttribute("cs", cs);
        }

        /*这里是获取购物车中一共有多少数量*/
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if (null != user) {
            List<OrderItem> ois = orderItemService.listByUser(user.getId());
            for (OrderItem orderItem : ois) {
                cartTotalItemNumber += orderItem.getNumber();
            }
        }
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }

}
