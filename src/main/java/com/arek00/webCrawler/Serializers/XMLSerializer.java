package com.arek00.webCrawler.Serializers;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.*;
import java.nio.Buffer;


public class XMLSerializer implements ISerializer {

    private Serializer serializer;

    public XMLSerializer() {
        this.serializer = new Persister();
    }

    public void serialize(Object objectToSerialize, File file) throws Exception {
        serializer.write(objectToSerialize, file);
    }

    public <T> T deserialize(Class<T> objectClass, File source) throws Exception {
        return serializer.read(objectClass, source);
    }

    public <T> T deserialize(Class<T> objectClass, InputStream source) throws Exception {
        return serializer.read(objectClass, source);
    }

}
