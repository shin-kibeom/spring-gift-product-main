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

    // 4. 상품 삭제 기능
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = products.get(id);
        if(product == null) {
            throw new NoSuchElementException("해당 ID의 상품을 찾을 수 없습니다: " + id);
        }
        products.remove(id);
        return "상품이 삭제됨";
    }

    // 5. 상품 수정 기능
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        Product existingProduct = products.get(id);
        if (existingProduct == null) {
            throw new NoSuchElementException("해당 ID의 상품을 찾을 수 없습니다: " + id);
        }
        if (updatedProduct.getName() != null && !updatedProduct.getName().isEmpty()) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getPrice() > 0) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getImageUrl() != null && !updatedProduct.getImageUrl().isEmpty()) {
            existingProduct.setImageUrl(updatedProduct.getImageUrl());
        }
        return existingProduct;
    }
}
