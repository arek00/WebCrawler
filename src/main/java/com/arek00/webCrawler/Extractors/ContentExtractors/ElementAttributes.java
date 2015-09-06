package com.arek00.webCrawler.Extractors.ContentExtractors;

import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Element;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Value object for ContentExtractor
 */

public class ElementAttributes {
    @Element private String element;
    @Element private String attribute;
    @Element private String attributeValue;

    public ElementAttributes(String element, String attribute, String attributeValue) {
        ObjectValidator.nullPointerValidate(element, attribute, attributeValue);

        this.element = element;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
    }

    /**
     * Mandatory framework implementation
     */

    public ElementAttributes() {
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
