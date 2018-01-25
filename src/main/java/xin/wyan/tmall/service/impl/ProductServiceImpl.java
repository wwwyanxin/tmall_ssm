package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.ProductMapper;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.pojo.ProductExample;
import xin.wyan.tmall.pojo.ProductImage;
import xin.wyan.tmall.service.*;
import xin.wyan.tmall.util.ImageType;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired(required = false)
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

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
        setFirstProductImage(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> result = productMapper.selectByExample(example);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product.getId());
        product.setSaleCount(saleCount);
        int reviewCount = reviewService.getCount(product.getId());
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    public void setFirstProductImage(Product product) {
        List<ProductImage> pis = productImageService.list(product.getId(), ImageType.single);
        if (!pis.isEmpty()) {
            ProductImage productImage = pis.get(0);
            product.setFirstProductImage(productImage);
        }
    }

    public void setFirstProductImage(List<Product> products) {
        for (Product product : products) {
            setFirstProductImage(product);
        }
    }

    private void setCategory(Product p) {
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }

    private void setCategory(List<Product> ps) {
        if (ps.isEmpty()) {
            return;
        }
        int cid = ps.get(0).getCid();
        Category c = categoryService.get(cid);
        for (Product p : ps) {
            p.setCategory(c);
        }
    }
}
