package com.ecommerce.core.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.ecommerce.core.models.ProductModel;

public interface StoreService {
    List<ProductModel> getAllProductModels();
    List<ProductModel> createAllProductsContentFragments(ResourceResolver resourceResolver);
}
