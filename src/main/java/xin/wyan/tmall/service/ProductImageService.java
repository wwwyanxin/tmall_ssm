package xin.wyan.tmall.service;

import xin.wyan.tmall.pojo.ProductImage;
import xin.wyan.tmall.util.ImageType;

import java.util.List;

public interface ProductImageService {
    void add(ProductImage productImage);

    void delete(int id);

    void update(ProductImage productImage);

    ProductImage get(int id);

    List list(int pid, ImageType imageType);
}
