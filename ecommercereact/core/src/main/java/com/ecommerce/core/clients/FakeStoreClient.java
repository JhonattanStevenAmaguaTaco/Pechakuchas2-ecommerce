package com.ecommerce.core.clients;

import java.util.List;

import com.ecommerce.core.models.ProductModel;

public interface FakeStoreClient {
   List<ProductModel>fetchAllProducts(); 
}
