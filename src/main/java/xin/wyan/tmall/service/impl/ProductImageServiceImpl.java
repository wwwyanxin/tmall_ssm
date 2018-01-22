package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.ProductImageMapper;
import xin.wyan.tmall.pojo.ProductImage;
import xin.wyan.tmall.pojo.ProductImageExample;
import xin.wyan.tmall.service.ProductImageService;
import xin.wyan.tmall.util.ImageType;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired(required = false)
    ProductImageMapper productImageMapper;

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKeySelective(productImage);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int pid, ImageType imageType) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria().andPidEqualTo(pid).andTypeEqualTo(imageType.getType());
        example.setOrderByClause("id desc");
        return productImageMapper.selectByExample(example);
    }
}
