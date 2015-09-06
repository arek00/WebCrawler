package com.arek00.webCrawler.Extractors.ContentExtractors;

import com.arek00.webCrawler.Validators.ObjectValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Simple extractor that extract text from given tag element of page.
 * <p>
 * This extractor get ONLY text from inside tag/element set in element variable, with given attribute and attribute value.
 * <code>
 * <element attribute="attributeValue"> some text </element>
 * </code>
 * <p>
 * Example below illustrate working of this class:
 * <p>
 * <code>
 * <div class="content">
 * <p> Some text</p>
 * </div>
 * </code>
 * <p>
 * For element = "div", attribute = "class" and attributeValue = "content"
 * The result of extractContent will be "Some text".
 */

public class SimpleContentExtractor implements IContentExtractor {

    private Document page;
    @org.simpleframework.xml.Element private ElementAttributes attributes;


    public SimpleContentExtractor(ElementAttributes attributes) {
        ObjectValidator.nullPointerValidate(attributes);

        this.attributes = attributes;
    }

    public String extractContent(String htmlCode) {
        ObjectValidator.nullPointerValidate(htmlCode);

        this.page = Jsoup.parse(htmlCode);
        String pattern = doGetPattern(attributes);
        String content = doGetContent(pattern);

        return content;
    }

    public boolean containsContent(String htmlCode) {
        ObjectValidator.nullPointerValidate(htmlCode);

        this.page = Jsoup.parse(htmlCode);
        return !page.select(doGetPattern(attributes)).isEmpty();
    }

    private String doGetPattern(ElementAttributes attributes) {
        String pattern = "";

        if (attributes.getAttribute().isEmpty()) {
            pattern = attributes.getElement();
        } else {
            pattern = String.format("%s[%s=\"%s\" ",
                    attributes.getElement(), attributes.getAttribute(), attributes.getAttributeValue());
        }

        return pattern;
    }

    private String doGetContent(String pattern) {
        Elements elements = page.select(pattern);
        String content = "";

        for (Element element : elements) {
            content = content + element.text();
        }

        return content;
    }

    /**
     * Framework mandatory implementation
     */

    public SimpleContentExtractor() {
    }

    public ElementAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ElementAttributes attributes) {
        this.attributes = attributes;
    }
}
