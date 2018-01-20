package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.ProductMapper;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.pojo.ProductExample;
import xin.wyan.tmall.service.CategoryService;
import xin.wyan.tmall.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired(required = false)
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product get(int id) {
        Product product= productMapper.selectByPrimaryKey(id);
        setCategory(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> result = productMapper.selectByExample(example);
        setCategory(result);
        return result;
    }

    private void setCategory(Product p) {
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }

    private void setCategory(List<Product> ps) {
        int cid = ps.get(0).getCid();
        Category c = categoryService.get(cid);
        for (Product p : ps) {
            p.setCategory(c);
        }
    }
}
