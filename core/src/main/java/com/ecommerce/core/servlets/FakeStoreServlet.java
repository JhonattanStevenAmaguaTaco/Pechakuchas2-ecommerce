package com.ecommerce.core.servlets;


import com.ecommerce.core.models.ProductModel;
import com.ecommerce.core.services.FakeStoreService;
import com.google.gson.Gson;
import org.osgi.framework.Constants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

@Component(service = { Servlet.class }, property = {
    Constants.SERVICE_DESCRIPTION + "=Servlet para obtener datos de FakeStore API y generar Content Fragments",

    "sling.servlet.methods=GET, POST",
    "sling.servlet.paths=/bin/store/product"
})
public class FakeStoreServlet extends SlingAllMethodsServlet {
    @Reference
    private FakeStoreService storeService;
    private final Gson gson = new Gson();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        List<ProductModel> allProducts=storeService.getAllProductModels();
        if (allProducts != null && !allProducts.isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(allProducts));
        } else {

            response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Personaje no encontrado.");
        }
    }
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        List<ProductModel> allProducts = storeService
                .createAllProductsContentFragments(request.getResourceResolver());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(allProducts));
    }

}
