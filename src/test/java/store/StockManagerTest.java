package store;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StockManagerTest {
    private Stock stock = new StockManager();

    @Test
    void 상품명_가격_프로모션_이름_재고_개수_조회() {
        Map<String, Product> stocks = stock.loadProductStocks();

        Product product = stocks.getOrDefault("콜라", null);

        Assertions.assertThat(product).isNotNull();             //테스트 수정 필요
    }

    @Test
    void 구입_물건이_주어지면_재고_수량이_감소한다() {
        //구입 물건
        Map<String, Integer> products = Map.of("콜라", 15);
        //재고 반영
        stock.reduceProduct(products);

        Map<String, Product> stocks = stock.loadProductStocks();

        System.out.println(stocks);
    }

    @Test
    void 증정해야하는_상품_개수보다_적은_개수가_입력되면_필요한_추가_개수를_반환한다() {
        int additionalQuantity = stock.checkNeedAdditionalQuantity(10, 8);

        Assertions.assertThat(additionalQuantity).isEqualTo(2);
    }
}