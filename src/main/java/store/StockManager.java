package store;

import java.util.Map;
import java.util.Map.Entry;

public class StockManager implements Stock {
    private Map<String, Product> stocks = new FileReader().readProductsFile();

    @Override
    public Map<String, Product> loadProductStocks() {
        if (stocks.isEmpty()) {
            stocks = new FileReader().readProductsFile();
        }
        return stocks;
    }

    @Override
    public void reduceProduct(Map<String, Integer> purchaseProducts) {
        for (Entry<String, Integer> purchaseProduct : purchaseProducts.entrySet()) {
            Product product = stocks.get(purchaseProduct.getKey());
            //해당 상품이 프로모션 기간이 맞는지 체크
            product.checkPromotionDate();         
            //최종 증정 개수 출하
            product.withdraw(purchaseProduct.getValue());
        }
    }

    public int checkNeedAdditionalQuantity(int totalWithdrawQuantity, int quantity) {
        if (totalWithdrawQuantity > quantity) {
            return totalWithdrawQuantity - quantity;
        }
        return 0;
    }
}
