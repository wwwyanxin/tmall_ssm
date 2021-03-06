package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.CategoryMapper;
import xin.wyan.tmall.pojo.Category;
import xin.wyan.tmall.pojo.CategoryExample;
import xin.wyan.tmall.service.CategoryService;
import xin.wyan.tmall.util.Page;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired(required = false)
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
//        return categoryMapper.list();
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }

    /*@Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }
*/
    @Override
    public void add(Category category) {
//        categoryMapper.add(category);
        categoryMapper.insert(category);
    }

    @Override
    public void delete(int id) {
//        categoryMapper.delete(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
//        return categoryMapper.get(id);
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
//        categoryMapper.update(category);
        categoryMapper.updateByPrimaryKey(category);
    }
}
