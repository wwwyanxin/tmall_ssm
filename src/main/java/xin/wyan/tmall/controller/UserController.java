package xin.wyan.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.wyan.tmall.pojo.User;
import xin.wyan.tmall.service.UserService;
import xin.wyan.tmall.util.Page;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> users = userService.list();

        int total = (int) new PageInfo<User>(users).getTotal();
        page.setTotal(total);

        model.addAttribute("us", users);
        model.addAttribute("page", page);

        return "admin/listUser";
    }
}
