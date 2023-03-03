package com.example.clema.yourApp.controller;

import com.example.clema.yourApp.exception.ProductNotFound;
import com.example.clema.yourApp.model.ProductDetail;
import com.example.clema.yourApp.service.SimilarProductsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/product")
public class SimilarProductsDetailController {

    @Autowired
    private SimilarProductsDetailService similarProductsDetailService;

    /**
     * Gets a list of similar products to provided product id
     * @param productId product id
     * @return similar detailed products
     */
    @GetMapping(path = "/{productId}/similar")
    public ResponseEntity<Flux<ProductDetail>> getSimilarProducts(@PathVariable("productId") String productId) {
        List<ProductDetail> similarProducts = null;
        try {
            var flux = similarProductsDetailService.getProductDetailList(productId);
            return new ResponseEntity<Flux<ProductDetail>>(flux, HttpStatus.OK);
        } catch (ProductNotFound e) {
            return new ResponseEntity<Flux<ProductDetail>>(HttpStatus.NOT_FOUND);
        }

    }



}
