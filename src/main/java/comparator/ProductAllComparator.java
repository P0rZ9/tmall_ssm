package comparator;

import cn.test.tmall.pojo.Product;

import java.util.Comparator;

public class ProductAllComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSale_count()*o2.getReview_count() - o1.getSale_count()*o2.getReview_count();
    }
}
