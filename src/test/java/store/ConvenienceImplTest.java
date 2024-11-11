package store;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ConvenienceImplTest {
    @Test
    void 구입한_상품을_전달하면_영수증을_보여준다() {
        Convenience convenience = new ConvenienceImpl();
        Map<String, Integer> purchaseItems = new LinkedHashMap<>();
        purchaseItems.put("콜라", 3);
        purchaseItems.put("에너지바", 5);

        convenience.getReceipt(purchaseItems);
    }
}