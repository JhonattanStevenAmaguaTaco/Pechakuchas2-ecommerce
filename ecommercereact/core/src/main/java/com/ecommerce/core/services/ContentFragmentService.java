package com.ecommerce.core.services;

import com.adobe.cq.dam.cfm.FragmentTemplate;
import org.apache.sling.api.resource.Resource;
import com.adobe.cq.dam.cfm.ContentFragmentException;

import com.adobe.cq.dam.cfm.ContentFragment;

//import com.ecommerce.core.models.StarWarsCharacterModel;

public interface ContentFragmentService {
    ContentFragment createContentFragment(FragmentTemplate fragmentTemplate, Resource parentResource, String name, String title, String description) 
            throws ContentFragmentException;
    void setFragmentElement(ContentFragment contentFragment, String elementName, String content) throws ContentFragmentException;
}
