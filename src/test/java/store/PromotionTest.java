package store;

import static org.assertj.core.api.Assertions.*;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PromotionTest {
    @Test
    void 프로모션_파일을_읽어서_Promotion_객체로_매핑한다() {
        Map<String, Promotion> promotionMap = Promotion.initPromotion();

        assertThat(promotionMap.get("탄산2+1")).isNotNull();
    }

    @Test
    void 프로모션_증정_개수를_구할_수_있다() {
        Map<String, Promotion> promotionMap = Promotion.initPromotion();
        PromotionProduct promotionProduct = new PromotionProduct(10, "MD추천상품");

        int promotableQuantity = promotionProduct.calculatePromotableQuantity(promotionMap);

        assertThat(promotableQuantity).isEqualTo(5);
    }
}