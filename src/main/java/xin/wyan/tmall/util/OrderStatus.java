package xin.wyan.tmall.util;

public enum  OrderStatus {
    waitPay("待付款"),waitDelivery("待发货"),waitConfirm("待收货"),waitReview("待评价"),finish("完成"),delete("删除");
    private String statusOfChinese;

    OrderStatus(String statusOfChinese) {
        this.statusOfChinese=statusOfChinese;
    }

    public String getStatusOfChinese() {
        return statusOfChinese;
    }

}
