package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    void update(Product product);

    Product get(int id);

    List<Product> list(int cid);

    List<Product> search(String keyword);

    /**
     * 为分类填充产品集合
     * @param categories
     */
    void fill(List<Category> categories);

    /**
     * 为多个分类填充产品集合
     * @param category
     */
    void fill(Category category);

    /**
     * 为多个分类填充推荐产品集合，即把分类下的产品集合，按照8个为一行，拆成多行，以利于后续页面上进行显示
     * @param categories
     */
    void fillByRow(List<Category> categories);

    void setSaleAndReviewNumber(Product product);

    void setSaleAndReviewNumber(List<Product> products);

}
