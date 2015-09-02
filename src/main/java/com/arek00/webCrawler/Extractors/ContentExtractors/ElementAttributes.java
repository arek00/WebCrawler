package com.arek00.webCrawler.Extractors.ContentExtractors;

import com.arek00.webCrawler.Validators.ObjectValidator;

/**
 * Value object for ContentExtractor
 */
public class ElementAttributes {
    private String element;
    private String attribute;
    private String attributeValue;

    public ElementAttributes(String element, String attribute, String attributeValue) {
        ObjectValidator.nullPointerValidate(element, attribute, attributeValue);


        this.element = element;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
    }


    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getAttribute() {
        return attribute;
    }

    /**
     * When need to be set element without attibute, set empty String.
     *
     * @param attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
