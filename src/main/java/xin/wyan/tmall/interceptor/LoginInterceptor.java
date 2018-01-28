package xin.wyan.tmall.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import xin.wyan.tmall.pojo.User;
import xin.wyan.tmall.service.CategoryService;
import xin.wyan.tmall.service.OrderItemService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    private Set<String> noNeedAuthPage;

    public LoginInterceptor() {
        super();
        String[] pages = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
        noNeedAuthPage = new HashSet<>();
        noNeedAuthPage.addAll(Arrays.asList(pages));
    }

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        String path = request.getServletPath();
        String methodName = path.replaceFirst("/fore", "");
        if (!noNeedAuthPage.contains(methodName)) {
            User user = (User) session.getAttribute("user");
            if (null == user) {
                response.sendRedirect("loginPage");
                return false;
            }
        }
        return true;
    }
}
