package xin.wyan.tmall.util;


import xin.wyan.tmall.pojo.Product;

import java.util.Comparator;

public enum  ProductComparator implements Comparator<Product> {
    //all排序，根据评论数+销量，从高到低排序
    all{
        @Override
        public int compare(Product p1, Product p2) {
            return (p2.getReviewCount() + p2.getSaleCount()) - (p1.getReviewCount() + p1.getSaleCount());
        }
    },
    //date排序，根据上新时间排序
    date{
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getCreateDate().compareTo(p2.getCreateDate());
        }
    },
    //price排序，根据价格，从低到高排序
    price{
        @Override
        public int compare(Product p1, Product p2) {
            return (int) (p1.getPromotePrice() - p2.getPromotePrice());
        }
    },
    //review排序，根据评论数量，从高到低排序
    review{
        @Override
        public int compare(Product p1, Product p2) {
            return p2.getReviewCount() - p1.getReviewCount();
        }
    },
    //saleCount排序，根据销量，从高到低排序
    saleCount{
        @Override
        public int compare(Product p1, Product p2) {
            return p2.getSaleCount() - p1.getSaleCount();
        }
    };
}
