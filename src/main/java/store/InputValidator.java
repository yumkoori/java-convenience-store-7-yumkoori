package store;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class InputValidator {
    private static final Stock stock = new StockManager();

    public static Map<String, Integer> validateItemInput(String itemInput) {
        Map<String, Integer> validatedItems = new LinkedHashMap<>();

        String[] splitItems = itemInput.split(",");
        for (String splitItem : splitItems) {
            String[] splitPart = splitItem.replace("[", "").replace("]", "").split("-");
            String itemName = splitPart[0];
            int itemQuantity = validateItemQuantity(splitPart[1]);
            checkItemAvailability(itemName, itemQuantity);
            validatedItems.put(itemName, itemQuantity);
        }
        return validatedItems;
    }

    public static void checkItemAvailability(String itemName, int itemQuantity) {
        Map<String, Product> itemStocks = stock.loadProductStocks();

        if (!itemStocks.containsKey(itemName)) {        //stock에서 검사 할까?
            throw new IllegalArgumentException("[ERROR] 해당 상품은 존재하지 않습니다.");
        }
        Product product = itemStocks.get(itemName);
        int totalStock = product.getPromotionStock() + product.getDefaultStock();
        if (totalStock < itemQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public Map<String, Integer> checkNeedAdditionalItemInput(Map<String, Integer> items) {
        Map<String, Product> stocks = stock.loadProductStocks();

        for (Entry<String, Integer> item : items.entrySet()) {
            Product product = stocks.get(item.getKey());
            int needAdditionalQuantity = product.isNeedAdditionalQuantity(item.getValue());

            String answer = "";
            if (needAdditionalQuantity > 0) {
                answer = InputView.inputAddItem(item.getKey(), needAdditionalQuantity);
            }
            if (answer.equals("Y")) {
                items.put(item.getKey(), item.getValue() + needAdditionalQuantity);
            }
        }
        return items;
    }

    public boolean checkNonPromotionItems(Map<String, Integer> items) {
        int nonPromotionQuantity = 0;
        Map<String, Product> stocks = stock.loadProductStocks();
        for (Entry<String, Integer> item : items.entrySet()) {
            Product product = stocks.get(item.getKey());
            if (product.getPromotion() != null) {
                nonPromotionQuantity = product.getNonPromotionQuantity(item.getValue());
            }
            String answer = "";
            if (nonPromotionQuantity > 0) {
                answer = InputView.inputNonPromotion(item.getKey(), nonPromotionQuantity);
            }
            if (answer.equals("No")) {
                return false;
            }
        }
        return true;
    }

    private static int validateItemQuantity(String quantity) {
        int parseQuantity = Integer.parseInt(quantity);
        if (parseQuantity < 0) {
            throw new IllegalArgumentException("[ERROR] 구매 수량은 1개 이상이어야 합니다.");
        }
        return parseQuantity;
    }
}
