package store;

import java.util.List;
import java.util.Map;

public interface Stock {
    Map<String, Product> loadProductStocks();             //initStock 이 필요할듯.

    void reduceProduct(Map<String, Integer> products);

    int checkNeedAdditionalQuantity(int totalWithdrawQuantity, int quantity);
}
