package store;

import java.util.Map;

public class Product {
    private String name;
    private int price;
    private int defaultStock;
    private int promotionStock;
    private String promotion;

    public Product(String name, int price, int defaultStock, int promotionStock, String promotion) {
        this.name = name;
        this.price = price;
        this.defaultStock = defaultStock;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

    public Product(String name, int price, int defaultStock) {
        this.name = name;
        this.price = price;
        this.defaultStock = defaultStock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getDefaultStock() {
        return defaultStock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setRegularStock(int quantity) {
        this.defaultStock = quantity;
    }

    public void setPromotionStock(int quantity) {
        this.promotionStock = quantity;
    }

    public int isNeedAdditionalQuantity(int purchaseQuantity) {
        Map<String, Promotion> promotions = Promotion.initPromotion();
        Promotion promotion = promotions.get(this.promotion);

        if (promotionStock < purchaseQuantity) {
            return 0;
        }

        return promotion.calculateAdditionalQuantity(purchaseQuantity);
    }

    public int getNonPromotionQuantity(int purchaseQuantity) {
        Map<String, Promotion> promotions = Promotion.initPromotion();
        Promotion promotion = promotions.getOrDefault(this.promotion, null);

        if (promotionStock < purchaseQuantity) {
            return promotion.calculateNonPromotionItem(promotionStock, purchaseQuantity);
        }
        return 0;
    }


    void withdraw(int purchaseQuantity) {
        if (promotionStock < purchaseQuantity) {
            //프로모션 재고가 부족한 경우
            purchaseQuantity -= promotionStock;
            promotionStock = 0;
            this.defaultStock -= purchaseQuantity;
            return;
        }
        this.promotionStock -= purchaseQuantity;
    }

    public boolean checkPromotionDate() {
        Map<String, Promotion> promotionMap = Promotion.initPromotion();
        if (promotion != null) {
            Promotion promotion = promotionMap.get(this.promotion);
            return promotion.checkPromotionDate();
        }
        return true;
    }

    //총 증정개수
    public int getPromotionNumber(int purchaseQuantity) {
        Map<String, Promotion> promotionMap = Promotion.initPromotion();
        Promotion promotion = promotionMap.getOrDefault(this.promotion, null);

        if (promotion != null) {
            return promotion.calculatePromotionNumber(purchaseQuantity);
        }
        return 0;
    }
}



