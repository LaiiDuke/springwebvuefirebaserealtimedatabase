package com.hust.chartapp.web.rest;

import com.github.alperkurtul.firebaserealtimedatabase.bean.FirebaseSaveResponse;
import com.hust.chartapp.domain.Product;
import com.hust.chartapp.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FirebaseDbRestcontroller {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public Product read(@RequestParam String authKey, @RequestParam String firebaseId) {
        Product product = new Product();
        product.setFirebaseId(firebaseId);

        return productRepository.read(product);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Product> all() {
        return productRepository.getAll();
    }

    @RequestMapping(value = "/saveWithRandomId", method = RequestMethod.POST)
    public FirebaseSaveResponse saveWithRandomId(@RequestParam String authKey, @RequestBody Product requestBody) {
        //        requestBody.setFirebaseId();

        return productRepository.saveWithRandomId(requestBody);
    }

    @RequestMapping(value = "/saveWithSpecificId", method = RequestMethod.POST)
    public FirebaseSaveResponse saveWithSpecificId(
        @RequestParam String authKey,
        @RequestParam String firebaseId,
        @RequestBody Product requestBody
    ) {
        requestBody.setFirebaseId(firebaseId);

        return productRepository.saveWithSpecificId(requestBody);
    }
}
