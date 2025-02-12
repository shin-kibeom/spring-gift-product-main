package gift.Controller;

import gift.Model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final Map<Long, Product> products = new HashMap<>();

    private long nextId = 1L; // 자동 증가 ID

    // 1. 상품 목록 조회
    @GetMapping
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    // 2. 상품 추가
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        product.setId(nextId++);
        products.put(product.getId(), product);
        return product;
    }

    // 3. 특정 ID의 상품 조회
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        Product product = products.get(id);
        if(product == null) {
            throw new NoSuchElementException("해당 ID의 상품을 찾을 수 없습니다: " + id);
        }

        return product;
    }



}
