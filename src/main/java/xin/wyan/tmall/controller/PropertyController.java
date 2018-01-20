package xin.wyan.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.Property;
import xin.wyan.tmall.service.CategoryService;
import xin.wyan.tmall.service.PropertyService;
import xin.wyan.tmall.util.Page;

import java.util.List;
@Controller
public class PropertyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_property_add")
    public String add(Model model, Property property) {
        propertyService.add(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("pt", property);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property property) {
        propertyService.update(property);
        return "redirect:admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_list")
    public String list(Model model, int cid, Page page) {
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(), page.getCount());

        List<Property> properties = propertyService.list(cid);

        int total = (int) new PageInfo<Property>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+cid);

        model.addAttribute("pts", properties);
        model.addAttribute("c", category);
        model.addAttribute("page", page);

        return "admin/listProperty";
    }
}
