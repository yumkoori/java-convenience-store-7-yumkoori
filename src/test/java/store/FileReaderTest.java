package store;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderTest {
    @Test
    void 상품_파일을_읽으면_상품정보가_담긴_Map이_반환된다() {
        FileReader fileReader = new FileReader();
        Map<String, Product> products = fileReader.readProductsFile();

        Assertions.assertThat(products).isNotEmpty();
    }
}