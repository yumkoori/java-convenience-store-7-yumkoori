package store;

import java.util.Map;

public class ConvenienceImpl implements Convenience {
    @Override
    public void loadProduct() {
        OutputView.printProducts();
    }

    @Override
    public Map<String, Integer> buy(String items) {
        InputValidator inputValidator = new InputValidator();
        Stock stock = new StockManager();
        Map<String, Integer> inputItem = inputValidator.validateItemInput(items);
        Map<String, Integer> purchaseItem = inputValidator.checkNeedAdditionalItemInput(inputItem);

        boolean isBuy = inputValidator.checkNonPromotionItems(purchaseItem);
        if (isBuy) {
            stock.reduceProduct(purchaseItem);
        }
        return purchaseItem;
    }

    @Override
    public void getReceipt(Map<String, Integer> purchaseItem) {
        Receipt receipt = new Receipt();
        String membership = InputView.inputMemberShip();

        Map<String, Integer> productTypeMoney = receipt.calculateProductTypeMoney(purchaseItem);
        Map<String, Integer> promotionNumber = receipt.getProductTypePromotionNumber(purchaseItem);
        int totalProductMoney = receipt.calculateTotalProductMoney(purchaseItem);
        int promotionDiscount = receipt.calculatePromotionDiscount(purchaseItem);
        int membershipDiscount = receipt.calculateMemberShipDiscount(purchaseItem, membership);
        int totalSendMoney = receipt.calculateTotalSendMoney(purchaseItem, membership);

        OutputView.printReceipt(purchaseItem, productTypeMoney, promotionNumber, totalProductMoney, promotionDiscount,
                membershipDiscount, totalSendMoney);
    }
}
