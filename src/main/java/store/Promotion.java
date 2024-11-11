package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private LocalDate start_date;
    private LocalDate end_date;

    public Promotion(String name, int buy, int get, LocalDate start_date, LocalDate end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int calculateAdditionalQuantity(int purchaseQuantity) {
        int promotionGroup = buy + get;
        int rest = purchaseQuantity % promotionGroup;

        if (rest == buy) {
            return get;
        }

        return 0;
    }

    public int calculateNonPromotionItem(int promotionStock, int purchaseQuantity) {
        int inPromotion = promotionStock % (buy + get); //프로모션 재고 내에서 적용되지 않는 개수
        int inDefault = purchaseQuantity - promotionStock; //일반 재고내에서 적용되지 않는 개수

        return inPromotion + inDefault;
    }

    public int calculatePromotionNumber(int purchaseQuantity) {
        return purchaseQuantity / (this.buy + this.get);
    }

    public boolean checkPromotionDate() {
        LocalDate today = DateTimes.now().toLocalDate();
        return today.compareTo(start_date) >= 0 && today.compareTo(end_date) <= 0;
    }

    static public Map<String, Promotion> initPromotion() {
        Map<String, Promotion> promotion = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/promotions.md"));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                mapToPromotion(line, promotion);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return promotion;
    }

    private static void mapToPromotion(String line, Map<String, Promotion> promotion) {
        String[] splitLine = line.split(",");

        String name = splitLine[0];
        int buy = Integer.parseInt(splitLine[1]);
        int get = Integer.parseInt(splitLine[2]);
        LocalDate start_date = LocalDate.parse(splitLine[3]);
        LocalDate end_date = LocalDate.parse(splitLine[4]);

        promotion.put(name, new Promotion(name, buy, get, start_date, end_date));
    }

    public int getBuy() {
        return this.buy;
    }

    public int getGet() {
        return this.get;
    }
}