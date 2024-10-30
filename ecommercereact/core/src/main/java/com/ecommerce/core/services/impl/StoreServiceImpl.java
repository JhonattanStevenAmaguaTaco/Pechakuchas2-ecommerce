package com.ecommerce.core.services.impl;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.ecommerce.core.clients.FakeStoreClient;
import com.ecommerce.core.exceptions.ArgumentedException;
import com.ecommerce.core.exceptions.Errors;
import com.ecommerce.core.models.ProductModel;
import com.ecommerce.core.models.RatingModel;
import com.ecommerce.core.services.ContentFragmentService;
import com.ecommerce.core.services.ResourceService;
import com.ecommerce.core.services.StoreService;
import org.apache.sling.api.resource.ResourceResolver;


public class StoreServiceImpl implements StoreService {
    public static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    @Reference
    private FakeStoreClient fakeStoreClient;

    @Reference
    private ContentFragmentService contentFragmentService;
    @Reference
    private ResourceService resourceService;
    
    private static final String PRODUCTS_CF_TEMPLATE_PATH = "/conf/e-commerce-react/settings/dam/cfm/models/productcfmodel";
    private static final String PRODUCTS_CONTENT_FRAGMENT_FOLDER = "/content/dam/e-commerce-react/e-commerce-cf/en/product";

    @Override
    public List<ProductModel> getAllProductModels() {
        List<ProductModel> productModelsApiResponse= fakeStoreClient.fetchAllProducts();
        if (productModelsApiResponse!=null && !productModelsApiResponse.isEmpty()) {
            return productModelsApiResponse;
        }
       return Collections.emptyList();
    }

    @Override
    public List<ProductModel> createAllProductsContentFragments(ResourceResolver resourceResolver) {
        List<ProductModel> allProducts= getAllProductModels();
        int batchSize = 10;
        int count = 0;
        if (allProducts!= null) {
            try {
                for (ProductModel product : allProducts) {
                    try {
                        FragmentTemplate fragmentTemplate = resourceService.getFragmentTemplate(resourceResolver,
                                PRODUCTS_CF_TEMPLATE_PATH);
                        Resource parentResource = resourceService.getParentResource(resourceResolver,
                                PRODUCTS_CONTENT_FRAGMENT_FOLDER);

                        ContentFragment newFragment = contentFragmentService.createContentFragment(
                                fragmentTemplate,
                                parentResource,
                                product.getTitle(),
                                product.getTitle(),
                                product.getDescription());
                        RatingModel ratingModel= new RatingModel();
                        ratingModel=product.getRating();
                        contentFragmentService.setFragmentElement(newFragment, "Id",
                                String.valueOf(product.getId()));
                        contentFragmentService.setFragmentElement(newFragment, "Title", product.getTitle());
                        contentFragmentService.setFragmentElement(newFragment, "Image", product.getImage());
                        contentFragmentService.setFragmentElement(newFragment, "Description", product.getCategory());
                        contentFragmentService.setFragmentElement(newFragment, "Category", product.getCategory());
                        contentFragmentService.setFragmentElement(newFragment, "Price", String.valueOf(product.getPrice()));
                        contentFragmentService.setFragmentElement(newFragment, "Rate",String.valueOf(ratingModel.getRate()));
                        contentFragmentService.setFragmentElement(newFragment, "Count",String.valueOf(ratingModel.getCount()));


                        count++;
                        if (count % batchSize == 0) {
                            resourceResolver.commit();
                        }

                    } catch (Exception e) {
                        logger.error(Errors.CF_NOT_CREATED + product.getId());
                        throw new ArgumentedException(Errors.CF_NOT_CREATED + product.getId());
                    }
                }

                resourceResolver.commit();
            } catch (Exception e) {
                resourceResolver.revert();
                throw new ArgumentedException(Errors.CF_NOT_CREATED + e);
            } finally {
                if (resourceResolver != null && resourceResolver.isLive()) {
                    resourceResolver.close();
                }
            }

        }
        return allProducts;       
    }
    
}
