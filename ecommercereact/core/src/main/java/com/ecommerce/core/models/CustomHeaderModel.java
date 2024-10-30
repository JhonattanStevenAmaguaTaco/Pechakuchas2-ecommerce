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
        resourceType = CustomHeaderModel.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CustomHeaderModel implements ComponentExporter {
    protected static final String RESOURCE_TYPE = "ecommercereact/components/customheader";

    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    protected String title;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    protected String pageUrl;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    protected String pageName;
    @ValueMapValue(injectionStrategy=InjectionStrategy.OPTIONAL)
    protected String fileReference;

    public String getTitle() {
        return title;
    }
    public String gePagetUrl() {
        return pageUrl;
    }
    public String gePageName() {
        return pageName;
    }
    public String getFileReference() {
        return fileReference;
    }

    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
