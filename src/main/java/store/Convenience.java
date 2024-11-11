package store;

import java.util.Map;

public interface Convenience {
    void loadProduct();

    Map<String, Integer> buy(String items);

    void getReceipt(Map<String, Integer> purchaseItems);
}
