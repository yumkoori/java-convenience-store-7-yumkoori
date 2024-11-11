package store;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Receipt {
    public Map<String, Integer> calculateProductTypeMoney(Map<String, Integer> purchasedItems) {
        Stock stock = new StockManager();
        Map<String, Product> stocks = stock.loadProductStocks();
        Map<String, Integer> productTypeMoney = new LinkedHashMap<>();      //상품명 / 각 상품별 총액수

        for (Entry<String, Integer> purchaseItem : purchasedItems.entrySet()) {
            Product product = stocks.get(purchaseItem.getKey());
            int itemMoney = product.getPrice() * purchaseItem.getValue();
            productTypeMoney.put(purchaseItem.getKey(), itemMoney);
        }
        return productTypeMoney;
    }

    //kety= 상품 value =증정개수
    public Map<String, Integer> getProductTypePromotionNumber(Map<String, Integer> purchasedItems) {
        Stock stock = new StockManager();
        Map<String, Product> stocks = stock.loadProductStocks();
        Map<String, Integer> productTypePromotionNumber = new LinkedHashMap<>();

        for (Entry<String, Integer> purchasedItem : purchasedItems.entrySet()) {
            Product product = stocks.get(purchasedItem.getKey());
            int promotionNumber = product.getPromotionNumber(purchasedItem.getValue());
            productTypePromotionNumber.put(purchasedItem.getKey(), promotionNumber);        //상품별 증정개수
        }
        return productTypePromotionNumber;
    }

    //행사 할인 금액
    public int calculatePromotionDiscount(Map<String, Integer> purchaseItems) {
        Map<String, Integer> productTypePromotionNumber = getProductTypePromotionNumber(purchaseItems);
        Stock stock = new StockManager();
        Map<String, Product> stocks = stock.loadProductStocks();

        int promotionDiscount = 0;
        for (Entry<String, Integer> promotionNumber : productTypePromotionNumber.entrySet()) {
            Product product = stocks.get(promotionNumber.getKey());
            if (product.checkPromotionDate()) {
                promotionDiscount += product.getPrice() * promotionNumber.getValue() * -1;
            }
        }
        return promotionDiscount;
    }

    public int calculateMemberShipDiscount(Map<String, Integer> purchasedItem, String membershipAnswer) {
        if (membershipAnswer.equals("N")) {
            return 0;
        }
        Stock stock = new StockManager();
        Map<String, Product> stocks = stock.loadProductStocks();
        int nonPromotionMoney = 0;      //프로모션 상품을 제외한 일반 상품의 가격

        for (Entry<String, Integer> purchaseItem : purchasedItem.entrySet()) {
            Product product = stocks.get(purchaseItem.getKey());
            if (product.getPromotion() != null) {
                nonPromotionMoney += product.getNonPromotionQuantity(purchaseItem.getValue()) * product.getPrice();
            }
            if (product.getPromotion() == null) {
                nonPromotionMoney += product.getDefaultStock() * product.getPrice();
            }
        }
        return (int) (nonPromotionMoney * 0.3) * -1;
    }

    //최종 내실돈
    public int calculateTotalSendMoney(Map<String, Integer> purchasedItem, String membership) {
        int totalProductMoney = calculateTotalProductMoney(purchasedItem);
        int promotionDiscount = calculatePromotionDiscount(purchasedItem);
        int membershipDiscount = calculateMemberShipDiscount(purchasedItem, membership);

        //총 구매액 + 프로모션 할인 가격 + 멤버십 할인 가격
        return totalProductMoney + promotionDiscount + membershipDiscount;
    }

    //모든 상품의 총 금액
    public int calculateTotalProductMoney(Map<String, Integer> purchasedItems) {
        Map<String, Integer> productTypeMoney = calculateProductTypeMoney(purchasedItems);
        int totalProductMoney = 0;
        for (Integer value : productTypeMoney.values()) {
            totalProductMoney += value;
        }
        return totalProductMoney;
    }
}

