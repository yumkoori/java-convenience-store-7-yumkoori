package store;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    private final static Stock stock = new StockManager();

    static void printProducts() {
        Map<String, Product> products = stock.loadProductStocks();

        for (Product product : products.values()) {
            if (product.getPromotion() != null) {
                System.out.println("- " + product.getName() + " " + String.format("%,d", product.getPrice()) + "원 " + product.getPromotionStock() + "개 " + product.getPromotion());
            }
            if (product.getDefaultStock() <= 0) {
                System.out.println("- " + product.getName() + " " + String.format("%,d", product.getPrice()) + "원 "+ "재고 없음");
                continue;
            }
            System.out.println("- " + product.getName() + " " + String.format("%,d", product.getPrice()) + "원 "+ product.getDefaultStock() + "개 ");
        }
    }

    static void printReceipt(Map<String, Integer> purchaseProduct, Map<String, Integer> productTypeMoney, Map<String, Integer> promotionNumber, int totalPurchaseMoney, int promotionDiscount, int membershipDiscount, int totalSendMoney) {
        int totalPurchaseNumber = 0;
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        for (Entry<String, Integer> purchaseItem : purchaseProduct.entrySet()) {
            System.out.println(purchaseItem.getKey() + "\t\t" + purchaseItem.getValue() + "\t" + productTypeMoney.get(purchaseItem.getKey()));
            totalPurchaseNumber += purchaseItem.getValue();
        }
        System.out.println("=============증\t정===============");
        for (Entry<String, Integer> promotionItem : promotionNumber.entrySet()) {
            if (promotionItem.getValue() > 0) {
                System.out.println(promotionItem.getKey() + "\t\t" + promotionItem.getValue());
            }
        }
        System.out.println("====================================");
        System.out.println("총구매액\t\t" +  totalPurchaseNumber + "\t" + totalPurchaseMoney);
        System.out.println("행사할인\t\t\t" + promotionDiscount);
        System.out.println("멤버십할인\t\t\t" + membershipDiscount);
        System.out.println("내실돈\t\t\t " + String.format("%,d", totalSendMoney));
    }
}
