package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.PropertyMapper;
import xin.wyan.tmall.pojo.Property;
import xin.wyan.tmall.pojo.PropertyExample;
import xin.wyan.tmall.service.PropertyService;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired(required = false)
    PropertyMapper propertyMapper;

    @Override
    public void add(Property property) {
        propertyMapper.insert(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int cid) {
        PropertyExample example = new PropertyExample();
        //设置where条件
        example.createCriteria().andCidEqualTo(cid);
        //设置排序
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
    }
}
