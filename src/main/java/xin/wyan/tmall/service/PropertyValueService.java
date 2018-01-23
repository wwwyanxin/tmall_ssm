package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.Product;
import xin.wyan.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void init(Product product);

    void update(PropertyValue propertyValue);

    PropertyValue get(int ptvId, int pid);

    List<PropertyValue> list(int pid);
}
