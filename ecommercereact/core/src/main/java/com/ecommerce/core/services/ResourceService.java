package com.ecommerce.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import org.apache.sling.api.resource.Resource;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import org.apache.sling.api.resource.PersistenceException;

public interface ResourceService {
    FragmentTemplate getFragmentTemplate(ResourceResolver resourceResolver, String templatePath)
            throws ContentFragmentException;

    Resource getParentResource(ResourceResolver resourceResolver, String resourcePath) throws PersistenceException;
}
