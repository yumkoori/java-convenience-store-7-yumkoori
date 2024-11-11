package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileReader {
    public Map<String, Product> readProductsFile() {
        Map<String, Product> productStocks = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader("src/main/resources/products.md"))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                processLine(line, productStocks);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return productStocks;
    }

    private void processLine(String line, Map<String, Product> productStocks) {
        String[] splitLine = line.split(",");
        String name = splitLine[0];
        int price = Integer.parseInt(splitLine[1]);
        int quantity = Integer.parseInt(splitLine[2]);
        String promotionType = splitLine[3];

        updateOrAddProduct(productStocks, name, price, quantity, promotionType);
    }

    private void updateOrAddProduct(Map<String, Product> productStocks, String name, int price, int quantity, String promotionType) {
        if (productStocks.containsKey(name)) {
            updateExistingProduct(productStocks.get(name), quantity, promotionType);
            return;
        }
        addNewProduct(productStocks, name, price, quantity, promotionType);
    }

    private void updateExistingProduct(Product existingProduct, int quantity, String promotionType) {
        if (promotionType == null || promotionType.equals("null")) {
            existingProduct.setRegularStock(quantity);
            return;
        }
        existingProduct.setPromotionStock(quantity);
    }

    private void addNewProduct(Map<String, Product> productStocks, String name, int price, int quantity, String promotionType) {
        if (promotionType == null || promotionType.equals("null")) {
            productStocks.put(name, new Product(name, price, quantity));
            return;
        }
        productStocks.put(name, new Product(name, price, 0, quantity, promotionType));
    }

}