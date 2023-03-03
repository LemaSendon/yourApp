package com.example.clema.yourApp.service;

import com.example.clema.yourApp.client.MocksClient;
import com.example.clema.yourApp.exception.ProductNotFound;
import com.example.clema.yourApp.model.ProductDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SimilarProductsDetailService {

    private Logger logger = LoggerFactory.getLogger(SimilarProductsDetailService.class);

    @Autowired
    private MocksClient mocksClient;

    /**
     * Gets all products that are similar to provided id
     * @param productId product id
     * @return similar products
     * @throws ProductNotFound if product isn't found
     */
    public Flux<ProductDetail> getProductDetailList(String productId) throws ProductNotFound {
        try {
            var similarProductIds = mocksClient.getSimilarIdProducts(productId);
            return similarProductIds.flatMap(this::getProductDetail);
        } catch (ProductNotFound e) {
            logger.warn(e.getMessage());
            throw e;
        }

    }

    /**
     * Method that calls API Mocks in order to get the product details
     *
     * @param id similar product id
     * @return product details if found
     */
    private Mono<ProductDetail> getProductDetail(String id) {
        try {
            return mocksClient.getProductDetail(id);
        } catch (WebClientException e){
            logger.error("Error when getting details from product {}", id, e);
            return Mono.empty();
        }
    }



}
