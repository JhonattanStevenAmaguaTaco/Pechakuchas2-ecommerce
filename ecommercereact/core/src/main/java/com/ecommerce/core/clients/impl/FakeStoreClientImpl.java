package com.ecommerce.core.clients.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;

import com.ecommerce.core.clients.FakeStoreClient;
import com.ecommerce.core.exceptions.Errors;
import com.ecommerce.core.models.ProductModel;
@Slf4j
@Component(service = FakeStoreClient.class)
public class FakeStoreClientImpl implements FakeStoreClient {
    private static final String FAKE_STRORE_API_URL="https://fakestoreapi.com/";
    private final Gson gson = new Gson();
    @Override
    public List<ProductModel> fetchAllProducts() {
        List<ProductModel> allProducts= new ArrayList<>();
        String urlRequest = FAKE_STRORE_API_URL+"products";
        try {
            HttpClient client= HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlRequest)).GET().build();
            HttpResponse <String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode()== HttpURLConnection.HTTP_OK){
                Type productListType = new TypeToken<List<ProductModel>>() {}.getType();
                allProducts= gson.fromJson(response.body(), productListType);
            }else{
                log.error(Errors.API_RESPONSE_ERROR + ", status: " + response.statusCode());
            }
            return allProducts;
        } catch (IOException | InterruptedException e) {
            log.error(Errors.API_REFUSED_CONNECTION, e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }
    
}
