package store;

import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void 구입_개수를_입력하면_해당_상품의_프로모션에_따라_추가로_받을수_있는_프로모션_상품개수를_반환받는다() {
        Product product = new Product("콜라", 1000, 5, 10, "MD추천상품");

        int needAdditionalQuantity = product.isNeedAdditionalQuantity(11);

        System.out.println(needAdditionalQuantity);
    }
}