package store;

import static org.assertj.core.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReceiptTest {
    @Test
    void 구매내역으로_Key는_상품명_value는_상품별_총가격을_반환한다() {
        Receipt receipt = new Receipt();
        Map<String, Integer> purchasedItem = Map.of("콜라", 3);

        Map<String, Integer> productTypeMoney = receipt.calculateProductTypeMoney(purchasedItem);

        assertThat(productTypeMoney.get("콜라")).isEqualTo(3000);
    }

    @Test
    void 구매내역으로_Key는_상품명_value는_증정개수를_반환한다() {
        Receipt receipt = new Receipt();
        Map<String, Integer> purchasedItem = Map.of("콜라", 6);

        Map<String, Integer> productTypePromotionNumber = receipt.getProductTypePromotionNumber(purchasedItem);

        assertThat(productTypePromotionNumber.get("콜라")).isEqualTo(2);
    }

    @Test
    void 구매내역으로_모든상품의_총구매액을_구할수_있다() {
        Receipt receipt = new Receipt();
        Map<String, Integer> purchasedItem = Map.of("콜라", 6);

        int totalProductMoney = receipt.calculateTotalProductMoney(purchasedItem);

        assertThat(totalProductMoney).isEqualTo(6000);
    }

    @Test
    void 구매내역으로_행사할인_가격을_알수있다() {
        Receipt receipt = new Receipt();
        Map<String, Integer> purchasedItem = new HashMap<>();
        purchasedItem.put("콜라", 3);
        purchasedItem.put("에너지바", 5);

        int promotionDiscount = receipt.calculatePromotionDiscount(purchasedItem);

        assertThat(promotionDiscount).isEqualTo(-1000);
    }

    @Test
    void 구매_내역으로_멤버십_할인_가격을_알수있다() {
        Receipt receipt = new Receipt();
        Map<String, Integer> purchasedItem = new HashMap<>();
        purchasedItem.put("콜라", 3);
        purchasedItem.put("에너지바", 5);

        int membershipDiscount = receipt.calculateMemberShipDiscount(purchasedItem, "Y");
        assertThat(membershipDiscount).isEqualTo(-3000);
    }

    @Test
    void 구매_내역으로_총_내야하는_가격을_알수있다() {
        Receipt receipt = new Receipt();
        Map<String, Integer> purchasedItem = new HashMap<>();
        purchasedItem.put("콜라", 3);
        purchasedItem.put("에너지바", 5);

        int totalSendMoney = receipt.calculateTotalSendMoney(purchasedItem, "Y");

        assertThat(totalSendMoney).isEqualTo(9000);
    }
}