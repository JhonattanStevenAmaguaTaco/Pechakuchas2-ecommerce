package com.ecommerce.core.services.impl;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.ecommerce.core.exceptions.ArgumentedException;
import com.ecommerce.core.exceptions.Errors;
import com.ecommerce.core.services.ResourceService;

@Component(service = ResourceService.class)
public class ResourceServiceImpl implements ResourceService {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public FragmentTemplate getFragmentTemplate(ResourceResolver resourceResolver, String templatePath)
            throws ContentFragmentException {
        Resource templateResource = resourceResolver.getResource(templatePath);
        if (templateResource == null) {
            throw new ArgumentedException(Errors.INVALID_ROUTE.toString() + templatePath);
        }
        FragmentTemplate fragmentTemplate = templateResource.adaptTo(FragmentTemplate.class);
        if (fragmentTemplate == null) {
            throw new ArgumentedException(Errors.RESOURCE_ADAPTATION_FAILED.toString());
        }
        return fragmentTemplate;
    }

    @Override
    public Resource getParentResource(ResourceResolver resourceResolver, String resourcePath)
            throws PersistenceException {
        Resource parentResource = resourceResolver.getResource(resourcePath);
        if (parentResource == null) {
            throw new ArgumentedException(
                    Errors.RESOURCE_NOT_FOUND.toString() + "RECURSP PADRE EN" + resourcePath + "Fin error");
        }
        return parentResource;
    }

    public void deleteContentFragment(ResourceResolver resourceResolver, String folderpath, String fragmentName)
            throws PersistenceException {

        String fragmentPath = folderpath + fragmentName;
        Resource fragmentResource = resourceResolver.getResource(fragmentPath);

        if (fragmentResource != null) {
            try {
                resourceResolver.delete(fragmentResource);

                resourceResolver.commit();

            } catch (PersistenceException e) {

                throw e;
            }
        } else {
            throw new ArgumentedException(Errors.RESOURCE_NOT_FOUND.toString() + fragmentPath);
        }
    }
}
