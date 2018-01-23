package xin.wyan.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.pojo.PropertyValue;
import xin.wyan.tmall.service.ProductService;
import xin.wyan.tmall.service.PropertyValueService;

import java.util.List;

@Controller
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid) {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> ptvs = propertyValueService.list(pid);

        model.addAttribute("p", product);
        model.addAttribute("ptvs", ptvs);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }
}
