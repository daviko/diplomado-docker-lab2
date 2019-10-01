package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import io.swagger.infrastructure.persistence.ProductRepository;
import io.swagger.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T03:43:35.949Z")

@Controller
public class ProductApiController implements ProductApi {

    private static final Logger log = LoggerFactory.getLogger(ProductApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> result = new ArrayList<>();

        productRepository.findAll().forEach(dbProduct -> {
            Product product = new Product();
            product.setProductId(dbProduct.getProductId());
            product.name(dbProduct.getName());
            product.description(dbProduct.getDescription());
            product.price(dbProduct.getPrice());
            result.add(product);
        });

        return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
    }

    public ResponseEntity<Product> addProduct(
        @ApiParam(value = "product object that needs to be added", required = true) @Valid @RequestBody Product body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            io.swagger.infrastructure.persistence.entities.Product dbProduct = new io.swagger.infrastructure.persistence.entities.Product();
            dbProduct.setName(body.getName());
            dbProduct.setDescription(body.getDescription());
            dbProduct.setPrice(body.getPrice());
            io.swagger.infrastructure.persistence.entities.Product persistedProduct = productRepository.save(dbProduct);
            body.setProductId(persistedProduct.getProductId());
            body.add(ControllerLinkBuilder.linkTo(ProductApi.class)
                .slash(Long.valueOf(persistedProduct.getProductId().longValue())).withSelfRel());
            return new ResponseEntity<Product>(body, HttpStatus.CREATED);
        }

        return new ResponseEntity<Product>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Boolean> deleteProduct(
        @ApiParam(value = "Product id to delete", required = true) @PathVariable("productId") Long productId) {
        if (productId != null) {
            productRepository.delete(productId);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Product>> findProductsByName(
        @NotNull @ApiParam(value = "part of the name to be considered for filtering",
            required = true) @Valid @RequestParam(value = "name", required = true) String name) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Product> result = new ArrayList<>();
            productRepository.findByName(name).forEach(dbProduct -> {
                Product product = new Product();
                product.setProductId(dbProduct.getProductId());
                product.name(dbProduct.getName());
                product.description(dbProduct.getDescription());
                product.price(dbProduct.getPrice());
                result.add(product);
            });
            return new ResponseEntity<List<Product>>(result, HttpStatus.OK);
        }

        return new ResponseEntity<List<Product>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Product> getProductById(
        @ApiParam(value = "ID of product to return", required = true) @PathVariable("productId") Long productId) {
        if (productId != null) {

            try {
                io.swagger.infrastructure.persistence.entities.Product dbProduct = productRepository.findOne(productId);
                Product product = new Product();
                product.setProductId(dbProduct.getProductId());
                product.name(dbProduct.getName());
                product.description(dbProduct.getDescription());
                product.price(dbProduct.getPrice());
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            } catch (Exception e) {
                log.error("ERROR retrieving the product from DB", e);
                return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Product> updateProduct(
        @ApiParam(value = "product object that needs to be added", required = true) @Valid @RequestBody Product body) {
        if (body != null && body.getProductId() != null) {
            io.swagger.infrastructure.persistence.entities.Product dbProduct = new io.swagger.infrastructure.persistence.entities.Product();
            dbProduct.setProductId(body.getProductId());
            dbProduct.setName(body.getName());
            dbProduct.setDescription(body.getDescription());
            dbProduct.setPrice(body.getPrice());

            io.swagger.infrastructure.persistence.entities.Product persistedProduct = productRepository.save(dbProduct);

            Product product = new Product();
            product.setProductId(persistedProduct.getProductId());
            product.name(persistedProduct.getName());
            product.description(persistedProduct.getDescription());
            product.price(persistedProduct.getPrice());
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }

        return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
