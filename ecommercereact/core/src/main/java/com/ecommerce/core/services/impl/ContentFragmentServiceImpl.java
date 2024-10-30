package com.ecommerce.core.services.impl;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentFragmentException;
import com.adobe.cq.dam.cfm.FragmentTemplate;
import com.ecommerce.core.services.ContentFragmentService;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

@Component(service = ContentFragmentService.class)
public class ContentFragmentServiceImpl implements ContentFragmentService {

    @Override
    public ContentFragment createContentFragment(FragmentTemplate fragmentTemplate, Resource parentResource,
            String name, String title, String description)
            throws ContentFragmentException {

        ContentFragment newFragment = fragmentTemplate.createFragment(parentResource,
                name,
                title);

        if (newFragment == null) {
            throw new ContentFragmentException(
                    "No se pudo crear el Content Fragment." + name + " " + title + " " + parentResource.toString());
        }

        newFragment.setDescription(description);

        return newFragment;
    }

    @Override
    public void setFragmentElement(ContentFragment contentFragment, String elementName, String content)
            throws ContentFragmentException {
        contentFragment.getElement(elementName).setContent(content, "text/plain");
    }
}
