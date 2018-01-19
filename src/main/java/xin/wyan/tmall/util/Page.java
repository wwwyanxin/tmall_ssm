package xin.wyan.tmall.util;

public class Page {
    //开始页数
    private int start;
    //每页显示个数
    private int count;
    //总个数
    private int total;
    //参数
    private String param;
    //默认每页显示5条
    private final static int defaultCount=5;

    public Page() {
        count = defaultCount;
    }

    public Page(int start, int count) {
        this();
        this.start=start;
        this.count=count;
    }

    public int getStart() {
        return start;
    }

    public int getTotalPage() {
        return (0==(total%count))?(total/count):(total/count+1);
    }

    public int getLast() {
        int last;
        if (0 == total % count) {
            last = total - count;
        } else {
            last=total-total%count;
        }
        //如果total=0,last会计算为负值，应变为0
        last=last<0?0:last;

        return last;
    }

    public boolean isHasPrevious() {
        return 0==start?false:true;
    }

    public boolean isHasNext() {
        return start==getLast()?false:true;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPrevious()=" + isHasPrevious() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
