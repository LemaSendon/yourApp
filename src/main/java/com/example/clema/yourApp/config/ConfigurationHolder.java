package com.example.clema.yourApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationHolder {

    @Value("${client.mocks.base-url}")
    private String clientMocksBaseUrl;

    public String getClientMocksBaseUrl() {
        return clientMocksBaseUrl;
    }

    public void setClientMocksBaseUrl(String clientMocksBaseUrl) {
        this.clientMocksBaseUrl = clientMocksBaseUrl;
    }
}
