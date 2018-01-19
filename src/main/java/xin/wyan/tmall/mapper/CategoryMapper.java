package xin.wyan.tmall.mapper;

import org.springframework.stereotype.Repository;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.util.Page;

import java.util.List;

public interface CategoryMapper {
    public List<Category> list();

    public List<Category> list(Page page);

    public int total();

    public void add(Category category);

    public void delete(int id);

    public Category get(int id);

    public void update(Category category);
}
