package store;

import java.util.Map;

public class Application {
    private static final Convenience convenience = new ConvenienceImpl();

    public static void main(String[] args) {
        try {
            while (true) {
                convenience.loadProduct();

                String inputItem = InputView.readItem();
                Map<String, Integer> purchaseItems = convenience.buy(inputItem);

                //영수증 뽑기
                convenience.getReceipt(purchaseItems);

                String answer = InputView.inputReStart();
                if (answer.equals("N")) {
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
