package com.ecommerce.core.services.impl;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
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
import com.ecommerce.core.services.FakeStoreService;
import org.apache.sling.api.resource.ResourceResolver;

@Component(service = FakeStoreService.class)
public class FakeStoreServiceImpl implements FakeStoreService {
    public static final Logger logger = LoggerFactory.getLogger(FakeStoreServiceImpl.class);

    @Reference
    private FakeStoreClient fakeStoreClient;

    @Reference
    private ContentFragmentService contentFragmentService;
    @Reference
    private ResourceService resourceService;
    
    private static final String PRODUCTS_CF_TEMPLATE_PATH = "/conf/ecommercereact/settings/dam/cfm/models/productcfmodel";
    private static final String PRODUCTS_CONTENT_FRAGMENT_FOLDER = "/content/dam/ecommercereact/en/products";

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

        if (allProducts!= null) {
            try {
                for (ProductModel product : allProducts) {
                    try {
                        FragmentTemplate fragmentTemplate = resourceService.getFragmentTemplate(resourceResolver,
                                PRODUCTS_CF_TEMPLATE_PATH);
                        Resource parentResource = resourceService.getParentResource(resourceResolver,
                                PRODUCTS_CONTENT_FRAGMENT_FOLDER);
                        String sanitizedTitle =sanitizeName(product.getTitle());
                        ContentFragment newFragment = contentFragmentService.createContentFragment(
                                fragmentTemplate,
                                parentResource,
                                sanitizedTitle,
                                product.getTitle(),
                                product.getDescription());
                        RatingModel ratingModel= new RatingModel();
                        ratingModel=product.getRating();
                        
                        contentFragmentService.setFragmentElement(newFragment, "id",
                                String.valueOf(product.getId()));
                        contentFragmentService.setFragmentElement(newFragment, "title", product.getTitle());
                        contentFragmentService.setFragmentElement(newFragment, "image", product.getImage());
                        contentFragmentService.setFragmentElement(newFragment, "description", product.getDescription());
                        contentFragmentService.setFragmentElement(newFragment, "category", product.getCategory());
                        contentFragmentService.setFragmentElement(newFragment, "price", String.valueOf(product.getPrice()));
                        contentFragmentService.setFragmentElement(newFragment, "rate",String.valueOf(ratingModel.getRate()));
                        contentFragmentService.setFragmentElement(newFragment, "count",String.valueOf(ratingModel.getCount()));

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
    private String sanitizeName(String name) {
        return name.replaceAll("[%^\\\\*\"<>\\[\\]{}#|+]", "-").replaceAll("\\s+", "-").trim();
    }
    
}
