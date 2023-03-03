package com.example.clema.yourApp.client;

import com.example.clema.yourApp.config.ConfigurationHolder;
import com.example.clema.yourApp.exception.ProductNotFound;
import com.example.clema.yourApp.model.ProductDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MocksClient {

    private Logger logger = LoggerFactory.getLogger(MocksClient.class);

    @Autowired
    private ConfigurationHolder configurationHolder;


    private WebClient getClient() {
        return WebClient.builder()//
                .baseUrl(configurationHolder.getClientMocksBaseUrl())//
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    /**
     * Get ids of similar products to provided product id
     * @param productId product id
     * @return similar products id or empty list if server error
     * @throws ProductNotFound if product not found
     */
    public Flux<String> getSimilarIdProducts(String productId) throws ProductNotFound {
        return getClient().get().uri("/product/" + productId + "/similarids").exchangeToFlux(response -> {
            if (HttpStatus.OK.equals(response.statusCode())) {
                return response.bodyToFlux(String.class);
            } else if (HttpStatus.NOT_FOUND.equals(response.statusCode())) {
                return Flux.error(new ProductNotFound(productId));
            } else {
                logger.error("Http {} status code when getting similar products from id {}", response.statusCode(), productId);
                return Flux.empty();
            }
        });
    }

    /**
     * Get details of product id
     * @param productId product id
     * @return product details
     */
    public Mono<ProductDetail> getProductDetail(String productId){
        return getClient().get().uri("/product/" + productId).exchangeToMono(response -> {
            if (HttpStatus.OK.equals(response.statusCode())) {
                return response.bodyToMono(ProductDetail.class);
            } else if (HttpStatus.NOT_FOUND.equals(response.statusCode())) {
                return Mono.empty();
            } else {
                logger.error("Http {} status code when getting product details from id {}", response.statusCode(), productId);
                return response.createException().flatMap(Mono::error);
            }
        });
    }




}
