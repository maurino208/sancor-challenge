package ar.com.sancorsalud.crm.controller;

import ar.com.sancorsalud.crm.domain.model.Product;
import ar.com.sancorsalud.crm.domain.usecase.ProductService;
import ar.com.sancorsalud.crm.exception.ProductNotFoundException;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsController {

    private final ProductService service;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product){
        Optional<Product> idChecker = service.findProductById(product.getId());
        if(idChecker.isPresent()){
            return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
        }else {
            service.addProduct(product);
            return new ResponseEntity<Product>(HttpStatus.CREATED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getById(@PathVariable Long id){
        Optional<Product> product = service.findProductById(id);
        if(!product.isPresent()){
            throw new ProductNotFoundException("No se ha encontrado el producto");
        }else{
            return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
        }

    }

    @GetMapping("/cat/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category){
        return new ResponseEntity<List<Product>>(service.findProductByCategory(category), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<List<Product>>(service.findAll(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Product>> update(@RequestBody Product prod, @PathVariable Long id){
        Optional<Product> productToUpd = service.findProductById(id);
        if(!productToUpd.isPresent()){
            return new ResponseEntity<Optional<Product>>(HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<Optional<Product>>(service.updateProduct(prod, id), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/job")
    public ResponseEntity<String> asyncMethod(){
        service.someLongOperation();
        return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
    }

}
