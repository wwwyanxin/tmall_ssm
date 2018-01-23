package xin.wyan.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wyan.tmall.mapper.PropertyValueMapper;
import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.pojo.Property;
import xin.wyan.tmall.pojo.PropertyValue;
import xin.wyan.tmall.pojo.PropertyValueExample;
import xin.wyan.tmall.service.PropertyService;
import xin.wyan.tmall.service.PropertyValueService;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired(required = false)
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> pts = propertyService.list(product.getCid());
        for (Property pt : pts) {
            PropertyValue ptv = get(pt.getId(), product.getId());
            if (null == ptv) {
                ptv = new PropertyValue();
                ptv.setPid(product.getId());
                ptv.setPtid(pt.getId());
                propertyValueMapper.insert(ptv);
            }
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int ptId, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptId).andPidEqualTo(pid);
        List<PropertyValue> ptvs = propertyValueMapper.selectByExample(example);
        if (ptvs.isEmpty()) {
            return null;
        }
        PropertyValue ptv = ptvs.get(0);
        Property property = propertyService.get(ptv.getPtid());
        ptv.setProperty(property);
        return ptv;
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        for (PropertyValue propertyValue : result) {
            Property property = propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }
        return result;
    }
}
