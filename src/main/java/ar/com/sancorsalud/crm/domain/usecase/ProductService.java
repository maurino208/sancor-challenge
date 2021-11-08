package ar.com.sancorsalud.crm.domain.usecase;

import ar.com.sancorsalud.crm.domain.model.Product;
import ar.com.sancorsalud.crm.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository repo;


    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public List<Product> findAll(){
        List<Product> products = repo.findAll();
        return products;
    }

    public Optional<Product> findProductById(Long id){
        return repo.findById(id);
    }

    public List<Product> findProductByCategory(String category){

        List<Product> products = repo.findByCategory(category);

        products.sort(Comparator.comparing(Product::getAvailability, Comparator.reverseOrder()).thenComparing(Product::getDiscountedPrice));

        return products;
    }

    public Optional<Product> updateProduct(Product newProduct, Long id){
        return repo.findById(id)
                    .map(product1 -> {
                        product1.setAvailability(newProduct.getAvailability());
                        product1.setRetailPrice(newProduct.getRetailPrice());
                        product1.setDiscountedPrice(newProduct.getDiscountedPrice());
                        return repo.save(product1);
                    });
    }

    @Async
    public void someLongOperation() {
        repo.findAll().forEach(p-> {
            log.info("Performing some long operation on productId={}", p.getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        });
    }

}
