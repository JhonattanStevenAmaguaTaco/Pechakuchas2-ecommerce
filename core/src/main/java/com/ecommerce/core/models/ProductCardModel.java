package com.ecommerce.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import com.adobe.cq.export.json.ComponentExporter;
import org.apache.sling.models.annotations.Exporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = {ComponentExporter.class},
        resourceType = ProductCardModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ProductCardModel implements ComponentExporter {
    protected static final String RESOURCE_TYPE = "ecommercereact/components/productcard";

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String price;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String description;

    
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String imageUrl;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String buttonText;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String labelSale;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String isUsingTheSameString;

    private String defaultButtonText = "SHOP NOW";
    private String defaultLabelSaleText = "SHOP NOW";
    
       
    public String getTitle() {
        return title;
    }
    public String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getButtonText() {
        if(buttonText==null){
            buttonText=defaultButtonText;
        }
        return buttonText;
    }
    public String getLabelSale(){
        if(labelSale==null){
            labelSale=defaultLabelSaleText;
        }
        return labelSale;
    }
    public String getIsUsingTheSameString(){
        isUsingTheSameString= ("Hash code:\n"+"LabelSale: "+ System.identityHashCode(labelSale)+ "\nButtonText:"+ System.identityHashCode(buttonText));
        return isUsingTheSameString;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
